package me.kvdpxne.dtm.data.validation

import java.util.Collections
import java.util.function.Consumer
import java.util.function.Supplier
import org.jetbrains.annotations.Range
import org.jetbrains.annotations.UnmodifiableView

sealed class BasicValidationResult : ValidationResult {

  override fun isValid(): Boolean {
    return this is Success
  }

  override fun combine(
    vararg others: ValidationResult?
  ): ValidationResult {
    if (others.isEmpty()) {
      return this
    }

    val errors: List<ValidationError> = buildList {
      for (result: ValidationResult? in others) {
        if (result is Failure) {
          this.addAll(result.errors)
        }
      }
    }

    return if (errors.isEmpty()) {
      Success
    } else {
      Failure(errors)
    }
  }

  override fun onFailure(
    action: Consumer<Collection<ValidationError>>
  ): ValidationResult {
    if (this is Failure) {
      action.accept(this.errors)
    }
    return this
  }

  override fun onSuccess(
    action: Supplier<Void>
  ): ValidationResult {
    if (this is Success) {
      action.get()
    }
    return this
  }

  class Failure(
    internal val errors: Collection<ValidationError>
  ) : BasicValidationResult(), ValidationResult.Failure {

    override fun getErrors(): @UnmodifiableView Collection<ValidationError> {
      return Collections.unmodifiableCollection(this.errors)
    }

    override fun hasErrorByCode(
      code: @Range(from = -2147483648, to = -1) Int
    ): Boolean {
      require(0 > code) {
        "Code must be negative"
      }

      return this.errors.any { error: ValidationError ->
        code == error.code
      }
    }

    override fun isValid(): Boolean {
      return !this.errors.isEmpty()
    }

    override fun onFailure(
      action: Consumer<Collection<ValidationError>>
    ): ValidationResult {
      action.accept(this.errors)
      return this
    }
  }

  object Success
    : BasicValidationResult(), ValidationResult.Success {

    override fun isValid(): Boolean {
      return true
    }

    override fun onSuccess(
      action: Supplier<Void>
    ): ValidationResult {
      action.get()
      return this
    }
  }
}