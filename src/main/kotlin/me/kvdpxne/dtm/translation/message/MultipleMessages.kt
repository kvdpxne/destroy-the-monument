package me.kvdpxne.dtm.translation.message

import me.kvdpxne.dtm.translation.formatter.Formatter

/**
 * @since 0.1.0
 */
class MultipleMessages(
  override var content: Array<String>
) : Message<Array<String>> {

  /**
   * @since 0.1.0
   */
  override val isArray: Boolean
    get() = true

  /**
   * @since 0.1.0
   */
  override fun format(
    formatter: Formatter
  ) {
    for ((field: String, value: String) in formatter.replaceable) {
      var i = 0
      for (contentLine: String in this.content) {
        this.content[++i] = contentLine.replace("{$field}", value)
      }
    }
  }
}