package me.kvdpxne.dtm.validation.extensions

import me.kvdpxne.dtm.data.validation.BasicValidationResultBuilder
import me.kvdpxne.dtm.data.validation.ValidationResultBuilder

/**
 * @since 0.1.0
 */
fun BasicValidationResultBuilder.addUndescribedError(
  field: String,
  code: Int
): ValidationResultBuilder {
  return this.addError(
    field,
    "The error does not contain content.",
    code
  )
}