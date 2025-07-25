package me.kvdpxne.dtm.data.validation.common

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class ColorValidatorTests {

  // ===========================================================
  // Hex color validation tests
  // ===========================================================

  @Test
  fun `null input returns false for hex color`() {
    assertFalse(isHexColorValid(null))
  }

  @ParameterizedTest
  @ValueSource(strings = ["", "#", "123", "#12", "#12345", "#1234567", "123456"])
  fun `invalid length returns false for hex color`(value: String) {
    assertFalse(isHexColorValid(value))
  }

  @ParameterizedTest
  @ValueSource(strings = ["1234", "abcd", "FFFFF", "!@#$%", "1234567"])
  fun `missing hash prefix returns false for hex color`(value: String) {
    assertFalse(isHexColorValid(value))
  }

  @ParameterizedTest
  @ValueSource(strings = ["#xyz", "#g12", "#12 3", "#12!4", "#ABCDEG"])
  fun `invalid hex characters return false`(value: String) {
    assertFalse(isHexColorValid(value))
  }

  @ParameterizedTest
  @ValueSource(strings = ["#abc", "#ABC", "#aBc", "#123", "#f00", "#F0F"])
  fun `valid short hex colors return true`(value: String) {
    assertTrue(isHexColorValid(value))
  }

  @ParameterizedTest
  @ValueSource(strings = ["#abcdef", "#ABCDEF", "#aBcDeF", "#123456", "#ff00ff", "#00FF00"])
  fun `valid long hex colors return true`(value: String) {
    assertTrue(isHexColorValid(value))
  }

  // ===========================================================
  // Legacy Minecraft color validation tests
  // ===========================================================

  @Test
  fun `null input returns false for legacy color`() {
    assertFalse(isMinecraftLegacyColorValid(null))
  }

  @ParameterizedTest
  @ValueSource(strings = ["", "a", "aaa", "&&&&", "§§§§"])
  fun `invalid length returns false for legacy color`(value: String) {
    assertFalse(isMinecraftLegacyColorValid(value))
  }

  @ParameterizedTest
  @ValueSource(strings = ["!a", "@b", "c1", "d", " ", "++", "##"])
  fun `invalid prefix returns false for legacy color`(value: String) {
    assertFalse(isMinecraftLegacyColorValid(value))
  }

  @ParameterizedTest
  @ValueSource(strings = ["&g", "&x", "&!", "& ", "§z", "§@", "§_"])
  fun `invalid hex digit returns false for legacy color`(value: String) {
    assertFalse(isMinecraftLegacyColorValid(value))
  }

  @ParameterizedTest
  @ValueSource(strings = ["&0", "&9", "&a", "&f", "&A", "&F", "§0", "§9", "§a", "§f", "§A", "§F"])
  fun `valid legacy colors return true`(value: String) {
    assertTrue(isMinecraftLegacyColorValid(value))
  }

  // ===========================================================
  // Minecraft version-specific color validation tests
  // ===========================================================

  // Setup for version-based tests
  private val originalVersion = MinecraftVersionCreator.getMinecraftVersion().number

  @BeforeEach
  fun setupVersion() {
    // Backup original version
  }

  @AfterEach
  fun restoreVersion() {
    // Restore original version after test
    MinecraftVersionCreator.setTestVersion(originalVersion)
  }

  @Test
  fun `null input returns false in all versions`() {
    setVersion(ChangesByVersion.COLOR_CODES)
    assertFalse(isMinecraftColorValid(null))

    setVersion(ChangesByVersion.COLOR_CODES - 1)
    assertFalse(isMinecraftColorValid(null))
  }

  @Test
  fun `modern version accepts both hex and legacy formats`() {
    setVersion(ChangesByVersion.COLOR_CODES)

    assertTrue(isMinecraftColorValid("#abc"))
    assertTrue(isMinecraftColorValid("#123456"))
    assertTrue(isMinecraftColorValid("&a"))
    assertTrue(isMinecraftColorValid("§f"))
    assertFalse(isMinecraftColorValid("invalid"))
  }

  @Test
  fun `legacy version only accepts legacy format`() {
    setVersion(ChangesByVersion.COLOR_CODES - 1)

    assertTrue(isMinecraftColorValid("&a"))
    assertTrue(isMinecraftColorValid("§f"))
    assertFalse(isMinecraftColorValid("#abc"))
    assertFalse(isMinecraftColorValid("#123456"))
    assertFalse(isMinecraftColorValid("invalid"))
  }

  @Test
  fun `boundary version accepts both formats`() {
    setVersion(ChangesByVersion.COLOR_CODES)

    assertTrue(isMinecraftColorValid("#abc"))
    assertTrue(isMinecraftColorValid("&a"))
  }

  @Test
  fun `valid hex rejected in pre-color-code versions`() {
    setVersion(ChangesByVersion.COLOR_CODES - 1)
    assertFalse(isMinecraftColorValid("#abc"))
  }

  @Test
  fun `valid legacy accepted in all versions`() {
    setVersion(ChangesByVersion.COLOR_CODES - 1)
    assertTrue(isMinecraftColorValid("&f"))

    setVersion(ChangesByVersion.COLOR_CODES)
    assertTrue(isMinecraftColorValid("§a"))
  }

  private fun setVersion(version: Int) {
    MinecraftVersionCreator.setTestVersion(version)
  }
}

// Test helper for Minecraft version control
object MinecraftVersionCreator {
  private var testVersion: Int = 0

  fun getMinecraftVersion(): MinecraftVersion {
    return MinecraftVersion(testVersion)
  }

  fun setTestVersion(version: Int) {
    testVersion = version
  }
}

class MinecraftVersion(val number: Int)

// Test configuration
object ChangesByVersion {
  // This should match the production value
  const val COLOR_CODES = 10
}