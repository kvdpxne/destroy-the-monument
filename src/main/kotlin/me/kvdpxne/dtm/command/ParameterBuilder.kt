package me.kvdpxne.dtm.command

import me.kvdpxne.dtm.shared.ancillary.Buildable

class ParameterBuilder<T> : Buildable<Parameter<T>> {

  // @formatter:off
  private var name    : String?  = null
  private var required: Boolean? = null
  private var varargs : Boolean = false

  private var validator: ParameterValidatorHandler<T>? = null
  private var autoCompletionHandler: AutoCompletionHandler? = null
  // @formatter:on

  fun name(name: String): ParameterBuilder<T> {
    this.name = name
    return this
  }

  /**
   *
   */
  fun required(): ParameterBuilder<T> {
    this.required = true
    return this
  }

  /**
   *
   */
  fun optional(): ParameterBuilder<T> {
    this.required = false
    return this
  }

  /**
   *
   */
  fun varargs(): ParameterBuilder<T> {
    this.varargs = true
    return this
  }

  fun validationBy(validator: ParameterValidatorHandler<T>): ParameterBuilder<T> {
    this.validator = validator
    return this
  }

  fun autocompletedWith(
    autoCompletionHandler: AutoCompletionHandler
  ): ParameterBuilder<T> {
    this.autoCompletionHandler = autoCompletionHandler
    return this
  }

  /**
   *
   */
  override fun build(): Parameter<T> {
    return Parameter<T>(
      this.name!!,
      this.required ?: throw IllegalStateException(""),
      this.varargs
    )
  }
}