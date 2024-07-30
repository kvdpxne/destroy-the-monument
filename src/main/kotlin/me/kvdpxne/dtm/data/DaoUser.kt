package me.kvdpxne.dtm.data

import java.util.UUID
import me.kvdpxne.dtm.data.source.database
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

/**
 * @since 0.1.0
 */
object DaoUser {

  /**
   * @since 0.1.0
   */
  private fun toUser(
    row: QueryRowSet
  ): User {
    //
    val identifier = UUID.fromString(row[TableUser.identifier]!!)

    //
    val statisticsIdentifier = row[TableUser.statisticsIdentifier]!!
    val statistics = DaoUserStatistics.findUserStatisticsByIdentifierOrNull(statisticsIdentifier)!!

    //
    val walletIdentifier = row[TableUser.walletIdentifier]!!
    val wallet = DaoUserWallet.findUserWalletByIdentifierOrNull(walletIdentifier)!!

    //
    val name = row[TableUser.name]!!
    val profession = row[TableUser.profession]!!

    //
    return User(
      name,
      statistics,
      wallet,
      identifier
    ).apply {
      ProfessionManager.findProfessionByName(profession)?.let {
        // TODO KIT UPGRADES
        this.currentProfession = it.clone()
      }
    }
  }

  /**
   * @since 0.1.0
   */
  fun findUserByIdentifierOrNull(
    identifier: UUID
  ): User? {
    return database.from(TableUser)
      .select()
      .where {
        TableUser.identifier eq identifier.toString()
      }
      .map {
        toUser(it)
      }
      .firstOrNull()
  }

  /**
   * @since 0.1.0
   */
  fun insertUser(
    user: User
  ) {
    database.insert(TableUser) {
      set(it.identifier, user.identifier.toString())
      set(it.statisticsIdentifier, user.statistics.identifier)
      set(it.walletIdentifier, user.wallet.identifier)
      set(it.name, user.name)
      set(it.profession, user.currentProfession?.name)
    }

    DaoUserStatistics.insertUserStatistics(user.statistics)
    DaoUserWallet.insertUserWallet(user.wallet)
  }

  /**
   * @since 0.1.0
   */
  fun updateUser(
    user: User
  ) {
    database.update(TableUser) {
      set(it.name, user.name)
      set(it.profession, user.currentProfession?.name)

      where {
        it.identifier eq user.identifier.toString()
      }
    }

    DaoUserStatistics.updateUserStatistics(user.statistics)
    DaoUserWallet.updateUserWallet(user.wallet)
  }

  /**
   * @since 0.1.0
   */
  fun updateUsers(
    users: Collection<User>
  ) {
    users.forEach {
      updateUser(it)
    }
  }
}