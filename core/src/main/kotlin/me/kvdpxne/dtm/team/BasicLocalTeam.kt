package me.kvdpxne.dtm.team

import java.util.UUID
import me.kvdpxne.boujee.TranslationKeyProvider
import me.kvdpxne.dtm.game.LocalGame
import me.kvdpxne.dtm.team.color.TeamColor
import me.kvdpxne.dtm.team.teammate.Teammate
import me.kvdpxne.dtm.user.LocalUser

class BasicLocalTeam(
  // @formatter:off
  uid              : UUID,
  name             : String,
  colorOfArmor     : TeamColor,
  colorOfProfession: TeamColor,
  colorOnChat      : TeamColor,
  colorOnPlayerList: TeamColor?
  // @formatter:on
) : BasicTeam(
  uid,
  name,
  colorOfArmor,
  colorOfProfession,
  colorOnChat,
  colorOnPlayerList
), LocalTeam {

  private val teammates: MutableSet<Teammate> = HashSet()
  private var health: Int = -1

  override fun chat(keyProvider: TranslationKeyProvider) {
    TODO("Not yet implemented")
  }

  override fun title(keyProvider: TranslationKeyProvider) {
    TODO("Not yet implemented")
  }

  override fun subtitle(keyProvider: TranslationKeyProvider) {
    TODO("Not yet implemented")
  }

  override fun action(keyProvider: TranslationKeyProvider) {
    TODO("Not yet implemented")
  }

  override fun getLocalGame(): LocalGame {
    TODO("Not yet implemented")
  }

  override fun getTeammates(): MutableCollection<Teammate> {
    TODO("Not yet implemented")
  }

  override fun getHealth(): Int {
    return this.health
  }

  override fun getSize(): Int {
    TODO("Not yet implemented")
  }

  override fun hasTeammate(user: LocalUser): Boolean {
    TODO("Not yet implemented")
  }

  override fun hasTeammate(teammate: Teammate): Boolean {
    TODO("Not yet implemented")
  }

  override fun getTeammate(user: LocalUser): Teammate? {
    TODO("Not yet implemented")
  }

  override fun addTeammate(teammate: Teammate): Boolean {
    TODO("Not yet implemented")
  }

  override fun removeTeammate(teammate: Teammate): Boolean {
    TODO("Not yet implemented")
  }

  override fun removeTeammate(localUser: LocalUser): Boolean {
    TODO("Not yet implemented")
  }

  override fun clearTeammates(): Int {
    TODO("Not yet implemented")
  }

  override fun injure(): Boolean {
    TODO("Not yet implemented")
  }
}