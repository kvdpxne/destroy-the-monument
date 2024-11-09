package me.kvdpxne.dtm.data.repositories

import me.kvdpxne.dtm.wallet.Wallet

/**
 * Repository interface for handling user wallet data operations.
 *
 * The [UserWalletRepository] interface provides methods for persisting and
 * updating wallet data associated with users. Implementations of this
 * interface are expected to handle the specific database or storage mechanism
 * to insert and update wallet data.
 *
 * @since 0.1.0
 */
interface UserWalletRepository {

  /**
   * Inserts a new user wallet into the data store.
   *
   * This method takes a [Wallet] object and stores it, typically associating
   * it with a user. The return value represents the status of the operation,
   * commonly the number of rows affected or an identifier for the inserted
   * wallet entry.
   *
   * @param userWallet The wallet to be inserted into the data store.
   *
   * @return An integer indicating the result of the insert operation,
   *         such as the number of rows affected.
   *
   * @since 0.1.0
   */
  suspend fun insertUserWallet(
    userWallet: Wallet
  ): Int

  /**
   * Updates an existing user wallet in the data store.
   *
   * This method modifies the data of an existing [Wallet] entry. The return
   * value represents the status of the update operation, commonly the number
   * of rows affected.
   *
   * @param userWallet The wallet containing updated information to be saved
   *                   in the data store.
   *
   * @return An integer indicating the result of the update operation, such as
   *         the number of rows affected.
   *
   * @since 0.1.0
   */
  suspend fun updateUserWallet(
    userWallet: Wallet
  ): Int
}