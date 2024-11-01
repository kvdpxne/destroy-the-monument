package me.kvdpxne.dtm.shared.text

import me.kvdpxne.dtm.PluginContext.textFormatter

object StringHolder {
  val fs = Regex("(\n*)\n")
}

val String.colorize: String
  get() = textFormatter.format(this)

val Array<String>.colorize: Array<String>
  get() = textFormatter.format(this)

val Collection<String>.colorize: Collection<String>
  get() = textFormatter.format(this)

/**
 * https://stackoverflow.com/a/74210585
 */
fun String.toSingleLines(): String {
  return this.trimIndent().replace(StringHolder.fs, "$1")
}