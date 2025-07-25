package me.kvdpxne.dtm.data.raw.factories

import kotlin.test.Test
import kotlin.test.assertTrue
import kotlin.test.fail
import me.kvdpxne.dtm.raw.NAMES_OF_ARENAS
import me.kvdpxne.dtm.raw.NAMES_OF_ARENA_MAPS
import me.kvdpxne.dtm.raw.NAMES_OF_GAMES
import me.kvdpxne.dtm.raw.NAMES_OF_TEAMS
import me.kvdpxne.dtm.raw.NAMES_OF_USERS
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.TestMethodOrder

@Order(0)
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class TestHolderNames {

  @Test
  fun `test all arrays have unique elements and no duplicates across arrays`() {
    val allArrays = listOf(
      "NAMES_OF_GAMES" to NAMES_OF_GAMES,
      "NAMES_OF_ARENAS" to NAMES_OF_ARENAS,
      "NAMES_OF_ARENA_MAPS" to NAMES_OF_ARENA_MAPS,
      "NAMES_OF_USERS" to NAMES_OF_USERS,
      "NAMES_OF_TEAMS" to NAMES_OF_TEAMS
    )

    val errors = mutableListOf<String>()
    val globalElements = mutableMapOf<String, MutableList<String>>()

    // Sprawdzanie unikalności w obrębie pojedynczej tablicy
    for ((arrayName, array) in allArrays) {
      val duplicates = array
        .groupingBy { it }
        .eachCount()
        .filter { it.value > 1 }

      if (duplicates.isNotEmpty()) {
        duplicates.forEach { (element, count) ->
          errors.add("DUPLIKAT W TABLICY '$arrayName': '$element' występuje $count razy")
        }
      }

      // Przygotowanie danych do globalnego sprawdzenia
      array.forEach { element ->
        globalElements
          .getOrPut(element) { mutableListOf() }
          .add(arrayName)
      }
    }

    // Sprawdzanie unikalności między wszystkimi tablicami
    globalElements.forEach { (element, arrays) ->
      if (arrays.size > 1) {
        errors.add("KONFLIKT MIĘDZY TABLICAMI: '$element' występuje w ${arrays.size} tablicach: [${arrays.joinToString()}]")
      }
    }

    if (errors.isNotEmpty()) {
      fail("\n" + errors.joinToString("\n") + "\n")
    } else {
      assertTrue(true, "Wszystkie elementy są unikatowe")
    }
  }
}