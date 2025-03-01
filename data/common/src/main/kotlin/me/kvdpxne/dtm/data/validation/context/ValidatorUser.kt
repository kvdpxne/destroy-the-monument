package me.kvdpxne.dtm.data.validation.context

import java.util.Locale
import me.kvdpxne.dtm.data.raw.RawUser
import me.kvdpxne.dtm.data.shared.isPositive
import me.kvdpxne.dtm.data.validation.EVERYTHING_OK
import me.kvdpxne.dtm.data.validation.INVALID_REFERENCE
import me.kvdpxne.dtm.data.validation.INVALID_USER_DISPLAY_NAME
import me.kvdpxne.dtm.data.validation.INVALID_USER_LOCALIZATION
import me.kvdpxne.dtm.data.validation.INVALID_USER_NAME
import me.kvdpxne.dtm.data.validation.LEGAL_NAME_CHARACTERS
import me.kvdpxne.dtm.data.validation.context.extensions.validate

/**
 * @since 0.1.0
 */
const val MIN_USER_NAME_LENGTH = 3

/**
 * @since 0.1.0
 */
const val MAX_USER_NAME_LENGTH = 16

/**
 * @since 0.1.0
 */
fun isUserNameValid(
  name: String?
): Boolean {
  if (null == name) {
    return false
  }

  if (name.length !in MIN_USER_NAME_LENGTH..MAX_USER_NAME_LENGTH) {
    return false
  }

  for (character in name.toCharArray()) {
    if (character !in LEGAL_NAME_CHARACTERS) {
      return false
    }
  }

  return true
}

/**
 * @since 0.1.0
 */
fun isUserDisplayNameValid(
  displayName: String?
): Boolean {
  if (null == displayName) {
    return true
  }

  if (displayName.length !in MIN_USER_NAME_LENGTH..MAX_USER_NAME_LENGTH) {
    return false
  }

  for (character in displayName.toCharArray()) {
    if (character !in LEGAL_NAME_CHARACTERS) {
      return false
    }
  }

  return true
}

/**
 * @since 0.1.0
 */
fun isUserLocalizationValid(
  localization: String?
): Boolean {
  if (null == localization) {
    return false
  }

  try {
    val locale = Locale.forLanguageTag(localization.replace("_", "-"))
    return locale.toLanguageTag().isNotEmpty()
  } catch (_: Exception) {
    return false
  }
}

/**
 * @since 0.1.0
 */
fun isUserValid(
  user: RawUser?
): Int {
  if (null == user) {
    return INVALID_REFERENCE
  }

  // The result of validation of objects related to the user object.
  @Suppress("JoinDeclarationAndAssignment")
  var result: Int

  result = user.statistics.validate()
  if (EVERYTHING_OK != result) {
    return result
  }

  result = user.wallet.validate()
  if (EVERYTHING_OK != result) {
    return result
  }

  if (!isUserNameValid(user.name)) {
    return INVALID_USER_NAME
  }

  if (!isUserDisplayNameValid(user.displayName)) {
    return INVALID_USER_DISPLAY_NAME
  }

  if (!isUserLocalizationValid(user.locale)) {
    return INVALID_USER_LOCALIZATION
  }

  return EVERYTHING_OK
}