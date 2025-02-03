package me.kvdpxne.dtm.data.repositories

import java.util.UUID
import me.kvdpxne.dtm.data.raw.RawUserWallet

interface RepositoryUserWallet {

  /**
   * @since 0.1.0
   */
  suspend fun findUserWallerByIdentifierOrNull(
    identifier: UUID
  ): RawUserWallet?

  /**
   * @since 0.1.0
   */
  suspend fun insertUserWallet(
    userWallet: RawUserWallet
  ): Int

  /**
   * @since 0.1.0
   */
  suspend fun updateUserWallet(
    userWallet: RawUserWallet
  ): Int

  /**
   * @since 0.1.0
   */
  suspend fun deleteUserWalletByIdentifier(
    identifier: UUID
  ): Int

  /**
   * @since 0.1.0
   */
  suspend fun deleteUserWallets(): Int

  /**
   * @since 0.1.0
   */
  suspend fun countUserWallets(): Long
}