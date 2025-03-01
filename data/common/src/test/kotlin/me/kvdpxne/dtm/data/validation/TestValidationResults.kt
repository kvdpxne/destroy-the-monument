package me.kvdpxne.dtm.data.validation

import kotlin.test.assertEquals
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test

/**
 * A test class for validating the uniqueness of constant values defined in the
 * `ValidationResults` class.
 *
 * This test ensures that all properties in `ValidationResults` have unique
 * values, which is critical for maintaining consistency in validation logic.
 *
 * @since 0.1.0
 */
@Order(0)
class TestValidationResults {

  /**
   * Tests the uniqueness of values assigned to properties in the
   * `ValidationResults` class.
   *
   * This test uses reflection to retrieve all properties of the
   * `ValidationResults` class, calls each property to get its value, and
   * verifies that all values are unique by comparing the size of the original
   * list to the size of a hash set created from the same list.
   *
   * If the sizes match, it confirms that all values are unique.
   *
   * @since 0.1.0
   */
  @Test
  fun `uniqueness of validation results`() {
    val clazz = Class.forName("me.kvdpxne.dtm.data.validation.ValidationResultsKt")
    val members = clazz.declaredFields
    val size = members.size

    assertEquals(
      size,
      members
        .map {
          it.get(null)
        }
        .toHashSet()
        .size
    )
  }
}