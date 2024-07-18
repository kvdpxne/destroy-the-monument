package me.kvdpxne.dtm.data

import java.util.UUID
import me.kvdpxne.dtm.data.tables.TableUser
import me.kvdpxne.dtm.profession.ProfessionManager
import me.kvdpxne.dtm.user.User
import org.ktorm.dsl.QueryRowSet
import org.ktorm.dsl.eq
import org.ktorm.dsl.from
import org.ktorm.dsl.insert
import org.ktorm.dsl.map
import org.ktorm.dsl.select
import org.ktorm.dsl.update
import org.ktorm.dsl.where

internal fun toUser(
  row: QueryRowSet
): User {

  val identifier = UUID.fromString(row[TableUser.identifier]!!)
  val name = row[TableUser.name]!!

  val profession = row[TableUser.profession]!!

  val statisticsIdentifier = row[TableUser.statisticsIdentifier]!!
  val statistics = findUserStatisticsByIdentifier(statisticsIdentifier)!!

  val walletIdentifier = row[TableUser.walletIdentifier]!!
  val wallet = findUserWalletByIdentifier(walletIdentifier)!!


  return User(
    identifier,
    name,
    statistics,
    wallet
  ).apply {
    ProfessionManager.findProfessionByName(profession)?.let {
      val profession1 = it.clone()

      this.addProfession(profession1)
      this.currentProfession = profession1
    }
  }
}

object DaoUser {

  fun findByIdentifier(identifier: UUID): User? {
    return database.from(TableUser)
      .select()
      .where { TableUser.identifier eq identifier.toString() }
      .map { toUser(it) }
      .firstOrNull()
  }


  fun update(user: User) {
    database.update(TableUser) {
      set(it.name, user.name)
      set(it.profession, user.currentProfession?.name)

      set(it.statisticsIdentifier, user.statistics.identifier)
      set(it.walletIdentifier, user.wallet.identifier)

      where {
        TableUser.identifier eq user.identifier.toString()
      }
    }

    updateUserStatistics(user.statistics)
    updateUserWaller(user.wallet)
  }

  fun insert(user: User) {
    database.insert(TableUser) {
      set(it.identifier, user.identifier.toString())
      set(it.name, user.name)
      set(it.profession, user.currentProfession?.name)

      set(it.statisticsIdentifier, user.statistics.identifier)
      set(it.walletIdentifier, user.wallet.identifier)
    }

    insertUserStatistics(user.statistics)
    insertUserWallet(user.wallet)
  }

  fun count(): Int {
    return database.from(TableUser)
      .select()
      .totalRecordsInAllPages
  }
}