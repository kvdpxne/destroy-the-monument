package me.kvdpxne.dtm.data.validation

import org.jetbrains.annotations.Range

/**
 * Creates a failure validation result pair with default error count.
 *
 * @param result Pre-built validation result containing errors
 * @return Pair where:
 * - First: -1 (generic failure indicator)
 * - Second: Provided validation result
 * @since 0.1.0
 */
fun <T> failure(
  result: ValidationResult
): Pair<T?, ValidationResult> {
  return Pair(null, result)
}

/**
 * Creates a failure validation result from a single error.
 *
 * @param error Validation error to include in the result
 * @return Pair where:
 * - First: -1 (generic failure indicator)
 * - Second: Validation result containing the single error
 * @since 0.1.0
 */
fun <T> failure(
  error: ValidationError
): Pair<T?, ValidationResult> {
  val result: ValidationResult =
    withValidationBuilder { builder: ValidationResultBuilder ->
      builder.addError(error).build()
    }
  return failure(result)
}

/**
 * Creates a failure validation result with a basic error.
 *
 * @param field Name of the invalid field
 * @param message Human-readable error description
 * @param code Negative error code (range: -2,147,483,648 to -1)
 * @param invalidValue Optional invalid value that caused the error
 * @return Pair where:
 * - First: -1 (generic failure indicator)
 * - Second: Validation result containing the generated error
 *
 * @since 0.1.0
 */
fun <T> failure(
  field: String,
  message: String,
  code: @Range(from = -2147483648, to = -1) Int,
  invalidValue: Any? = null
): Pair<T?, ValidationResult> {
  return failure(
    BasicValidationError(
      field,
      message,
      code,
      invalidValue
    )
  )
}

/**
 * Creates a success validation result.
 *
 * @param rows Number of affected rows/items (default: 0)
 * @return Pair where:
 *         - First: Non-negative count of affected items
 *         - Second: Success validation result instance
 * @since 0.1.0
 */
fun <T> success(
  rows: T
): Pair<T, ValidationResult> {
  return Pair(rows, BasicValidationResult.Success)
}