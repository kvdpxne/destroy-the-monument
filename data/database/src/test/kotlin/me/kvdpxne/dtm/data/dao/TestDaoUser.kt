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
import me.kvdpxne.dtm.data.validation.INVALID_USER_DISPLAY_NAME
import me.kvdpxne.dtm.data.validation.INVALID_USER_NAME
import me.kvdpxne.dtm.raw.factories.makeRawUser
import me.kvdpxne.dtm.shared.randomPositiveInt
import me.kvdpxne.dtm.shared.randomPositiveLong
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
private val USER = makeRawUser()

private val USER_STATISTICS = USER.statistics

private val USER_WALLET = USER.wallet

/**
 * @since 0.1.0
 */
@Order(1)
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TestDaoUser {

  /**
   * @since 0.1.0
   */
  @AfterAll
  fun `clean up battlefield after battle`() {
    try {
      runBlocking {
        UserDao.truncateUsers()
        UserStatisticsDao.truncateUserStatistics()
        UserWalletDao.truncateUserWallets()
      }
    } catch (_: Throwable) {
    }
  }

  /**
   * @since 0.1.0
   */
  @BeforeAll
  fun `prepare battlefield`() {
    this.`clean up battlefield after battle`()
    println(USER.toStylishString().listed(2))
  }

  @Order(0)
  @Test
  fun `insert user`() {
    assertEquals(
      1,
      runBlocking {
        UserDao.insertUser(USER)
      }
    )
  }

  @Order(1)
  @Test
  fun `insert duplicated user`() {
    assertEquals(
      ResponseCodes.DUPLICATED,
      runBlocking {
        UserDao.insertUser(USER)
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
        UserDao.insertUser(tooLong)
      }
    )

    val tooShort = USER.copy(
      name = "No"
    )

    assertEquals(
      INVALID_USER_NAME,
      runBlocking {
        UserDao.insertUser(tooShort)
      }
    )

    val illegalCharacters = USER.copy(
      name = "_illegalN@me^"
    )

    assertEquals(
      INVALID_USER_NAME,
      runBlocking {
        UserDao.insertUser(illegalCharacters)
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
        UserDao.insertUser(tooLong)
      }
    )

    val tooShort = USER.copy(
      displayName = "No"
    )

    assertEquals(
      INVALID_USER_DISPLAY_NAME,
      runBlocking {
        UserDao.insertUser(tooShort)
      }
    )

    val illegalCharacters = USER.copy(
      displayName = "_illegalN@me^"
    )

    assertEquals(
      INVALID_USER_DISPLAY_NAME,
      runBlocking {
        UserDao.insertUser(illegalCharacters)
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
        UserDao.findUserByIdentifierOrNull(
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
        UserDao.findUserByIdentifierOrNull(
          uniqueUuid(USER.identifier)
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
        UserDao.findUserByNameOrNull(
          USER.name
        )
      }
    )

    assertEquals(
      USER,
      runBlocking {
        UserDao.findUserByNameOrNull(
          USER.name.uppercase()
        )
      }
    )

    assertEquals(
      USER,
      runBlocking {
        UserDao.findUserByNameOrNull(
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
        UserDao.findUserByNameOrNull(
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
        UserDao.containsUserByIdentifier(
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
        UserDao.containsUserByIdentifier(
          uniqueUuid(USER.identifier)
        )
      }
    )
  }

  @Order(11)
  @Test
  fun `contains user by name`() {
    assertTrue(
      runBlocking {
        UserDao.containsUserByName(
          USER.name
        )
      }
    )

    assertTrue(
      runBlocking {
        UserDao.containsUserByName(
          USER.name.uppercase()
        )
      }
    )

    assertTrue(
      runBlocking {
        UserDao.containsUserByName(
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
        UserDao.containsUserByName(
          Random.nextInt(1_000, 1_000_000).toString()
        )
      }
    )
  }

  private fun updatedUser(
    identifier: UUID
  ): RawUser {
    return RawUser(
      // @formatter:off
      identifier  = identifier,
      statistics  = USER.statistics.copy(
        kills   = randomPositiveInt(),
        assists = randomPositiveInt(),
        deaths  = randomPositiveInt()
      ),
      wallet      = USER.wallet.copy(
        coins = randomPositiveLong()
      ),
      name        = USER.name,
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
        UserDao.updateUser(updated)
      }
    )

    assertEquals(
      updated,
      runBlocking {
        UserDao.findUserByIdentifierOrNull(
          updated.identifier
        )
      }
    )
  }

  @Order(14)
  @Test
  fun `update non existent user`() {
    val updated = this.updatedUser(
      uniqueUuid(USER.identifier)
    )

    assertEquals(
      ResponseCodes.NO_RECORD,
      runBlocking {
        UserDao.updateUser(updated)
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
        UserDao.updateUser(tooLong)
      }
    )

    val tooShort = USER.copy(
      name = "No"
    )

    assertEquals(
      INVALID_USER_NAME,
      runBlocking {
        UserDao.updateUser(tooShort)
      }
    )

    val illegalCharacters = USER.copy(
      name = "_illegalN@me^"
    )

    assertEquals(
      INVALID_USER_NAME,
      runBlocking {
        UserDao.updateUser(illegalCharacters)
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
        UserDao.updateUser(tooLong)
      }
    )

    val tooShort = USER.copy(
      displayName = "No"
    )

    assertEquals(
      INVALID_USER_DISPLAY_NAME,
      runBlocking {
        UserDao.updateUser(tooShort)
      }
    )

    val illegalCharacters = USER.copy(
      displayName = "_illegalN@me^"
    )

    assertEquals(
      INVALID_USER_DISPLAY_NAME,
      runBlocking {
        UserDao.updateUser(illegalCharacters)
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
        UserDao.countUsers()
      }
    )
  }

  @Order(19)
  @Test
  fun `delete user by identifier`() {
    assertEquals(
      true,
      runBlocking {
        UserDao.deleteUserByIdentifier(
          USER.identifier
        )
      }
    )

    assertNull(
      runBlocking {
        UserDao.findUserByIdentifierOrNull(
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
        UserDao.deleteUserByIdentifier(
          uniqueUuid(USER.identifier)
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
        UserDao.truncateUsers()
      }
    )
  }

  @AfterAll
  fun `delete users after all`() {
    try {
      runBlocking {
        UserDao.truncateUsers()
      }
    } catch (_: Throwable) {
    }
  }
}