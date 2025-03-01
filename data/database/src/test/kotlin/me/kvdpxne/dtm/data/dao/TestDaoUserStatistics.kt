package me.kvdpxne.dtm.data.dao

import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue
import kotlinx.coroutines.runBlocking
import me.kvdpxne.dtm.data.ResponseCodes
import me.kvdpxne.dtm.data.raw.RawUserStatistics
import me.kvdpxne.dtm.data.util.UniqueUuid
import me.kvdpxne.dtm.data.validation.INVALID_USER_STATISTICS_ASSISTS
import me.kvdpxne.dtm.data.validation.INVALID_USER_STATISTICS_DEATHS
import me.kvdpxne.dtm.data.validation.INVALID_USER_STATISTICS_DESTROYED_MONUMENTS
import me.kvdpxne.dtm.data.validation.INVALID_USER_STATISTICS_GAMES_LOST
import me.kvdpxne.dtm.data.validation.INVALID_USER_STATISTICS_GAMES_WON
import me.kvdpxne.dtm.data.validation.INVALID_USER_STATISTICS_KILLS
import me.kvdpxne.dtm.data.validation.INVALID_USER_STATISTICS_PLAYED_GAMES
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestMethodOrder

@Order(0)
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TestDaoUserStatistics {

  companion object {

    // Creating an unacceptable object via the constructor is always possible
    // but should be used only for testing.
    internal val USER_STATISTICS = RawUserStatistics(
      // @formatter:off
      identifier         = UniqueUuid.v4(),
      kills              = 0,
      assists            = 0,
      deaths             = 0,
      destroyedMonuments = 0,
      playedGames        = 0,
      gamesWon           = 0,
      gamesLost          = 0
      // @formatter:on
    )
  }

  @Order(0)
  @Test
  fun `insert user statistics`() {
    assertEquals(
      1,
      runBlocking {
        DaoUserStatistics.insertUserStatistics(USER_STATISTICS)
      }
    )
  }

  @Order(1)
  @Test
  fun `insert duplicated user statistics`() {
    assertEquals(
      ResponseCodes.DUPLICATED,
      runBlocking {
        DaoUserStatistics.insertUserStatistics(USER_STATISTICS)
      }
    )
  }

  @Order(2)
  @Test
  fun `insert user statistics with invalid kills`() {
    val invalid = USER_STATISTICS.copy(
      kills = -1
    )

    assertEquals(
      INVALID_USER_STATISTICS_KILLS,
      runBlocking {
        DaoUserStatistics.insertUserStatistics(invalid)
      }
    )
  }

  @Order(3)
  @Test
  fun `insert user statistics with invalid assists`() {
    val invalid = USER_STATISTICS.copy(
      assists = -1
    )

    assertEquals(
      INVALID_USER_STATISTICS_ASSISTS,
      runBlocking {
        DaoUserStatistics.insertUserStatistics(invalid)
      }
    )
  }

  @Order(4)
  @Test
  fun `insert user statistics with invalid deaths`() {
    val invalid = USER_STATISTICS.copy(
      deaths = -1
    )

    assertEquals(
      INVALID_USER_STATISTICS_DEATHS,
      runBlocking {
        DaoUserStatistics.insertUserStatistics(invalid)
      }
    )
  }

  @Order(5)
  @Test
  fun `insert user statistics with invalid destroyed monuments`() {
    val invalid = USER_STATISTICS.copy(
      destroyedMonuments = -1
    )

    assertEquals(
      INVALID_USER_STATISTICS_DESTROYED_MONUMENTS,
      runBlocking {
        DaoUserStatistics.insertUserStatistics(invalid)
      }
    )
  }

  @Order(6)
  @Test
  fun `insert user statistics with invalid played games`() {
    val invalid = USER_STATISTICS.copy(
      playedGames = -1
    )

    assertEquals(
      INVALID_USER_STATISTICS_PLAYED_GAMES,
      runBlocking {
        DaoUserStatistics.insertUserStatistics(invalid)
      }
    )
  }

  @Order(7)
  @Test
  fun `insert user statistics with invalid games won`() {
    val invalid = USER_STATISTICS.copy(
      gamesWon = -1
    )

    assertEquals(
      INVALID_USER_STATISTICS_GAMES_WON,
      runBlocking {
        DaoUserStatistics.insertUserStatistics(invalid)
      }
    )
  }

  @Order(8)
  @Test
  fun `insert user statistics with invalid games lost`() {
    val invalid = USER_STATISTICS.copy(
      gamesLost = -1
    )

    assertEquals(
      INVALID_USER_STATISTICS_GAMES_LOST,
      runBlocking {
        DaoUserStatistics.insertUserStatistics(invalid)
      }
    )
  }

  @Order(9)
  @Test
  fun `find user statistics by identifier`() {
    assertEquals(
      USER_STATISTICS,
      runBlocking {
        DaoUserStatistics.findUserStatisticsByIdentifierOrNull(
          USER_STATISTICS.identifier
        )
      }
    )
  }

  @Order(10)
  @Test
  fun `find non existent user statistics by identifier`() {
    assertNull(
      runBlocking {
        DaoUserStatistics.findUserStatisticsByIdentifierOrNull(
          UniqueUuid.v4(USER_STATISTICS.identifier)
        )
      }
    )
  }

  @Order(11)
  @Test
  fun `contains user statistics by identifier`() {
    assertTrue(
      runBlocking {
        DaoUserStatistics.containsUserStatisticsByIdentifier(
          USER_STATISTICS.identifier
        )
      }
    )
  }

  @Order(12)
  @Test
  fun `contains non existent user statistics by identifier`() {
    assertFalse(
      runBlocking {
        DaoUserStatistics.containsUserStatisticsByIdentifier(
          UniqueUuid.v4(USER_STATISTICS.identifier)
        )
      }
    )
  }

  @Order(13)
  @Test
  fun `update user statistics`() {
    val updated = USER_STATISTICS.copy(
      // @formatter:off
      kills              = 42,
      assists            = 9,
      deaths             = 34,
      destroyedMonuments = 2,
      playedGames        = 3,
      gamesWon           = 2,
      gamesLost          = 1
      // @formatter:on
    )

    assertEquals(
      1,
      runBlocking {
        DaoUserStatistics.updateUserStatistics(updated)
      }
    )

    assertEquals(
      updated,
      runBlocking {
        DaoUserStatistics.findUserStatisticsByIdentifierOrNull(
          updated.identifier
        )
      }
    )
  }

  @Order(14)
  @Test
  fun `update non existent user statistics`() {
    val updated = USER_STATISTICS.copy(
      // @formatter:off
      identifier         = UniqueUuid.v4(USER_STATISTICS.identifier),
      kills              = 42,
      assists            = 9,
      deaths             = 34,
      destroyedMonuments = 2,
      playedGames        = 3,
      gamesWon           = 2,
      gamesLost          = 1
      // @formatter:on
    )

    assertEquals(
      ResponseCodes.NO_RECORD,
      runBlocking {
        DaoUserStatistics.updateUserStatistics(updated)
      }
    )
  }

  @Order(15)
  @Test
  fun `update user statistics with invalid kills`() {
    val invalid = USER_STATISTICS.copy(
      kills = -1
    )

    assertEquals(
      INVALID_USER_STATISTICS_KILLS,
      runBlocking {
        DaoUserStatistics.updateUserStatistics(invalid)
      }
    )
  }

  @Order(16)
  @Test
  fun `update user statistics with invalid assists`() {
    val invalid = USER_STATISTICS.copy(
      assists = -1
    )

    assertEquals(
      INVALID_USER_STATISTICS_ASSISTS,
      runBlocking {
        DaoUserStatistics.updateUserStatistics(invalid)
      }
    )
  }

  @Order(17)
  @Test
  fun `update user statistics with invalid deaths`() {
    val invalid = USER_STATISTICS.copy(
      deaths = -1
    )

    assertEquals(
      INVALID_USER_STATISTICS_DEATHS,
      runBlocking {
        DaoUserStatistics.updateUserStatistics(invalid)
      }
    )
  }

  @Order(18)
  @Test
  fun `update user statistics with invalid destroyed monuments`() {
    val invalid = USER_STATISTICS.copy(
      destroyedMonuments = -1
    )

    assertEquals(
      INVALID_USER_STATISTICS_DESTROYED_MONUMENTS,
      runBlocking {
        DaoUserStatistics.updateUserStatistics(invalid)
      }
    )
  }

  @Order(19)
  @Test
  fun `update user statistics with invalid played games`() {
    val invalid = USER_STATISTICS.copy(
      playedGames = -1
    )

    assertEquals(
      INVALID_USER_STATISTICS_PLAYED_GAMES,
      runBlocking {
        DaoUserStatistics.updateUserStatistics(invalid)
      }
    )
  }

  @Order(20)
  @Test
  fun `update user statistics with invalid games won`() {
    val invalid = USER_STATISTICS.copy(
      gamesWon = -1
    )

    assertEquals(
      INVALID_USER_STATISTICS_GAMES_WON,
      runBlocking {
        DaoUserStatistics.updateUserStatistics(invalid)
      }
    )
  }

  @Order(21)
  @Test
  fun `update user statistics with invalid games lost`() {
    val invalid = USER_STATISTICS.copy(
      gamesLost = -1
    )

    assertEquals(
      INVALID_USER_STATISTICS_GAMES_LOST,
      runBlocking {
        DaoUserStatistics.updateUserStatistics(invalid)
      }
    )
  }

  @Order(22)
  @Test
  fun `count user statistics`() {
    assertEquals(
      1,
      runBlocking {
        DaoUserStatistics.countUserStatistics()
      }
    )
  }

  @Order(23)
  @Test
  fun `delete user statistics by identifier`() {
    assertEquals(
      1,
      runBlocking {
        DaoUserStatistics.deleteUserStatisticsByIdentifier(
          USER_STATISTICS.identifier
        )
      }
    )

    assertNull(
      runBlocking {
        DaoUserStatistics.findUserStatisticsByIdentifierOrNull(
          USER_STATISTICS.identifier
        )
      }
    )
  }

  @Order(24)
  @Test
  fun `delete non existent user statistics by identifier`() {
    assertEquals(
      0,
      runBlocking {
        DaoUserStatistics.deleteUserStatisticsByIdentifier(
          UniqueUuid.v4(USER_STATISTICS.identifier)
        )
      }
    )
  }

  @Order(25)
  @Test
  fun `delete user statistics`() {
    assertEquals(
      0,
      runBlocking {
        DaoUserStatistics.truncateUserStatistics()
      }
    )
  }

  @AfterAll
  fun `delete user statistics after all`() {
    try {
      runBlocking {
        DaoUserStatistics.truncateUserStatistics()
      }
    } catch (_: Throwable) {
    }
  }
}