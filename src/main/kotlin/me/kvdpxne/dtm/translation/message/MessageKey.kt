package me.kvdpxne.dtm.translation.message

import java.util.Locale
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap
import java.util.concurrent.atomic.AtomicInteger
import me.kvdpxne.dtm.shared.StylishToStringBuilder
import me.kvdpxne.dtm.shared.debug.Debug

/**
 * Represents a unique message key identifier for translation purposes.
 *
 * This class is designed to ensure unique keys for each translation
 * message using a normalized and thread-safe approach. Each key is associated
 * with a unique index.
 *
 * @property name The name or identifier of the message key.
 * @property index A unique integer index assigned to each message key.
 * @constructor Creates a new instance of [MessageKey] with a specific [name] and [index].
 * @since 0.1.0
 */
class MessageKey private constructor(
  val name: String,
  val index: Int
) : MessageKeyProvider {

  companion object {

    /**
     * A thread-safe map to store and retrieve message keys by their normalized
     * name. Ensures that each unique key name corresponds to a single
     * [MessageKey] instance.
     *
     * @since 0.1.0
     */
    private val messageKeys: ConcurrentMap<String, MessageKey> =
      ConcurrentHashMap(EnumMessageKey.entries.size)

    /**
     * A counter used to generate a unique index for each new [MessageKey].
     *
     * @since 0.1.0
     */
    private val counter: AtomicInteger = AtomicInteger()

    /**
     * Retrieves or creates a new [MessageKey] instance based on the
     * provided [content].
     *
     * This method normalizes the provided [content] by trimming whitespace
     * and converting it to uppercase. If a [MessageKey] with the normalized
     * key already exists in the map, it will return the existing instance;
     * otherwise, it creates a new [MessageKey] with a unique index.
     *
     * @param content The string content representing the message key.
     * @return The corresponding [MessageKey] instance for the provided content.
     * @throws IllegalArgumentException if [content] is blank or empty.
     * @since 0.1.0
     */
    fun of(
      content: String
    ): MessageKey {
      require(content.isNotBlank()) {
        "Key cannot be empty or blank."
      }

      // Normalize the key by trimming and converting to uppercase.
      val normalizedKey: String = content.trim()
        .uppercase(Locale.ENGLISH)
        .intern()

      var oldValue: MessageKey? = this.messageKeys[normalizedKey]
      if (null != oldValue) {
        return oldValue
      }

      val messageKey = MessageKey(normalizedKey, this.counter.getAndIncrement())
      oldValue = this.messageKeys.put(normalizedKey, messageKey)

      if (null != oldValue) {
        return oldValue
      }

      Debug.log {
        "A new ${messageKey.name} message key has been registered."
      }

      return messageKey
    }
  }

  /**
   * Provides access to the [MessageKey] itself for implementations
   * of [MessageKeyProvider].
   *
   * @return This instance of [MessageKey].
   * @since 0.1.0
   */
  override val messageKey: MessageKey
    get() = this

  /**
   * Compares this [MessageKey] instance with another for equality based on
   * their unique indices.
   *
   * @param other The other object to compare with.
   * @return `true` if both [MessageKey] instances have the same index;
   *         `false` otherwise.
   * @since 0.1.0
   */
  override fun equals(
    other: Any?
  ): Boolean {
    if (this === other) {
      return true
    }

    if (this.javaClass != other?.javaClass) {
      return false
    }

    other as MessageKey
    return this.index == other.index
  }

  /**
   * Generates a hash code based on the unique index of this [MessageKey].
   *
   * @return The hash code representing this [MessageKey].
   * @since 0.1.0
   */
  override fun hashCode(): Int {
    return this.index
  }

  /**
   * Provides a string representation of this [MessageKey], including its name
   * and index.
   *
   * This uses a custom [StylishToStringBuilder] to format the output string.
   *
   * @return A formatted string representation of this [MessageKey].
   * @since 0.1.0
   */
  override fun toString(): String {
    return StylishToStringBuilder()
      .begin("MessageKey")
      .add("name", this.name)
      .add("index", this.index)
      .build()
  }
}