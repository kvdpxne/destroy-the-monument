package me.kvdpxne.dtm.data

import me.kvdpxne.dtm.statistics.Statistics
import me.kvdpxne.dtm.user.User
import org.ktorm.dsl.*
import org.ktorm.schema.Table
import org.ktorm.schema.int
import org.ktorm.schema.varchar
import java.util.*

object UserTable : Table<Nothing>("user") {

  var identifier = varchar("identifier").primaryKey()
  val name = varchar("name")

  // Statistics
  val kills = int("kills")
  val assists = int("assists")
  val deaths = int("deaths")
}

object UserDao {

  fun getUserByIdentifier(identifier: UUID): User? {
    return database.from(UserTable)
      .select()
      .where { UserTable.identifier eq identifier.toString() }
      .map { row ->
        val identifier2 = row[UserTable.identifier].let {
          UUID.fromString(it)
        }

        val name = row[UserTable.name]!!
        val kills = row[UserTable.kills]!!
        val assists = row[UserTable.assists]!!
        val deaths = row[UserTable.deaths]!!

        User(identifier2, name, Statistics(kills, assists, deaths))
      }
      .firstOrNull()
  }


  fun update(user: User) {
    database.update(UserTable) {
      set(it.name, user.name)

      val statistics = user.statistics
      set(it.kills, statistics.kills)
      set(it.assists, statistics.assists)
      set(it.deaths, statistics.deaths)

      where {
        UserTable.identifier eq user.identifier.toString()
      }
    }
  }

  fun insert(user: User) {
    database.insert(UserTable) {
      set(it.identifier, user.identifier.toString())
      set(it.name, user.name)

      val statistics = user.statistics
      set(it.kills, statistics.kills)
      set(it.assists, statistics.assists)
      set(it.deaths, statistics.deaths)
    }
  }

  fun count(): Int {
    return database.from(UserTable)
      .select()
      .totalRecordsInAllPages
  }
}