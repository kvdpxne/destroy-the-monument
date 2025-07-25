package me.kvdpxne.dtm.data.validation.main

import java.util.Locale
import java.util.UUID
import me.kvdpxne.dtm.data.EntityFieldNames
import me.kvdpxne.dtm.data.EntityNames
import me.kvdpxne.dtm.data.raw.RawUser
import me.kvdpxne.dtm.data.raw.RawUserStatistics
import me.kvdpxne.dtm.data.raw.RawUserWallet
import me.kvdpxne.dtm.data.validation.BasicValidationFactoryPool
import me.kvdpxne.dtm.data.validation.ReentrantValidationFactoryPool
import me.kvdpxne.dtm.data.validation.ValidationResult
import me.kvdpxne.dtm.data.validation.ValidationResultBuilder
import me.kvdpxne.dtm.data.validation.codes.StandardCodes
import me.kvdpxne.dtm.data.validation.codes.StatisticsCodes
import me.kvdpxne.dtm.data.validation.codes.UserCodes
import me.kvdpxne.dtm.data.validation.codes.WalletCodes
import me.kvdpxne.dtm.data.validation.common.isNameValid
import me.kvdpxne.dtm.data.validation.common.isUuidValid
import me.kvdpxne.dtm.data.validation.rules.UserRules
import me.kvdpxne.dtm.data.validation.withValidationBuilder

/**
 * Validates a user's localization/language tag.
 *
 * ### Validation Rules
 * - Converts underscores to hyphens (Java Locale compatibility)
 * - Must be parseable by [Locale.forLanguageTag]
 * - Must produce a non-empty language tag after parsing
 *
 * @param localization The language tag to validate (e.g., "en_US" or "pl-PL")
 * @return `true` if valid BCP 47 language tag, `false` otherwise
 * @since 0.1.0
 */
fun isUserLocalizationValid(
  localization: String?
): Boolean {
  if (null == localization) {
    return false
  }

  try {
    // Normalize to Java Locale format (en_US -> en-US)
    val locale = Locale.forLanguageTag(localization.replace("_", "-"))
    return locale.toLanguageTag().isNotEmpty()
  } catch (_: Exception) {
    return false
  }
}

fun validateUser(
  identifier: UUID?,
  statistics: RawUserStatistics?,
  wallet: RawUserWallet?,
  name: String?,
  profession: String?,
  locale: String?,
  flat: Boolean = true
): ValidationResult = withValidationBuilder(
  if (flat) BasicValidationFactoryPool else ReentrantValidationFactoryPool
) { builder: ValidationResultBuilder ->
  if (!isUuidValid(identifier)) {
    builder.addError(
      EntityFieldNames.IDENTIFIER,
      "",
      StandardCodes.INVALID_PRIMARY_KEY,
      identifier
    )
  }

  var part: ValidationResult? = null
  if (!flat) {
    part = validateUserStatistics(statistics)
      .combine(validateUserWallet(wallet))
  }

  if (!isNameValid(name, UserRules.MIN_NAME_LENGTH..UserRules.MAX_NAME_LENGTH)) {
    builder.addError(
      EntityFieldNames.NAME,
      "",
      UserCodes.INVALID_NAME,
      name
    )
  }

  if (!isUserLocalizationValid(locale)) {
    builder.addError(
      EntityFieldNames.LOCALE,
      "",
      UserCodes.INVALID_LOCALIZATION,
      locale
    )
  }

  builder.build().combine(part)
}

/**
 * Validates a complete user object.
 *
 * Performs the following checks in order:
 * 1. Null reference check
 * 2. Statistics validation
 * 3. Wallet validation
 * 4. Name validation
 * 5. Localization validation
 *
 * ### Validation Flow
 * - If any check fails, returns immediately with specific error code
 * - Only returns [StandardCodes.EVERYTHING_OK] if all checks pass
 * - Nested validations (statistics/wallet) return their own specific codes
 *
 * @param user The user object to validate
 * @return Validation result code:
 * - [StandardCodes.EVERYTHING_OK] if valid
 * - [StandardCodes.INVALID_REFERENCE] if null
 * - [StatisticsCodes.INVALID_KILLS] for invalid kills count
 * - [StatisticsCodes.INVALID_ASSISTS] for invalid assists count
 * - [StatisticsCodes.INVALID_DEATHS] for invalid deaths count
 * - [StatisticsCodes.INVALID_DESTROYED_MONUMENTS] for invalid monument count
 * - [StatisticsCodes.INVALID_PLAYED_GAMES] for invalid played games count
 * - [StatisticsCodes.INVALID_GAMES_WON] for invalid games won count
 * - [StatisticsCodes.INVALID_GAMES_LOST] for invalid games lost count
 * - [StandardCodes.EVERYTHING_OK] if valid
 * - [StandardCodes.INVALID_REFERENCE] if null
 * - [WalletCodes.INVALID_COINS] for invalid coin balance
 * - [WalletCodes.INVALID_MULTIPLIER] for invalid multiplier
 * - [UserCodes.INVALID_NAME] for invalid username
 * - [UserCodes.INVALID_LOCALIZATION] for invalid language tag
 *
 * @see validateUserStatistics
 * @see validateUserWallet
 * @see isNameValid
 * @see isUserLocalizationValid
 * @since 0.1.0
 */
fun validateUser(
  user: RawUser?,
  flat: Boolean = true
): ValidationResult {
  return user?.run {
    validateUser(
      this.identifier,
      this.statistics,
      this.wallet,
      this.name,
      this.profession,
      this.locale,
      flat
    )
  } ?: withValidationBuilder { builder ->
    builder.addError(
      EntityNames.USER,
      "",
      StandardCodes.INVALID_REFERENCE
    ).build()
  }
}