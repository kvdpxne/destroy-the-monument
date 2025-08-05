package me.kvdpxne.dtm.data.daos

import java.util.Locale
import java.util.UUID
import me.kvdpxne.dtm.data.repositories.UserRepository
import me.kvdpxne.dtm.data.sources.DatabasesConfiguration
import me.kvdpxne.dtm.data.tables.UserStatisticsTable
import me.kvdpxne.dtm.data.tables.UserTable
import me.kvdpxne.dtm.data.tables.UserWalletTable
import me.kvdpxne.dtm.data.transactions.concurrentTransaction
import me.kvdpxne.dtm.profession.ProfessionManager
import me.kvdpxne.dtm.translation.locale.Locales
import me.kvdpxne.dtm.translation.TranslationService
import me.kvdpxne.dtm.user.User
import me.kvdpxne.dtm.user.UserImpl
import me.kvdpxne.dtm.user.statistics.UserStatisticsImpl
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
import org.jetbrains.exposed.sql.update

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
    UserTable.locale,
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
    val locale: Locale = this[UserTable.locale]?.let { Locales.fromString(it) } ?: TranslationService.defaultLocale

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
      locale,
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
      builder[this.locale] = "${user.locale.language}_${user.locale.country}"
    }
  }

  override suspend fun findUsers(): List<User> {
    return concurrentTransaction(DatabasesConfiguration.main) {
      UserTable
        .innerJoin(UserStatisticsTable)
        .innerJoin(UserWalletTable)
        .select(fields)
        .map { row: ResultRow ->
          row.toUser()
        }
        .toList()
    }
  }

  override suspend fun findUsersNames(): List<String> {
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
        .firstNotNullOfOrNull { row: ResultRow ->
          row.toUser()
        }
    }
  }

  /**
   * @since 0.1.0
   */
  override suspend fun findUserByIdentifier(
    identifier: UUID
  ): User? {
    return findUserBy {
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
    return findUserBy {
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
    var count = 0

    count += UserStatisticsDao.insertUserStatistics(user.statistics)
    count += UserWalletDao.insertUserWallet(user.wallet)

    count += concurrentTransaction(DatabasesConfiguration.main) {
      UserTable.insert {
        it[this.identifier] = user.identifier
        buildUserStatement(user, it)
      }.insertedCount
    }

    return count
  }

  override suspend fun updateUser(
    user: User
  ): Int {
    var count = 0

    if (user.wasModified) {
      count += concurrentTransaction(DatabasesConfiguration.main) {
        UserTable.update({
          UserTable.identifier eq user.identifier
        }) {
          buildUserStatement(user, it)
        }
      }
    }

    count += UserStatisticsDao.updateUserStatistics(user.statistics)
    count += UserWalletDao.updateUserWallet(user.wallet)

    return count
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