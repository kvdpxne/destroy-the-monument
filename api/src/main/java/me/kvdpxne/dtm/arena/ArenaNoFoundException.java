package me.kvdpxne.dtm.arena;

/**
 * @since 0.1.0
 */
public class ArenaNoFoundException
  extends ArenaException {

  /**
   * @since 0.1.0
   */
  private static final long serialVersionUID = -180212178502037131L;

  /**
   * @since 0.1.0
   */
  public ArenaNoFoundException(
    final String message,
    final String code
  ) {
    super(message, code);
  }

  /**
   * @since 0.1.0
   */
  public ArenaNoFoundException(String message, Throwable cause, String code) {
    super(message, cause, code);
  }
}
