package me.kvdpxne.dtm.data.validation.codes;

import me.kvdpxne.dtm.UnsubstantiatedInitializationError;

public final class TeamCodes {

  public static final int INVALID_NAME = 0xa9bb2b88;

  public static final int INVALID_COLOR_OF_ARMOR = 0xd382dac4;

  public static final int INVALID_COLOR_OF_PROFESSION = 0xf951e109;

  public static final int INVALID_COLOR_ON_CHAT = 0xe6231af6;

  public static final int INVALID_COLOR_ON_PLAYER_LIST = 0x8c2e6b07;

  /**
   * Private constructor to prevent instantiation.
   * <p>
   * Throws {@link UnsubstantiatedInitializationError} if invoked,
   * enforcing this class's nature as a static utility container.
   *
   * @throws UnsubstantiatedInitializationError Always thrown upon invocation
   * @since 0.1.0
   */
  private TeamCodes() {
    throw new UnsubstantiatedInitializationError(this.getClass());
  }
}
