package me.kvdpxne.dtm.shared

import me.kvdpxne.dtm.shared.ancillary.Buildable

/**
 * @since 0.1.0
 */
class StylishToStringBuilder : Buildable<String> {

  private val stringBuilder: StringBuilder = StringBuilder(512)

  /**
   * @param name
   *
   * @since 0.1.0
   */
  fun begin(
    name: String
  ): StylishToStringBuilder {
    this.stringBuilder.append("$name{")
    return this
  }

  /**
   * @param name
   * @param value
   *
   * @since 0.1.0
   */
  fun add(
    name: String,
    value: Any?
  ): StylishToStringBuilder {
    this.stringBuilder.append("$name=").append("\"${value.toString()}\",")
    return this
  }

  /**
   * @since 0.1.0
   */
  override fun build(): String {
    return this.stringBuilder
      .insert(this.stringBuilder.lastIndex, '}')
      .toString()
  }
}