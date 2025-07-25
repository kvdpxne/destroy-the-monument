package me.kvdpxne.dtm.data.validation.codes;

import me.kvdpxne.dtm.UnsubstantiatedInitializationError;

/**
 * @author Łukasz Pietrzak (kvdpxne)
 * @version 0.1.0
 * @since 0.1.0
 */
public final class RevivalPositionCodes {

  /**
   * @since 0.1.0
   */
  public static final int INVALID_X = 0xa28bb09f;

  /**
   * @since 0.1.0
   */
  public static final int INVALID_Y = 0xf45d04a1;

  /**
   * @since 0.1.0
   */
  public static final int INVALID_Z = 0xaf823c3a;

  /**
   * @since 0.1.0
   */
  public static final int INVALID_PITCH = 0xc7546eb6;

  /**
   * @since 0.1.0
   */
  public static final int INVALID_YAW = 0xed038065;

  /**
   * Private constructor to prevent instantiation.
   * <p>
   * Throws {@link UnsubstantiatedInitializationError} if invoked,
   * enforcing this class's nature as a static utility container.
   *
   * @throws UnsubstantiatedInitializationError Always thrown upon invocation
   * @since 0.1.0
   */
  private RevivalPositionCodes() {
    throw new UnsubstantiatedInitializationError(this.getClass());
  }
}
