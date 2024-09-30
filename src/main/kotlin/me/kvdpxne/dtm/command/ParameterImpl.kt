package me.kvdpxne.dtm.command

/**
 * @since 0.1.0
 */
internal class ParameterImpl<T> internal constructor(
  // @formatter:off
  override val name             : String,
  override val required         : Boolean,
  override val varargs          : Boolean,
  override val validatorHandler : ParameterValidatorHandler<T>?,
  override val suggestionHandler: SuggestionHandler?,
  // @formatter:on
) : Parameter<T> {

  /**
   * @since 0.1.0
   */
  internal var _command: Command<*>? = null

  /**
   * @since 0.1.0
   */
  override val command: Command<*>
    get() = this._command!!
}