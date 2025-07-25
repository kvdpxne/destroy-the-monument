package me.kvdpxne.dtm.data.validation

import me.kvdpxne.dtm.shared.toSingleLines
import org.jetbrains.annotations.Range

class BasicValidationResultBuilder : ValidationResultBuilder {

  /**
   * Lazy-initialized collection of validation errors.
   * The collection is only created when the first error is added.
   *
   * @since 0.1.0
   */
  private val errorsDelegate: Lazy<MutableList<ValidationError>> = lazy {
    arrayListOf()
  }

  /**
   * Gets the mutable error list, initializing it if necessary.
   *
   * @since 0.1.0
   */
  private val errors: MutableList<ValidationError>
    get() = this.errorsDelegate.value

  override fun addError(
    error: ValidationError
  ): ValidationResultBuilder {
    this.errors.add(error)
    return this
  }

  override fun addError(
    field: String,
    message: String,
    code: @Range(from = -2147483648, to = -1) Int,
    invalidValue: Any?,
    metadata: Map<String, Any?>?
  ): ValidationResultBuilder {
    return this.addError(
      BasicValidationError(
        field,
        message.toSingleLines(),
        code,
        invalidValue,
        metadata
      )
    )
  }

  override fun build(): ValidationResult {
    if (!this.errorsDelegate.isInitialized() || this.errors.isEmpty()) {
      return BasicValidationResult.Success
    }
    return BasicValidationResult.Failure(this.errors.toList())
  }

  override fun reset() {
    if (this.errorsDelegate.isInitialized()) {
      this.errors.clear()
    }
  }
}