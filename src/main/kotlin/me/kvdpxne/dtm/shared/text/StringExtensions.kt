package me.kvdpxne.dtm.shared.text

import me.kvdpxne.dtm.PluginContext.textFormatter

val String.colorize: String
  get() = textFormatter.format(this)

val Array<String>.colorize: Array<String>
  get() = textFormatter.format(this)

val Collection<String>.colorize: Collection<String>
  get() = textFormatter.format(this)