package me.kvdpxne.dtm.data.dao

import kotlin.random.Random
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue
import kotlinx.coroutines.runBlocking
import me.kvdpxne.dtm.data.ResponseCodes
import me.kvdpxne.dtm.data.validation.INVALID_ARENA_MAP_NAME
import me.kvdpxne.dtm.data.validation.INVALID_REFERENCE
import me.kvdpxne.dtm.raw.NAMES_OF_ARENA_MAPS
import me.kvdpxne.dtm.raw.factories.makeRawArenaMap
import me.kvdpxne.dtm.shared.uniqueOf
import me.kvdpxne.dtm.shared.uniqueUuid
import me.kvdpxne.dtm.shared.toSingleLines
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestMethodOrder

/**
 * @since 0.1.0
 */
private val ARENA_MAP = makeRawArenaMap()

/**
 * @since 0.1.0
 */
@Order(0)
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TestDaoArenaMap {

  @AfterAll
  fun `clean up battlefield after battle`() {
    runBlocking {
      ArenaMapDao.truncateArenaMaps()
    }
  }

  @BeforeAll
  fun `prepare battlefield`() {
    this.`clean up battlefield after battle`()
    println(ARENA_MAP.toStylishString())
  }

  @Order(0)
  @Test
  fun `insert arena map`() {
    assertEquals(
      1,
      runBlocking {
        ArenaMapDao.insertArenaMap(ARENA_MAP)
      }
    )
  }

  @Order(1)
  @Test
  fun `insert duplicated arena map`() {
    assertEquals(
      ResponseCodes.DUPLICATED,
      runBlocking {
        ArenaMapDao.insertArenaMap(ARENA_MAP)
      }
    )
  }

  @Order(2)
  @Test
  fun `insert arena map with duplicated identifier`() {
    val duplicated = ARENA_MAP.copy(
      name = uniqueOf(NAMES_OF_ARENA_MAPS, ARENA_MAP.name)
    )

    assertEquals(
      ResponseCodes.DUPLICATED,
      runBlocking {
        ArenaMapDao.insertArenaMap(duplicated)
      }
    )
  }

  @Order(3)
  @Test
  fun `insert arena map with duplicated name`() {
    val duplicated = ARENA_MAP.copy(
      identifier = uniqueUuid(ARENA_MAP.identifier)
    )

    assertEquals(
      ResponseCodes.DUPLICATED,
      runBlocking {
        ArenaMapDao.insertArenaMap(duplicated)
      }
    )
  }

  @Order(4)
  @Test
  fun `insert invalid arena map`() {
    assertEquals(
      INVALID_REFERENCE,
      runBlocking {
        ArenaMapDao.insertArenaMap(null)
      }
    )
  }

  @Order(5)
  @Test
  fun `insert arena map with invalid name`() {
    val tooShort = ARENA_MAP.copy(
      name = "A"
    )

    assertEquals(
      INVALID_ARENA_MAP_NAME,
      runBlocking {
        ArenaMapDao.insertArenaMap(tooShort)
      }
    )

    val tooLong = ARENA_MAP.copy(
      name = """
        Surely_someone_will_want_to_name_a_folder_containing_world_files_with_a_
        name_longer_than_127_characters_Something_else_I_need_to_add_here_
        because_there_are_not_enough_characters
      """.toSingleLines()
    )

    assertEquals(
      INVALID_ARENA_MAP_NAME,
      runBlocking {
        ArenaMapDao.insertArenaMap(tooLong)
      }
    )

    val illegalCharacters = ARENA_MAP.copy(
      name = "world dark elves castle"
    )

    assertEquals(
      INVALID_ARENA_MAP_NAME,
      runBlocking {
        ArenaMapDao.insertArenaMap(illegalCharacters)
      }
    )
  }

  @Order(6)
  @Test
  fun `find arena map by identifier`() {
    assertEquals(
      ARENA_MAP,
      runBlocking {
        ArenaMapDao.findArenaMapByIdentifierOrNull(
          ARENA_MAP.identifier
        )
      }
    )
  }

  @Order(7)
  @Test
  fun `find non existent arena map by identifier`() {
    assertNull(
      runBlocking {
        ArenaMapDao.findArenaMapByIdentifierOrNull(
          uniqueUuid(ARENA_MAP.identifier)
        )
      }
    )
  }

  @Order(8)
  @Test
  fun `find arena map by name`() {
    assertEquals(
      ARENA_MAP,
      runBlocking {
        ArenaMapDao.findArenaMapByNameOrNull(
          ARENA_MAP.name
        )
      }
    )

    assertEquals(
      ARENA_MAP,
      runBlocking {
        ArenaMapDao.findArenaMapByNameOrNull(
          ARENA_MAP.name.uppercase()
        )
      }
    )

    assertEquals(
      ARENA_MAP,
      runBlocking {
        ArenaMapDao.findArenaMapByNameOrNull(
          ARENA_MAP.name.lowercase()
        )
      }
    )
  }

  @Order(9)
  @Test
  fun `find non existent arena map by name`() {
    assertNull(
      runBlocking {
        ArenaMapDao.findArenaMapByNameOrNull(
          Random.nextInt(1_000, 1_000_000).toString()
        )
      }
    )
  }

  @Order(10)
  @Test
  fun `contains arena map by identifier`() {
    assertTrue(
      runBlocking {
        ArenaMapDao.containsArenaMapByIdentifier(
          ARENA_MAP.identifier
        )
      }
    )
  }

  @Order(11)
  @Test
  fun `contains non existent arena map by identifier`() {
    assertFalse(
      runBlocking {
        ArenaMapDao.containsArenaMapByIdentifier(
          uniqueUuid(ARENA_MAP.identifier)
        )
      }
    )
  }

  @Order(12)
  @Test
  fun `contains arena map by name`() {
    assertTrue(
      runBlocking {
        ArenaMapDao.containsArenaMapByName(
          ARENA_MAP.name
        )
      }
    )

    assertTrue(
      runBlocking {
        ArenaMapDao.containsArenaMapByName(
          ARENA_MAP.name.uppercase()
        )
      }
    )

    assertTrue(
      runBlocking {
        ArenaMapDao.containsArenaMapByName(
          ARENA_MAP.name.lowercase()
        )
      }
    )
  }

  @Order(13)
  @Test
  fun `contains non existent arena map by name`() {
    assertFalse(
      runBlocking {
        ArenaMapDao.containsArenaMapByName(
          Random.nextInt(1_000, 1_000_000).toString()
        )
      }
    )
  }

  @Order(14)
  @Test
  fun `update arena map`() {

  }

  @Order(15)
  @Test
  fun `update non existent arena map`() {

  }

  @Order(16)
  @Test
  fun `update arena map with duplicated name`() {

  }

  @Order(17)
  @Test
  fun `update arena map with invalid name`() {

  }

  @Order(18)
  @Test
  fun `count arena maps`() {
    assertEquals(
      1,
      runBlocking {
        ArenaMapDao.countArenaMaps()
      }
    )
  }

  @Order(19)
  @Test
  fun `delete arena map by identifier`() {
    assertEquals(
      1,
      runBlocking {
        ArenaMapDao.deleteArenaMapByIdentifier(
          ARENA_MAP.identifier
        )
      }
    )

    assertNull(
      runBlocking {
        ArenaMapDao.findArenaMapByIdentifierOrNull(
          ARENA_MAP.identifier
        )
      }
    )
  }

  @Order(20)
  @Test
  fun `delete non existent arena map by identifier`() {
    assertEquals(
      0,
      runBlocking {
        ArenaMapDao.deleteArenaMapByIdentifier(
          uniqueUuid(ARENA_MAP.identifier)
        )
      }
    )
  }

  @Order(21)
  @Test
  fun `delete arena maps`() {
    assertEquals(
      0,
      runBlocking {
        ArenaMapDao.truncateArenaMaps()
      }
    )
  }
}