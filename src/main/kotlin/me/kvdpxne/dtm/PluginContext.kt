package me.kvdpxne.dtm

import me.kvdpxne.dtm.PluginContext.textFormatter
import me.kvdpxne.dtm.shared.TextFormatter

object PluginContext {

  lateinit var textFormatter: TextFormatter
}

fun String.colorize(): String = textFormatter.format(this)

fun Array<String>.colorizeAll(): List<String> = map { it.colorize() }

fun <T> T.colorizeAll(): List<String>
  where T : Iterable<String> = map { it.colorize() }