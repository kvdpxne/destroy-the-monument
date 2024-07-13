package me.kvdpxne.dtm.data

import java.util.UUID
import me.kvdpxne.dtm.profession.ProfessionManager
import me.kvdpxne.dtm.user.UserStatistics
import me.kvdpxne.dtm.user.User
import org.ktorm.dsl.QueryRowSet
import org.ktorm.dsl.eq
import org.ktorm.dsl.from
import org.ktorm.dsl.insert
import org.ktorm.dsl.map
import org.ktorm.dsl.select
import org.ktorm.dsl.update
import org.ktorm.dsl.where
import org.ktorm.schema.Table
import org.ktorm.schema.int
import org.ktorm.schema.varchar

object UserTable : Table<Nothing>("user") {

  var identifier = varchar("identifier").primaryKey()
  val name = varchar("name")

  // profession
  val profession = varchar("profession")

  val statisticsIdentifier = varchar("statistics_identifier")
  val walletIdentifier = varchar("wallet_identifier")
}

internal fun toUser(
  row: QueryRowSet
): User {

  val identifier = UUID.fromString(row[UserTable.identifier]!!)
  val name = row[UserTable.name]!!

  val profession = row[UserTable.profession]!!

  val statisticsIdentifier = row[UserTable.statisticsIdentifier]!!
  val statistics = findUserStatisticsByIdentifier(statisticsIdentifier)!!


  val walletIdentifier = row[UserTable.walletIdentifier]!!
  val wallet = findUserWalletByIdentifier(walletIdentifier)!!


  return User(
    identifier,
    name,
    statistics,
    wallet
  ).apply {
    ProfessionManager.findByName(profession)?.let {
      this.profession = it
    }
  }
}

object UserDao {

  fun findByIdentifier(identifier: UUID): User? {
    return database.from(UserTable)
      .select()
      .where { UserTable.identifier eq identifier.toString() }
      .map { toUser(it) }
      .firstOrNull()
  }


  fun update(user: User) {
    database.update(UserTable) {
      set(it.name, user.name)
      set(it.profession, user.profession.name)

      set(it.statisticsIdentifier, user.statistics.identifier)
      set(it.walletIdentifier, user.wallet.identifier)

      where {
        UserTable.identifier eq user.identifier.toString()
      }
    }

    updateUserStatistics(user.statistics)
    updateUserWaller(user.wallet)
  }

  fun insert(user: User) {
    database.insert(UserTable) {
      set(it.identifier, user.identifier.toString())
      set(it.name, user.name)
      set(it.profession, user.profession.name)

      set(it.statisticsIdentifier, user.statistics.identifier)
      set(it.walletIdentifier, user.wallet.identifier)
    }

    insertUserStatistics(user.statistics)
    insertUserWallet(user.wallet)
  }

  fun count(): Int {
    return database.from(UserTable)
      .select()
      .totalRecordsInAllPages
  }
}