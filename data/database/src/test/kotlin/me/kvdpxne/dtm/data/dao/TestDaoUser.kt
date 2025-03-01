package me.kvdpxne.dtm.data.dao

import java.util.UUID
import kotlin.random.Random
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue
import kotlinx.coroutines.runBlocking
import me.kvdpxne.dtm.data.ResponseCodes
import me.kvdpxne.dtm.data.raw.RawUser
import me.kvdpxne.dtm.data.util.UniqueUuid
import me.kvdpxne.dtm.data.validation.INVALID_USER_DISPLAY_NAME
import me.kvdpxne.dtm.data.validation.INVALID_USER_NAME
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestMethodOrder

@Order(1)
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TestDaoUser {

  companion object {

    // Creating an unacceptable object via the constructor is always possible
    // but should be used only for testing.
    internal val USER = RawUser(
      // @formatter:off
      identifier  = UniqueUuid.v4(),
      statistics  = TestDaoUserStatistics.USER_STATISTICS,
      wallet      = TestDaoUserWallet.USER_WALLET,
      name        = "kvd_currants",
      displayName = "Currants",
      profession  = "dtm_scout",
      locale      = "pl_pl"
      // @formatter:on
    )
  }

  @Order(0)
  @Test
  fun `insert user`() {
    assertEquals(
      1,
      runBlocking {
        DaoUser.insertUser(USER)
      }
    )
  }

  @Order(1)
  @Test
  fun `insert duplicated user`() {
    assertEquals(
      ResponseCodes.DUPLICATED,
      runBlocking {
        DaoUser.insertUser(USER)
      }
    )
  }

  @Order(2)
  @Test
  fun `insert user with invalid name`() {
    val tooLong = USER.copy(
      name = "TheBestManInNorthernEurope"
    )

    assertEquals(
      INVALID_USER_NAME,
      runBlocking {
        DaoUser.insertUser(tooLong)
      }
    )

    val tooShort = USER.copy(
      name = "No"
    )

    assertEquals(
      INVALID_USER_NAME,
      runBlocking {
        DaoUser.insertUser(tooShort)
      }
    )

    val illegalCharacters = USER.copy(
      name = "_illegalN@me^"
    )

    assertEquals(
      INVALID_USER_NAME,
      runBlocking {
        DaoUser.insertUser(illegalCharacters)
      }
    )
  }

  @Order(3)
  @Test
  fun `insert user with invalid display name`() {
    val tooLong = USER.copy(
      displayName = "TheWorstManInNorthernEurope"
    )

    assertEquals(
      INVALID_USER_DISPLAY_NAME,
      runBlocking {
        DaoUser.insertUser(tooLong)
      }
    )

    val tooShort = USER.copy(
      displayName = "No"
    )

    assertEquals(
      INVALID_USER_DISPLAY_NAME,
      runBlocking {
        DaoUser.insertUser(tooShort)
      }
    )

    val illegalCharacters = USER.copy(
      displayName = "_illegalN@me^"
    )

    assertEquals(
      INVALID_USER_DISPLAY_NAME,
      runBlocking {
        DaoUser.insertUser(illegalCharacters)
      }
    )
  }

  @Order(4)
  @Test
  fun `insert user with invalid locale`() {
    // TODO w przyszłości
  }

  @Order(5)
  @Test
  fun `find user by identifier`() {
    assertEquals(
      USER,
      runBlocking {
        DaoUser.findUserByIdentifierOrNull(
          USER.identifier
        )
      }
    )
  }

  @Order(6)
  @Test
  fun `find non existent user by identifier`() {
    assertNull(
      runBlocking {
        DaoUser.findUserByIdentifierOrNull(
          UniqueUuid.v4(USER.identifier)
        )
      }
    )
  }

  @Order(7)
  @Test
  fun `find user by name`() {
    assertEquals(
      USER,
      runBlocking {
        DaoUser.findUserByNameOrNull(
          USER.name
        )
      }
    )

    assertEquals(
      USER,
      runBlocking {
        DaoUser.findUserByNameOrNull(
          USER.name.uppercase()
        )
      }
    )

    assertEquals(
      USER,
      runBlocking {
        DaoUser.findUserByNameOrNull(
          USER.name.lowercase()
        )
      }
    )
  }

  @Order(8)
  @Test
  fun `find non existent user by name`() {
    assertNull(
      runBlocking {
        DaoUser.findUserByNameOrNull(
          Random.nextInt(1_000, 1_000_000).toString()
        )
      }
    )
  }

  @Order(9)
  @Test
  fun `contains user by identifier`() {
    assertTrue(
      runBlocking {
        DaoUser.containsUserByIdentifier(
          USER.identifier
        )
      }
    )
  }

  @Order(10)
  @Test
  fun `contains non existent user by identifier`() {
    assertFalse(
      runBlocking {
        DaoUser.containsUserByIdentifier(
          UniqueUuid.v4(USER.identifier)
        )
      }
    )
  }

  @Order(11)
  @Test
  fun `contains user by name`() {
    assertTrue(
      runBlocking {
        DaoUser.containsUserByName(
          USER.name
        )
      }
    )

    assertTrue(
      runBlocking {
        DaoUser.containsUserByName(
          USER.name.uppercase()
        )
      }
    )

    assertTrue(
      runBlocking {
        DaoUser.containsUserByName(
          USER.name.lowercase()
        )
      }
    )
  }

  @Order(12)
  @Test
  fun `contains non existent user by name`() {
    assertFalse(
      runBlocking {
        DaoUser.containsUserByName(
          Random.nextInt(1_000, 1_000_000).toString()
        )
      }
    )
  }

  /**
   * @since 0.1.0
   */
  private fun updatedUser(
    identifier: UUID
  ): RawUser {
    return RawUser(
      // @formatter:off
      identifier  = identifier,
      statistics  = USER.statistics.let {
        it.copy(
          kills   = 2 * it.kills,
          assists = 2 * it.assists,
          deaths  = 2 * it.deaths
        )
      },
      wallet      = USER.wallet.let {
        it.copy(
          coins = 2 * it.coins
        )
      },
      name        = USER.name,
      displayName = null,
      profession  = "dtm_archer",
      locale      = "en_us"
      // @formatter:on
    )
  }

  @Order(13)
  @Test
  fun `update user`() {
    val updated = this.updatedUser(USER.identifier)

    assertEquals(
      1,
      runBlocking {
        DaoUser.updateUser(updated)
      }
    )

    assertEquals(
      updated,
      runBlocking {
        DaoUser.findUserByIdentifierOrNull(
          updated.identifier
        )
      }
    )
  }

  @Order(14)
  @Test
  fun `update non existent user`() {
    val updated = this.updatedUser(
      UniqueUuid.v4(USER.identifier)
    )

    assertEquals(
      ResponseCodes.NO_RECORD,
      runBlocking {
        DaoUser.updateUser(updated)
      }
    )
  }

  @Order(15)
  @Test
  fun `update user with invalid name`() {
    val tooLong = USER.copy(
      name = "TheBestManInNorthernEurope"
    )

    assertEquals(
      INVALID_USER_NAME,
      runBlocking {
        DaoUser.updateUser(tooLong)
      }
    )

    val tooShort = USER.copy(
      name = "No"
    )

    assertEquals(
      INVALID_USER_NAME,
      runBlocking {
        DaoUser.updateUser(tooShort)
      }
    )

    val illegalCharacters = USER.copy(
      name = "_illegalN@me^"
    )

    assertEquals(
      INVALID_USER_NAME,
      runBlocking {
        DaoUser.updateUser(illegalCharacters)
      }
    )
  }

  @Order(16)
  @Test
  fun `update user with invalid display name`() {
    val tooLong = USER.copy(
      displayName = "TheWorstManInNorthernEurope"
    )

    assertEquals(
      INVALID_USER_DISPLAY_NAME,
      runBlocking {
        DaoUser.updateUser(tooLong)
      }
    )

    val tooShort = USER.copy(
      displayName = "No"
    )

    assertEquals(
      INVALID_USER_DISPLAY_NAME,
      runBlocking {
        DaoUser.updateUser(tooShort)
      }
    )

    val illegalCharacters = USER.copy(
      displayName = "_illegalN@me^"
    )

    assertEquals(
      INVALID_USER_DISPLAY_NAME,
      runBlocking {
        DaoUser.updateUser(illegalCharacters)
      }
    )
  }

  @Order(17)
  @Test
  fun `update user with invalid locale`() {
    // TODO w przyszłości
  }

  @Order(18)
  @Test
  fun `count users`() {
    assertEquals(
      1,
      runBlocking {
        DaoUser.countUsers()
      }
    )
  }

  @Order(19)
  @Test
  fun `delete user by identifier`() {
    assertEquals(
      true,
      runBlocking {
        DaoUser.deleteUserByIdentifier(
          USER.identifier
        )
      }
    )

    assertNull(
      runBlocking {
        DaoUser.findUserByIdentifierOrNull(
          USER.identifier
        )
      }
    )
  }

  @Order(20)
  @Test
  fun `delete non existent user by identifier`() {
    assertEquals(
      false,
      runBlocking {
        DaoUser.deleteUserByIdentifier(
          UniqueUuid.v4(USER.identifier)
        )
      }
    )
  }

  @Order(21)
  @Test
  fun `delete users`() {
    assertEquals(
      0,
      runBlocking {
        DaoUser.truncateUsers()
      }
    )
  }

  @AfterAll
  fun `delete users after all`() {
    try {
      runBlocking {
        DaoUser.truncateUsers()
      }
    } catch (_: Throwable) {
    }
  }
}