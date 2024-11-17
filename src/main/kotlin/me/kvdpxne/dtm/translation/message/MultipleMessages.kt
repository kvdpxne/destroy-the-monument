package me.kvdpxne.dtm.translation.message

import me.kvdpxne.dtm.translation.formatter.Formatter

/**
 * @since 0.1.0
 */
class MultipleMessages(
  override val content: Collection<String>
) : Message<Collection<String>> {

  /**
   * @since 0.1.0
   */
  override fun format(
    formatter: Formatter
  ): Message<Collection<String>> {
    val newContent: MutableList<String> = ArrayList(this.content.size)
    for (contentLine: String in this.content) {
      var newContentLine: String = contentLine
      for ((field: String, value: String) in formatter.replaceable) {
        newContentLine = newContentLine.replace(field, value)
      }
      newContent.add(newContentLine)
    }
    return MultipleMessages(newContent)
  }
}