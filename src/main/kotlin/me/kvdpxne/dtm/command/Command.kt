package me.kvdpxne.dtm.command

interface Command<T : Performer> {

  val name: String

  val fullName: String

  val description: String

  val usage: String

  val aliases: Array<String>

  val permission: String

  val executable: Boolean

  val parameters: Array<Parameter<*>>

  val children: Array<Command<Performer>>

  val parent: Command<Performer>?

  val handler: CommandHandler<T>?

  fun matches(
    input: String
  ): Boolean

  fun execute(
    performer: T,
    arguments: Array<String>
  )

  fun suggestions(
    suggestions: MutableList<String>,
    arguments: Array<String>,
    commandIndex: Int
  )
}