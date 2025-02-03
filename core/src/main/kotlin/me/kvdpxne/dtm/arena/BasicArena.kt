package me.kvdpxne.dtm.arena

import java.util.UUID
import me.kvdpxne.dtm.BasicIdentifiable
import me.kvdpxne.dtm.arena.map.ArenaMap
import me.kvdpxne.dtm.arena.settings.ArenaSettings
import me.kvdpxne.dtm.position.BlockPosition
import me.kvdpxne.dtm.position.monument.MonumentPosition
import me.kvdpxne.dtm.position.revival.RevivalPosition
import me.kvdpxne.dtm.team.Team

class BasicArena(
  // @formatter:off
  private val name: String,
  identifier: UUID = UUID.randomUUID(),
  // @formatter:on
) :
  BasicIdentifiable<UUID>(identifier),
  Arena {

  companion object {
    private const val serialVersionUID: Long = 3566415945564878243L
  }

  val revivalPositions: MutableMap<UUID, RevivalPosition> = mutableMapOf()

  val monumentPositions: MutableMap<UUID, MonumentPosition> = mutableMapOf()

  override fun getName(): String {
    TODO("Not yet implemented")
  }

  override fun getDisplayName(): String? {
    TODO("Not yet implemented")
  }

  override fun getRevivalPositions(): MutableCollection<RevivalPosition> {
    TODO("Not yet implemented")
  }

  override fun getMonumentPositions(): MutableCollection<MonumentPosition> {
    TODO("Not yet implemented")
  }

  override fun getMap(): ArenaMap {
    TODO("Not yet implemented")
  }

  override fun getSettings(): ArenaSettings {
    TODO("Not yet implemented")
  }

  override fun getRevivalPositionByTeam(team: Team): RevivalPosition {
    TODO("Not yet implemented")
  }

  override fun getMonumentPositionsByTeam(team: Team): MutableCollection<MonumentPosition> {
    TODO("Not yet implemented")
  }

  override fun getMonumentPositionByPositionOrNull(position: BlockPosition): MonumentPosition? {
    TODO("Not yet implemented")
  }

  override fun getMonumentPositionByPositionOrNull(
    x: Int,
    y: Int,
    z: Int
  ): MonumentPosition? {
    TODO("Not yet implemented")
  }

  override fun getRevivalPositionsCount(): Int {
    TODO("Not yet implemented")
  }

  override fun getMonumentPositionsCount(): Int {
    TODO("Not yet implemented")
  }
}