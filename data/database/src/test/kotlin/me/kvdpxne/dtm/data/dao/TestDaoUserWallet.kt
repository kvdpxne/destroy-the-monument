package me.kvdpxne.dtm.data.dao

import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue
import kotlinx.coroutines.runBlocking
import me.kvdpxne.dtm.data.EntityFieldNames
import me.kvdpxne.dtm.data.ResponseCodes
import me.kvdpxne.dtm.data.raw.RawUserWallet
import me.kvdpxne.dtm.data.validation.BasicValidationResult
import me.kvdpxne.dtm.data.validation.BasicValidationResultBuilder
import me.kvdpxne.dtm.data.validation.ValidationResult
import me.kvdpxne.dtm.data.validation.codes.WalletCodes
import me.kvdpxne.dtm.raw.factories.makeRawUserWallet
import me.kvdpxne.dtm.shared.randomFloat
import me.kvdpxne.dtm.shared.randomLong
import me.kvdpxne.dtm.shared.randomPositiveLong
import me.kvdpxne.dtm.shared.uniqueUuid
import me.kvdpxne.dtm.validation.extensions.addUndescribedError
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestMethodOrder

/**
 * Shared test fixture: Preconfigured [RawUserWallet] instance for test operations.
 *
 * @since 0.1.0
 */
private val USER_WALLET: RawUserWallet = makeRawUserWallet()

/**
 * @since 0.1.0
 */
private val EXPECTED_USER_WALLET: Pair<RawUserWallet, ValidationResult.Success> by lazy {
  Pair(USER_WALLET, BasicValidationResult.Success)
}

/**
 * Comprehensive test suite for [UserWalletDao] operations, including insertion, retrieval,
 * update, deletion, and validation of user wallet entities. Tests enforce constraints:
 * - `coins` must be ≥ 0
 * - `multiplier` must be in [0.00, 10000.00]
 *
 * Lifecycle: Uses `PER_CLASS` test instance to share state across ordered test methods.
 *
 * @since 0.1.0
 */
