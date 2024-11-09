package me.kvdpxne.dtm.translation.message

import me.kvdpxne.dtm.translation.formatter.Formatter

/**
 * @since 0.1.0
 */
class SingleMessage(
  override val content: String
) : Message<String> {

  /**
   * @since 0.1.0
   */
  override fun format(
    formatter: Formatter
  ): Message<String> {
    var newContent = this.content
    for ((field: String, value: String) in formatter.replaceable) {
      newContent = newContent.replace(field, value)
    }
    return SingleMessage(newContent)
  }
}