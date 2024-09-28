package me.kvdpxne.dtm.data

import me.kvdpxne.dtm.data.repositories.UserWalletRepository
import me.kvdpxne.dtm.data.sources.DatabasesConfiguration
import me.kvdpxne.dtm.data.tables.UserWalletTable
import me.kvdpxne.dtm.data.transactions.concurrentTransaction
import me.kvdpxne.dtm.wallet.Wallet
import org.jetbrains.exposed.sql.insert

object UserWalletDao : UserWalletRepository {

  override suspend fun insertUserWallet(
    userWallet: Wallet
  ): Int {
    return concurrentTransaction(DatabasesConfiguration.main) {
      UserWalletTable.insert {
        it[this.identifier] = userWallet.identifier

        it[this.coins] = userWallet.coins
        it[this.multiplier] = userWallet.multiplier
      }.insertedCount
    }
  }
}