package me.kvdpxne.dtm.command

interface Parameter<T> {

  val name: String

  val required: Boolean

  val varargs: Boolean

  val validatorHandler: ParameterValidatorHandler<T>?

  val suggestionHandler: SuggestionHandler?

  val command: Command<*>
}