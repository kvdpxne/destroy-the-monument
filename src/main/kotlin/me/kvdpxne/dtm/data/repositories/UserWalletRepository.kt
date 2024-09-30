package me.kvdpxne.dtm.data.repositories

import me.kvdpxne.dtm.wallet.Wallet

interface UserWalletRepository {

  suspend fun insertUserWallet(
    userWallet: Wallet
  ): Int
}