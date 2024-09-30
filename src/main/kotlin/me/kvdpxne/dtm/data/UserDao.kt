package me.kvdpxne.dtm.data

import java.util.UUID
import me.kvdpxne.dtm.data.repositories.UserRepository
import me.kvdpxne.dtm.data.sources.DatabasesConfiguration
import me.kvdpxne.dtm.data.tables.UserStatisticsTable
import me.kvdpxne.dtm.data.tables.UserTable
import me.kvdpxne.dtm.data.tables.UserWalletTable
import me.kvdpxne.dtm.data.transactions.concurrentTransaction
import me.kvdpxne.dtm.profession.ProfessionManager
import me.kvdpxne.dtm.user.User
import me.kvdpxne.dtm.user.UserImpl
import me.kvdpxne.dtm.user.UserStatisticsImpl
import me.kvdpxne.dtm.wallet.WalletImpl
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SqlExpressionBuilder
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.lowerCase
import org.jetbrains.exposed.sql.statements.UpdateBuilder

object UserDao : UserRepository {

  /**
   * @since 0.1.0
   */
  private val fields: List<Column<*>> = listOf(
    UserTable.identifier,
    UserTable.statisticsIdentifier,
    UserTable.walletIdentifier,
    UserTable.name,
    UserTable.profession,
    UserStatisticsTable.kills,
    UserStatisticsTable.assists,
    UserStatisticsTable.deaths,
    UserStatisticsTable.destroyedMonuments,
    UserStatisticsTable.playedGames,
    UserStatisticsTable.gamesWon,
    UserStatisticsTable.gamesLost,
    UserWalletTable.coins,
    UserWalletTable.multiplier
  )

  /**
   * @since 0.1.0
   */
  private fun ResultRow.toUser(): User {
    // Podstawowy klucz tabeli użytkownika.
    val identifier: UUID = this[UserTable.identifier]

    //
    val statisticsIdentifier: UUID = this[UserTable.statisticsIdentifier]
    val walletIdentifier: UUID = this[UserTable.walletIdentifier]

    //
    val kills: Int = this[UserStatisticsTable.kills]
    val assists: Int = this[UserStatisticsTable.assists]
    val deaths: Int = this[UserStatisticsTable.deaths]
    val destroyedMonuments: Int = this[UserStatisticsTable.destroyedMonuments]
    val playedGames: Int = this[UserStatisticsTable.playedGames]
    val gamesWon: Int = this[UserStatisticsTable.gamesWon]
    val gamesLost: Int = this[UserStatisticsTable.gamesLost]

    //
    val coins: Long = this[UserWalletTable.coins]
    val multiplier: Float = this[UserWalletTable.multiplier]

    //
    val name: String = this[UserTable.name]
    val professionName: String = this[UserTable.profession]

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
      ProfessionManager.findProfessionByName(professionName)
        ?: ProfessionManager.randomProfession,
      identifier
    )
  }

  private fun buildUserStatement(
    user: User,
    builder: UpdateBuilder<Int>
  ) {
    UserTable.run {
      builder[this.statisticsIdentifier] = user.statistics.identifier
      builder[this.walletIdentifier] = user.wallet.identifier

      builder[this.name] = user.name
      builder[this.profession] = user.currentProfession.name
    }
  }

  override suspend fun findUsers(): List<User> {
    return concurrentTransaction(DatabasesConfiguration.main) {
      UserTable
        .innerJoin(UserStatisticsTable)
        .innerJoin(UserWalletTable)
        .select(this.fields)
        .map { row: ResultRow ->
          row.toUser()
        }
        .toList()
    }
  }

  override suspend fun getUsersNames(): List<String> {
    return concurrentTransaction(DatabasesConfiguration.main) {
      UserTable
        .select(UserTable.name)
        .map { row: ResultRow ->
          row[UserTable.name]
        }
        .toList()
    }
  }

  /**
   * @param predicate
   *
   * @since 0.1.0
   */
  private suspend fun findUserBy(
    predicate: SqlExpressionBuilder.() -> Op<Boolean>
  ): User? {
    return concurrentTransaction(DatabasesConfiguration.main) {
      UserTable
        .innerJoin(UserStatisticsTable)
        .innerJoin(UserWalletTable)
        .select(fields)
        .where(predicate)
        .map { row: ResultRow ->
          row.toUser()
        }
        .firstOrNull()
    }
  }

  /**
   * @since 0.1.0
   */
  override suspend fun findUserByIdentifier(
    identifier: UUID
  ): User? {
    return this.findUserBy {
      UserTable.identifier eq identifier
    }
  }

  /**
   * @since 0.1.0
   */
  override suspend fun findUserByName(
    name: String,
    ignoreCase: Boolean
  ): User? {
    return this.findUserBy {
      if (ignoreCase) {
        UserTable.name.lowerCase() eq name.lowercase()
      } else {
        UserTable.name eq name
      }
    }
  }

  override suspend fun insertUser(
    user: User
  ): Int {
    val count: Int = concurrentTransaction(DatabasesConfiguration.main) {
      UserTable.insert {
        //
        it[this.identifier] = user.identifier

        //
        buildUserStatement(user, it)
      }.insertedCount
    }

    //
    UserStatisticsDao.insertUserStatistics(user.statistics)
    UserWalletDao.insertUserWallet(user.wallet)

    return count
  }

  override suspend fun updateUser(
    user: User
  ): Int {
    TODO("Not yet implemented")
  }

  override suspend fun deleteUserByIdentifier(
    identifier: UUID
  ): Int {
    return concurrentTransaction(DatabasesConfiguration.main) {
      UserTable.deleteWhere {
        UserTable.identifier eq identifier
      }
    }
  }

  override suspend fun countUsers(): Long {
    return concurrentTransaction(DatabasesConfiguration.main) {
      UserTable
        .select(UserTable.identifier)
        .count()
    }
  }
}