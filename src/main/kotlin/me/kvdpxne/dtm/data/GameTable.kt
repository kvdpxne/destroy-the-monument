package me.kvdpxne.dtm.data

import java.util.UUID
import me.kvdpxne.dtm.game.Game
import org.ktorm.dsl.QueryRowSet
import org.ktorm.dsl.eq
import org.ktorm.dsl.from
import org.ktorm.dsl.innerJoin
import org.ktorm.dsl.insert
import org.ktorm.dsl.map
import org.ktorm.dsl.mapNotNull
import org.ktorm.dsl.select
import org.ktorm.dsl.where
import org.ktorm.schema.Table
import org.ktorm.schema.varchar
import org.ktorm.support.sqlite.toLowerCase

object GameTable : Table<Nothing>("game") {

  val name = varchar("name")

  //
  val identifier = varchar("identifier").primaryKey()
}

private fun toGame(row: QueryRowSet): Game {
  val key = UUID.fromString(row[GameTable.identifier])

  val teams = GameTeamsDao.findAllByGameIdentifier(key)
  val arenas = GameArenasDao.findAllByGameIdentifier(key)

  return Game(
    key,
    row[GameTable.name]!!
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

object GameDao {

  /**
   * @throws StackOverflowError
   */
  fun findAll() = database.from(GameTable)
    .innerJoin(GameArenasTable, GameArenasTable.game eq GameTable.identifier)
    .select()
    .mapNotNull { toGame(it) }

  fun findByIdentifier(identifier: UUID) = database.from(GameTable)
    .innerJoin(GameArenasTable, GameArenasTable.game eq GameTable.identifier)
    .select()
    .where { GameTable.identifier eq identifier.toString() }
    .map { toGame(it) }
    .firstOrNull()

  fun findByName(name: String) = database.from(GameTable)
    .innerJoin(GameArenasTable, GameArenasTable.game eq GameTable.identifier)
    .select()
    .where { GameTable.name.toLowerCase() eq name.lowercase() }
    .map { toGame(it) }
    .firstOrNull()

  fun insert(game: Game) = database.insert(GameTable) {
    set(it.identifier, game.identifier.toString())
    set(it.name, game.name)
  }

  fun insertAll(games: Collection<Game>) = games.forEach {
    insert(it)
  }

  fun count() = database.from(GameTable)
    .select()
    .totalRecordsInAllPages
}