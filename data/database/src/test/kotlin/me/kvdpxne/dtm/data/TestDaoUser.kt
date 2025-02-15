package me.kvdpxne.dtm.data

import kotlin.test.assertEquals
import kotlinx.coroutines.runBlocking
import me.kvdpxne.dtm.data.raw.RawUser
import me.kvdpxne.dtm.data.util.UniqueUuid
import org.jetbrains.exposed.exceptions.ExposedSQLException
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestMethodOrder
import org.junit.jupiter.api.assertThrows

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
  fun insert_user() {
    assertEquals(
      1,
      runBlocking {
        DaoUser.insertUser(USER)
      }
    )
  }

  @Order(1)
  @Test
  fun insert_duplicated_user() {
    assertThrows<ExposedSQLException> {
      runBlocking {
        DaoUser.insertUser(USER)
      }
    }
  }

  @Order(Int.MAX_VALUE)
  @Test
  fun delete_users() {
    assertEquals(
      1,
      runBlocking {
        DaoUser.deleteUsers()
      }
    )
  }
}