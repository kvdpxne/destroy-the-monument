package me.kvdpxne.dtm.data.dao

import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue
import kotlinx.coroutines.runBlocking
import me.kvdpxne.dtm.data.ResponseCodes
import me.kvdpxne.dtm.data.raw.RawUserStatistics
import me.kvdpxne.dtm.data.validation.INVALID_USER_STATISTICS_ASSISTS
import me.kvdpxne.dtm.data.validation.INVALID_USER_STATISTICS_DEATHS
import me.kvdpxne.dtm.data.validation.INVALID_USER_STATISTICS_DESTROYED_MONUMENTS
import me.kvdpxne.dtm.data.validation.INVALID_USER_STATISTICS_GAMES_LOST
import me.kvdpxne.dtm.data.validation.INVALID_USER_STATISTICS_GAMES_WON
import me.kvdpxne.dtm.data.validation.INVALID_USER_STATISTICS_KILLS
import me.kvdpxne.dtm.data.validation.INVALID_USER_STATISTICS_PLAYED_GAMES
import me.kvdpxne.dtm.raw.factories.makeRawUserStatistics
import me.kvdpxne.dtm.shared.randomInt
import me.kvdpxne.dtm.shared.randomPositiveInt
import me.kvdpxne.dtm.shared.uniqueUuid
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestMethodOrder

/**
 * Shared test fixture: Preconfigured [RawUserStatistics] instance for test operations.
 *
 * @since 0.1.0
 */
private val USER_STATISTICS: RawUserStatistics = makeRawUserStatistics()

/**
 * @since 0.1.0
 */
