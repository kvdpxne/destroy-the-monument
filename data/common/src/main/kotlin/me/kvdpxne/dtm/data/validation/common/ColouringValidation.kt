package me.kvdpxne.dtm.data.validation.common

import me.kvdpxne.dtm.ChangesByVersion
import me.kvdpxne.notchity.MinecraftVersionCreator

/**
 * Checks whether a character is a valid hexadecimal digit used in color codes.
 *
 * Hexadecimal digits include numbers 0-9 and letters A-F (case-insensitive).
 * This format is commonly seen in web color codes and other technical systems.
 *
 * @return True if the character is a valid hex digit (0-9, A-F, a-f), false otherwise.
 * @since 0.1.0
 */
private fun Char.isHexDigit(): Boolean {
  return this in '0'..'9'
    || this in 'A'..'F'
    || this in 'a'..'f'
}

/**
 * Validates standard hexadecimal color codes like those used in HTML/CSS.
 *
 * Accepts both short form (#ABC equivalent to #AABBCC) and long form (#FF5733).
 * The code must start with '#' followed by 3 or 6 hexadecimal characters.
 *
 * @param color Color string to validate (can be null)
 * @return True if the string matches the hex color format, false otherwise
 * @since 0.1.0
 */
fun isHexColorValid(
  color: String?
): Boolean {
  if (null == color) {
    return false
  }

  val size: Int = color.length
  if (4 != size && 7 != size) {
    return false
  }

  return when (color[0]) {
    '#' -> (1 until size).all { n: Int -> color[n].isHexDigit() }
    else -> false
  }
}

/**
 * Validates Minecraft-style legacy color codes with prefix symbols.
 *
 * Checks for 2-character codes starting with '&' or '§' followed by a hex digit.
 * Common in Minecraft formatting (e.g., "&c" for red text).
 *
 * @param color Color string to validate (can be null)
 * @return True if the string matches Minecraft's legacy color format, false otherwise
 * @since 0.1.0
 */
fun isMinecraftLegacyColorValid(
  color: String?
): Boolean {
  if (null == color || 2 != color.length) {
    return false
  }

  return when (color[0]) {
    '&', '\u00A7' -> color[1].isHexDigit()
    else -> false
  }
}

/**
 * Validates color codes compatible with Minecraft's version-specific formatting rules.
 *
 * Supports modern hex colors (#RRGGBB) in newer versions while maintaining
 * compatibility with legacy codes (&c, §a) in older versions.
 *
 * @param color Color string to validate (can be null)
 * @return True if the color matches valid format for current Minecraft version, false otherwise
 * @since 0.1.0
 */
fun isMinecraftColorValid(
  color: String?
): Boolean {
  if (null == color) {
    return false
  }

  val version: Int = MinecraftVersionCreator.getMinecraftVersion().number
  return if (ChangesByVersion.COLOR_CODES <= version) {
    isHexColorValid(color) || isMinecraftLegacyColorValid(color)
  } else {
    isMinecraftLegacyColorValid(color)
  }
}