package me.kvdpxne.dtm.data.validation.codes;

import me.kvdpxne.dtm.UnsubstantiatedInitializationError;

public final class MonumentPositionCodes {

  /**
   * @since 0.1.0
   */
  public static final int INVALID_X = -1531351989;

  /**
   * @since 0.1.0
   */
  public static final int INVALID_Y = -905272957;

  /**
   * @since 0.1.0
   */
  public static final int INVALID_Z = -2115938479;

  /**
   * Private constructor to prevent instantiation.
   * <p>
   * Throws {@link UnsubstantiatedInitializationError} if invoked,
   * enforcing this class's nature as a static utility container.
   *
   * @throws UnsubstantiatedInitializationError Always thrown upon invocation
   * @since 0.1.0
   */
  private MonumentPositionCodes() {
    throw new UnsubstantiatedInitializationError(this.getClass());
  }
}
