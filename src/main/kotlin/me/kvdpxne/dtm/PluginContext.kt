package me.kvdpxne.dtm

import me.kvdpxne.dtm.PluginContext.textFormatter
import me.kvdpxne.dtm.shared.TextFormatter

object PluginContext {

  lateinit var textFormatter: TextFormatter
}

val String.colorize: String
  get() = textFormatter.format(this)

val Array<String>.colorize: Array<String>
  get() = textFormatter.format(this)

val Collection<String>.colorize: Collection<String>
  get() = textFormatter.format(this)