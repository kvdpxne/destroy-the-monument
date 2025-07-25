package me.kvdpxne.dtm.data.validation

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test

@Order(0)
class TestValidationConstantsHolder {

  @Test
  fun `correctness of color notation in hex format`() {
    val regex = Colori

    // Correct cases
    assertTrue(regex.matches("#abc"))
    assertTrue(regex.matches("#ABC"))
    assertTrue(regex.matches("#123"))
    assertTrue(regex.matches("#a1b2c3"))
    assertTrue(regex.matches("#A1B2C3"))
    assertTrue(regex.matches("#123456"))

    // Incorrect cases
    assertFalse(regex.matches("#ab"))
    assertFalse(regex.matches("#abcd"))
    assertFalse(regex.matches("#12345"))
    assertFalse(regex.matches("#1234567"))
    assertFalse(regex.matches("#g12345"))
    assertFalse(regex.matches("#12345z"))
    assertFalse(regex.matches("abc"))
    assertFalse(regex.matches("#abcdefg"))
    assertFalse(regex.matches("#"))
    assertFalse(regex.matches(""))
  }

  @Test
  fun `correctness of color notation in minecraft format`() {
    val regex = REGEX_COLOR_MINECRAFT_NOTATION

    // Correct cases
    assertTrue(regex.matches("&a"))
    assertTrue(regex.matches("&f"))
    assertTrue(regex.matches("&0"))
    assertTrue(regex.matches("&9"))
    assertTrue(regex.matches("&A"))
    assertTrue(regex.matches("&F"))

    // Incorrect cases
    assertFalse(regex.matches("&g"))
    assertFalse(regex.matches("&z"))
    assertFalse(regex.matches("&G"))
    assertFalse(regex.matches("&Z"))
    assertFalse(regex.matches("&"))
    assertFalse(regex.matches("&aa"))
    assertFalse(regex.matches("&1a"))
    assertFalse(regex.matches("&a1"))
    assertFalse(regex.matches("&aA"))
    assertFalse(regex.matches("&A1"))
    assertFalse(regex.matches("&A1"))
    assertFalse(regex.matches("&A1A"))
    assertFalse(regex.matches("&A1a"))
    assertFalse(regex.matches("&A1aA"))
  }
}