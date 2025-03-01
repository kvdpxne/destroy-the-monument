package me.kvdpxne.dtm.data.dao

import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue
import kotlinx.coroutines.runBlocking
import me.kvdpxne.dtm.data.ResponseCodes
import me.kvdpxne.dtm.data.raw.RawUserWallet
import me.kvdpxne.dtm.data.util.UniqueUuid
import me.kvdpxne.dtm.data.validation.INVALID_USER_WALLET_COINS
import me.kvdpxne.dtm.data.validation.INVALID_USER_WALLET_MULTIPLIER
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestMethodOrder

@Order(0)
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TestDaoUserWallet {

  companion object {

    // Creating an unacceptable object via the constructor is always possible
    // but should be used only for testing.
    internal val USER_WALLET = RawUserWallet(
      // @formatter:off
      identifier = UniqueUuid.v4(),
      coins      = 0,
      multiplier = 1.0F,
      infinite   = false,
      locked     = false
      // @formatter:on
    )
  }

  @Order(0)
  @Test
  fun `insert user wallet`() {
    runBlocking {
      assertEquals(
        1,
        DaoUserWallet.insertUserWallet(USER_WALLET)
      )
    }
  }

  @Order(1)
  @Test
  fun `insert duplicated user wallet`() {
    assertEquals(
      ResponseCodes.DUPLICATED,
      runBlocking {
        DaoUserWallet.insertUserWallet(USER_WALLET)
      }
    )
  }

  @Order(2)
  @Test
  fun `insert user wallet with invalid coins`() {
    val invalid = USER_WALLET.copy(
      coins = -74 // Coins must be greater than or equal to 0.
    )

    assertEquals(
      INVALID_USER_WALLET_COINS,
      runBlocking {
        DaoUserWallet.insertUserWallet(invalid)
      }
    )
  }

  @Order(3)
  @Test
  fun `insert user wallet with invalid multiplier`() {
    val invalid = USER_WALLET.copy(
      multiplier = 0.007F // Multiplier must be between 0.00F and 10000.00F
    )

    assertEquals(
      INVALID_USER_WALLET_MULTIPLIER,
      runBlocking {
        DaoUserWallet.insertUserWallet(invalid)
      }
    )
  }

  @Order(4)
  @Test
  fun `find user wallet by identifier`() {
    assertEquals(
      USER_WALLET,
      runBlocking {
        DaoUserWallet.findUserWallerByIdentifierOrNull(
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
        DaoUserWallet.findUserWallerByIdentifierOrNull(
          UniqueUuid.v4(USER_WALLET.identifier)
        )
      }
    )
  }

  @Order(6)
  @Test
  fun `contains user wallet by identifier`() {
    assertTrue(
      runBlocking {
        DaoUserWallet.containsUserWalletByIdentifier(
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
        DaoUserWallet.containsUserWalletByIdentifier(
          UniqueUuid.v4(USER_WALLET.identifier)
        )
      }
    )
  }

  @Order(8)
  @Test
  fun `update user wallet`() {
    val updated = USER_WALLET.copy(
      coins      = 1410,
      multiplier = 1.1F
    )

    assertEquals(
      1,
      runBlocking {
        DaoUserWallet.updateUserWallet(updated)
      }
    )

    assertEquals(
      updated,
      runBlocking {
        DaoUserWallet.findUserWallerByIdentifierOrNull(
          updated.identifier
        )
      }
    )
  }

  @Order(9)
  @Test
  fun `update non existent user wallet`() {
    val updated = USER_WALLET.copy(
      // @formatter:off
      identifier = UniqueUuid.v4(USER_WALLET.identifier),
      coins      = 1410,
      multiplier = 1.1F
      // @formatter:on
    )

    assertEquals(
      ResponseCodes.NO_RECORD,
      runBlocking {
        DaoUserWallet.updateUserWallet(updated)
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
        DaoUserWallet.updateUserWallet(invalid)
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
        DaoUserWallet.updateUserWallet(invalid)
      }
    )
  }

  @Order(12)
  @Test
  fun `count user wallets`() {
    assertEquals(
      1,
      runBlocking {
        DaoUserWallet.countUserWallets()
      }
    )
  }

  @Order(12)
  @Test
  fun `delete user wallet by identifier`() {
    assertEquals(
      1,
      runBlocking {
        DaoUserWallet.deleteUserWalletByIdentifier(
          USER_WALLET.identifier
        )
      }
    )

    assertNull(
      runBlocking {
        DaoUserWallet.findUserWallerByIdentifierOrNull(
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
        DaoUserWallet.deleteUserWalletByIdentifier(
          UniqueUuid.v4(USER_WALLET.identifier)
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
        DaoUserWallet.truncateUserWallets()
      }
    )
  }

  @AfterAll
  fun `delete user wallets after all`() {
    try {
      runBlocking {
        DaoUserWallet.truncateUserWallets()
      }
    } catch (_: Throwable) {
    }
  }
}