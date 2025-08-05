package me.kvdpxne.dtm.translation

import java.util.Locale
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap
import java.util.concurrent.atomic.AtomicInteger
import me.kvdpxne.dtm.shared.StylishToStringBuilder
import me.kvdpxne.dtm.shared.debug.Debug
import me.kvdpxne.dtm.translation.message.EnumTranslationKey

/**
 * Represents a unique message key identifier for translation purposes.
 *
 * This class is designed to ensure unique keys for each translation
 * message using a normalized and thread-safe approach. Each key is associated
 * with a unique index.
 *
 * @property name The name or identifier of the message key.
 * @property ordinal A unique integer index assigned to each message key.
 * @constructor Creates a new instance of [TranslationKey] with a specific [name] and [ordinal].
 * @since 0.1.0
 */
class BasicTranslationKey private constructor(
  override val name: String,
  override val ordinal: Int
) : TranslationKey {

  companion object {

    /**
     * A thread-safe map to store and retrieve message keys by their normalized
     * name. Ensures that each unique key name corresponds to a single
     * [BasicTranslationKey] instance.
     *
     * @since 0.1.0
     */
    private val messageKeys: ConcurrentMap<String, TranslationKey> =
      ConcurrentHashMap(EnumTranslationKey.entries.size)

    /**
     * A counter used to generate a unique index for each new [BasicTranslationKey].
     *
     * @since 0.1.0
     */
    private val counter: AtomicInteger = AtomicInteger()

    /**
     * Retrieves or creates a new [TranslationKey] instance based on the
     * provided [content].
     *
     * This method normalizes the provided [content] by trimming whitespace
     * and converting it to uppercase. If a [TranslationKey] with the normalized
     * key already exists in the map, it will return the existing instance;
     * otherwise, it creates a new [TranslationKey] with a unique index.
     *
     * @param content The string content representing the message key.
     * @return The corresponding [TranslationKey] instance for the provided content.
     * @throws IllegalArgumentException if [content] is blank or empty.
     * @since 0.1.0
     */
    fun of(
      content: String,
      denyCreation: Boolean = false
    ): TranslationKey {
      require(content.isNotBlank()) {
        "Key cannot be empty or blank."
      }

      // Normalize the key by trimming and converting to uppercase.
      val normalizedKey: String = content.trim()
        .uppercase(Locale.ENGLISH)
        .intern()

      var presentValue: TranslationKey? = this.messageKeys[normalizedKey]
      if (null != presentValue) {
        return presentValue
      }

      if (denyCreation) {
        throw MissingTranslationKeyException(normalizedKey)
      }

      val newValue = BasicTranslationKey(normalizedKey, this.counter.getAndIncrement())
      presentValue = this.messageKeys.put(normalizedKey, newValue)

      if (null != presentValue) {
        return presentValue
      }

      Debug.log {
        "A new ${newValue.name} message key has been registered."
      }

      return newValue
    }
  }

  /**
   * Provides access to the [TranslationKey] itself for implementations
   * of [TranslationKeyProvider].
   *
   * @return This instance of [TranslationKey].
   * @since 0.1.0
   */
  override val messageKey: TranslationKey
    get() = this

  /**
   * Compares this [BasicTranslationKey] instance with another for equality based on
   * their unique indices.
   *
   * @param other The other object to compare with.
   * @return `true` if both [BasicTranslationKey] instances have the same index;
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

    other as BasicTranslationKey
    return this.ordinal == other.ordinal
  }

  /**
   * Generates a hash code based on the unique index of this [BasicTranslationKey].
   *
   * @return The hash code representing this [BasicTranslationKey].
   * @since 0.1.0
   */
  override fun hashCode(): Int {
    return this.ordinal
  }

  /**
   * Provides a string representation of this [BasicTranslationKey], including its name
   * and index.
   *
   * This uses a custom [StylishToStringBuilder] to format the output string.
   *
   * @return A formatted string representation of this [BasicTranslationKey].
   * @since 0.1.0
   */
  override fun toString(): String {
    return StylishToStringBuilder()
      .begin("MessageKey")
      .add("name", this.name)
      .add("index", this.ordinal)
      .build()
  }
}