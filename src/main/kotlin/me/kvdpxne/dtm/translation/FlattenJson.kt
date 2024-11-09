package me.kvdpxne.dtm.translation

import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.jsonPrimitive
import me.kvdpxne.dtm.translation.message.Message
import me.kvdpxne.dtm.translation.message.MessageKey
import me.kvdpxne.dtm.translation.message.MultipleMessages
import me.kvdpxne.dtm.translation.message.SingleMessage

/**
 * A utility object for flattening JSON structures into message representations.
 *
 * Provides functions to create [SingleMessage] or [MultipleMessages] from
 * JSON primitives and arrays, as well as for generating formatted keys.
 *
 * @since 0.1.0
 */
internal object FlattenJson {

  /**
   * Converts a [JsonPrimitive] to a [SingleMessage] object if it contains a valid non-blank string.
   *
   * @param json The JSON primitive to convert.
   * @return A [SingleMessage] containing the string content of the JSON primitive.
   * @throws IllegalArgumentException if the JSON primitive is not a string or is blank.
   * @since 0.1.0
   */
  private fun toSingleMessage(
    json: JsonPrimitive
  ): SingleMessage {
    require(json.isString) {
      "Expected a JSON string for SingleMessage, but received a different type."
    }

    val rawMessage: String = json.content
    require(rawMessage.isNotBlank()) {
      "SingleMessage content cannot be blank."
    }

    return SingleMessage(rawMessage)
  }

  /**
   * Converts a [JsonArray] to a [MultipleMessages] object containing each string element
   * as a separate line. Ensures that the array is non-empty and that all elements are strings.
   *
   * @param json The JSON array to convert.
   * @return A [MultipleMessages] object with each line as a string element.
   * @throws IllegalArgumentException if the array is empty or contains non-string elements.
   * @since 0.1.0
   */
  private fun toMultipleMessages(
    json: JsonArray
  ): MultipleMessages {
    require(json.isNotEmpty()) {
      "Expected a non-empty JSON array for MultipleMessages."
    }

    val messages: MutableList<String> = ArrayList(json.size)
    for (lineElement: JsonElement in json) {
      val linePrimitive: JsonPrimitive = lineElement.jsonPrimitive
      require(linePrimitive.isString) {
        "Expected all elements in JSON array to be strings."
      }

      messages.add(linePrimitive.content)
    }

    return MultipleMessages(messages.toTypedArray())
  }

  /**
   * Generates a formatted key by appending the `previous` key to the current `key`.
   * If `previous` is empty, it returns the `key` unmodified. Used to create
   * hierarchical keys for nested JSON structures.
   *
   * @param key The base key for the current JSON element.
   * @param previous The previous key part in a hierarchical structure.
   * @return A combined key string.
   * @since 0.1.0
   */
  private fun toKey(
    key: String,
    previous: String = "",
  ): String {
    if (previous.isEmpty()) {
      return key.uppercase()
    }

    return "${previous}_${key.uppercase()}"
  }

  /**
   * Recursively flattens a [JsonObject], converting nested elements into a flat map
   * of [MessageKey] and [Message]. This function appends hierarchical keys using
   * `previousKey` to ensure uniqueness across nested structures.
   *
   * @param json The JSON object to flatten.
   * @param previousKey A string representing the key path from higher levels.
   * @param map The mutable map where flattened key-value pairs are added.
   * @return The resulting flat map with keys mapped to corresponding messages.
   * @since 0.1.0
   */
  private fun flattenJsonObject(
    json: JsonObject,
    previousKey: String = "",
    map: MutableMap<MessageKey, Message<*>>
  ): Map<MessageKey, Message<*>> {
    for ((key: String, element: JsonElement) in json) {
      val newKey: String = this.toKey(key, previousKey)

      when (element) {
        is JsonObject -> map.putAll(this.flattenJsonObject(element, newKey, map))
        is JsonPrimitive -> map[MessageKey.of(newKey)] = this.toSingleMessage(element)
        is JsonArray -> map[MessageKey.of(newKey)] = this.toMultipleMessages(element)
      }
    }

    return map
  }

  /**
   * Flattens the root JSON element into a map of [MessageKey] and [Message].
   * Supports only JSON objects as the root element; other types will result in an exception.
   *
   * @param root The root JSON element to flatten.
   * @return A map containing message keys mapped to their respective message values.
   * @throws RuntimeException if the root element is not a [JsonObject].
   * @since 0.1.0
   */
  internal fun flatten(
    root: JsonElement
  ): Map<MessageKey, Message<*>> {
    val map: MutableMap<MessageKey, Message<*>> = hashMapOf()

    when (root) {
      is JsonObject -> this.flattenJsonObject(root, "", map)
      else -> throw RuntimeException("Unsupported root json type.")
    }

    return map
  }
}