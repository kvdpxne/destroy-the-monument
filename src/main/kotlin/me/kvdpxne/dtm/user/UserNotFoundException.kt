package me.kvdpxne.dtm.user

/**
 * Exception thrown when a requested user cannot be found.
 *
 * `UserNotFoundException` is a specialized form of `UserException` used to indicate that
 * a search or retrieval operation has failed to locate a specific user within the system.
 * It provides both a customizable error message and an optional cause, allowing developers
 * to trace back the source of the error if needed.
 *
 * This exception is ideal for scenarios where user-related data is expected to exist but is missing,
 * enabling clear communication of this error type throughout the application.
 *
 * @param message The detail message explaining the reason the user was not found. Defaults to an empty string.
 * @param cause The underlying cause of this exception, if applicable. Defaults to `null`.
 * @since 0.1.0
 */
open class UserNotFoundException(
  message: String = "",
  cause: Throwable? = null
) : UserException(message, cause) {

  companion object {

    /**
     * @since 0.1.0
     */
    @Suppress("ConstPropertyName")
    private const val serialVersionUID: Long = 1813457582575061013L
  }
}