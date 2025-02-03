package me.kvdpxne.dtm.shared.language

object LazyStringHolder {

  /**
   * @since 0.1.0
   */
  val SINGLE_LINE_REGEX: Regex by lazy {
    Regex("(\n*)\n")
  }
}