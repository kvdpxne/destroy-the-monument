package me.kvdpxne.dtm.game

import fr.mrmicky.fastboard.FastBoard
import me.kvdpxne.dtm.profession.Profession
import me.kvdpxne.dtm.shared.collections.QueuingPair
import me.kvdpxne.dtm.shared.collections.toQueuingPair
import me.kvdpxne.dtm.shared.minecraft.bukkit.equipB
import me.kvdpxne.dtm.shared.minecraft.bukkit.moveToLobby
import me.kvdpxne.dtm.shared.minecraft.bukkit.reset
import me.kvdpxne.dtm.statistics.BaseStatistics
import me.kvdpxne.dtm.user.User
import org.bukkit.Bukkit

/**
 * @since 0.1.0
 */
class BaseTeammate(
  override val game: LocalGame,
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

  override fun leave() {
    (this.game as BaseLocalGame).timerTask?.playerMutableList?.remove(this.fastBoard)
    this.currentProfession.ability?.cancelCooldown()
    this.fastBoard.delete()
    this.game.removeTeammate(this.team, this.user)

    val player = this.user.performer.player ?: return

    player.scoreboard.getPlayerTeam(player).removePlayer(player)
    player.scoreboard = Bukkit.getScoreboardManager().mainScoreboard

    player.reset()
    player.moveToLobby()
    player.equipB()
  }

  override fun toString(): String {
    return "Teammate{" +
      "user=\"${this.user}\", " +
      "professionQueuingPair=\"${this.professionQueuingPair}\", " +
      "statistics=\"${this.statistics}\"" +
      "}"
  }
}