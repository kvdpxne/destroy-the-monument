package me.kvdpxne.dtm.command

interface Executor<out P : Performer> {

  @Throws(CommandException::class)
  fun execute(performer: @UnsafeVariance P, parameter: Parameter)
}