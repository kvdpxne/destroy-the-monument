package me.kvdpxne.dtm.data

import java.util.UUID
import me.kvdpxne.dtm.data.source.database
import me.kvdpxne.dtm.data.tables.TableUser
import me.kvdpxne.dtm.data.tables.TableUserStatistics
import me.kvdpxne.dtm.data.tables.TableUserWallet
import me.kvdpxne.dtm.profession.ProfessionManager
import me.kvdpxne.dtm.user.User
import me.kvdpxne.dtm.user.UserImpl
import me.kvdpxne.dtm.user.UserStatisticsImpl
import me.kvdpxne.dtm.wallet.WalletImpl
import org.ktorm.dsl.Query
import org.ktorm.dsl.QueryRowSet
import org.ktorm.dsl.eq
import org.ktorm.dsl.from
import org.ktorm.dsl.innerJoin
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
    val kills = row[TableUserStatistics.kills]!!
    val assists = row[TableUserStatistics.assists]!!
    val deaths = row[TableUserStatistics.deaths]!!
    val destroyedMonuments = row[TableUserStatistics.destroyedMonuments]!!
    val playedGames = row[TableUserStatistics.playedGames]!!
    val gamesWon = row[TableUserStatistics.gamesWon]!!
    val gamesLost = row[TableUserStatistics.gamesLost]!!

    //
    val walletIdentifier = row[TableUser.walletIdentifier]!!
    val coins = row[TableUserWallet.coins]!!
    val multiplier = row[TableUserWallet.multiplier]!!

    //
    val name = row[TableUser.name]!!
    val profession = row[TableUser.profession]!!

    //
    return UserImpl(
      name,
      name,
      UserStatisticsImpl(
        kills,
        assists,
        deaths,
        destroyedMonuments,
        playedGames,
        gamesWon,
        gamesLost,
        statisticsIdentifier
      ),
      WalletImpl(
        coins,
        multiplier,
        walletIdentifier
      ),
      ProfessionManager.findProfessionByName(profession)
        ?: ProfessionManager.randomProfession,
      identifier.toString()
    )
  }

  private fun findUserBy(): Query {
    return database.from(TableUser)
      .innerJoin(
        TableUserStatistics,
        TableUser.statisticsIdentifier eq TableUserStatistics.identifier
      )
      .innerJoin(
        TableUserWallet,
        TableUser.walletIdentifier eq TableUserWallet.identifier
      )
      .select(
        TableUser.identifier,
        TableUser.name,
        TableUser.statisticsIdentifier,
        TableUser.walletIdentifier,
        TableUser.profession,
        TableUserStatistics.kills,
        TableUserStatistics.assists,
        TableUserStatistics.deaths,
        TableUserStatistics.destroyedMonuments,
        TableUserStatistics.playedGames,
        TableUserStatistics.gamesWon,
        TableUserStatistics.gamesLost,
        TableUserWallet.coins,
        TableUserWallet.multiplier
      )
  }

  /**
   * @since 0.1.0
   */
  fun findUserByIdentifierOrNull(
    identifier: String
  ): User? {
    return this.findUserBy()
      .where {
        TableUser.identifier eq identifier
      }
      .map {
        this.toUser(it)
      }
      .firstOrNull()
  }

  /**
   * @since 0.1.0
   */
  fun findUserByNameOrNull(
    name: String
  ): User? {
    return this.findUserBy()
      .where {
        TableUser.name eq name
      }
      .map {
        this.toUser(it)
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
      set(it.identifier, user.identifier)
      set(it.statisticsIdentifier, user.statistics.identifier)
      set(it.walletIdentifier, user.wallet.identifier)
      set(it.name, user.name)
      set(it.profession, user.currentProfession.name)
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
      set(it.profession, user.currentProfession.name)

      where {
        it.identifier eq user.identifier
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

  fun findNames(): List<String> {
    return database.from(TableUser)
      .select(TableUser.name)
      .map { it[TableUser.name]!! }
      .toList()
  }
}