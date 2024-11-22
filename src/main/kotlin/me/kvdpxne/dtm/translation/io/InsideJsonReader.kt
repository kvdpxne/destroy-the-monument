package me.kvdpxne.dtm.translation.io

import java.nio.file.Files
import java.nio.file.Path
import java.util.Locale
import java.util.stream.Collectors
import kotlin.io.path.nameWithoutExtension
import kotlin.io.path.readText
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import me.kvdpxne.dtm.shared.io.Files2
import me.kvdpxne.dtm.translation.locale.LocaleMessages
import me.kvdpxne.dtm.translation.locale.Locales

/**
 * A utility to read locale-specific JSON translation files and parse them into `LocaleMessages`.
 *
 * @since 0.1.0
 */
internal object InsideJsonReader {

  /**
   * Decodes a JSON file from the given path into a `LocaleMessages` object.
   *
   * @param path Path of the JSON file to decode.
   * @return Parsed `LocaleMessages` containing messages for a specific locale.
   * @throws IllegalArgumentException if the file name does not represent a valid locale.
   * @since 0.1.0
   */
  private fun decodeJson(
    path: Path
  ): LocaleMessages {
    val fileName: String = path.nameWithoutExtension
    val locale: Locale = Locales.fromString(fileName)

    val texts: String = path.readText()
    val rootJson: JsonElement = Json.parseToJsonElement(texts)

    return LocaleMessages(locale, FlattenJson.flatten(rootJson))
  }

  /**
   * Reads and decodes a specific locale's translation file based on the provided name.
   *
   * @param name The name of the locale file to read (without extension).
   * @return The `LocaleMessages` for the specified locale.
   * @throws NoSuchElementException if the translation file is not found.
   * @since 0.1.0
   */
  internal fun read(
    name: String
  ): LocaleMessages {
    return Files2.fs(
      this::class.java.classLoader,
      "translations"
    ) { _, path ->
      //
      Files.walk(path)
        .filter(Files::isRegularFile)
        .filter {
          it.fileName.toString().contains(name)
        }
        .map(this::decodeJson)
        .findFirst()
        .orElseThrow {
          NoSuchElementException("Translation file for locale '$name' not found.")
        }
    }
  }

  /**
   * Reads and decodes all translation files found in the `translations` directory.
   *
   * @return A collection of `LocaleMessages` for all available locales.
   * @since 0.1.0
   */
  internal fun read(): Collection<LocaleMessages> {
    return Files2.fs(
      this::class.java.classLoader,
      "translations"
    ) { _, path ->
      //
      Files.walk(path)
        .filter(Files::isRegularFile)
        .map(this::decodeJson)
        .collect(Collectors.toList())
    }
  }
}