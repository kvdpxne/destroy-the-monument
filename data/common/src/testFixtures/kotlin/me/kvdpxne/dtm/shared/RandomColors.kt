package me.kvdpxne.dtm.shared

/**
 * Generates a random RGB color in hexadecimal format (#RRGGBB).
 *
 * Example outputs: `#a3f2c4`, `#ff0033`
 *
 * @return Hexadecimal color string with 6-digit RGB representation (lowercase).
 *
 * @since 0.1.0
 * @see randomInt
 */
fun randomHexColor(): String {
  val r = randomInt(0, 255)
  val g = randomInt(0, 255)
  val b = randomInt(0, 255)

  val fr = r.toString(16).padStart(2, '0')
  val fg = g.toString(16).padStart(2, '0')
  val fb = b.toString(16).padStart(2, '0')

  return "#$fr$fg$fb"
}

/**
 * Generates a random Minecraft color code (format: `&x` where `x` is a hex digit 0-9 or a-f).
 *
 * Example outputs: `&c` (red), `&e` (yellow), `&9` (blue)
 *
 * @return Minecraft color code string prefixed with `&`.
 *
 * @since 0.1.0
 * @see randomInt
 */
fun randomMinecraftColor(): String {
  val r = randomInt(0, 15)
  return "&${r.toString(16)}"
}