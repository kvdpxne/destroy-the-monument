package me.kvdpxne.dtm.command

class CommandBuilder {

  private var name: String? = null
  private var aliases: Array<out String> = arrayOf()
  private var place: ExecutionPlaceType? = null
  private var handler: CommandHandler<Any>? = null

  private var parent: String? = null

  fun name(name: String): CommandBuilder {
    this.name = name
    return this
  }

  fun aliases(vararg aliases: String): CommandBuilder {
    this.aliases = aliases
    return this
  }

  fun `in`(place: ExecutionPlaceType): CommandBuilder {
    this.place = place
    return this
  }

  fun parent(parent: String): CommandBuilder {
    this.parent = parent
    return this
  }

  fun <T> handler(
    handler: CommandHandler<T>
  ): CommandBuilder
    where T : Performer {
    @Suppress("UNCHECKED_CAST")
    this.handler = handler as CommandHandler<Any>
    return this
  }

  fun build(): Command {
    check(this.name.isNullOrBlank().not())


    return Command(
      name = this.name!!,
      aliases = this.aliases,
      handler = this.handler!!
    )
  }
}