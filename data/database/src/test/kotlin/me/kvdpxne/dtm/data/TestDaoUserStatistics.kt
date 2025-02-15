package me.kvdpxne.dtm.data

import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlinx.coroutines.runBlocking
import me.kvdpxne.dtm.data.raw.RawUserStatistics
import me.kvdpxne.dtm.data.util.UniqueUuid
import org.jetbrains.exposed.exceptions.ExposedSQLException
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestMethodOrder
import org.junit.jupiter.api.assertThrows

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
  fun insert_user_statistics() {
    assertEquals(
      1,
      runBlocking {
        DaoUserStatistics.insertUserStatistics(USER_STATISTICS)
      }
    )
  }

  @Order(1)
  @Test
  fun insert_duplicated_user_statistics() {
    assertThrows<ExposedSQLException> {
      runBlocking {
        DaoUserStatistics.insertUserStatistics(USER_STATISTICS)
      }
    }
  }

  @Order(2)
  @Test
  fun insert_user_statistics_with_invalid_kills() {
    val invalid = RawUserStatistics(
      // @formatter:off
      identifier         = UniqueUuid.v4(USER_STATISTICS.identifier),
      kills              = -1,
      assists            = USER_STATISTICS.assists,
      deaths             = USER_STATISTICS.deaths,
      destroyedMonuments = USER_STATISTICS.destroyedMonuments,
      playedGames        = USER_STATISTICS.playedGames,
      gamesWon           = USER_STATISTICS.gamesWon,
      gamesLost          = USER_STATISTICS.gamesLost
      // @formatter:on
    )

    assertThrows<ExposedSQLException> {
      runBlocking {
        DaoUserStatistics.insertUserStatistics(invalid)
      }
    }
  }

  @Order(3)
  @Test
  fun insert_user_statistics_with_invalid_assists() {
    val invalid = RawUserStatistics(
      // @formatter:off
      identifier         = UniqueUuid.v4(USER_STATISTICS.identifier),
      kills              = USER_STATISTICS.kills,
      assists            = -1,
      deaths             = USER_STATISTICS.deaths,
      destroyedMonuments = USER_STATISTICS.destroyedMonuments,
      playedGames        = USER_STATISTICS.playedGames,
      gamesWon           = USER_STATISTICS.gamesWon,
      gamesLost          = USER_STATISTICS.gamesLost
      // @formatter:on
    )

    assertThrows<ExposedSQLException> {
      runBlocking {
        DaoUserStatistics.insertUserStatistics(invalid)
      }
    }
  }

  @Order(4)
  @Test
  fun insert_user_statistics_with_invalid_deaths() {
    val invalid = RawUserStatistics(
      // @formatter:off
      identifier         = UniqueUuid.v4(USER_STATISTICS.identifier),
      kills              = USER_STATISTICS.kills,
      assists            = USER_STATISTICS.assists,
      deaths             = -1,
      destroyedMonuments = USER_STATISTICS.destroyedMonuments,
      playedGames        = USER_STATISTICS.playedGames,
      gamesWon           = USER_STATISTICS.gamesWon,
      gamesLost          = USER_STATISTICS.gamesLost
      // @formatter:on
    )

    assertThrows<ExposedSQLException> {
      runBlocking {
        DaoUserStatistics.insertUserStatistics(invalid)
      }
    }
  }

  @Order(5)
  @Test
  fun insert_user_statistics_with_invalid_destroyed_monuments() {
    val invalid = RawUserStatistics(
      // @formatter:off
      identifier         = UniqueUuid.v4(USER_STATISTICS.identifier),
      kills              = USER_STATISTICS.kills,
      assists            = USER_STATISTICS.assists,
      deaths             = USER_STATISTICS.deaths,
      destroyedMonuments = -1,
      playedGames        = USER_STATISTICS.playedGames,
      gamesWon           = USER_STATISTICS.gamesWon,
      gamesLost          = USER_STATISTICS.gamesLost
      // @formatter:on
    )

    assertThrows<ExposedSQLException> {
      runBlocking {
        DaoUserStatistics.insertUserStatistics(invalid)
      }
    }
  }

  @Order(6)
  @Test
  fun insert_user_statistics_with_invalid_played_games() {
    val invalid = RawUserStatistics(
      // @formatter:off
      identifier         = UniqueUuid.v4(USER_STATISTICS.identifier),
      kills              = USER_STATISTICS.kills,
      assists            = USER_STATISTICS.assists,
      deaths             = USER_STATISTICS.deaths,
      destroyedMonuments = USER_STATISTICS.destroyedMonuments,
      playedGames        = -1,
      gamesWon           = USER_STATISTICS.gamesWon,
      gamesLost          = USER_STATISTICS.gamesLost
      // @formatter:on
    )

    assertThrows<ExposedSQLException> {
      runBlocking {
        DaoUserStatistics.insertUserStatistics(invalid)
      }
    }
  }

  @Order(7)
  @Test
  fun insert_user_statistics_with_invalid_games_won() {
    val invalid = RawUserStatistics(
      // @formatter:off
      identifier         = UniqueUuid.v4(USER_STATISTICS.identifier),
      kills              = USER_STATISTICS.kills,
      assists            = USER_STATISTICS.assists,
      deaths             = USER_STATISTICS.deaths,
      destroyedMonuments = USER_STATISTICS.destroyedMonuments,
      playedGames        = USER_STATISTICS.playedGames,
      gamesWon           = -1,
      gamesLost          = USER_STATISTICS.gamesLost
      // @formatter:on
    )

    assertThrows<ExposedSQLException> {
      runBlocking {
        DaoUserStatistics.insertUserStatistics(invalid)
      }
    }
  }

  @Order(8)
  @Test
  fun insert_user_statistics_with_invalid_games_lost() {
    val invalid = RawUserStatistics(
      // @formatter:off
      identifier         = UniqueUuid.v4(USER_STATISTICS.identifier),
      kills              = USER_STATISTICS.kills,
      assists            = USER_STATISTICS.assists,
      deaths             = USER_STATISTICS.deaths,
      destroyedMonuments = USER_STATISTICS.destroyedMonuments,
      playedGames        = USER_STATISTICS.playedGames,
      gamesWon           = USER_STATISTICS.gamesWon,
      gamesLost          = -1
      // @formatter:on
    )

    assertThrows<ExposedSQLException> {
      runBlocking {
        DaoUserStatistics.insertUserStatistics(invalid)
      }
    }
  }

  @Order(9)
  @Test
  fun find_user_statistics_by_identifier() {
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
  fun find_non_existent_user_statistics_by_identifier() {
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
  fun update_user_statistics() {
    val updated = RawUserStatistics(
      // @formatter:off
      identifier         = USER_STATISTICS.identifier,
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

  @Order(12)
  @Test
  fun update_non_existent_user_statistics() {
    val updated = RawUserStatistics(
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
      0,
      runBlocking {
        DaoUserStatistics.updateUserStatistics(updated)
      }
    )
  }

  @Order(13)
  @Test
  fun delete_user_statistics_by_identifier() {
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

  @Order(14)
  @Test
  fun delete_non_existent_user_statistics_by_identifier() {
    assertEquals(
      0,
      runBlocking {
        DaoUserStatistics.deleteUserStatisticsByIdentifier(
          UniqueUuid.v4(USER_STATISTICS.identifier)
        )
      }
    )
  }

  @AfterAll
  @Test
  fun delete_user_statistics() {
    assertEquals(
      0,
      runBlocking {
        DaoUserStatistics.deleteUserStatistics()
      }
    )
  }
}