package me.kvdpxne.dtm

/**
 * Base class for all error messages in the system.
 *
 * Used when unexpected problems occur during gameplay or server operations.
 * Provides clear error descriptions and tracks the original cause of issues.
 *
 * @param message Description of what went wrong
 * @param cause Original error that triggered this exception (if applicable)
 * @param enableSuppression Technical setting for error handling (default: true)
 * @param writableStackTrace Technical setting for error tracking (default: true)
 * @since 0.1.0
 */
open class RootException protected constructor(
  message: String = "",
  cause: Throwable? = null,
  enableSuppression: Boolean = true,
  writableStackTrace: Boolean = true
) : RuntimeException(
  message,
  cause,
  enableSuppression,
  writableStackTrace
) {

  companion object {

    /**
     * @since 0.1.0
     */
    private const val serialVersionUID: Long = -5409239583264290974L
  }
}