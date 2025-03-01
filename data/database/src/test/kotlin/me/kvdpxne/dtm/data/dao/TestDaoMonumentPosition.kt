package me.kvdpxne.dtm.data.dao

import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue
import kotlinx.coroutines.runBlocking
import me.kvdpxne.dtm.data.ResponseCodes
import me.kvdpxne.dtm.data.raw.RawMonumentPosition
import me.kvdpxne.dtm.data.raw.RawTeam
import me.kvdpxne.dtm.data.util.UniqueUuid
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestMethodOrder

@Order(1)
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TestDaoMonumentPosition {

  companion object {

    // Creating an unacceptable object via the constructor is always possible
    // but should be used only for testing.
    internal val MONUMENT_POSITION = RawMonumentPosition(
      // @formatter:off
      identifier = UniqueUuid.v4(),
      team       = RawTeam(
        identifier        = UniqueUuid.v4(),
        name              = "blue",
        colorOfArmor      = "#000acd",
        colorOfProfession = "&2",
        colorOnChat       = "&9",
        colorOnPlayerList = "&9"
      ),
      x          = 60,
      y          = 120,
      z          = 60
      // @formatter:on
    )
  }

  @Order(0)
  @Test
  fun `insert monument position`() {
    runBlocking {
      DaoTeam.insertTeam(MONUMENT_POSITION.team)
    }

    assertEquals(
      1,
      runBlocking {
        DaoMonumentPosition.insertMonumentPosition(MONUMENT_POSITION)
      }
    )
  }

  @Order(1)
  @Test
  fun `insert duplicated monument position`() {
    assertEquals(
      ResponseCodes.DUPLICATED,
      runBlocking {
        DaoMonumentPosition.insertMonumentPosition(MONUMENT_POSITION)
      }
    )
  }

  @Order(2)
  @Test
  fun `insert monument position with invalid team reference`() {
    val invalid = MONUMENT_POSITION.copy(
      identifier = UniqueUuid.v4(MONUMENT_POSITION.identifier),
      team = MONUMENT_POSITION.team.copy(
        identifier = UniqueUuid.v4(MONUMENT_POSITION.team.identifier),
      )
    )

    assertEquals(
      ResponseCodes.NO_REFERENCE,
      runBlocking {
        DaoMonumentPosition.insertMonumentPosition(invalid)
      }
    )
  }

  @Order(3)
  @Test
  fun `find monument position by identifier`() {
    assertEquals(
      MONUMENT_POSITION,
      runBlocking {
        DaoMonumentPosition.findMonumentPositionByIdentifierOrNull(
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
        DaoMonumentPosition.findMonumentPositionByIdentifierOrNull(
          UniqueUuid.v4(MONUMENT_POSITION.identifier)
        )
      }
    )
  }

  @Order(5)
  @Test
  fun `contains monument position by identifier`() {
    assertTrue(
      runBlocking {
        DaoMonumentPosition.containsMonumentPositionByIdentifier(
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
        DaoMonumentPosition.containsMonumentPositionByIdentifier(
          UniqueUuid.v4(MONUMENT_POSITION.identifier)
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
        DaoMonumentPosition.updateMonumentPosition(updated)
      }
    )

    assertEquals(
      updated,
      runBlocking {
        DaoMonumentPosition.findMonumentPositionByIdentifierOrNull(
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
      identifier = UniqueUuid.v4(MONUMENT_POSITION.identifier),
      x          = 1000,
      y          = 20,
      z          = -500
      // @formatter:on
    )

    assertEquals(
      ResponseCodes.NO_RECORD,
      runBlocking {
        DaoMonumentPosition.updateMonumentPosition(updated)
      }
    )
  }

  @Order(9)
  @Test
  fun `update monument position with invalid team reference`() {
    val invalid = MONUMENT_POSITION.copy(
      team = MONUMENT_POSITION.team.copy(
        identifier = UniqueUuid.v4(MONUMENT_POSITION.team.identifier),
      )
    )

    assertEquals(
      ResponseCodes.NO_REFERENCE,
      runBlocking {
        DaoMonumentPosition.updateMonumentPosition(invalid)
      }
    )
  }

  @Order(10)
  @Test
  fun `count monument position`() {
    assertEquals(
      1,
      runBlocking {
        DaoMonumentPosition.countMonumentPositions()
      }
    )
  }

  @Order(11)
  @Test
  fun `delete monument position by identifier`() {
    assertEquals(
      1,
      runBlocking {
        DaoMonumentPosition.deleteMonumentPositionByIdentifier(
          MONUMENT_POSITION.identifier
        )
      }
    )

    assertNull(
      runBlocking {
        DaoMonumentPosition.findMonumentPositionByIdentifierOrNull(
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
        DaoMonumentPosition.deleteMonumentPositionByIdentifier(
          UniqueUuid.v4(MONUMENT_POSITION.identifier)
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
        DaoMonumentPosition.truncateMonumentPositions()
      }
    )
  }

  @AfterAll
  fun `delete monument positions after all`() {
    try {
      runBlocking {
        DaoMonumentPosition.truncateMonumentPositions()
        DaoTeam.truncateTeams()
      }
    } catch (_: Throwable) {
    }
  }
}