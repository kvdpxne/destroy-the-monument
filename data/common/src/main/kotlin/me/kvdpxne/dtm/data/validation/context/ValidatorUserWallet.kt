package me.kvdpxne.dtm.data.validation.context

import me.kvdpxne.dtm.data.raw.RawUserWallet
import me.kvdpxne.dtm.data.validation.EVERYTHING_OK
import me.kvdpxne.dtm.data.validation.INVALID_REFERENCE
import me.kvdpxne.dtm.data.validation.INVALID_USER_WALLET_COINS
import me.kvdpxne.dtm.data.validation.INVALID_USER_WALLET_MULTIPLIER

/**
 * Validates whether the given number of coins in a user's wallet is valid.
 * A valid number of coins must be greater than or equal to 0.
 *
 * @param coins The number of coins to validate.
 * @return `true` if the number of coins is valid (i.e., greater than or equal to 0),
 *         otherwise `false`.
 * @since 0.1.0
 */
fun isUserWalletCoinsValid(
  coins: Long
): Boolean {
  return 0L <= coins
}

/**
 * Validates whether the given multiplier in a user's wallet is valid. A valid
 * multiplier must be either exactly `0.00F` or within the range of `0.01F` to
 * `10000.00F`.
 *
 * @param multiplier The multiplier to validate.
 * @return `true` if the multiplier is valid (i.e., 0.00 or within the range of
 *         0.01 to 10000.00), otherwise `false`.
 * @since 0.1.0
 */
fun isUserWalletMultiplierValid(
  multiplier: Float
): Boolean {
  return 0.00F == multiplier || multiplier > 0.009F && multiplier <= 10000.00F
}

/**
 * Validates the integrity and correctness of a [RawUserWallet] object.
 *
 * This function checks if the provided [RawUserWallet] is not `null`, if the
 * number of coins is valid, and if the multiplier is valid. It returns a
 * validation result code indicating the outcome of the validation.
 *
 * @param userWallet The [RawUserWallet] object to validate.
 * @return An integer code representing the validation result:
 * - [EVERYTHING_OK] if the wallet is valid.
 * - [INVALID_REFERENCE] if the wallet reference is `null`.
 * - [INVALID_USER_WALLET_COINS] if the number of coins is invalid.
 * - [INVALID_USER_WALLET_MULTIPLIER] if the multiplier is invalid.
 * @see ValidationResults
 * @since 0.1.0
 */
fun isUserWalletValid(
  userWallet: RawUserWallet?
): Int {
  if (null == userWallet) {
    return INVALID_REFERENCE
  }

  if (!isUserWalletCoinsValid(userWallet.coins)) {
    return INVALID_USER_WALLET_COINS
  }

  if (!isUserWalletMultiplierValid(userWallet.multiplier)) {
    return INVALID_USER_WALLET_MULTIPLIER
  }

  return EVERYTHING_OK
}
