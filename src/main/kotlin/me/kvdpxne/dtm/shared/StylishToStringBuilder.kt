package me.kvdpxne.dtm.shared

/**
 * A utility class for building customizable, stylish `toString` representations of objects.
 *
 * This builder constructs a structured string in the format:
 * `ClassName{propertyName="propertyValue", ... }`.
 *
 * @since 0.1.0
 */
class StylishToStringBuilder : Buildable<String> {

  /**
   * Internal `StringBuilder` used to assemble the `toString` output.
   * Initialized with a capacity of 512 for performance efficiency.
   *
   * @since 0.1.0
   */
  private val stringBuilder: StringBuilder = StringBuilder(512)

  /**
   * Starts the building process by appending the class or object name.
   *
   * @param name The name of the class or object to display at the beginning of the string.
   * @return The `StylishToStringBuilder` instance for chaining.
   * @since 0.1.0
   */
  fun begin(
    name: String
  ): StylishToStringBuilder {
    this.stringBuilder.append("$name{")
    return this
  }

  /**
   * Adds a named property and its value to the output.
   *
   * @param name The name of the property.
   * @param value The value of the property (nullable). If `null`, the value is represented as `"null"`.
   * @return The `StylishToStringBuilder` instance for chaining.
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
   * Finalizes and returns the constructed `toString` representation.
   *
   * @return The complete `toString` representation as a `String`.
   * @since 0.1.0
   */
  override fun build(): String {
    return this.stringBuilder
      .insert(this.stringBuilder.lastIndex, '}')
      .toString()
  }
}