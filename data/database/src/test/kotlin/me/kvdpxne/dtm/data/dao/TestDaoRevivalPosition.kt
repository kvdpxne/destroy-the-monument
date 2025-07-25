package me.kvdpxne.dtm.data.dao

import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue
import kotlinx.coroutines.runBlocking
import me.kvdpxne.dtm.data.ResponseCodes
import me.kvdpxne.dtm.data.validation.INVALID_REVIVAL_POSITION_PITCH
import me.kvdpxne.dtm.data.validation.INVALID_REVIVAL_POSITION_YAW
import me.kvdpxne.dtm.raw.factories.makeRawRevivalPosition
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
private val REVIVAL_POSITION = makeRawRevivalPosition()

/**
 * @since 0.1.0
 */
@Order(1)
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TestDaoRevivalPosition {

  /**
   * @since 0.1.0
   */
  @AfterAll
  fun `cleanup battlefield after battle`() {
    runBlocking {
      RevivalPositionDao.truncateRevivalPositions()
      TeamDao.truncateTeams()
    }
  }

  /**
   * @since 0.1.0
   */
  @BeforeAll
  fun `prepare battlefield`() {
    this.`cleanup battlefield after battle`()
    println(REVIVAL_POSITION.toStylishString().listed(2))
  }

  @Order(0)
  @Test
  fun `insert revival position`() {
    runBlocking {
      TeamDao.insertTeam(REVIVAL_POSITION.team)
    }

    assertEquals(
      1,
      runBlocking {
        RevivalPositionDao.insertRevivalPosition(REVIVAL_POSITION)
      }
    )
  }

  @Order(1)
  @Test
  fun `insert duplicated revival position`() {
    assertEquals(
      ResponseCodes.DUPLICATED,
      runBlocking {
        RevivalPositionDao.insertRevivalPosition(REVIVAL_POSITION)
      }
    )
  }

  @Order(2)
  @Test
  fun `insert revival position with invalid team reference`() {
    val invalid = REVIVAL_POSITION.copy(
      identifier = uniqueUuid(REVIVAL_POSITION.identifier),
      team = REVIVAL_POSITION.team.copy(
        identifier = uniqueUuid(REVIVAL_POSITION.team.identifier),
      )
    )

    assertEquals(
      ResponseCodes.NO_REFERENCE,
      runBlocking {
        RevivalPositionDao.insertRevivalPosition(invalid)
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
        RevivalPositionDao.insertRevivalPosition(tooMuch)
      }
    )

    val tooLittle = REVIVAL_POSITION.copy(
      pitch = -100F
    )

    assertEquals(
      INVALID_REVIVAL_POSITION_PITCH,
      runBlocking {
        RevivalPositionDao.insertRevivalPosition(tooLittle)
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
        RevivalPositionDao.insertRevivalPosition(tooMuch)
      }
    )

    val tooLittle = REVIVAL_POSITION.copy(
      yaw = -200F
    )

    assertEquals(
      INVALID_REVIVAL_POSITION_YAW,
      runBlocking {
        RevivalPositionDao.insertRevivalPosition(tooLittle)
      }
    )
  }

  @Order(5)
  @Test
  fun `find revival position by identifier`() {
    assertEquals(
      REVIVAL_POSITION,
      runBlocking {
        RevivalPositionDao.findRevivalPositionByIdentifierOrNull(
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
        RevivalPositionDao.findRevivalPositionByIdentifierOrNull(
          uniqueUuid(REVIVAL_POSITION.identifier)
        )
      }
    )
  }

  @Order(7)
  @Test
  fun `contains revival position by identifier`() {
    assertTrue(
      runBlocking {
        RevivalPositionDao.containsRevivalPositionByIdentifier(
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
        RevivalPositionDao.containsRevivalPositionByIdentifier(
          uniqueUuid(REVIVAL_POSITION.identifier)
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
        RevivalPositionDao.updateRevivalPosition(updated)
      }
    )

    assertEquals(
      updated,
      runBlocking {
        RevivalPositionDao.findRevivalPositionByIdentifierOrNull(
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
      identifier = uniqueUuid(REVIVAL_POSITION.identifier),
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
        RevivalPositionDao.updateRevivalPosition(updated)
      }
    )
  }

  @Order(11)
  @Test
  fun `update revival position with invalid team reference`() {
    val invalid = REVIVAL_POSITION.copy(
      team = REVIVAL_POSITION.team.copy(
        identifier = uniqueUuid(REVIVAL_POSITION.team.identifier),
      )
    )

    assertEquals(
      ResponseCodes.NO_REFERENCE,
      runBlocking {
        RevivalPositionDao.updateRevivalPosition(invalid)
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
        RevivalPositionDao.updateRevivalPosition(tooMuch)
      }
    )

    val tooLittle = REVIVAL_POSITION.copy(
      pitch = -100F
    )

    assertEquals(
      INVALID_REVIVAL_POSITION_PITCH,
      runBlocking {
        RevivalPositionDao.updateRevivalPosition(tooLittle)
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
        RevivalPositionDao.updateRevivalPosition(tooMuch)
      }
    )

    val tooLittle = REVIVAL_POSITION.copy(
      yaw = -200F
    )

    assertEquals(
      INVALID_REVIVAL_POSITION_YAW,
      runBlocking {
        RevivalPositionDao.updateRevivalPosition(tooLittle)
      }
    )
  }

  @Order(14)
  @Test
  fun `count revival position`() {
    assertEquals(
      1,
      runBlocking {
        RevivalPositionDao.countRevivalPositions()
      }
    )
  }

  @Order(15)
  @Test
  fun `delete revival position by identifier`() {
    assertEquals(
      1,
      runBlocking {
        RevivalPositionDao.deleteRevivalPositionByIdentifier(
          REVIVAL_POSITION.identifier
        )
      }
    )

    assertNull(
      runBlocking {
        RevivalPositionDao.findRevivalPositionByIdentifierOrNull(
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
        RevivalPositionDao.deleteRevivalPositionByIdentifier(
          uniqueUuid(REVIVAL_POSITION.identifier)
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
        RevivalPositionDao.truncateRevivalPositions()
      }
    )
  }
}