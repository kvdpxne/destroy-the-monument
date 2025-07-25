package me.kvdpxne.dtm.data.validation.common

import me.kvdpxne.dtm.data.validation.rules.NamingRules

/**
 * Validates a name string against configurable naming requirements.
 *
 * Checks three fundamental conditions: the name must exist (non-null),
 * its length must fit within specified boundaries (standard or custom), and
 * all characters must belong to an allowed set. By default uses [NamingRules]
 * constraints: length between [NamingRules.MIN_NAME_LENGTH] and [NamingRules.MAX_NAME_LENGTH],
 * accepting only [NamingRules.ALLOWED_CHARACTERS].
 *
 * @param name The string to validate (can be null)
 * @param lengthRange Optional custom length limits (defaults to standard naming rules)
 * @param characters Optional allowed characters array (defaults to [NamingRules.ALLOWED_CHARACTERS])
 * @return True if all validation conditions pass: name exists, has valid length, and contains only
 *         allowed characters; false otherwise
 * @since 0.1.0
 */
fun isNameValid(
  name: String?,
  lengthRange: IntRange = NamingRules.MIN_NAME_LENGTH..NamingRules.MAX_NAME_LENGTH,
  characters: CharArray = NamingRules.ALLOWED_CHARACTERS
): Boolean {
  if (null == name || name.length !in lengthRange) {
    return false
  }

  for (character in name.toCharArray()) {
    if (character !in characters) {
      return false
    }
  }

  return true
}