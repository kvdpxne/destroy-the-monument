package me.kvdpxne.dtm.team

import fr.mrmicky.fastboard.FastBoard
import me.kvdpxne.dtm.game.LocalGame
import me.kvdpxne.dtm.profession.Profession
import me.kvdpxne.dtm.shared.ancillary.Communicative
import me.kvdpxne.dtm.statistics.Statistics
import me.kvdpxne.dtm.user.LocalUser

interface Teammate : Communicative {

  /**
   * @since 0.1.0
   */
  val team: LocalTeam

  /**
   * @since 0.1.0
   */
  val game: LocalGame

  /**
   * @since 0.1.0
   */
  val user: LocalUser

  /**
   * @since 0.1.0
   */
  val statistics: Statistics

  /**
   * @since 0.1.0
   */
  var fastBoard: FastBoard

  /**
   * @since 0.1.0
   */
  val currentProfession: Profession

  /**
   * @since 0.1.0
   */
  val nextProfession: Profession?

  /**
   * @since 0.1.0
   */
  val hasNextProfession: Boolean
    get() = null != this.nextProfession

  /**
   * @since 0.1.0
   */
  fun shiftProfession()

  /**
   * @since 0.1.0
   */
  fun addProfession(profession: Profession)

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
   * @since 0.1.0
   */
  fun leave()

  /**
   * Alias for [UserStatistics.addDestroyedMonuments]
   *
   * @since 0.1
   */
  override fun sendMessage(message: String) {
    this.user.sendMessage(message)
  }

  /**
   * Alias for [UserStatistics.addDestroyedMonuments]
   *
   * @since 0.1
   */
  override fun sendMessages(vararg messages: String) {
    this.user.sendMessages(*messages)
  }
}