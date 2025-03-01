package me.kvdpxne.dtm.data.dao

import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue
import kotlinx.coroutines.runBlocking
import me.kvdpxne.dtm.data.ResponseCodes
import me.kvdpxne.dtm.data.raw.RawRevivalPosition
import me.kvdpxne.dtm.data.raw.RawTeam
import me.kvdpxne.dtm.data.util.UniqueUuid
import me.kvdpxne.dtm.data.validation.INVALID_REVIVAL_POSITION_PITCH
import me.kvdpxne.dtm.data.validation.INVALID_REVIVAL_POSITION_YAW
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestMethodOrder

@Order(1)
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TestDaoRevivalPosition {

  companion object {

    // Creating an unacceptable object via the constructor is always possible
    // but should be used only for testing.
    internal val REVIVAL_POSITION = RawRevivalPosition(
      // @formatter:off
      identifier = UniqueUuid.v4(),
      team       = RawTeam(
        identifier        = UniqueUuid.v4(),
        name              = "red",
        colorOfArmor      = "#cd0a00",
        colorOfProfession = "&4",
        colorOnChat       = "&c",
        colorOnPlayerList = "&c"
      ),
      x          = 60.0,
      y          = 120.0,
      z          = 60.0,
      pitch      = 90F,
      yaw        = 90F
      // @formatter:on
    )
  }

  @Order(0)
  @Test
  fun `insert revival position`() {
    runBlocking {
      DaoTeam.insertTeam(REVIVAL_POSITION.team)
    }

    assertEquals(
      1,
      runBlocking {
        DaoRevivalPosition.insertRevivalPosition(REVIVAL_POSITION)
      }
    )
  }

  @Order(1)
  @Test
  fun `insert duplicated revival position`() {
    assertEquals(
      ResponseCodes.DUPLICATED,
      runBlocking {
        DaoRevivalPosition.insertRevivalPosition(REVIVAL_POSITION)
      }
    )
  }

  @Order(2)
  @Test
  fun `insert revival position with invalid team reference`() {
    val invalid = REVIVAL_POSITION.copy(
      identifier = UniqueUuid.v4(REVIVAL_POSITION.identifier),
      team = REVIVAL_POSITION.team.copy(
        identifier = UniqueUuid.v4(REVIVAL_POSITION.team.identifier),
      )
    )

    assertEquals(
      ResponseCodes.NO_REFERENCE,
      runBlocking {
        DaoRevivalPosition.insertRevivalPosition(invalid)
      }
    )
  }

  @Order(3)
  @Test
  fun `insert revival position with invalid pitch`() {
    val tooMuch = REVIVAL_POSITION.copy(
      pitch = 100F
    )

    assertEquals(
      INVALID_REVIVAL_POSITION_PITCH,
      runBlocking {
        DaoRevivalPosition.insertRevivalPosition(tooMuch)
      }
    )

    val tooLittle = REVIVAL_POSITION.copy(
      pitch = -100F
    )

    assertEquals(
      INVALID_REVIVAL_POSITION_PITCH,
      runBlocking {
        DaoRevivalPosition.insertRevivalPosition(tooLittle)
      }
    )
  }

  @Order(4)
  @Test
  fun `insert revival position with invalid yaw`() {
    val tooMuch = REVIVAL_POSITION.copy(
      yaw = 200F
    )

    assertEquals(
      INVALID_REVIVAL_POSITION_YAW,
      runBlocking {
        DaoRevivalPosition.insertRevivalPosition(tooMuch)
      }
    )

    val tooLittle = REVIVAL_POSITION.copy(
      yaw = -200F
    )

    assertEquals(
      INVALID_REVIVAL_POSITION_YAW,
      runBlocking {
        DaoRevivalPosition.insertRevivalPosition(tooLittle)
      }
    )
  }

  @Order(5)
  @Test
  fun `find revival position by identifier`() {
    assertEquals(
      REVIVAL_POSITION,
      runBlocking {
        DaoRevivalPosition.findRevivalPositionByIdentifierOrNull(
          REVIVAL_POSITION.identifier
        )
      }
    )
  }

  @Order(6)
  @Test
  fun `find non existent revival position by identifier`() {
    assertNull(
      runBlocking {
        DaoRevivalPosition.findRevivalPositionByIdentifierOrNull(
          UniqueUuid.v4(REVIVAL_POSITION.identifier)
        )
      }
    )
  }

  @Order(7)
  @Test
  fun `contains revival position by identifier`() {
    assertTrue(
      runBlocking {
        DaoRevivalPosition.containsRevivalPositionByIdentifier(
          REVIVAL_POSITION.identifier
        )
      }
    )
  }

  @Order(8)
  @Test
  fun `contains non existent revival position by identifier`() {
    assertFalse(
      runBlocking {
        DaoRevivalPosition.containsRevivalPositionByIdentifier(
          UniqueUuid.v4(REVIVAL_POSITION.identifier)
        )
      }
    )
  }

  @Order(9)
  @Test
  fun `update revival position`() {
    val updated = REVIVAL_POSITION.copy(
      // @formatter:off
      x     = 990.0,
      y     = 63.25,
      z     = -275.5,
      pitch = 64F,
      yaw   = -91.33F
      // @formatter:on
    )

    assertEquals(
      1,
      runBlocking {
        DaoRevivalPosition.updateRevivalPosition(updated)
      }
    )

    assertEquals(
      updated,
      runBlocking {
        DaoRevivalPosition.findRevivalPositionByIdentifierOrNull(
          updated.identifier
        )
      }
    )
  }

  @Order(10)
  @Test
  fun `update non existent revival position`() {
    val updated = REVIVAL_POSITION.copy(
      // @formatter:off
      identifier = UniqueUuid.v4(REVIVAL_POSITION.identifier),
      x          = 990.0,
      y          = 63.25,
      z          = -275.5,
      pitch      = 64F,
      yaw        = -91.33F
      // @formatter:on
    )

    assertEquals(
      ResponseCodes.NO_RECORD,
      runBlocking {
        DaoRevivalPosition.updateRevivalPosition(updated)
      }
    )
  }

  @Order(11)
  @Test
  fun `update revival position with invalid team reference`() {
    val invalid = REVIVAL_POSITION.copy(
      team = REVIVAL_POSITION.team.copy(
        identifier = UniqueUuid.v4(REVIVAL_POSITION.team.identifier),
      )
    )

    assertEquals(
      ResponseCodes.NO_REFERENCE,
      runBlocking {
        DaoRevivalPosition.updateRevivalPosition(invalid)
      }
    )
  }

  @Order(12)
  @Test
  fun `update revival position with invalid pitch`() {
    val tooMuch = REVIVAL_POSITION.copy(
      pitch = 100F
    )

    assertEquals(
      INVALID_REVIVAL_POSITION_PITCH,
      runBlocking {
        DaoRevivalPosition.updateRevivalPosition(tooMuch)
      }
    )

    val tooLittle = REVIVAL_POSITION.copy(
      pitch = -100F
    )

    assertEquals(
      INVALID_REVIVAL_POSITION_PITCH,
      runBlocking {
        DaoRevivalPosition.updateRevivalPosition(tooLittle)
      }
    )
  }

  @Order(13)
  @Test
  fun `update revival position with invalid yaw`() {
    val tooMuch = REVIVAL_POSITION.copy(
      yaw = 200F
    )

    assertEquals(
      INVALID_REVIVAL_POSITION_YAW,
      runBlocking {
        DaoRevivalPosition.updateRevivalPosition(tooMuch)
      }
    )

    val tooLittle = REVIVAL_POSITION.copy(
      yaw = -200F
    )

    assertEquals(
      INVALID_REVIVAL_POSITION_YAW,
      runBlocking {
        DaoRevivalPosition.updateRevivalPosition(tooLittle)
      }
    )
  }

  @Order(14)
  @Test
  fun `count revival position`() {
    assertEquals(
      1,
      runBlocking {
        DaoRevivalPosition.countRevivalPositions()
      }
    )
  }

  @Order(15)
  @Test
  fun `delete revival position by identifier`() {
    assertEquals(
      1,
      runBlocking {
        DaoRevivalPosition.deleteRevivalPositionByIdentifier(
          REVIVAL_POSITION.identifier
        )
      }
    )

    assertNull(
      runBlocking {
        DaoRevivalPosition.findRevivalPositionByIdentifierOrNull(
          REVIVAL_POSITION.identifier
        )
      }
    )
  }

  @Order(16)
  @Test
  fun `delete non existent revival position by identifier`() {
    assertEquals(
      0,
      runBlocking {
        DaoRevivalPosition.deleteRevivalPositionByIdentifier(
          UniqueUuid.v4(REVIVAL_POSITION.identifier)
        )
      }
    )
  }

  @Order(17)
  @Test
  fun `delete revival positions`() {
    assertEquals(
      0,
      runBlocking {
        DaoRevivalPosition.truncateRevivalPositions()
      }
    )
  }

  @AfterAll
  fun `delete revival positions after all`() {
    try {
      runBlocking {
        DaoRevivalPosition.truncateRevivalPositions()
        DaoTeam.truncateTeams()
      }
    } catch (_: Throwable) {
    }
  }
}