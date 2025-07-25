package me.kvdpxne.dtm.user;

/**
 * Exception indicating a requested user resource could not be located. Thrown when:
 * <ul>
 *   <li>Querying by non-existent identifier</li>
 *   <li>Accessing user profiles that have been removed</li>
 *   <li>Resolving references to deactivated accounts</li>
 * </ul>
 * <p>
 * This exception typically triggers "not found" handling in API responses and
 * user interface workflows.
 *
 * @since 0.1.0
 */
public class UserNotFoundException extends UserException {

  private static final long serialVersionUID = -2618818579969403291L;

  /**
   * Fully parameterized constructor for precise exception control.
   *
   * @param message            Detailed "not found" description
   * @param cause              Root cause exception (e.g., EmptyResultDataAccessException)
   * @param enableSuppression  Exception suppression toggle
   * @param writableStackTrace Stack trace population toggle
   * @since 0.1.0
   */
  protected UserNotFoundException(
    final String message,
    final Throwable cause,
    final boolean enableSuppression,
    final boolean writableStackTrace
  ) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

  /**
   * Constructs with "not found" description and root cause.
   *
   * @param message Description of missing resource
   * @param cause   Underlying data access exception
   * @since 0.1.0
   */
  public UserNotFoundException(
    final String message,
    final Throwable cause
  ) {
    super(message, cause);
  }

  /**
   * Constructs with "not found" description.
   *
   * @param message Description of missing resource
   * @since 0.1.0
   */
  public UserNotFoundException(
    final String message
  ) {
    super(message);
  }

  /**
   * Constructs with generic "not found" message.
   *
   * @since 0.1.0
   */
  public UserNotFoundException() {
    super("User not found");
  }
}