package me.kvdpxne.dtm.command

object ParameterValidators {

  val STRING_VALIDATOR: ParameterValidatorHandler<String> = {
    ParameterValidatorResult.fine(it)
  }

  val INTEGER_VALIDATOR: ParameterValidatorHandler<Int> = {
    try {
      ParameterValidatorResult.fine(it.toInt())
    } catch (e: NumberFormatException) {
      ParameterValidatorResult.error("'$it' is not a valid integer")
    }
  }

  val POSITIVE_INTEGER_VALIDATOR: ParameterValidatorHandler<Int> = {
    try {
      val value = it.toInt()

      if (value >= 0) {
        ParameterValidatorResult.fine(value)
      } else {
        ParameterValidatorResult.error("The integer must be positive")
      }
    } catch (e: NumberFormatException) {
      ParameterValidatorResult.error("'$it' is not a valid integer")
    }
  }

  val FLOAT_VALIDATOR: ParameterValidatorHandler<Float> = {
    try {
      ParameterValidatorResult.fine(it.toFloat())
    } catch (e: NumberFormatException) {
      ParameterValidatorResult.error("'$it' is not a valid integer")
    }
  }

  val POSITIVE_FLOAT_VALIDATOR: ParameterValidatorHandler<Float> = {
    try {
      val value = it.toFloat()

      if (value >= 0.0F) {
        ParameterValidatorResult.fine(value)
      } else {
        ParameterValidatorResult.error("The integer must be positive")
      }
    } catch (e: NumberFormatException) {
      ParameterValidatorResult.error("'$it' is not a valid integer")
    }
  }
}