package me.kvdpxne.dtm.command

/**
 * @param value
 * @param errorMessage
 *
 * @since 0.1.0
 */
class ParameterValidatorResult<T>(
  val value: T?,
  val errorMessage: String?
) {

  companion object {

    /**
     *
     */
    fun <T> fine(value: T): ParameterValidatorResult<T> {
      return ParameterValidatorResult(value, null)
    }

    /**
     *
     */
    fun <T> error(message: String): ParameterValidatorResult<T> {
      return ParameterValidatorResult(null, message)
    }
  }
}