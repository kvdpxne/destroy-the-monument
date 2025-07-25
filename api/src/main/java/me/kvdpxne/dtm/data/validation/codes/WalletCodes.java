package me.kvdpxne.dtm.data.validation.codes;

import me.kvdpxne.dtm.UnsubstantiatedInitializationError;

/**
 * Defines error codes specific to wallet validation failures within the application.
 * These numeric codes provide unique identifiers for different types of wallet-related
 * validation errors, enabling precise error handling and communication.
 * <p>
 * This class serves as a constants container and cannot be instantiated.
 *
 * @author Łukasz Pietrzak (kvdpxne)
 * @version 0.1.0
 * @since 0.1.0
 */
public final class WalletCodes {

  /**
   * Error code indicating invalid coins value during wallet operations.
   * <p>
   * This code signifies that a coins value failed validation against the
   * established rules (e.g., out-of-boundary values or invalid format).
   *
   * @since 0.1.0
   */
  public static final int INVALID_COINS = 0xb450c785;

  /**
   * Error code indicating invalid multiplier value during wallet operations.
   * <p>
   * This code signifies that a multiplier value failed validation against the
   * established rules (e.g., out-of-boundary values or invalid format).
   *
   * @since 0.1.0
   */
  public static final int INVALID_MULTIPLIER = 0xe5b6db7d;

  /**
   * Private constructor to prevent instantiation.
   * <p>
   * Throws {@link UnsubstantiatedInitializationError} if invoked,
   * enforcing this class's nature as a static utility container.
   *
   * @throws UnsubstantiatedInitializationError Always thrown upon invocation
   * @since 0.1.0
   */
  private WalletCodes() {
    throw new UnsubstantiatedInitializationError(this.getClass());
  }
}
