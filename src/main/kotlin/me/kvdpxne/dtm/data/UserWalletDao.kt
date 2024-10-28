package me.kvdpxne.dtm.data

import me.kvdpxne.dtm.data.repositories.UserWalletRepository
import me.kvdpxne.dtm.data.sources.DatabasesConfiguration
import me.kvdpxne.dtm.data.tables.UserWalletTable
import me.kvdpxne.dtm.data.transactions.concurrentTransaction
import me.kvdpxne.dtm.wallet.Wallet
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import org.jetbrains.exposed.sql.update

/**
 * @since 0.1.0
 */
object UserWalletDao : UserWalletRepository {

  /**
   * @param wallet
   * @param builder
   *
   * @since 0.1.0
   */
  private fun buildUserWalletStatement(
    wallet: Wallet,
    builder: UpdateBuilder<Int>
  ) {
    UserWalletTable.run {
      builder[this.coins] = wallet.coins
      builder[this.multiplier] = wallet.multiplier
    }
  }

  override suspend fun insertUserWallet(
    userWallet: Wallet
  ): Int {
    return concurrentTransaction(DatabasesConfiguration.main) {
      UserWalletTable.insert {
        it[this.identifier] = userWallet.identifier
        this@UserWalletDao.buildUserWalletStatement(userWallet, it)
      }.insertedCount
    }
  }

  override suspend fun updateUserWallet(
    userWallet: Wallet
  ): Int {
    return concurrentTransaction(DatabasesConfiguration.main) {
      UserWalletTable.update({
        UserWalletTable.identifier eq userWallet.identifier
      }) {
        this@UserWalletDao.buildUserWalletStatement(userWallet, it)
      }
    }
  }
}