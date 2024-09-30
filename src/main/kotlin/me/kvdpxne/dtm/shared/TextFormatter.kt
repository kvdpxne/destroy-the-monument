package me.kvdpxne.dtm.shared

interface TextFormatter {

  fun format(text: String): String

  fun format(texts: Array<String>): Array<String>

  fun format(texts: Collection<String>): Collection<String>
}