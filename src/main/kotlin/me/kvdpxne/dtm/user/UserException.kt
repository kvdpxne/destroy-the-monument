package me.kvdpxne.dtm.user

/**
 * Base exception for user-related errors within the plugin.
 *
 * [UserException] serves as the foundation for handling all exceptions related
 * to user operations, providing a customizable message and optional cause.
 * By extending [RuntimeException], this exception allows unchecked handling,
 * making it suitable for scenarios where user-related errors may occur
 * unexpectedly during runtime.
 *
 * This class can be extended by other specific user-related exceptions,
 * enabling a structured approach to error management within the user module.
 *
 * @param message The detail message for this exception, which provides
 *                specific information about the error encountered. Defaults to
 *                an empty string.
 * @param cause The underlying cause of the exception, if any. Defaults
 *              to `null`.
 * @since 0.1.0
 */
open class UserException(
  // @formatter:off
  message: String    = "",
  cause  : Throwable? = null
  // @formatter:on
) : RuntimeException(message, cause) {

  companion object {

    /**
     * @since 0.1.0
     */
    @Suppress("ConstPropertyName")
    private const val serialVersionUID: Long = -8556564440605537188L
  }
}