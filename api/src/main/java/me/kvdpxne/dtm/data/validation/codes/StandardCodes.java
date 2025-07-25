package me.kvdpxne.dtm.data.validation.codes;

import me.kvdpxne.dtm.UnsubstantiatedInitializationError;

/**
 * Defines standard error codes for common validation scenarios within the application.
 * These numeric codes serve as universal identifiers for both successful operations
 * and general validation failures, providing a consistent error handling framework.
 * <p>
 * This class serves as a constants container and cannot be instantiated.
 *
 * @author Łukasz Pietrzak (kvdpxne)
 * @version 0.1.0
 * @since 0.1.0
 */
public final class StandardCodes {

  /**
   * Success code indicating a validation operation completed without errors.
   * This code signifies that all validation checks passed successfully and
   * the operation meets all required criteria.
   * <p>
   * Value: {@value #EVERYTHING_OK} (-390,377,871)
   *
   * @since 0.1.0
   */
  public static final int EVERYTHING_OK = 0xe8bb4e71;

  /**
   * Error code indicating an invalid object reference or identifier.
   * This code signifies that a required reference (e.g., database ID, object pointer)
   * failed validation due to being null, invalid, or inaccessible.
   * <p>
   * Value: {@value #INVALID_REFERENCE} (-518,255,912)
   *
   * @since 0.1.0
   */
  public static final int INVALID_REFERENCE = 0xe11c0ad8;

  public static final int INVALID_PRIMARY_KEY = 5252;

  /**
   * Private constructor to prevent instantiation.
   * <p>
   * Throws {@link UnsubstantiatedInitializationError} if invoked,
   * enforcing this class's nature as a static utility container.
   *
   * @throws UnsubstantiatedInitializationError Always thrown upon invocation
   * @since 0.1.0
   */
  private StandardCodes() {
    throw new UnsubstantiatedInitializationError(this.getClass());
  }
}
