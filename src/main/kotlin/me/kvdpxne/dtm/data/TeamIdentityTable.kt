package me.kvdpxne.dtm.data

import me.kvdpxne.dtm.game.TeamIdentity
import org.bukkit.ChatColor
import org.bukkit.DyeColor
import org.ktorm.dsl.QueryRowSet
import org.ktorm.dsl.eq
import org.ktorm.dsl.from
import org.ktorm.dsl.insert
import org.ktorm.dsl.map
import org.ktorm.dsl.select
import org.ktorm.dsl.where
import org.ktorm.schema.Table
import org.ktorm.schema.varchar

object TeamIdentityTable : Table<Nothing>("team_identity") {

  val identifier = varchar("identifier")
  val name = varchar("name")

  val colorInChat = varchar("color_in_chat")
  val professionColor = varchar("profession_color")
  val dyeColor = varchar("dye_color")
}

object TeamIdentityDao {

  private fun toChatColor(name: String): ChatColor? {
    return ChatColor.entries.find {
      it.name.equals(name, true)
    }
  }

  private fun toDyeColor(name: String): DyeColor? {
    return DyeColor.entries.find {
      it.name.equals(name, true)
    }
  }

  private fun toTeamIdentity(row: QueryRowSet): TeamIdentity {
    val identifier = row[TeamIdentityTable.identifier]!!
    val name = row[TeamIdentityTable.name]!!

    val colorInChat = this.toChatColor(row[TeamIdentityTable.colorInChat]!!)!!
    val professionColor = this.toChatColor(row[TeamIdentityTable.professionColor]!!)!!
    val dyeColor = this.toDyeColor(row[TeamIdentityTable.dyeColor]!!)!!

    return TeamIdentity(
      identifier,
      name,
      colorInChat,
      professionColor,
      dyeColor
    )
  }

  fun findByIdentifier(
    identifier: String
  ): TeamIdentity? {
    return database.from(TeamIdentityTable)
      .select()
      .where { TeamIdentityTable.identifier eq identifier }
      .map { this.toTeamIdentity(it) }
      .firstOrNull()
  }

  fun findByName(teamName: String): TeamIdentity? {
    return database.from(TeamIdentityTable)
      .select()
      .where { TeamIdentityTable.name eq teamName }
      .map { this.toTeamIdentity(it) }
      .firstOrNull()
  }

  fun insert(
    teamIdentity: TeamIdentity
  ) {
    database.insert(TeamIdentityTable) {
      set(TeamIdentityTable.identifier, teamIdentity.identifier)
      set(TeamIdentityTable.name, teamIdentity.name)
      set(TeamIdentityTable.colorInChat, teamIdentity.colorInChat.name)
      set(TeamIdentityTable.professionColor, teamIdentity.professionColor.name)
      set(TeamIdentityTable.dyeColor, teamIdentity.dyeColor.name)
    }
  }
}