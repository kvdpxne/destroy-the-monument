package me.kvdpxne.dtm.shared

/**
 * A utility class for building customizable, stylish `toString`
 * representations of objects.
 *
 * This builder constructs a structured string in the format:
 * `ClassName{propertyName="propertyValue", ... }`.
 *
 * @since 0.1.0
 */
class StylishToStringBuilder : Buildable<String> {

  /**
   * Internal [StringBuilder] used to assemble the `toString` output.
   *
   * Initialized with a capacity of `512` for performance efficiency.
   *
   * @since 0.1.0
   */
  private val stringBuilder: StringBuilder = StringBuilder(512)

  /**
   * Starts the building process by appending the class or object name.
   *
   * @param name The name of the class or object to display at the beginning
   *             of the string.
   * @return The [StylishToStringBuilder] instance for chaining.
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
   * Determines if the given value is of a primitive type or a string.
   *
   * @param value The value to check.
   * @return `true` if the value is a primitive type or string; `false
   *         otherwise.
   *
   * @since 0.1.0
   */
  private fun isPrimitiveType(
    value: Any?
  ): Boolean {
    if (null == value) {
      return false
    }

    return when (value) {
      is Boolean, is Byte, is Short, is Int, is Long,
      is Float, is Double,
      is Char, is String -> true

      else -> false
    }
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
   * Adds a named property with values from an iterator to the output.
   *
   * @param name The name of the property.
   * @param iterator An iterator for the property values.
   * @return The [StylishToStringBuilder] instance for chaining.
   *
   * @since 0.1.0
   */
  private fun add(
    name: String,
    iterator: Iterator<Any?>
  ): StylishToStringBuilder {
    if (!iterator.hasNext()) {
      this.stringBuilder.append("$name=[],")
      return this
    }

    this.stringBuilder.append("$name=[")

    while (iterator.hasNext()) {
      val value: Any? = iterator.next()
      val isPrimitive: Boolean = this.isPrimitiveType(value)

      this.stringBuilder.append(
        if (isPrimitive) {
          "\"${value.toString()}\""
        } else {
          value.toString()
        }
      )

      if (iterator.hasNext()) {
        this.stringBuilder.append(',')
      }
    }

    this.stringBuilder.append("],")
    return this
  }

  /**
   * Adds an array as a named property to the output.
   *
   * @param name The name of the property.
   * @param array The array of property values.
   * @return The [StylishToStringBuilder] instance for chaining.
   *
   * @since 0.1.0
   */
  fun add(
    name: String,
    array: Array<Any?>
  ): StylishToStringBuilder {
    return this.add(name, array.iterator())
  }

  /**
   * Adds an iterable as a named property to the output.
   *
   * @param name The name of the property.
   * @param iterable The iterable of property values.
   * @return The [StylishToStringBuilder] instance for chaining.
   *
   * @since 0.1.0
   */
  fun add(
    name: String,
    iterable: Iterable<Any>
  ): StylishToStringBuilder {
    return this.add(name, iterable.iterator())
  }

  /**
   * Finalizes and returns the constructed `toString` representation.
   *
   * @return The complete `toString` representation as a `String`.
   * @since 0.1.0
   */
  override fun build(): String {
    return this.stringBuilder
      .deleteCharAt(this.stringBuilder.lastIndex)
      .append('}')
      .toString()
  }
}