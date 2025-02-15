package me.kvdpxne.dtm.data

import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlinx.coroutines.runBlocking
import me.kvdpxne.dtm.data.raw.RawUserWallet
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
  fun insert_user_wallet() {
    runBlocking {
      assertEquals(
        1,
        DaoUserWallet.insertUserWallet(USER_WALLET)
      )
    }
  }

  @Order(1)
  @Test
  fun insert_duplicated_user_wallet() {
    assertEquals(
      Fsfsfsf.ALREADY_EXISTS,
      runBlocking {
        DaoUserWallet.insertUserWallet(USER_WALLET)
      }
    )
  }

  @Order(2)
  @Test
  fun insert_user_wallet_with_invalid_coins() {
    val invalid = RawUserWallet(
      // @formatter:off
      identifier = UniqueUuid.v4(USER_WALLET.identifier),
      coins      = -74, // Coins must be greater than or equal to 0.
      multiplier = USER_WALLET.multiplier,
      infinite   = USER_WALLET.infinite,
      locked     = USER_WALLET.locked
      // @formatter:on
    )

    assertThrows<ExposedSQLException> {
      runBlocking {
        DaoUserWallet.insertUserWallet(invalid)
      }
    }
  }

  @Order(3)
  @Test
  fun insert_user_wallet_with_invalid_multiplier() {
    val invalid = RawUserWallet(
      // @formatter:off
      identifier = UniqueUuid.v4(USER_WALLET.identifier),
      coins      = USER_WALLET.coins,
      multiplier = 0.009F, // Multiplier must be between 0.01 and 1.0
      infinite   = USER_WALLET.infinite,
      locked     = USER_WALLET.locked
      // @formatter:on
    )

    assertThrows<ExposedSQLException> {
      runBlocking {
        DaoUserWallet.insertUserWallet(invalid)
      }
    }
  }

  @Order(4)
  @Test
  fun find_user_wallet_by_identifier() {
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
  fun find_non_existent_user_wallet_by_identifier() {
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
  fun update_user_wallet() {
    val updated = RawUserWallet(
      // @formatter:off
      identifier = USER_WALLET.identifier,
      coins      = 1410,
      multiplier = 1.1F,
      infinite   = USER_WALLET.infinite,
      locked     = USER_WALLET.locked
      // @formatter:on
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

  @Order(7)
  @Test
  fun update_non_existent_user_wallet() {
    val updated = RawUserWallet(
      // @formatter:off
      identifier = UniqueUuid.v4(USER_WALLET.identifier),
      coins      = 1410,
      multiplier = 1.1F,
      infinite   = USER_WALLET.infinite,
      locked     = USER_WALLET.locked
      // @formatter:on
    )

    assertEquals(
      0,
      runBlocking {
        DaoUserWallet.updateUserWallet(updated)
      }
    )
  }

  @Order(8)
  @Test
  fun delete_user_wallet_by_identifier() {
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

  @Order(9)
  @Test
  fun delete_non_existent_user_wallet_by_identifier() {
    assertEquals(
      0,
      runBlocking {
        DaoUserWallet.deleteUserWalletByIdentifier(
          UniqueUuid.v4(USER_WALLET.identifier)
        )
      }
    )
  }

  @AfterAll
  @Test
  fun delete_user_wallets() {
    assertEquals(
      0,
      runBlocking {
        DaoUserWallet.deleteUserWallets()
      }
    )
  }
}