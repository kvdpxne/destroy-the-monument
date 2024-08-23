package me.kvdpxne.dtm.game

import fr.mrmicky.fastboard.FastBoard
import me.kvdpxne.dtm.profession.Profession
import me.kvdpxne.dtm.shared.collections.QueuingPair
import me.kvdpxne.dtm.shared.collections.toQueuingPair
import me.kvdpxne.dtm.shared.debug.Debug
import me.kvdpxne.dtm.statistics.BaseStatistics
import me.kvdpxne.dtm.user.User

/**
 * @since 0.1.0
 */
class BaseTeammate(
  override val game: Game<LocalTeam>,
  override val team: LocalTeam,
  override val user: User,
) : Teammate {

  /**
   * @since 0.1.0
   */
  val professionQueuingPair: QueuingPair<Profession> =
    this.user.currentProfession.toQueuingPair()

  /**
   * @since 0.1.0
   */
  override var fastBoard: FastBoard = FastBoard(this.user.performer.player)

  /**
   * @since 0.1.0
   */
  override val statistics: BaseStatistics = BaseStatistics()

  /**
   * @since 0.1.0
   */
  override val currentProfession: Profession
    get() = this.professionQueuingPair.current

  /**
   * @since 0.1.0
   */
  override val nextProfession: Profession?
    get() = this.professionQueuingPair.next

  override fun shiftProfession() {
    this.professionQueuingPair.shift()
  }

  override fun addProfession(profession: Profession) {
    this.professionQueuingPair.next = profession
  }
}