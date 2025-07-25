package me.kvdpxne.dtm;

import me.kvdpxne.dtm.util.Strings;

/**
 * A foundational unchecked exception serving as the base for custom runtime
 * exceptions within the application. Provides standardized message handling and
 * configuration options for exception chaining and stack trace behavior.
 * <p>
 * When constructed with a blank message ({@code null}, empty, or
 * whitespace-only), a predefined default message will be used automatically.
 * This exception supports configurable suppression enabling and writable stack
 * trace parameters.
 *
 * @author Łukasz Pietrzak (kvdpxne)
 * @version 0.1.0
 * @since 0.1.0
 */
public class RootException
  extends RuntimeException {

  /**
   * Unique identifier for serialization/deserialization compatibility.
   *
   * @see java.io.Serializable
   * @since 0.1.0
   */
  private static final long serialVersionUID = 2854257465038198694L;

  /**
   * Constructs a new exception with detailed configuration parameters.
   * <p>
   * If the provided message is blank ({@code null}, empty, or whitespace-only),
   * the default message {@value DefaultMessageLazyHolder#NOT_SPECIFIED} will be
   * used.
   *
   * @param message            the detail message (may be {@code null})
   * @param cause              the causal exception (may be {@code null})
   * @param enableSuppression  whether exception suppression is enabled
   * @param writableStackTrace whether the stack trace should be writable
   * @see Strings#isBlank(String)
   * @since 0.1.0
   */
  protected RootException(
    final String message,
    final Throwable cause,
    final boolean enableSuppression,
    final boolean writableStackTrace
  ) {
    super(
      Strings.isBlank(message)
        ? DefaultMessageLazyHolder.NOT_SPECIFIED
        : message,
      cause,
      enableSuppression,
      writableStackTrace
    );
  }

  /**
   * Constructs a new exception with specified detail message and cause.
   * <p>
   * Configures the exception with:
   * <ul>
   *   <li>Suppression enabled</li>
   *   <li>Non-writable stack trace</li>
   * </ul>
   *
   * @param message the detail message (may be {@code null})
   * @param cause   the causal exception (may be {@code null})
   * @see #RootException(String, Throwable, boolean, boolean)
   * @since 0.1.0
   */
  public RootException(
    final String message,
    final Throwable cause
  ) {
    this(message, cause, true, false);
  }

  /**
   * Constructs a new exception with specified detail message and no cause.
   *
   * @param message the detail message (may be {@code null})
   * @see #RootException(String, Throwable)
   * @since 0.1.0
   */
  public RootException(
    final String message
  ) {
    this(message, null);
  }

  /**
   * Constructs a new exception with default message and no cause.
   * <p>
   * Uses predefined message: {@value DefaultMessageLazyHolder#NOT_SPECIFIED}
   *
   * @see #RootException(String)
   * @since 0.1.0
   */
  public RootException() {
    this(DefaultMessageLazyHolder.NOT_SPECIFIED);
  }

  /**
   * Holder class for lazy initialization of the default exception message.
   * <p>
   * Utilizes the initialization-on-demand holder idiom for thread-safe static
   * field loading without explicit synchronization.
   *
   * @since 0.1.0
   */
  private static final class DefaultMessageLazyHolder {

    /**
     * Default exception message used when no message is provided.
     * <p>
     * Constant value: {@value}
     *
     * @since 0.1.0
     */
    public static final String NOT_SPECIFIED
      = "The message for this error was not specified.";

    /**
     * Private constructor to prevent instantiation.
     *
     * @throws AssertionError always thrown on instantiation attempt
     * @since 0.1.0
     */
    private DefaultMessageLazyHolder() {
      throw new AssertionError("Cannot instantiate holder class.");
    }
  }
}
