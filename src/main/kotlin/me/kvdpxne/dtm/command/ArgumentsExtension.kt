package me.kvdpxne.dtm.command

/**
 * Converts an array of strings into an `Arguments` object.
 *
 * This extension function provides a concise way to construct an `Arguments`
 * object from an existing array of strings.
 *
 * @receiver The array of strings to be converted (receiver of the extension
 *           function).
 * @return A new `Arguments` object containing the provided strings.
 *
 * @since 0.1.0
 */
fun Array<out String>.toArguments(from: Int = 0): Arguments {
  if (0 == from) {
    return Arguments(this)
  }
  return Arguments(this.copyOfRange(from, this.size))
}