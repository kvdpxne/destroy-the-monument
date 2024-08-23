package me.kvdpxne.dtm.game

import me.kvdpxne.dtm.shared.ancillary.Buildable

class GameBuilder : Buildable<Game<*>> {

  // @formatter:off
  private var name       : String?            = null
  private var displayName: String?            = null
  private val teams      : MutableList<Team>  = mutableListOf()
  private val arenas     : MutableList<Arena> = mutableListOf()
  private var identifier : String?            = null
  // @formatter:on

  fun identifier(identifier: String): GameBuilder {
    this.identifier = identifier
    return this
  }

  fun name(name: String): GameBuilder {
    this.name = name
    return this
  }



  override fun build(): Game<*> {
    TODO("Not yet implemented")
  }
}