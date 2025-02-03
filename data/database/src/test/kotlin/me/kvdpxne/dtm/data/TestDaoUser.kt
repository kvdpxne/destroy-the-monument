package me.kvdpxne.dtm.data

import me.kvdpxne.dtm.data.raw.RawUser
import me.kvdpxne.dtm.data.util.UniqueUuid
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestMethodOrder

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
}