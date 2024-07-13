package me.kvdpxne.dtm.game

import fr.mrmicky.fastboard.FastBoard
import me.kvdpxne.dtm.command.Communicative
import me.kvdpxne.dtm.profession.Profession
import me.kvdpxne.dtm.shared.QueuingPair
import me.kvdpxne.dtm.shared.toQueuingPair
import me.kvdpxne.dtm.statistics.BaseStatistics
import me.kvdpxne.dtm.user.UserStatistics
import me.kvdpxne.dtm.user.User

class Teammate(
  val user: User,
  val team: Team
): Communicative {

  /**
   *
   */
  val professionQueuingPair: QueuingPair<Profession>

  /**
   *
   */
  val statistics: BaseStatistics

  /**
   *
   */
  var fastBoard: FastBoard?

  val currentProfession: Profession
    get() = this.professionQueuingPair.current

  /**
   *
   */
  init {
    this.professionQueuingPair = this.user.profession.toQueuingPair()
    this.statistics = BaseStatistics()


    this.fastBoard = null
  }

  /**
   * Alias for [UserStatistics.addKills]
   *
   * @since 0.1
   */
  fun addKill() {
    this.user.statistics.addKills()
    this.statistics.addKills()
  }

  /**
   * Alias for [UserStatistics.addAssists]
   *
   * @since 0.1
   */
  fun addAssist() {
    this.user.statistics.addAssists()
    this.statistics.addAssists()
  }

  /**
   * Alias for [UserStatistics.addDeaths]
   *
   * @since 0.1
   */
  fun addDeath() {
    this.user.statistics.addDeaths()
    this.statistics.addDeaths()
  }

  /**
   * Alias for [UserStatistics.addDestroyedMonuments]
   *
   * @since 0.1
   */
  fun addDestroyedMonument() {
    this.user.statistics.addDestroyedMonuments()
    this.statistics.addDestroyedMonuments()
  }

  /**
   * Alias for [User.sendMessage]
   *
   * @since 0.1
   */
  override fun sendMessage(
    message: String
  ) {
    this.user.sendMessage(message)
  }

  /**
   * Alias for [User.sendMessages]
   *
   * @since 0.1
   */
  override fun sendMessages(
    vararg messageArray: String
  ) {
    this.user.sendMessages(*messageArray)
  }

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as Teammate

    return user == other.user
  }

  override fun hashCode(): Int {
    return user.hashCode()
  }

  override fun toString(): String {
    return "Teammate(user=$user, team=$team)"
  }
}