package me.kvdpxne.dtm.data.validation.rules;

import me.kvdpxne.dtm.UnsubstantiatedInitializationError;

/**
 * @since 0.1.0
 */
public final class UserRules {

  /**
   * @since 0.1.0
   */
  public static final int MIN_NAME_LENGTH = 3;

  /**
   * @since 0.1.0
   */
  public static final int MAX_NAME_LENGTH = 16;


  /**
   * Private constructor to prevent instantiation.
   * <p>
   * Throws {@link UnsubstantiatedInitializationError} if invoked,
   * enforcing this class's nature as a static utility container.
   *
   * @throws UnsubstantiatedInitializationError Always thrown upon invocation
   * @since 0.1.0
   */
  private UserRules() {
    throw new UnsubstantiatedInitializationError(this.getClass());
  }
}
