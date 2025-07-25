package me.kvdpxne.dtm.game

import java.util.Collections
import java.util.UUID
import me.kvdpxne.dtm.arena.Arena
import me.kvdpxne.dtm.arena.map.ArenaMap
import me.kvdpxne.dtm.arena.voting.ArenaVotingRegistry
import me.kvdpxne.dtm.team.LocalTeam
import me.kvdpxne.dtm.team.Team
import me.kvdpxne.dtm.team.teammate.Teammate
import me.kvdpxne.dtm.user.LocalUser
import me.kvdpxne.dtm.user.LocalUserProvider
import org.jetbrains.annotations.Range
import org.jetbrains.annotations.UnmodifiableView

class BasicLocalGame(
  uid: UUID,
  name: String,
  settings: GameSettings,
  arenas: Map<UUID, Arena>,
  teams: Map<UUID, LocalTeam>,
  private val _participants: Map<UUID, LocalUser> = mutableMapOf()
) : BasicGame<LocalTeam>(uid, name, settings, arenas, teams), LocalGame {

  internal var _votingRegistry: ArenaVotingRegistry? = null

  @Volatile
  internal var _currentArena: Arena? = null

  private var _state: Int = GameStates.INITIALIZED

  private var _spectators: Int = 0



  override fun getParticipants(): @UnmodifiableView Collection<LocalUser> {
    return Collections.unmodifiableCollection(this._participants.values)
  }

  override fun getTeammates(): @UnmodifiableView Collection<Teammate> {
    TODO("Not yet implemented")
  }

  override fun getSmallestTeam(): LocalTeam {
    return super._teams.values.minBy(LocalTeam::getSize)
  }

  override fun getLargestTeam(): LocalTeam {
    return super._teams.values.maxBy(LocalTeam::getSize)
  }

  override fun getRandomTeam(): LocalTeam {
    return super._teams.values.random()
  }

  override fun getTeamByDefaultCriteria(): LocalTeam {
    if (this.isTeamsSameSize) {
      return this.randomTeam
    }
    return this.smallestTeam
  }

  override fun getVotingRegistry(): ArenaVotingRegistry? {
    return this._votingRegistry
  }

  override fun getCurrentArena(): Arena? {
    return this._currentArena
  }

  override fun getCurrentArenaMap(): ArenaMap? {
    return this.currentArena?.map
  }

  override fun getState(): @Range(from = 0, to = 2147483647) Int {
    return this._state
  }

  override fun getNumberOfSpectators(): @Range(from = 0, to = 2147483647) Int {
    return this._spectators
  }

  override fun getNumberOfParticipants(): @Range(from = 0, to = 2147483647) Int {
    return this._participants.size
  }

  override fun getNumberOfTeammates(): @Range(from = 0, to = 2147483647) Int {
    return this._teams.values.sumOf { localTeam -> localTeam.size }
  }

  override fun isTeamsSameSize(): Boolean {
    val first: Int = this._teams.values.firstOrNull()?.size ?: return false
    return this._teams.values.all { localTeam ->
      localTeam.size == first
    }
  }

  override fun setAsInitialized() {
    this._state = GameStates.INITIALIZED
  }

  override fun setAsStarting() {
    this._state = GameStates.STARTING
  }

  override fun setAsRunning() {
    this._state = GameStates.RUNNING
  }

  override fun setAsEnding() {
    this._state = GameStates.ENDING
  }

  override fun setAsStopping() {
    this._state = GameStates.STOPPING
  }

  override fun findParticipantByIdentifier(identifier: UUID): LocalUser? {
    TODO("Not yet implemented")
  }

  override fun findParticipantTeamByParticipant(userProvider: LocalUserProvider): LocalTeam? {
    TODO("Not yet implemented")
  }

  override fun addParticipant(participant: LocalUser): Boolean {
    TODO("Not yet implemented")
  }

  override fun addTeammate(
    team: LocalTeam,
    participant: LocalUser
  ): Boolean {
    TODO("Not yet implemented")
  }

  override fun removeParticipant(participant: LocalUser): Boolean {
    TODO("Not yet implemented")
  }

  override fun removeTeammate(
    team: LocalTeam,
    participant: LocalUser
  ): Boolean {
    TODO("Not yet implemented")
  }

  override fun removeTeammate(participant: LocalUser): Boolean {
    TODO("Not yet implemented")
  }

  override fun relocateTeammateToTeam(
    teammate: Teammate,
    to: LocalTeam
  ): Boolean {
    TODO("Not yet implemented")
  }

  override fun start() {
    TODO("Not yet implemented")
  }

  override fun stop() {
    TODO("Not yet implemented")
  }

  override fun freeze() {
    TODO("Not yet implemented")
  }
}