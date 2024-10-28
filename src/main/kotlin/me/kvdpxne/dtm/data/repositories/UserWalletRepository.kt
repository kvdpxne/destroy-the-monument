package me.kvdpxne.dtm.data.repositories

import me.kvdpxne.dtm.wallet.Wallet

/**
 * @since 0.1.0
 */
interface UserWalletRepository {

  /**
   * @param userWallet
   *
   * @since 0.1.0
   */
  suspend fun insertUserWallet(
    userWallet: Wallet
  ): Int

  /**
   * @param userWallet
   *
   * @since 0.1.0
   */
  suspend fun updateUserWallet(
    userWallet: Wallet
  ): Int
}