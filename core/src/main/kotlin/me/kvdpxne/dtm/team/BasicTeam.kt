package me.kvdpxne.dtm.team

import java.util.UUID
import me.kvdpxne.dtm.team.color.TeamColor

/**
 * @param uid
 * @param name
 * @param colorOfArmor
 * @param colorOfProfession
 * @param colorOnChat
 * @param colorOnPlayerList
 *
 * @since 0.1.0
 */
open class BasicTeam(
  // @formatter:off
  private val uid              : UUID,
  private val name             : String,
  private val colorOfArmor     : TeamColor,
  private val colorOfProfession: TeamColor,
  private val colorOnChat      : TeamColor,
  private val colorOnPlayerList: TeamColor?
  // @formatter:on
) : Team {

  override fun getIdentifier(): UUID {
    return this.uid
  }

  override fun getName(): String {
    return this.name
  }

  override fun getColorOfArmor(): TeamColor {
    return this.colorOfArmor
  }

  override fun getColorOfProfession(): TeamColor {
    return this.colorOfProfession
  }

  override fun getColorOnChat(): TeamColor {
    return this.colorOnChat
  }

  override fun getColorOnPlayerList(): TeamColor? {
    return this.colorOnPlayerList
  }

  override fun toLocalTeam(): LocalTeam {
    TODO("Not yet implemented")
  }
}