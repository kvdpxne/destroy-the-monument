package me.kvdpxne.dtm.data

import kotlin.reflect.KProperty
import kotlin.reflect.full.memberProperties
import kotlin.test.assertEquals
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder

/**
 * @since 0.1.0
 */
@Order(0)
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class TestResponseCodes {

  /**
   * @since 0.1.0
   */
  @Test
  fun `uniqueness of response codes`() {
    val members = ResponseCodes::class.memberProperties
    val size = members.size

    assertEquals(
      size,
      members
        .map { member: KProperty<*> ->
          member.call()
        }
        .toHashSet()
        .size
    )
  }

  /**
   * @since 0.1.0
   */
  @Test
  fun `uniqueness of response codes and validation results`() {
    val rcm = ResponseCodes::class.memberProperties
    val clazz = Class.forName("me.kvdpxne.dtm.data.validation.ValidationResultsKt")
    val vrm = clazz.declaredFields

    val size = rcm.size + vrm.size
    val set = hashSetOf<Int>()

    for (member: KProperty<*> in rcm) {
      val value = member.call() as Int
      if (!set.add(value)) {
        println("DUPLICATED: $value")
      }
    }

    for (member in vrm) {
      val value = member.getInt(null)
      if (!set.add(value)) {
        println("DUPLICATED: $value")
      }
    }

    assertEquals(size, set.size)
  }
}