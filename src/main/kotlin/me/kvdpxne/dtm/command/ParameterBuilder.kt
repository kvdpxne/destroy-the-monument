package me.kvdpxne.dtm.command

import me.kvdpxne.dtm.shared.Buildable

/**
 * @since 0.1.0
 */
class ParameterBuilder<T> private constructor(
  private val name: String
) : Buildable<Parameter<T>> {

  // @formatter:off
  private var required         : Boolean?
  private var varargs          : Boolean
  private var validatorHandler : ParameterValidatorHandler<T>?
  private var suggestionHandler: SuggestionHandler?
  // @formatter:on

  init {
    //
    this.required = null
    this.varargs = false
    this.validatorHandler = null
    this.suggestionHandler = null
  }

  companion object {

    /**
     * @throws IllegalArgumentException
     *
     * @since 0.1.0
     */
    fun <T> begin(
      name: String
    ): ParameterBuilder<T> {
      require(name.isNotEmpty()) {
        "name must not be empty"
      }

      return ParameterBuilder(name)
    }
  }

  /**
   * @since 0.1.0
   */
  fun required(): ParameterBuilder<T> {
    this.required = true
    return this
  }

  /**
   * @since 0.1.0
   */
  fun optional(): ParameterBuilder<T> {
    this.required = false
    return this
  }

  /**
   * @since 0.1.0
   */
  fun varargs(): ParameterBuilder<T> {
    this.varargs = true
    return this
  }

  /**
   * @since 0.1.0
   */
  fun validatorHandler(
    validator: ParameterValidatorHandler<T>
  ): ParameterBuilder<T> {
    this.validatorHandler = validator
    return this
  }

  /**
   * @since 0.1.0
   */
  fun suggestionHandler(
    suggestionHandler: SuggestionHandler
  ): ParameterBuilder<T> {
    this.suggestionHandler = suggestionHandler
    return this
  }

  /**
   * @throws IllegalStateException
   *
   * @since 0.1.0
   */
  override fun build(): Parameter<T> {
    return ParameterImpl(
      this.name,
      this.required ?: throw IllegalStateException(""),
      this.varargs,
      this.validatorHandler,
      this.suggestionHandler
    )
  }
}