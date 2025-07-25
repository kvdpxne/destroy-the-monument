package me.kvdpxne.dtm.data.dao

import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue
import kotlinx.coroutines.runBlocking
import me.kvdpxne.dtm.data.ResponseCodes
import me.kvdpxne.dtm.raw.factories.makeRawMonumentPosition
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
private val MONUMENT_POSITION = makeRawMonumentPosition()

/**
 * @since 0.1.0
 */
@Order(1)
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TestDaoMonumentPosition {

  /**
   * @since 0.1.0
   */
  @AfterAll
  fun `cleanup battlefield after battle`() {
    runBlocking {
      MonumentPositionDao.truncateMonumentPositions()
      TeamDao.truncateTeams()
    }
  }

  /**
   * @since 0.1.0
   */
  @BeforeAll
  fun `prepare battlefield`() {
    this.`cleanup battlefield after battle`()
    println(MONUMENT_POSITION.toStylishString().listed(2))
  }

  @Order(0)
  @Test
  fun `insert monument position`() {
    runBlocking {
      TeamDao.insertTeam(MONUMENT_POSITION.team)
    }

    assertEquals(
      1,
      runBlocking {
        MonumentPositionDao.insertMonumentPosition(MONUMENT_POSITION)
      }
    )
  }

  @Order(1)
  @Test
  fun `insert duplicated monument position`() {
    assertEquals(
      ResponseCodes.DUPLICATED,
      runBlocking {
        MonumentPositionDao.insertMonumentPosition(MONUMENT_POSITION)
      }
    )
  }

  @Order(2)
  @Test
  fun `insert monument position with invalid team reference`() {
    val invalid = MONUMENT_POSITION.copy(
      identifier = uniqueUuid(MONUMENT_POSITION.identifier),
      team = MONUMENT_POSITION.team.copy(
        identifier = uniqueUuid(MONUMENT_POSITION.team.identifier),
      )
    )

    assertEquals(
      ResponseCodes.NO_REFERENCE,
      runBlocking {
        MonumentPositionDao.insertMonumentPosition(invalid)
      }
    )
  }

  @Order(3)
  @Test
  fun `find monument position by identifier`() {
    assertEquals(
      MONUMENT_POSITION,
      runBlocking {
        MonumentPositionDao.findMonumentPositionByIdentifierOrNull(
          MONUMENT_POSITION.identifier
        )
      }
    )
  }

  @Order(4)
  @Test
  fun `find non existent monument position by identifier`() {
    assertNull(
      runBlocking {
        MonumentPositionDao.findMonumentPositionByIdentifierOrNull(
          uniqueUuid(MONUMENT_POSITION.identifier)
        )
      }
    )
  }

  @Order(5)
  @Test
  fun `contains monument position by identifier`() {
    assertTrue(
      runBlocking {
        MonumentPositionDao.containsMonumentPositionByIdentifier(
          MONUMENT_POSITION.identifier
        )
      }
    )
  }

  @Order(6)
  @Test
  fun `contains non existent monument position by identifier`() {
    assertFalse(
      runBlocking {
        MonumentPositionDao.containsMonumentPositionByIdentifier(
          uniqueUuid(MONUMENT_POSITION.identifier)
        )
      }
    )
  }

  @Order(7)
  @Test
  fun `update monument position`() {
    val updated = MONUMENT_POSITION.copy(
      // @formatter:off
      x = 1000,
      y = 20,
      z = -500
      // @formatter:on
    )

    assertEquals(
      1,
      runBlocking {
        MonumentPositionDao.updateMonumentPosition(updated)
      }
    )

    assertEquals(
      updated,
      runBlocking {
        MonumentPositionDao.findMonumentPositionByIdentifierOrNull(
          updated.identifier
        )
      }
    )
  }

  @Order(8)
  @Test
  fun `update non existent monument position`() {
    val updated = MONUMENT_POSITION.copy(
      // @formatter:off
      identifier = uniqueUuid(MONUMENT_POSITION.identifier),
      x          = 1000,
      y          = 20,
      z          = -500
      // @formatter:on
    )

    assertEquals(
      ResponseCodes.NO_RECORD,
      runBlocking {
        MonumentPositionDao.updateMonumentPosition(updated)
      }
    )
  }

  @Order(9)
  @Test
  fun `update monument position with invalid team reference`() {
    val invalid = MONUMENT_POSITION.copy(
      team = MONUMENT_POSITION.team.copy(
        identifier = uniqueUuid(MONUMENT_POSITION.team.identifier),
      )
    )

    assertEquals(
      ResponseCodes.NO_REFERENCE,
      runBlocking {
        MonumentPositionDao.updateMonumentPosition(invalid)
      }
    )
  }

  @Order(10)
  @Test
  fun `count monument position`() {
    assertEquals(
      1,
      runBlocking {
        MonumentPositionDao.countMonumentPositions()
      }
    )
  }

  @Order(11)
  @Test
  fun `delete monument position by identifier`() {
    assertEquals(
      1,
      runBlocking {
        MonumentPositionDao.deleteMonumentPositionByIdentifier(
          MONUMENT_POSITION.identifier
        )
      }
    )

    assertNull(
      runBlocking {
        MonumentPositionDao.findMonumentPositionByIdentifierOrNull(
          MONUMENT_POSITION.identifier
        )
      }
    )
  }

  @Order(12)
  @Test
  fun `delete non existent monument position by identifier`() {
    assertEquals(
      0,
      runBlocking {
        MonumentPositionDao.deleteMonumentPositionByIdentifier(
          uniqueUuid(MONUMENT_POSITION.identifier)
        )
      }
    )
  }

  @Order(13)
  @Test
  fun `delete monument positions`() {
    assertEquals(
      0,
      runBlocking {
        MonumentPositionDao.truncateMonumentPositions()
      }
    )
  }
}