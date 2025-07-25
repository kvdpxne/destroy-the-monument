package me.kvdpxne.dtm.data.repositories

import java.util.UUID
import me.kvdpxne.dtm.data.raw.RawUserWallet
import me.kvdpxne.dtm.data.validation.ValidationResult

/**
 * Provides data access operations for user wallet entities.
 *
 * @author Łukasz Pietrzak (kvdpxne)
 * @since 0.1.0
 */
interface UserWalletRepository {

  /**
   * Finds a user wallet by its unique identifier.
   * @param identifier UUID of the wallet to find
   * @return Found [RawUserWallet] or `null` if not found
   * @since 0.1.0
   */
  suspend fun findUserWallerByIdentifierOrNull(
    identifier: UUID
  ): Pair<RawUserWallet, ValidationResult>?

  /**
   * Checks existence of a wallet by identifier.
   * @param identifier UUID to check
   * @return `true` if wallet exists, `false` otherwise
   * @since 0.1.0
   */
  suspend fun containsUserWalletByIdentifier(
    identifier: UUID
  ): Boolean

  /**
   * Inserts a new user wallet after validation.
   *
   * Return value specifics:
   * - `1`: Wallet successfully inserted
   * - `0`: No operation performed (no error)
   * - Negative: Validation or database error code (see below)
   *
   * Possible error codes:
   * - [INVALID_REFERENCE]
   * - [INVALID_USER_WALLET_COINS]
   * - [INVALID_USER_WALLET_MULTIPLIER]
   *
   * @param userWallet Wallet data to insert
   * @return Operation result code
   * @since 0.1.0
   */
  suspend fun insertUserWallet(
    userWallet: RawUserWallet?
  ): Pair<RawUserWallet?, ValidationResult>

  /**
   * Updates an existing user wallet after validation.
   *
   * Return value specifics:
   * - Positive: Number of updated wallets (typically `1`)
   * - `0`: Wallet not found or no changes made
   * - Negative: Validation or database error code
   *
   * Possible error codes:
   * - [INVALID_REFERENCE]
   * - [INVALID_USER_WALLET_COINS]
   * - [INVALID_USER_WALLET_MULTIPLIER]
   *
   * @param userWallet Wallet data to update
   * @return Operation result code
   * @since 0.1.0
   */
  suspend fun updateUserWallet(
    userWallet: RawUserWallet?
  ): Pair<RawUserWallet?, ValidationResult>

  /**
   * Deletes a wallet by its identifier.
   *
   * Return value specifics:
   * - `1`: Wallet successfully deleted
   * - `0`: Wallet not found
   * - Negative: Database error code
   *
   * Possible error codes:
   * - `NO_TABLE`
   * - `NO_REFERENCE`
   *
   * @param identifier UUID of wallet to delete
   * @return Operation result code
   * @since 0.1.0
   */
  suspend fun deleteUserWalletByIdentifier(
    identifier: UUID
  ): Int

  /**
   * Removes all wallet entries (use with caution).
   *
   * Return value specifics:
   * - Positive: Number of deleted wallets
   * - `0`: Table was empty
   * - Negative: Database error code
   *
   * Possible error codes:
   * - `NO_TABLE`
   *
   * @return Operation result code
   * @since 0.1.0
   */
  suspend fun truncateUserWallets(): Int

  /**
   * Counts total stored wallets.
   * @return Total number of wallets (never negative)
   * @since 0.1.0
   */
  suspend fun countUserWallets(): Long
}