@Order(0)
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TestDaoUserStatistics {

  /**
   * @since 0.1.0
   */
  @AfterAll
  fun `clean up battlefield after battle`() {
    runBlocking {
      UserStatisticsDao.truncateUserStatistics()
    }
  }

  /**
   * @since 0.1.0
   */
  @BeforeAll
  fun `prepare battlefield`() {
    this.`clean up battlefield after battle`()
    println(USER_STATISTICS.toStylishString().listed(2))
  }

  /**
   * @since 0.1.0
   */
  @Order(0)
  @Test
  fun `insert user statistics`() {
    assertEquals(
      1,
      runBlocking {
        UserStatisticsDao.insertUserStatistics(USER_STATISTICS)
      }
    )
  }

  /**
   * @since 0.1.0
   */
  @Order(1)
  @Test
  fun `insert duplicated user statistics`() {
    assertEquals(
      ResponseCodes.DUPLICATED,
      runBlocking {
        UserStatisticsDao.insertUserStatistics(USER_STATISTICS)
      }
    )
  }

  /**
   * @since 0.1.0
   */
  @Order(2)
  @Test
  fun `insert user statistics with invalid kills`() {
    val invalid = USER_STATISTICS.copy(
      kills = randomInt(Int.MIN_VALUE, -1)
    )

    assertEquals(
      INVALID_USER_STATISTICS_KILLS,
      runBlocking {
        UserStatisticsDao.insertUserStatistics(invalid)
      }
    )
  }

  /**
   * @since 0.1.0
   */
  @Order(3)
  @Test
  fun `insert user statistics with invalid assists`() {
    val invalid = USER_STATISTICS.copy(
      assists = randomInt(Int.MIN_VALUE, -1)
    )

    assertEquals(
      INVALID_USER_STATISTICS_ASSISTS,
      runBlocking {
        UserStatisticsDao.insertUserStatistics(invalid)
      }
    )
  }

  /**
   * @since 0.1.0
   */
  @Order(4)
  @Test
  fun `insert user statistics with invalid deaths`() {
    val invalid = USER_STATISTICS.copy(
      deaths = randomInt(Int.MIN_VALUE, -1)
    )

    assertEquals(
      INVALID_USER_STATISTICS_DEATHS,
      runBlocking {
        UserStatisticsDao.insertUserStatistics(invalid)
      }
    )
  }

  /**
   * @since 0.1.0
   */
  @Order(5)
  @Test
  fun `insert user statistics with invalid destroyed monuments`() {
    val invalid = USER_STATISTICS.copy(
      destroyedMonuments = randomInt(Int.MIN_VALUE, -1)
    )

    assertEquals(
      INVALID_USER_STATISTICS_DESTROYED_MONUMENTS,
      runBlocking {
        UserStatisticsDao.insertUserStatistics(invalid)
      }
    )
  }

  /**
   * @since 0.1.0
   */
  @Order(6)
  @Test
  fun `insert user statistics with invalid played games`() {
    val invalid = USER_STATISTICS.copy(
      playedGames = randomInt(Int.MIN_VALUE, -1)
    )

    assertEquals(
      INVALID_USER_STATISTICS_PLAYED_GAMES,
      runBlocking {
        UserStatisticsDao.insertUserStatistics(invalid)
      }
    )
  }

  /**
   * @since 0.1.0
   */
  @Order(7)
  @Test
  fun `insert user statistics with invalid games won`() {
    val invalid = USER_STATISTICS.copy(
      gamesWon = randomInt(Int.MIN_VALUE, -1)
    )

    assertEquals(
      INVALID_USER_STATISTICS_GAMES_WON,
      runBlocking {
        UserStatisticsDao.insertUserStatistics(invalid)
      }
    )
  }

  /**
   * @since 0.1.0
   */
  @Order(8)
  @Test
  fun `insert user statistics with invalid games lost`() {
    val invalid = USER_STATISTICS.copy(
      gamesLost = randomInt(Int.MIN_VALUE, -1)
    )

    assertEquals(
      INVALID_USER_STATISTICS_GAMES_LOST,
      runBlocking {
        UserStatisticsDao.insertUserStatistics(invalid)
      }
    )
  }

  /**
   * @since 0.1.0
   */
  @Order(9)
  @Test
  fun `find user statistics by identifier`() {
    assertEquals(
      USER_STATISTICS,
      runBlocking {
        UserStatisticsDao.findUserStatisticsByIdentifierOrNull(
          USER_STATISTICS.identifier
        )
      }
    )
  }

  /**
   * @since 0.1.0
   */
  @Order(10)
  @Test
  fun `find non existent user statistics by identifier`() {
    assertNull(
      runBlocking {
        UserStatisticsDao.findUserStatisticsByIdentifierOrNull(
          uniqueUuid(USER_STATISTICS.identifier)
        )
      }
    )
  }

  /**
   * @since 0.1.0
   */
  @Order(11)
  @Test
  fun `contains user statistics by identifier`() {
    assertTrue(
      runBlocking {
        UserStatisticsDao.containsUserStatisticsByIdentifier(
          USER_STATISTICS.identifier
        )
      }
    )
  }

  /**
   * @since 0.1.0
   */
  @Order(12)
  @Test
  fun `contains non existent user statistics by identifier`() {
    assertFalse(
      runBlocking {
        UserStatisticsDao.containsUserStatisticsByIdentifier(
          uniqueUuid(USER_STATISTICS.identifier)
        )
      }
    )
  }

  /**
   * @since 0.1.0
   */
  @Order(13)
  @Test
  fun `update user statistics`() {
    val updated = USER_STATISTICS.copy(
      // @formatter:off
      kills              = randomPositiveInt(),
      assists            = randomPositiveInt(),
      deaths             = randomPositiveInt(),
      destroyedMonuments = randomPositiveInt(),
      playedGames        = randomPositiveInt(),
      gamesWon           = randomPositiveInt(),
      gamesLost          = randomPositiveInt()
      // @formatter:on
    )

    assertEquals(
      1,
      runBlocking {
        UserStatisticsDao.updateUserStatistics(updated)
      }
    )

    assertEquals(
      updated,
      runBlocking {
        UserStatisticsDao.findUserStatisticsByIdentifierOrNull(
          updated.identifier
        )
      }
    )
  }

  /**
   * @since 0.1.0
   */
  @Order(14)
  @Test
  fun `update non existent user statistics`() {
    val updated = USER_STATISTICS.copy(
      // @formatter:off
      identifier         = uniqueUuid(USER_STATISTICS.identifier),
      kills              = randomPositiveInt(),
      assists            = randomPositiveInt(),
      deaths             = randomPositiveInt(),
      destroyedMonuments = randomPositiveInt(),
      playedGames        = randomPositiveInt(),
      gamesWon           = randomPositiveInt(),
      gamesLost          = randomPositiveInt()
      // @formatter:on
    )

    assertEquals(
      ResponseCodes.NO_RECORD,
      runBlocking {
        UserStatisticsDao.updateUserStatistics(updated)
      }
    )
  }

  /**
   * @since 0.1.0
   */
  @Order(15)
  @Test
  fun `update user statistics with invalid kills`() {
    val invalid = USER_STATISTICS.copy(
      kills = randomInt(Int.MIN_VALUE, -1)
    )

    assertEquals(
      INVALID_USER_STATISTICS_KILLS,
      runBlocking {
        UserStatisticsDao.updateUserStatistics(invalid)
      }
    )
  }

  /**
   * @since 0.1.0
   */
  @Order(16)
  @Test
  fun `update user statistics with invalid assists`() {
    val invalid = USER_STATISTICS.copy(
      assists = randomInt(Int.MIN_VALUE, -1)
    )

    assertEquals(
      INVALID_USER_STATISTICS_ASSISTS,
      runBlocking {
        UserStatisticsDao.updateUserStatistics(invalid)
      }
    )
  }

  /**
   * @since 0.1.0
   */
  @Order(17)
  @Test
  fun `update user statistics with invalid deaths`() {
    val invalid = USER_STATISTICS.copy(
      deaths = randomInt(Int.MIN_VALUE, -1)
    )

    assertEquals(
      INVALID_USER_STATISTICS_DEATHS,
      runBlocking {
        UserStatisticsDao.updateUserStatistics(invalid)
      }
    )
  }

  /**
   * @since 0.1.0
   */
  @Order(18)
  @Test
  fun `update user statistics with invalid destroyed monuments`() {
    val invalid = USER_STATISTICS.copy(
      destroyedMonuments = randomInt(Int.MIN_VALUE, -1)
    )

    assertEquals(
      INVALID_USER_STATISTICS_DESTROYED_MONUMENTS,
      runBlocking {
        UserStatisticsDao.updateUserStatistics(invalid)
      }
    )
  }

  /**
   * @since 0.1.0
   */
  @Order(19)
  @Test
  fun `update user statistics with invalid played games`() {
    val invalid = USER_STATISTICS.copy(
      playedGames = randomInt(Int.MIN_VALUE, -1)
    )

    assertEquals(
      INVALID_USER_STATISTICS_PLAYED_GAMES,
      runBlocking {
        UserStatisticsDao.updateUserStatistics(invalid)
      }
    )
  }

  /**
   * @since 0.1.0
   */
  @Order(20)
  @Test
  fun `update user statistics with invalid games won`() {
    val invalid = USER_STATISTICS.copy(
      gamesWon = randomInt(Int.MIN_VALUE, -1)
    )

    assertEquals(
      INVALID_USER_STATISTICS_GAMES_WON,
      runBlocking {
        UserStatisticsDao.updateUserStatistics(invalid)
      }
    )
  }

  /**
   * @since 0.1.0
   */
  @Order(21)
  @Test
  fun `update user statistics with invalid games lost`() {
    val invalid = USER_STATISTICS.copy(
      gamesLost = randomInt(Int.MIN_VALUE, -1)
    )

    assertEquals(
      INVALID_USER_STATISTICS_GAMES_LOST,
      runBlocking {
        UserStatisticsDao.updateUserStatistics(invalid)
      }
    )
  }

  /**
   * @since 0.1.0
   */
  @Order(22)
  @Test
  fun `count user statistics`() {
    assertEquals(
      1,
      runBlocking {
        UserStatisticsDao.countUserStatistics()
      }
    )
  }

  /**
   * @since 0.1.0
   */
  @Order(23)
  @Test
  fun `delete user statistics by identifier`() {
    assertEquals(
      1,
      runBlocking {
        UserStatisticsDao.deleteUserStatisticsByIdentifier(
          USER_STATISTICS.identifier
        )
      }
    )

    assertNull(
      runBlocking {
        UserStatisticsDao.findUserStatisticsByIdentifierOrNull(
          USER_STATISTICS.identifier
        )
      }
    )
  }

  /**
   * @since 0.1.0
   */
  @Order(24)
  @Test
  fun `delete non existent user statistics by identifier`() {
    assertEquals(
      0,
      runBlocking {
        UserStatisticsDao.deleteUserStatisticsByIdentifier(
          uniqueUuid(USER_STATISTICS.identifier)
        )
      }
    )
  }

  /**
   * @since 0.1.0
   */
  @Order(25)
  @Test
  fun `delete user statistics`() {
    assertEquals(
      0,
      runBlocking {
        UserStatisticsDao.truncateUserStatistics()
      }
    )
  }
}