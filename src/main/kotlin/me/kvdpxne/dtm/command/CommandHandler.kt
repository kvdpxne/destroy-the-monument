package me.kvdpxne.dtm.command

/**
 * Defines a functional interface for handling commands.
 *
 * A `CommandHandler` takes two arguments:
 *  * `performer`: The object that will execute the command. The type is
 *     generic (`T`) and can be any class.
 *  * `parameter`: An object containing additional information about the
 *     command. Its type is `Parameter`.
 *
 * This typealias is useful for defining callbacks that handle different types
 * of commands.
 */
typealias CommandHandler<T> = (
  performer: T,
  arguments: Arguments
) -> Unit