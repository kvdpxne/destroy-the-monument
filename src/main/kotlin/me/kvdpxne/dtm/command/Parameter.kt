package me.kvdpxne.dtm.command

/**
 * @param name
 * @param required
 * @param varargs
 * @param validator
 * @param autoComplete
 * @param command
 *
 * @since 0.1.0
 */
class Parameter<T>(
  // @formatter:off
  val name        : String,
  val required    : Boolean,
  val varargs     : Boolean,
  val validator   : ParameterValidatorHandler<T>? = null,
  val autoComplete: AutoCompletionHandler?        = null,
      command     : Command?                      = null
  // @formatter:on
) {

  /**
   * @since 0.1.0
   */
  var command = command
    internal set
}