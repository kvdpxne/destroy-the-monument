package me.kvdpxne.dtm.data.dao

import kotlin.test.assertEquals
import kotlinx.coroutines.runBlocking
import me.kvdpxne.dtm.data.ResponseCodes
import me.kvdpxne.dtm.raw.NAMES_OF_ARENAS
import me.kvdpxne.dtm.raw.factories.makeRawArena
import me.kvdpxne.dtm.shared.uniqueUuid
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
private val ARENA = makeRawArena()

/**
 * @since 0.1.0
 */
@Order(1)
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TestDaoArena {

  /**
   * @since 0.1.0
   */
  @BeforeAll
  fun `prepare battleground`() {
    this.`clean up battlefield after battle`()
    println(ARENA.toStylishString().listed(2))
  }

  /**
   * @since 0.1.0
   */
  @AfterAll
  fun `clean up battlefield after battle`() {
    try {
      runBlocking {
        ArenaDao.truncateArenas()
        ArenaMapDao.truncateArenaMaps()
        MonumentPositionDao.truncateMonumentPositions()
        RevivalPositionDao.truncateRevivalPositions()
        TeamDao.truncateTeams()
      }
    } catch (_: Throwable) {
    }
  }

  @Order(0)
  @Test
  fun `insert arena`() {
    assertEquals(
      1,
      runBlocking {
        ArenaDao.insertArena(ARENA)
      }
    )
  }

  @Order(1)
  @Test
  fun `inset duplicated arena`() {
    assertEquals(
      ResponseCodes.DUPLICATED,
      runBlocking {
        ArenaDao.insertArena(ARENA)
      }
    )
  }

  @Order(2)
  @Test
  fun `insert arena with duplicated identifier`() {
    val duplicated = ARENA.copy(
      name = NAMES_OF_ARENAS.random()
    )

    assertEquals(
      ResponseCodes.DUPLICATED,
      runBlocking {
        ArenaDao.insertArena(duplicated)
      }
    )
  }

  @Order(3)
  @Test
  fun `insert arena with duplicated name`() {
    val duplicated = ARENA.copy(
      identifier = uniqueUuid(ARENA.identifier)
    )

    assertEquals(
      ResponseCodes.DUPLICATED,
      runBlocking {
        ArenaDao.insertArena(duplicated)
      }
    )
  }

  @Order(4)
  @Test
  fun `insert invalid arena`() {

  }

  @Order(5)
  @Test
  fun `insert arena with invalid name`() {

  }
}