package me.kvdpxne.dtm.user

import me.kvdpxne.dtm.game.LocalGame
import me.kvdpxne.dtm.profession.Profession
import me.kvdpxne.dtm.shared.ancillary.Communicative
import me.kvdpxne.dtm.team.LocalTeam
import me.kvdpxne.dtm.team.Teammate

/**
 * @since 0.1.0
 */
interface LocalUser : User, Communicative {

  /**
   * @since 0.1.0
   */
  val cache: LocalUserCache

  /**
   * @since 0.1.0
   */
  val performer: LocalUserPerformer

  /**
   * @since 0.1.0
   */
  val game: LocalGame?

  /**
   * @since 0.1.0
   */
  val team: LocalTeam?

  /**
   * @since 0.1.0
   */
  val teammate: Teammate?

  fun updateCurrentProfession(
    profession: Profession
  )
}