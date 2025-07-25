package me.kvdpxne.dtm.data.validation;

import me.kvdpxne.dtm.RootException;

/**
 * @author Łukasz Pietrzak (kvdpxne)
 * @version 0.1.0
 * @since 0.1.0
 */
public class ValidationException
  extends
  RootException {

  private static final long serialVersionUID = 6531809157537421599L;

  protected ValidationException(
    final String message,
    final Throwable cause,
    final boolean enableSuppression,
    final boolean writableStackTrace
  ) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

  public ValidationException(
    final String message,
    final Throwable cause
  ) {
    super(message, cause);
  }

  public ValidationException(
    final String message
  ) {
    super(message);
  }

  public ValidationException() {
    super();
  }
}
