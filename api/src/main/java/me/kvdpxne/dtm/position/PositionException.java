package me.kvdpxne.dtm.position;

import me.kvdpxne.dtm.RootException;

/**
 * @author Łukasz Pietrzak (kvdpxne)
 * @version 0.1.0
 * @since 0.1.0
 */
public class PositionException
  extends
  RootException {

  /**
   * @since 0.1.0
   */
  private static final long serialVersionUID = -1170148774646828859L;

  /**
   * @since 0.1.0
   */
  protected PositionException(
    final String message,
    final Throwable cause,
    final boolean enableSuppression,
    final boolean writableStackTrace
  ) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

  /**
   * @since 0.1.0
   */
  public PositionException(
    final String message,
    final Throwable cause
  ) {
    super(message, cause);
  }

  /**
   * @since 0.1.0
   */
  public PositionException(
    final String message
  ) {
    super(message);
  }

  /**
   * @since 0.1.0
   */
  public PositionException() {
    super("");
  }
}
