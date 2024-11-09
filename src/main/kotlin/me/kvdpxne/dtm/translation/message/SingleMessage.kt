package me.kvdpxne.dtm.translation.message

import me.kvdpxne.dtm.translation.formatter.Formatter

/**
 * @since 0.1.0
 */
class SingleMessage(
  override var content: String
) : Message<String> {

  /**
   * @since 0.1.0
   */
  override val isArray: Boolean
    get() = false

  /**
   * @since 0.1.0
   */
  override fun format(
    formatter: Formatter
  ) {
    for ((field: String, value: String) in formatter.replaceable) {
      this.content = this.content.replace("{$field}", value)
    }
  }
}