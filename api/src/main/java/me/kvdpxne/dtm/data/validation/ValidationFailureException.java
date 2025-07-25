package me.kvdpxne.dtm.data.validation;

/**
 * @author Łukasz Pietrzak (kvdpxne)
 * @version 0.1.0
 * @since 0.1.0
 */
public class ValidationFailureException extends ValidationException {

  private static final long serialVersionUID = -3341539062634223267L;

  protected ValidationFailureException(
    final String message,
    final Throwable cause,
    final boolean enableSuppression,
    final boolean writableStackTrace
  ) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

  public ValidationFailureException(
    final String message,
    final Throwable cause
  ) {
    super(message, cause);
  }

  public ValidationFailureException(
    final String message
  ) {
    super(message);
  }

  public ValidationFailureException() {
    super();
  }
}
