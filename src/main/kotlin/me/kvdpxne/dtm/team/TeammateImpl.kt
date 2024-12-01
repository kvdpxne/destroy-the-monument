package me.kvdpxne.dtm.team

import fr.mrmicky.fastboard.FastBoard
import me.kvdpxne.dtm.game.LocalGame
import me.kvdpxne.dtm.game.LocalGameImpl
import me.kvdpxne.dtm.profession.Profession
import me.kvdpxne.dtm.shared.StylishToStringBuilder
import me.kvdpxne.dtm.shared.collections.QueuingPair
import me.kvdpxne.dtm.shared.collections.toQueuingPair
import me.kvdpxne.dtm.shared.player.equipItemsOfTeamSelection
import me.kvdpxne.dtm.shared.player.moveToLobby
import me.kvdpxne.dtm.shared.player.reset
import me.kvdpxne.dtm.statistics.StatisticsImpl
import me.kvdpxne.dtm.user.LocalUser
import org.bukkit.Bukkit

/**
 * @since 0.1.0
 */
class TeammateImpl(
  override val game: LocalGame,
  override val team: LocalTeam,
  override val user: LocalUser,
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
  override val statistics: StatisticsImpl = StatisticsImpl()

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

  override fun leave() {
    (this.game as LocalGameImpl).timerTask?.playerMutableList?.remove(this.fastBoard)
    this.currentProfession.ability?.cancelCooldown()
    this.fastBoard.delete()
    this.game.removeTeammate(this.team, this.user)

    val player = this.user.performer.player ?: return

    player.scoreboard.getPlayerTeam(player).removePlayer(player)
    player.scoreboard = Bukkit.getScoreboardManager().mainScoreboard

    player.reset()
    player.moveToLobby()
    player.equipItemsOfTeamSelection()
  }

  /**
   * @since 0.1.0
   */
  override fun sendMessage(
    message: String
  ) {
    this.user.performer.sendMessage(message)
  }

  override fun toString(): String {
    return StylishToStringBuilder()
      .begin("Teammate")
      .add("user", this.user)
      .add("professionQueuingPair", this.professionQueuingPair)
      .add("statistics", this.statistics)
      .build()
  }
}