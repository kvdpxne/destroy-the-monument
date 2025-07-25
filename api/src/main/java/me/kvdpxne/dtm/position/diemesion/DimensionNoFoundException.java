package me.kvdpxne.dtm.position.diemesion;

/**
 * @since 0.1.0
 */
public class DimensionNoFoundException
  extends
  DimensionException {

  /**
   * @since 0.1.0
   */
  private static final long serialVersionUID = 7354904699863340754L;

  /**
   * @since 0.1.0
   */
  protected DimensionNoFoundException(
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
  public DimensionNoFoundException(
    final String message,
    final Throwable cause
  ) {
    super(message, cause);
  }

  /**
   * @since 0.1.0
   */
  public DimensionNoFoundException(
    final String message
  ) {
    super(message);
  }

  /**
   * @since 0.1.0
   */
  public DimensionNoFoundException() {
    super("");
  }
}
