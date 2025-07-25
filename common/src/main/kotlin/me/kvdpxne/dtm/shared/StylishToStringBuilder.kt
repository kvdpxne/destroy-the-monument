package me.kvdpxne.dtm.shared

import me.kvdpxne.dtm.capabilities.Buildable
import me.kvdpxne.dtm.capabilities.Nameable
import me.kvdpxne.dtm.util.StylishToString
import me.kvdpxne.dtm.util.StylishToStringProvider

/**
 * A fluent builder for creating stylized string representations of objects.
 *
 * Supports both compact (single-line) and indented (multi-line) output formats.
 * This class is mutable and not thread-safe.
 *
 * Example:
 * ```kotlin
 * val result = StylishToStringBuilder.begin("Person")
 *     .add("name", "Alice")
 *     .add("age", 30)
 *     .addNested("address") {
 *         add("city", "New York")
 *         add("zip", 10001)
 *     }
 *     .build()
 *
 * println(result)
 * // Output: Person{name="Alice",age=30,address=Address{city="New York",zip=10001}}
 * ```
 *
 * @property name The name of the object being represented.
 * @property propertyEntries Internal list of formatted properties.
 * @since 0.1.0
 */
class StylishToStringBuilder private constructor(
  private val name: String
) : Buildable<String>, Nameable, StylishToString, StylishToStringProvider {

  private val propertyEntries: MutableList<PropertyEntry> = mutableListOf()

  companion object {

    /**
     * Factory method to begin constructing a new [StylishToStringBuilder].
     *
     * @param className The name of the object to represent.
     * @return A new instance of [StylishToStringBuilder].
     * @throws IllegalArgumentException If the provided className is blank.
     * @since 0.1.0
     */
    fun begin(className: String): StylishToStringBuilder {
      require(className.isNotBlank()) {
        "Class name must not be blank."
      }
      return StylishToStringBuilder(className)
    }
  }

  /**
   * Formats a single value into a stylized string representation.
   *
   * Handles null, Boolean, Number, Char, and any other object.
   *
   * @param value The value to format.
   * @return A string representation suitable for output.
   * @since 0.1.0
   */
  private fun formatValue(value: Any?): String = when (value) {
    null -> "null"
    is Boolean, is Number -> value.toString()
    is Char -> "'$value'"
    else -> "\"$value\""
  }

  /**
   * Adds a simple key-value property to the builder.
   *
   * @param name The name of the property.
   * @param value The value to represent.
   * @return This builder instance for fluent chaining.
   * @throws IllegalArgumentException If the property name is blank.
   * @since 0.1.0
   */
  fun add(name: String, value: Any?): StylishToStringBuilder {
    require(name.isNotBlank()) { "Property name must not be blank." }
    propertyEntries.add(SimpleProperty(name, value))
    return this
  }

  /**
   * Adds an array property to the builder.
   *
   * @param name The name of the property.
   * @param array The array of values to represent.
   * @return This builder instance for fluent chaining.
   * @since 0.1.0
   */
  fun add(name: String, array: Array<Any?>): StylishToStringBuilder {
    propertyEntries.add(CollectionProperty(name, array.toList()))
    return this
  }

  /**
   * Adds an iterable property to the builder.
   *
   * @param name The name of the property.
   * @param iterable The collection of values to represent.
   * @return This builder instance for fluent chaining.
   * @since 0.1.0
   */
  fun add(name: String, iterable: Iterable<Any?>): StylishToStringBuilder {
    propertyEntries.add(CollectionProperty(name, iterable.toList()))
    return this
  }

  /**
   * Adds a map as a nested property under the given name.
   *
   * Each key-value pair in the map is added as a property to a new nested builder,
   * which is then added to this builder under the specified name.
   *
   * @param name The key under which the map will be added as a nested object.
   * @param map The map containing properties to add to the nested object.
   * @return This builder instance for fluent chaining.
   * @since 0.1.0
   */
  fun add(name: String, map: Map<Any, Any?>): StylishToStringBuilder {
    val nestedBuilder = StylishToStringBuilder.begin(name)
    for ((key, value) in map) {
      val propertyName = key.toString()
      when (value) {
        is StylishToStringProvider -> nestedBuilder.addNested(propertyName, value)
        is Iterable<*> -> nestedBuilder.add(propertyName, value)
        is Array<*> -> nestedBuilder.add(propertyName, value)
        else -> nestedBuilder.add(propertyName, value)
      }
    }
    return addNested(name, nestedBuilder)
  }

  /**
   * Adds a nested [StylishToStringProvider] property.
   *
   * @param name The name of the nested property.
   * @param provider The nested object to represent.
   * @return This builder instance for fluent chaining.
   * @since 0.1.0
   */
  fun addNested(
    name: String,
    provider: StylishToStringProvider?
  ): StylishToStringBuilder {
    propertyEntries.add(NestedProperty(name, provider?.toStylishString() as? StylishToStringBuilder))
    return this
  }

  /**
   * Adds a collection of nested [StylishToStringProvider] objects.
   *
   * @param name The name of the collection property.
   * @param providers The collection of nested objects to represent.
   * @return This builder instance for fluent chaining.
   * @since 0.1.0
   */
  fun addNested(
    name: String,
    providers: Iterable<StylishToStringProvider>
  ): StylishToStringBuilder {
    propertyEntries.add(
      NestedCollectionProperty(
        name,
        providers.map { it.toStylishString() as StylishToStringBuilder }.toList()
      )
    )
    return this
  }

  /**
   * Adds a nested block that constructs a nested object inline.
   *
   * @param name The name of the nested property.
   * @param block A lambda that builds the nested object.
   * @return This builder instance for fluent chaining.
   * @since 0.1.0
   */
  fun addNested(
    name: String,
    block: StylishToStringBuilder.() -> Unit
  ): StylishToStringBuilder {
    val builder = begin(name).apply(block)
    return addNested(name, builder)
  }

  /**
   * Returns a compact string representation of the object.
   *
   * @return A single-line string representation.
   * @since 0.1.0
   */
  override fun packed(): String {
    val properties = propertyEntries.joinToString(",") { it.formatPacked() }
    return "$name{$properties}"
  }

  /**
   * Returns an indented, multi-line representation of the object.
   *
   * @param indentSize The number of spaces per indentation level.
   * @return A formatted string with indentation.
   * @since 0.1.0
   */
  override fun listed(indentSize: Int): String {
    return buildListed(name, propertyEntries, 0, indentSize)
  }

  /**
   * Builds the multi-line representation recursively.
   *
   * @param name The name of the current object.
   * @param properties The list of properties to render.
   * @param indentLevel The current indentation level.
   * @param indentSize The number of spaces per indentation level.
   * @return A formatted multi-line string.
   */
  private fun buildListed(
    name: String,
    properties: List<PropertyEntry>,
    indentLevel: Int,
    indentSize: Int
  ): String {
    if (properties.isEmpty()) return "$name{}"

    val currentIndent = " ".repeat(indentLevel * indentSize)
    val propertyIndent = " ".repeat((indentLevel + 1) * indentSize)

    val sb = StringBuilder("$name{\n")

    properties.forEachIndexed { index, entry ->
      sb.append(propertyIndent).append(entry.name).append('=')

      when (entry) {
        is NestedProperty -> {
          val nested = entry.builder?.buildListed(
            entry.builder.name,
            entry.builder.propertyEntries,
            indentLevel + 1,
            indentSize
          ) ?: "null"
          sb.append(nested)
        }
        is NestedCollectionProperty -> {
          sb.append(entry.formatListed(indentLevel + 1, indentSize))
        }
        else -> {
          sb.append(entry.formatListed(indentLevel + 1, indentSize))
        }
      }

      if (index < properties.size - 1) sb.append(',')
      sb.append('\n')
    }

    sb.append(currentIndent).append('}')
    return sb.toString()
  }

  private sealed class PropertyEntry(val name: String) {
    abstract fun formatPacked(): String
    abstract fun formatListed(indentLevel: Int, indentSize: Int): String
  }

  private inner class SimpleProperty(name: String, private val value: Any?) : PropertyEntry(name) {
    override fun formatPacked(): String = "$name=${formatValue(value)}"
    override fun formatListed(indentLevel: Int, indentSize: Int): String = formatValue(value)
  }

  private inner class CollectionProperty(name: String, private val elements: List<Any?>) : PropertyEntry(name) {
    override fun formatPacked(): String {
      val items = elements.joinToString(",") { formatValue(it) }
      return "$name=[$items]"
    }

    override fun formatListed(indentLevel: Int, indentSize: Int): String {
      if (elements.isEmpty()) return "[]"

      val currentIndent = " ".repeat(indentLevel * indentSize)
      val itemIndent = " ".repeat((indentLevel + 1) * indentSize)

      return elements.joinToString(
        prefix = "[\n",
        postfix = "\n$currentIndent]",
        separator = ",\n"
      ) { "$itemIndent${formatValue(it)}" }
    }
  }

  private inner class NestedProperty(name: String, val builder: StylishToStringBuilder?) : PropertyEntry(name) {
    override fun formatPacked(): String = "$name=${builder?.packed() ?: "null"}"
    override fun formatListed(indentLevel: Int, indentSize: Int): String = ""
  }

  private inner class NestedCollectionProperty(name: String, private val builders: List<StylishToStringBuilder>) : PropertyEntry(name) {
    override fun formatPacked(): String {
      val items = builders.joinToString(",") { it.packed() }
      return "$name=[$items]"
    }

    override fun formatListed(indentLevel: Int, indentSize: Int): String {
      if (builders.isEmpty()) return "[]"

      val currentIndent = " ".repeat(indentLevel * indentSize)
      val itemIndent = " ".repeat((indentLevel + 1) * indentSize)

      val items = builders.joinToString(",\n") { builder ->
        val nested = builder.buildListed(
          builder.name,
          builder.propertyEntries,
          indentLevel + 1,
          indentSize
        )
        nested.split("\n").joinToString("\n") { line ->
          if (line.isBlank()) line else "$itemIndent$line"
        }
      }

      return "[\n$items\n$currentIndent]"
    }
  }

  /**
   * Builds the compact representation of this object.
   *
   * @return The compact string representation.
   */
  override fun build(): String = packed()

  /**
   * Returns the compact representation of this object as a string.
   *
   * @return The compact string representation.
   */
  override fun toString(): String = packed()

  /**
   * Returns the name of the object being represented.
   *
   * @return The object name.
   */
  override fun getName(): String = name

  /**
   * Returns this builder as a [StylishToString] representation.
   *
   * @return This instance.
   */
  override fun toStylishString(): StylishToString = this
}