package me.kvdpxne.dtm.data

import me.kvdpxne.dtm.game.Game
import org.ktorm.dsl.*
import org.ktorm.schema.Table
import org.ktorm.schema.varchar
import java.util.UUID

object GameTable : Table<Nothing>("game") {

  val name = varchar("name")

  var identifier = varchar("identifier").primaryKey()
}

object GameDao {

  /**
   * @throws StackOverflowError
   */
  fun findAll(): Collection<Game> {
    return database.from(GameTable)
      .innerJoin(GameArenasTable, GameArenasTable.game eq GameTable.identifier)
      .select()
      .mapNotNull {
        val key = UUID.fromString(it[GameTable.identifier])

        val teams = GameTeamsDao.findAllByGameIdentifier(key)
        val arenas = GameArenasDao.findAllByGameIdentifier(key)

        Game(
          key,
          it[GameTable.name]!!
        ).apply {
          teams.forEach {
            if (null == it.game) {
              it.game = this
            }
            this.teams.add(it)
          }
          this.arenas.addAll(arenas)
        }
      }
  }

  fun findByIdentifier(identifier: UUID): Game? {
    return database.from(GameTable)
      .innerJoin(GameArenasTable, GameArenasTable.game eq GameTable.identifier)
      .select()
      .where {
        GameTable.identifier eq identifier.toString()
      }
      .map {
        val key = UUID.fromString(it[GameTable.identifier])

        val teams = GameTeamsDao.findAllByGameIdentifier(key)
        val arenas = GameArenasDao.findAllByGameIdentifier(key)

        Game(
          key,
          it[GameTable.name]!!
        ).apply {
          this.teams.addAll(teams)
          this.arenas.addAll(arenas)
        }
      }
      .firstOrNull()
  }

  fun insert(game: Game) {
    database.insert(GameTable) {
      set(it.identifier, game.identifier.toString())
      set(it.name, game.name)
    }
  }

  fun count(): Int {
    return database.from(ArenaTable)
      .select()
      .totalRecordsInAllPages
  }
}