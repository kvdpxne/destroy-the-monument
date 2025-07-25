package me.kvdpxne.dtm.data.validation.codes;

import me.kvdpxne.dtm.UnsubstantiatedInitializationError;

/**
 *
 * @author Łukasz Pietrzak (kvdpxne)
 * @version 0.1.0
 * @since 0.1.0
 */
public final class UserCodes {

  /**
   * @since 0.1.0
   */
  public static final int INVALID_NAME = 0xfb824844;

  /**
   * @since 0.1.0
   */
  public static final int INVALID_LOCALIZATION = 0xeb8a4405;

  /**
   * Private constructor to prevent instantiation.
   * <p>
   * Throws {@link UnsubstantiatedInitializationError} if invoked,
   * enforcing this class's nature as a static utility container.
   *
   * @throws UnsubstantiatedInitializationError Always thrown upon invocation
   * @since 0.1.0
   */
  private UserCodes() {
    throw new UnsubstantiatedInitializationError(this.getClass());
  }
}
