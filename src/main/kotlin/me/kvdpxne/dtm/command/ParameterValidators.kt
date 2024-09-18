package me.kvdpxne.dtm.command

object ParameterValidators {

  val STRING_VALIDATOR: ParameterValidatorHandler<String> = {
    ParameterValidationResult.ok(it)
  }

  val INTEGER_VALIDATOR: ParameterValidatorHandler<Int> = {
    try {
      ParameterValidationResult.ok(it.toInt())
    } catch (e: NumberFormatException) {
      ParameterValidationResult.error("'$it' is not a valid integer")
    }
  }

  val POSITIVE_INTEGER_VALIDATOR: ParameterValidatorHandler<Int> = {
    try {
      val value = it.toInt()

      if (value >= 0) {
        ParameterValidationResult.ok(value)
      } else {
        ParameterValidationResult.error("The integer must be positive")
      }
    } catch (e: NumberFormatException) {
      ParameterValidationResult.error("'$it' is not a valid integer")
    }
  }

  val POSITIVE_LONG_VALIDATOR: ParameterValidatorHandler<Long> = {
    try {
      val value: Long = it.toLong()

      if (0 <= value) {
        ParameterValidationResult.ok(value)
      } else {
        ParameterValidationResult.error("The integer must be positive")
      }
    } catch (e: NumberFormatException) {
      ParameterValidationResult.error("'$it' is not a valid integer")
    }
  }

  val FLOAT_VALIDATOR: ParameterValidatorHandler<Float> = {
    try {
      ParameterValidationResult.ok(it.toFloat())
    } catch (e: NumberFormatException) {
      ParameterValidationResult.error("'$it' is not a valid integer")
    }
  }

  val POSITIVE_FLOAT_VALIDATOR: ParameterValidatorHandler<Float> = {
    try {
      val value = it.toFloat()

      if (value >= 0.0F) {
        ParameterValidationResult.ok(value)
      } else {
        ParameterValidationResult.error("The integer must be positive")
      }
    } catch (e: NumberFormatException) {
      ParameterValidationResult.error("'$it' is not a valid integer")
    }
  }
}