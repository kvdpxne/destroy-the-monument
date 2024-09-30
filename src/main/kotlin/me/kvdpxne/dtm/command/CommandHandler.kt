package me.kvdpxne.dtm.command

typealias CommandHandler<T> = (
  performer: T,
  parameters: Array<Any>
) -> Unit