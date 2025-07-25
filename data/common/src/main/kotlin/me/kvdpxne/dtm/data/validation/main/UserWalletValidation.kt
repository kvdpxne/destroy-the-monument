package me.kvdpxne.dtm.data.validation.main

import java.util.UUID
import me.kvdpxne.dtm.data.EntityFieldNames
import me.kvdpxne.dtm.data.EntityNames
import me.kvdpxne.dtm.data.raw.RawUserWallet
import me.kvdpxne.dtm.data.validation.ValidationResult
import me.kvdpxne.dtm.data.validation.ValidationResultBuilder
import me.kvdpxne.dtm.data.validation.codes.StandardCodes
import me.kvdpxne.dtm.data.validation.codes.WalletCodes
import me.kvdpxne.dtm.data.validation.common.isUuidValid
import me.kvdpxne.dtm.data.validation.rules.WalletRules
import me.kvdpxne.dtm.data.validation.withValidationBuilder

/**
 * Validates a user's coin balance according to wallet rules.
 *
 * ### Validation Rules
 * - Coin balance must be between [WalletRules.MIN_COINS] and [WalletRules.MAX_COINS]
 * - Both inclusive boundaries are valid
 *
 * @param coins The coin balance to validate
 * @return `true` if coins are within valid range, `false` otherwise
 * @since 0.1.0
 */
fun isUserWalletCoinsValid(
  coins: Long
): Boolean {
  return coins in WalletRules.MIN_COINS..WalletRules.MAX_COINS
}

/**
 * Validates a user's coin multiplier according to wallet rules.
 *
 * ### Validation Rules
 * - Exactly 0.0F is always valid
 * - Non-zero multipliers must be in range:
 *   - Lower bound: Greater than [WalletRules.MIN_MULTIPLIER] - 0.001F
 *   - Upper bound: Less than or equal to [WalletRules.MAX_MULTIPLIER]
 *
 * This accounts for floating-point precision while maintaining:
 * - Minimum practical value: 0.01F
 * - Maximum value: 10000.00F
 *
 * @param multiplier The multiplier value to validate
 * @return `true` if multiplier is valid, `false` otherwise
 * @since 0.1.0
 */
fun isUserWalletMultiplierValid(
  multiplier: Float
): Boolean {
  return WalletRules.MIN_MULTIPLIER_INCLUSIVE_ZERO == multiplier
    || WalletRules.MIN_MULTIPLIER - .001F < multiplier
    && WalletRules.MAX_MULTIPLIER >= multiplier
}

/**
 * @param identifier
 * @param coins
 * @param multiplier
 * @param infinite
 * @param locked
 *
 * @since 0.1.0
 */
fun validateUserWallet(
  identifier: UUID?,
  coins: Long,
  multiplier: Float,
  infinite: Boolean,
  locked: Boolean
): ValidationResult = withValidationBuilder { builder: ValidationResultBuilder ->
  if (!isUuidValid(identifier)) {
    builder.addError(
      EntityFieldNames.IDENTIFIER,
      "The primary key of the user's wallet is invalid.",
      StandardCodes.INVALID_PRIMARY_KEY,
      identifier
    )
  }

  if (!isUserWalletCoinsValid(coins)) {
    builder.addError(
      EntityFieldNames.COINS,
      "",
      WalletCodes.INVALID_COINS,
      coins
    )
  }

  if (!isUserWalletMultiplierValid(multiplier)) {
    builder.addError(
      EntityFieldNames.MULTIPLIER,
      "",
      WalletCodes.INVALID_MULTIPLIER,
      multiplier
    )
  }

  builder.build()
}

/**
 * Validates a complete user wallet object.
 *
 * Performs the following checks in order:
 * 1. Null reference check
 * 2. Coin balance validation
 * 3. Multiplier validation
 *
 * ### Validation Flow
 * - If any check fails, returns immediately with specific error code
 * - Only returns [StandardCodes.EVERYTHING_OK] if all checks pass
 *
 * @param userWallet The wallet object to validate
 * @return Validation result code:
 * - [StandardCodes.EVERYTHING_OK] if valid
 * - [StandardCodes.INVALID_REFERENCE] if null
 * - [WalletCodes.INVALID_COINS] for invalid coin balance
 * - [WalletCodes.INVALID_MULTIPLIER] for invalid multiplier
 *
 * @see isUserWalletCoinsValid
 * @see isUserWalletMultiplierValid
 * @since 0.1.0
 */
fun validateUserWallet(
  userWallet: RawUserWallet?
): ValidationResult = userWallet?.run {
  validateUserWallet(
    this.identifier,
    this.coins,
    this.multiplier,
    this.infinite,
    this.locked,
  )
} ?: withValidationBuilder { builder: ValidationResultBuilder ->
  builder.addError(
    EntityNames.USER_WALLET,
    "The user's raw wallet object cannot be null.",
    StandardCodes.INVALID_REFERENCE
  ).build()
}