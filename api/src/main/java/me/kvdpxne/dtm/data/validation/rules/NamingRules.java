package me.kvdpxne.dtm.data.validation.rules;

import me.kvdpxne.dtm.UnsubstantiatedInitializationError;

/**
 * @since 0.1.0
 */
public final class NamingRules {

  /**
   * @since 0.1.0
   */
  public static final int MIN_NAME_LENGTH = 2;

  /**
   * @since 0.1.0
   */
  public static final int MAX_NAME_LENGTH = 32;

  /**
   * @since 0.1.0
   */
  public static final char[] ALLOWED_CHARACTERS = {
    '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
    'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O',
    'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
    'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o',
    'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
    '_'
  };

  /**
   * Private constructor to prevent instantiation.
   * <p>
   * Throws {@link UnsubstantiatedInitializationError} if invoked,
   * enforcing this class's nature as a static utility container.
   *
   * @throws UnsubstantiatedInitializationError Always thrown upon invocation
   * @since 0.1.0
   */
  private NamingRules() {
    throw new UnsubstantiatedInitializationError(this.getClass());
  }
}