@Order(0)
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TestDaoUserWallet {

  /**
   * Clears the user wallet table after all tests complete.
   *
   * @since 0.1.0
   */
  @AfterAll
  fun `clean up battlefield after battle`() {
    runBlocking {
      UserWalletDao.truncateUserWallets()
    }
  }

  /**
   * Clears the user wallet table before test execution. Logs warning if cleanup results in row deletions.
   *
   * @since 0.1.0
   */
  @BeforeAll
  fun `prepare battlefield`() {
    this.`clean up battlefield after battle`()
    println(USER_WALLET.toStylishString().listed(2))
  }

  @Order(0)
  @Test
  fun `insert user wallet`() {
    runBlocking {
      assertEquals(
        EXPECTED_USER_WALLET,
        UserWalletDao.insertUserWallet(USER_WALLET)
      )
    }
  }

  @Order(1)
  @Test
  fun `insert duplicated user wallet`() {
    assertEquals(
      EXPECTED_USER_WALLET,
      runBlocking {
        UserWalletDao.insertUserWallet(USER_WALLET)
      }
    )
  }

  @Order(2)
  @Test
  fun `insert user wallet with invalid coins`() {
    val invalid = USER_WALLET.copy(
      // The number represents coins must be greater than or equal to 0.
      coins = randomLong(Long.MIN_VALUE, -1)
    )

    val (_, actual) = runBlocking {
      UserWalletDao.insertUserWallet(invalid)
    }

    val expected = BasicValidationResultBuilder()
      .addUndescribedError(EntityFieldNames.COINS, WalletCodes.INVALID_COINS)
      .build()

    assertTrue(
      expected === actual
    )
  }

  @Order(3)
  @Test
  fun `insert user wallet with invalid multiplier`() {
    val tooSmall = USER_WALLET.copy(
      // The number represents the multiplier must be between 0.00 and 10000.00.
      multiplier = randomFloat(0.001F, 0.009F)
    )

    val expected = BasicValidationResultBuilder()
      .addUndescribedError(EntityFieldNames.MULTIPLIER, WalletCodes.INVALID_MULTIPLIER)
      .build()

    assertEquals(
      expected,
      runBlocking {
        UserWalletDao
          .insertUserWallet(tooSmall)
          .second
      }
    )

    val tooLarge = USER_WALLET.copy(
      // The number represents the multiplier must be between 0.00 and 10000.00.
      multiplier = randomFloat(10000.01F)
    )

    assertEquals(
      expected,
      runBlocking {
        UserWalletDao
          .insertUserWallet(tooLarge)
          .second
      }
    )
  }

  @Order(4)
  @Test
  fun `find user wallet by identifier`() {
    assertEquals(
      USER_WALLET,
      runBlocking {
        UserWalletDao.findUserWallerByIdentifierOrNull(
          USER_WALLET.identifier
        )
      }
    )
  }

  @Order(5)
  @Test
  fun `find non existent user wallet by identifier`() {
    assertNull(
      runBlocking {
        UserWalletDao.findUserWallerByIdentifierOrNull(
          uniqueUuid(USER_WALLET.identifier)
        )
      }
    )
  }

  @Order(6)
  @Test
  fun `contains user wallet by identifier`() {
    assertTrue(
      runBlocking {
        UserWalletDao.containsUserWalletByIdentifier(
          USER_WALLET.identifier
        )
      }
    )
  }

  @Order(7)
  @Test
  fun `contains non existent user wallet by identifier`() {
    assertFalse(
      runBlocking {
        UserWalletDao.containsUserWalletByIdentifier(
          uniqueUuid(USER_WALLET.identifier)
        )
      }
    )
  }

  @Order(8)
  @Test
  fun `update user wallet`() {
    val updated = USER_WALLET.copy(
      coins = randomPositiveLong(),
      multiplier = randomFloat(0.01F, 10000.00F)
    )

    assertEquals(
      1,
      runBlocking {
        UserWalletDao.updateUserWallet(updated)
      }
    )

    assertEquals(
      updated,
      runBlocking {
        UserWalletDao.findUserWallerByIdentifierOrNull(
          updated.identifier
        )
      }
    )
  }

  @Order(9)
  @Test
  fun `update non existent user wallet`() {
    val updated = USER_WALLET.copy(
      identifier = uniqueUuid(USER_WALLET.identifier),
      coins = randomPositiveLong(),
      multiplier = randomFloat(0.01F, 10000.00F)
    )

    assertEquals(
      ResponseCodes.NO_RECORD,
      runBlocking {
        UserWalletDao.updateUserWallet(updated)
      }
    )
  }

  @Order(10)
  @Test
  fun `update user wallet with invalid coins`() {
    val invalid = USER_WALLET.copy(
      coins = -74 // Coins must be greater than or equal to 0.
    )

    assertEquals(
      INVALID_USER_WALLET_COINS,
      runBlocking {
        UserWalletDao.updateUserWallet(invalid)
      }
    )
  }

  @Order(11)
  @Test
  fun `update user wallet with invalid multiplier`() {
    val invalid = USER_WALLET.copy(
      multiplier = 0.007F // Multiplier must be between 0.00F and 10000.00F
    )

    assertEquals(
      INVALID_USER_WALLET_MULTIPLIER,
      runBlocking {
        UserWalletDao.updateUserWallet(invalid)
      }
    )
  }

  @Order(12)
  @Test
  fun `count user wallets`() {
    assertEquals(
      1,
      runBlocking {
        UserWalletDao.countUserWallets()
      }
    )
  }

  @Order(12)
  @Test
  fun `delete user wallet by identifier`() {
    assertEquals(
      1,
      runBlocking {
        UserWalletDao.deleteUserWalletByIdentifier(
          USER_WALLET.identifier
        )
      }
    )

    assertNull(
      runBlocking {
        UserWalletDao.findUserWallerByIdentifierOrNull(
          USER_WALLET.identifier
        )
      }
    )
  }

  @Order(13)
  @Test
  fun `delete non existent user wallet by identifier`() {
    assertEquals(
      0,
      runBlocking {
        UserWalletDao.deleteUserWalletByIdentifier(
          uniqueUuid(USER_WALLET.identifier)
        )
      }
    )
  }

  @Order(14)
  @Test
  fun `delete user wallets`() {
    assertEquals(
      0,
      runBlocking {
        UserWalletDao.truncateUserWallets()
      }
    )
  }
}