package me.kvdpxne.dtm.wallet;

import java.io.Serializable;
import java.util.UUID;
import me.kvdpxne.dtm.Copyable;
import me.kvdpxne.dtm.Identifiable;
import me.kvdpxne.dtm.state.State;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

/**
 * Represents a wallet that manages a custom currency system.
 * <p>
 * This interface provides methods to retrieve, update, and modify the coin
 * balance, as well as to handle wallet-specific properties such as multipliers,
 * infinity mode, and blocked state. The coin system uses long integers for
 * simplicity and avoids fractional values, resembling a medieval currency
 * system.
 *
 * @since 0.1.0
 */
public interface Wallet
  extends
  Comparable<Wallet>,
  Copyable<Wallet>,
  Identifiable<UUID>,
  State,
  Serializable {

  /**
   * Retrieves the unique identifier for the wallet.
   *
   * @return the {@link UUID} that uniquely identifies this wallet.
   * @since 0.1.0
   */
  @Override
  @NotNull
  UUID getIdentifier();

  /**
   * Retrieves the current number of coins in the wallet.
   *
   * @return the number of coins, guaranteed to be non-negative.
   * @since 0.1.0
   */
  @Range(from = 0, to = Long.MAX_VALUE)
  long getCoins();

  /**
   * Sets the number of coins in the wallet to a specific value.
   *
   * @param coins the new coin balance, must be non-negative.
   * @throws IllegalArgumentException if {@code coins} is negative.
   * @implNote This method is thread-safe.
   * @since 0.1.0
   */
  void setCoins(
    @Range(from = 0, to = Long.MAX_VALUE) final long coins
  );

  /**
   * Retrieves the multiplier applied to the wallet.
   *
   * @return the multiplier, guaranteed to be non-negative.
   * @since 0.1.0
   */
  @Range(from = 0, to = Long.MAX_VALUE)
  float getMultiplier();

  /**
   * Sets the multiplier for the wallet. This multiplier is used when adding
   * coins.
   *
   * @param multiplier the new multiplier, must be non-negative.
   * @throws IllegalArgumentException if {@code multiplier} is negative.
   * @implNote This method is thread-safe.
   * @since 0.1.0
   */
  void setMultiplier(
    @Range(from = 0, to = Long.MAX_VALUE) final float multiplier
  );

  /**
   * Checks if the wallet is in infinity mode.
   *
   * @return {@code true} if the wallet has infinite coins, {@code false}
   * otherwise.
   * @since 0.1.0
   */
  boolean isInfinity();

  /**
   * @since 0.1.0
   */
  void setInfinity(
    final boolean infinity
  );

  /**
   * Checks if the wallet is blocked.
   *
   * @return {@code true} if the wallet is blocked, {@code false} otherwise.
   * @since 0.1.0
   */
  boolean isBlocked();

  /**
   * @since 0.1.0
   */
  void setBlocked(
    final boolean blocked
  );

  /**
   * Adds coins to the wallet.
   * <p>
   * The number of coins is multiplied by the wallet's multiplier before being
   * added. If the product of the coins and multiplier equals 0.0, the method
   * will immediately return without modifying the wallet.
   *
   * @param coins the number of coins to add, must be non-negative.
   * @throws IllegalArgumentException if {@code coins} is negative.
   * @throws ArithmeticException      if an arithmetic overflow occurs, or if
   *                                  the product of {@code coins} and the
   *                                  multiplier is outside the range of
   *                                  {@code 0} to {@code Long.MAX_VALUE}.
   * @implNote This method is thread-safe.
   * @since 0.1.0
   */
  void addCoins(
    @Range(from = 0, to = Long.MAX_VALUE) final long coins
  );

  /**
   * Subtracts coins from the wallet.
   * <p>
   * The difference between the current balance and the number of coins to
   * subtract is calculated. If the resulting balance is outside the range of
   * {@code 0} to {@code Long.MAX_VALUE}, an {@code ArithmeticException} will be
   * thrown.
   *
   * @param coins the number of coins to subtract, must be non-negative.
   * @throws IllegalArgumentException if {@code coins} is negative or exceeds
   *                                  the current balance.
   * @throws ArithmeticException      if an arithmetic overflow occurs, or if
   *                                  the result of subtracting {@code coins}
   *                                  from the current balance is outside the
   *                                  valid range.
   * @implNote This method is thread-safe.
   * @since 0.1.0
   */
  void subtractCoins(
    @Range(from = 0, to = Long.MAX_VALUE) final long coins
  );

  /**
   * Checks whether the wallet was modified since its creation or last reset.
   *
   * @return {@code true} if the wallet was modified, {@code false} otherwise.
   * @since 0.1.0
   */
  @Override
  boolean wasModified();

  /**
   * Compares this wallet to another wallet based on their coin balances.
   *
   * @param other the wallet to compare to.
   * @return a negative integer, zero, or a positive integer as this wallet has
   * less than, equal to, or greater coin balance than the other wallet.
   * @since 0.1.0
   */
  @Override
  int compareTo(
    @NotNull final Wallet other
  );

  /**
   * Creates a deep copy of this wallet.
   *
   * @return a new wallet object with the same properties as this wallet.
   * @since 0.1.0
   */
  @Override
  @NotNull
  Wallet copy();
}
