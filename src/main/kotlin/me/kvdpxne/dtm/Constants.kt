package me.kvdpxne.dtm

/**
 * A centralized object for holding plugin-wide constants for the Minecraft
 * plugin.
 *
 * This object provides static constants used throughout the plugin, such as
 * versioning and platform information etc.
 *
 * @since 0.1.0
 */
object Constants {

  /**
   * The full name of the plugin.
   *
   * @since 0.1.0
   */
  const val FULL_NAME = "Destroy the Monument"

  /**
   * The short identifier or internal name of the plugin.
   *
   * @since 0.1.0
   */
  const val NAME = "DTM"

  /**
   * The current version of the plugin.
   *
   * @since 0.1.0
   */
  const val VERSION = "0.1.0"

  /**
   * The target platform for the plugin.
   * Indicates compatibility with the `bukkit` platform.
   *
   * @since 0.1.0
   */
  const val TARGET_PLATFORM = "bukkit"

  /**
   * Flag indicating if the plugin is running in legacy mode.
   *
   * @since 0.1.0
   */
  const val IS_LEGACY = true

  /**
   * Flag indicating if the plugin is in development mode.
   *
   * @since 0.1.0
   */
  const val IS_DEVELOPMENT = true
}