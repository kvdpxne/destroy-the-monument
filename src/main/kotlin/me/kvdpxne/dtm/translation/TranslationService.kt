package me.kvdpxne.dtm.translation

import java.io.FileNotFoundException
import java.io.IOException
import java.net.URI
import java.net.URISyntaxException
import java.net.URL
import java.nio.file.FileSystems
import java.nio.file.Files
import java.nio.file.Path
import java.util.Locale
import java.util.stream.Collectors
import kotlin.io.path.nameWithoutExtension
import kotlin.io.path.readText
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.jsonObject
import me.kvdpxne.dtm.translation.message.MessageBuilder
import me.kvdpxne.dtm.translation.message.MessageKey


object TranslationService {

  val localeMessages: MutableMap<Locale, LocaleMessages> = mutableMapOf()
  var defaultLocale: Locale = Locale.US

  /**
   * @param locale
   * @since 0.1.0
   */
  fun findLocalMessagesOrNull(locale: Locale): LocaleMessages? {
    return this.localeMessages[locale]
  }

  /**
   * @param locale
   * @throws IllegalArgumentException
   * @since 0.1.0
   */
  fun findLocalMessages(locale: Locale): LocaleMessages {
    return requireNotNull(this.findLocalMessagesOrNull(locale)) {
      "Locale $locale not found."
    }
  }

  /**
   * @param locale
   * @since 0.1.0
   */
  fun findLocalMessagesOrDefaultOrNull(locale: Locale): LocaleMessages? {
    return this.localeMessages[locale]
      ?: this.localeMessages[this.defaultLocale]
  }

  /**
   * @param locale
   * @since 0.1.0
   */
  fun findLocalMessagesOrDefault(locale: Locale): LocaleMessages {
    return requireNotNull(this.findLocalMessagesOrDefaultOrNull(locale)) {
      "Locale $locale not found."
    }
  }

  private fun openFile(path: Path): Pair<JsonObject, String> {
    val texts: String = path.readText()

    val jsonElement: JsonObject = Json.parseToJsonElement(texts).jsonObject
    val fileName: String = path.nameWithoutExtension

    return Pair(jsonElement, fileName)
  }

  // Get all paths from a folder that inside the JAR file
  @Throws(URISyntaxException::class, IOException::class)
  private fun openDirectory(): List<Pair<JsonObject, String>> {

    val classLoader: ClassLoader = this::class.java.classLoader
    val resource: URL = classLoader.getResource("translations")
      ?: throw FileNotFoundException("No translations found.")

    // A is project directory
    // B is working directory
    // C is jar file name with extension
    // jar:file:/[A]/[B]/plugins/[C]!/translations/
    val rawPath: String = resource.toString()

    val environments: Map<String, String> = emptyMap()
    val arrays: List<String> = rawPath.split('!')

    return FileSystems.newFileSystem(URI.create(arrays[0]), environments).use {
      val path: Path = it.getPath(arrays[1])

      //
      Files.walk(path)
        .filter(Files::isRegularFile)
        .map(TranslationService::openFile)
        .collect(Collectors.toList())
    }
  }

  private fun flattenJson(
    jsonObject: JsonObject,
    prefix: String = ""
  ): Map<MessageKey, String> {
    val map = mutableMapOf<MessageKey, String>()

    for ((key: String, value: JsonElement) in jsonObject) {
      val newKey: String = if (prefix.isEmpty()) key else "${prefix}_$key"

      when (value) {
        is JsonObject -> map.putAll(this.flattenJson(value, newKey))
        is JsonPrimitive -> map[MessageKey.of(newKey)] = value.content
        else -> throw TypeCastException("Unsupported value type ${value.javaClass}")
      }
    }

    return map
  }

  fun loadTranslations() {
    for ((element: JsonObject, key: String) in this.openDirectory()) {

      val splitted = key.split("_")
      val language = splitted[0]
      val country = splitted[1]

      val locale = Locale.Builder()
        .setLanguage(language)
        .setRegion(country)
        .build()

      val messages: Map<MessageKey, String> = this.flattenJson(element).toMap()
      val localeMessages = LocaleMessages(locale, messages)

      this.localeMessages[locale] = localeMessages
    }
  }

  /**
   * @since 0.1.0
   */
  fun reloadTranslations() {
    //
    this.localeMessages.clear()

    //
    this.loadTranslations()
  }

  fun chains(): MessageBuilder {
    return MessageBuilder()
  }
}