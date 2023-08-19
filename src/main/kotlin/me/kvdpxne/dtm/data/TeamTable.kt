package me.kvdpxne.dtm.data

import java.util.UUID
import me.kvdpxne.dtm.game.DefaultTeamColor
import me.kvdpxne.dtm.shared.Identity
import org.ktorm.dsl.eq
import org.ktorm.dsl.from
import org.ktorm.dsl.insert
import org.ktorm.dsl.map
import org.ktorm.dsl.select
import org.ktorm.dsl.where
import org.ktorm.schema.Table
import org.ktorm.schema.varchar

object TeamTable : Table<Nothing>("team") {

  val identifier = varchar("identifier")
  val name = varchar("name")
}

object TeamDao {

  fun findByIdentifier(identifier: UUID): DefaultTeamColor? {
    return database.from(TeamTable)
      .select()
      .where { TeamTable.identifier eq identifier.toString() }
      .map {
        DefaultTeamColor.findByIdentityKey(it[TeamTable.name]!!)
      }
      .firstOrNull()
  }

  fun insert(identity: Identity) {
    database.insert(TeamTable) {
      set(TeamTable.identifier, identity.identifier.toString())
      set(TeamTable.name, identity.key)
    }
  }
}