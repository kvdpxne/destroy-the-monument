package me.kvdpxne.dtm.command

/**
 * @param errorMessage
 * @param mappedResult
 *
 * @since 0.1.0
 */
class ParameterValidationResult<T>(
  val errorMessage: String?,
  val mappedResult: T?
) {

  companion object {

    /**
     * @param errorMessage
     */
    fun <T> error(
      errorMessage: String
    ): ParameterValidationResult<T> {
      return ParameterValidationResult(errorMessage, null)
    }

    /**
     * @param value
     */
    fun <T> ok(
      value: T
    ): ParameterValidationResult<T> {
      return ParameterValidationResult(null, value)
    }
  }

  override fun toString(): String {
    return "ParameterValidationResult(errorMessage=$errorMessage, mappedResult=$mappedResult)"
  }
}