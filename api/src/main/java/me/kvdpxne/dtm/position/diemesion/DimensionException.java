package me.kvdpxne.dtm.position.diemesion;

import me.kvdpxne.dtm.position.PositionException;

/**
 * @since 0.1.0
 */
public class DimensionException
  extends
  PositionException {

  /**
   * @since 0.1.0
   */
  private static final long serialVersionUID = -8053570664585863201L;

  /**
   * @since 0.1.0
   */
  protected DimensionException(
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
  public DimensionException(
    final String message,
    final Throwable cause
  ) {
    super(message, cause);
  }

  /**
   * @since 0.1.0
   */
  public DimensionException(
    final String message
  ) {
    super(message);
  }

  /**
   * @since 0.1.0
   */
  public DimensionException() {
    super("");
  }
}
