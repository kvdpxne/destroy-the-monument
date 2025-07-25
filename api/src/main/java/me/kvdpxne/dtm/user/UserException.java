package me.kvdpxne.dtm.user;

import me.kvdpxne.dtm.RootException;

/**
 * Base exception for all user-related operation failures within the application. Represents
 * fundamental errors in user management workflows including but not limited to:
 * <ul>
 *   <li>User data persistence failures</li>
 *   <li>Profile state inconsistencies</li>
 *   <li>Authorization and authentication errors</li>
 *   <li>Session management issues</li>
 * </ul>
 * <p>
 * This exception hierarchy enables precise error handling and recovery strategies
 * for user management subsystems.
 *
 * @since 0.1.0
 */
public class UserException extends RootException {

  private static final long serialVersionUID = 5914294232811266064L;

  /**
   * Constructs a fully specified user exception with complete diagnostic controls.
   *
   * @param message            Detailed diagnostic message (may be null)
   * @param cause              Root cause exception (may be null)
   * @param enableSuppression  Whether exception suppression is enabled
   * @param writableStackTrace Whether stack trace population is enabled
   * @since 0.1.0
   */
  protected UserException(
    final String message,
    final Throwable cause,
    final boolean enableSuppression,
    final boolean writableStackTrace
  ) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

  /**
   * Constructs a user exception with diagnostic message and root cause.
   *
   * @param message Detailed diagnostic message (may be null)
   * @param cause   Root cause exception (may be null)
   * @since 0.1.0
   */
  public UserException(
    final String message,
    final Throwable cause
  ) {
    super(message, cause);
  }

  /**
   * Constructs a user exception with diagnostic message.
   *
   * @param message Detailed diagnostic message (may be null)
   * @since 0.1.0
   */
  public UserException(
    final String message
  ) {
    super(message);
  }

  /**
   * Constructs an unspecified user exception.
   *
   * @since 0.1.0
   */
  public UserException() {
    super("");
  }
}