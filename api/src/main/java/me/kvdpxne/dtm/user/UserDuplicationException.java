package me.kvdpxne.dtm.user;

/**
 * Exception indicating an attempt to create or register a user that conflicts with existing unique
 * constraints. Typically thrown when:
 * <ul>
 *   <li>Creating a user with an existing username</li>
 *   <li>Registering a duplicate email address</li>
 *   <li>Assigning a unique identifier already present in the system</li>
 * </ul>
 * <p>
 * This exception should trigger conflict resolution workflows in user management
 * systems.
 *
 * @since 0.1.0
 */
public class UserDuplicationException extends UserException {

  private static final long serialVersionUID = -6566540440903722392L;

  /**
   * Fully parameterized constructor for precise exception control.
   *
   * @param message            Conflict description (may include duplicate identifier)
   * @param cause              Root cause exception (e.g.,
   *                           SQLIntegrityConstraintViolationException)
   * @param enableSuppression  Exception suppression toggle
   * @param writableStackTrace Stack trace population toggle
   * @since 0.1.0
   */
  protected UserDuplicationException(
    final String message,
    final Throwable cause,
    final boolean enableSuppression,
    final boolean writableStackTrace
  ) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

  /**
   * Constructs with conflict description and root cause.
   *
   * @param message Description of duplicate conflict
   * @param cause   Underlying data access exception
   * @since 0.1.0
   */
  public UserDuplicationException(
    final String message,
    final Throwable cause
  ) {
    super(message, cause);
  }

  /**
   * Constructs with conflict description.
   *
   * @param message Description of duplicate conflict
   * @since 0.1.0
   */
  public UserDuplicationException(
    final String message
  ) {
    super(message);
  }

  /**
   * Constructs with generic duplication message.
   *
   * @since 0.1.0
   */
  public UserDuplicationException() {
    super("User duplication detected");
  }
}