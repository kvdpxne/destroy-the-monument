package me.kvdpxne.dtm.data.validation.rules;

import me.kvdpxne.dtm.UnsubstantiatedInitializationError;

/**
 * Defines validation rules and boundaries for wallet-related operations.
 * Contains immutable constants representing minimum and maximum allowable values
 * for coins and multipliers in a wallet system.
 * <p>
 * This class cannot be instantiated as it serves as a constants container.
 *
 * @author Łukasz Pietrzak (kvdpxne)
 * @version 0.1.0
 * @since 0.1.0
 */
public final class WalletRules {

  /**
   * The absolute minimum number of coins allowed in a wallet.
   * <p>
   * Represents the lower boundary inclusive value ({@code 0} coins).
   *
   * @since 0.1.0
   */
  public static final long MIN_COINS = 0L;

  /**
   * The absolute maximum number of coins allowed in a wallet.
   * <p>
   * Represents the upper boundary inclusive value ({@code 999,999,999,999} coins).
   *
   * @since 0.1.0
   */
  public static final long MAX_COINS = 999_999_999_999L;

  /**
   * The minimum positive multiplier value allowed (exclusive of zero).
   * <p>
   * Represents the smallest non-zero multiplier ({@code 0.01}).
   *
   * @since 0.1.0
   */
  public static final float MIN_MULTIPLIER = 0.01F;

  /**
   * The minimum multiplier value including zero.
   * <p>
   * Represents the absolute lower boundary for multipliers ({@code 0.0}).
   *
   * @since 0.1.0
   */
  public static final float MIN_MULTIPLIER_INCLUSIVE_ZERO = 0.0F;

  /**
   * The maximum multiplier value allowed (exclusive of upper limit).
   * <p>
   * Calculated as {@code (10,000 - MIN_MULTIPLIER)} resulting in {@code 9999.99}.
   *
   * @since 0.1.0
   */
  public static final float MAX_MULTIPLIER = 10_000F - MIN_MULTIPLIER;

  /**
   * Private constructor to prevent instantiation.
   * <p>
   * Throws {@link UnsubstantiatedInitializationError} if invoked,
   * enforcing this class's nature as a static utility container.
   *
   * @throws UnsubstantiatedInitializationError Always thrown upon invocation
   * @since 0.1.0
   */
  private WalletRules() {
    throw new UnsubstantiatedInitializationError(this.getClass());
  }
}
