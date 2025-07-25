package me.kvdpxne.dtm;

/**
 * Signals an illegal attempt to instantiate a class that is designed to be
 * non-instantiable.
 * <p>
 * This error is typically thrown from private constructors of utility classes
 * to enforce their static-only nature and prevent object creation.
 * <p>
 * The error message can be customized based on the provided object:
 * <ul>
 *   <li>For {@code null} input: Uses a standard message</li>
 *   <li>For {@code String} input: Formats the message with the string</li>
 *   <li>For {@code Class} input: Formats the message with the class name</li>
 *   <li>For other objects: Falls back to the standard message</li>
 * </ul>
 *
 * @author Łukasz Pietrzak (kvdpxne)
 * @version 0.1.0
 * @since 0.1.0
 */
public final class UnsubstantiatedInitializationError
  extends Error {

  /**
   * Unique identifier for serialization/deserialization compatibility.
   *
   * @since 0.1.0
   */
  private static final long serialVersionUID = -3933677435063777751L;

  /**
   * Constructs an error with a context-specific message.
   * <p>
   * The message is generated based on the provided object's type:
   * <ul>
   *   <li>If {@code object} is a {@code Class}, includes the class name</li>
   *   <li>If {@code object} is a {@code String}, includes the string</li>
   *   <li>Otherwise uses a generic message</li>
   * </ul>
   *
   * @param object The context object (class instance, class, or string) to
   *               include in the message
   * @since 0.1.0
   */
  public UnsubstantiatedInitializationError(
    final Object object
  ) {
    super(buildErrorMessage(object));
  }

  /**
   * Constructs an error with the standard message.
   * <p>
   * Equivalent to calling {@code ErrorUnsubstantiatedInitialization(null)}.
   *
   * @since 0.1.0
   */
  public UnsubstantiatedInitializationError() {
    this(null);
  }

  /**
   * Builds an appropriate error message based on the input object.
   * <p>
   * Message generation rules:
   * <ol>
   *   <li>{@code null} input: "This constructor should not be initialized."</li>
   *   <li>{@code String} input: "The constructor of \"[string]\" should not be initialized."</li>
   *   <li>{@code Class} input: "The constructor of \"[classname]\" should not be initialized."</li>
   *   <li>Other types: Falls back to standard message</li>
   * </ol>
   *
   * @param object The context object for message generation
   * @return The formatted error message
   * @since 0.1.0
   */
  private static String buildErrorMessage(
    final Object object
  ) {
    if (null == object) {
      return LazyHolderStandardMessage.SM;
    }

    if (object instanceof String) {
      return LazyHolderFormattedMessage.FM
        .replace("{}", (String) object);
    }

    if (object instanceof Class<?>) {
      return LazyHolderFormattedMessage.FM
        .replace("{}", ((Class<?>) object).getName());
    }

    return LazyHolderStandardMessage.SM;
  }

  /**
   * Holds the standard error message template.
   * <p>
   * This lazy initialization holder pattern ensures efficient memory usage by
   * deferring creation until first access.
   *
   * @since 0.1.0
   */
  private static final class LazyHolderStandardMessage {

    /**
     * The standard error message for unsubstantiated initialization attempts.
     */
    private static final String SM = "This constructor should not be initialized.";
  }

  /**
   * Holds the formatted error message template.
   * <p>
   * This lazy initialization holder pattern ensures efficient memory usage by
   * deferring creation until first access. The template contains a placeholder
   * ({@code {}}) that gets replaced with contextual information.
   *
   * @since 0.1.0
   */
  private static final class LazyHolderFormattedMessage {

    /**
     * The formatted error message template with placeholder.
     */
    private static final String FM = "The constructor of \"{}\" should not be initialized.";
  }
}
