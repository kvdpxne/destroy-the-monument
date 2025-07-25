package me.kvdpxne.dtm.game;

import me.kvdpxne.dtm.RootException;

/**
 * Represents game-specific runtime errors in the application.
 * Extends the foundational {@link RootException} to provide specialized
 * exception handling for game-related operations. This exception type
 * uses a custom default message when no message is explicitly provided.
 *
 * @see RootException
 * @since 0.1.0
 */
public class GameException
  extends RootException {

  /**
   * Unique identifier for serialization/deserialization compatibility.
   *
   * @see java.io.Serializable
   * @since 0.1.0
   */
  private static final long serialVersionUID
    = 2102574301802282576L;

  /**
   * Creates a fully configurable game-related exception instance.
   * Inherits behavior from {@link RootException#RootException(String, Throwable, boolean, boolean)}
   * with identical parameter handling.
   *
   * @param message             The detail message describing the game error (may be {@code null})
   * @param cause               The root cause of the exception (may be {@code null})
   * @param enableSuppression   Whether exception suppression is enabled
   * @param writableStackTrace  Whether the stack trace should be writable
   * @since 0.1.0
   */
  protected GameException(
    final String message,
    final Throwable cause,
    final boolean enableSuppression,
    final boolean writableStackTrace
  ) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

  /**
   * Creates a game exception with message and cause.
   * Inherits behavior from {@link RootException#RootException(String, Throwable)}
   * with suppression enabled and non-writable stack trace.
   *
   * @param message  The detail message describing the game error (may be {@code null})
   * @param cause    The root cause of the exception (may be {@code null})
   * @since 0.1.0
   */
  public GameException(
    final String message,
    final Throwable cause
  ) {
    super(message, cause);
  }

  /**
   * Creates a game exception with message but no cause. Equivalent to
   * {@code GameException(message, null)}.
   *
   * @param message The detail message describing the game error (may be
   *                {@code null})
   * @since 0.1.0
   */
  public GameException(
    final String message
  ) {
    super(message);
  }

  /**
   * Creates a game exception with no message or cause.
   * <p>
   * Uses the specialized default message: "The message for the game-related
   * error has not been specified."
   *
   * @since 0.1.0
   */
  public GameException() {
    super("The message for the game-related error has not been specified.");
  }
}
