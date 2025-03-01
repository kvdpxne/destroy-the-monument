package me.kvdpxne.dtm.data.dao

import java.util.Locale
import java.util.UUID
import me.kvdpxne.dtm.data.ResponseCodes
import me.kvdpxne.dtm.data.extensions.mapping.toRawUser
import me.kvdpxne.dtm.data.raw.RawUser
import me.kvdpxne.dtm.data.repositories.RepositoryUser
import me.kvdpxne.dtm.data.sources.DatabasesConfiguration
import me.kvdpxne.dtm.data.tables.TableUser
import me.kvdpxne.dtm.data.tables.TableUserStatistics
import me.kvdpxne.dtm.data.tables.TableUserWallet
import me.kvdpxne.dtm.data.transactions.concurrentTransaction
import me.kvdpxne.dtm.data.validation.EVERYTHING_OK
import me.kvdpxne.dtm.data.validation.context.extensions.validate
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SqlExpressionBuilder
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteAll
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.lowerCase
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.statements.InsertStatement
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import org.jetbrains.exposed.sql.statements.UpdateStatement
import org.jetbrains.exposed.sql.update

object DaoUser : RepositoryUser {

  /**
   * @since 0.1.0
   */
  private val OP_COLUMNS: List<Column<*>> by lazy {
    arrayListOf(
      TableUser.identifier,
      TableUser.statisticsIdentifier,
      TableUser.walletIdentifier,
      TableUser.name,
      TableUser.displayName,
      TableUser.profession,
      TableUser.locale,
      TableUserStatistics.kills,
      TableUserStatistics.assists,
      TableUserStatistics.deaths,
      TableUserStatistics.destroyedMonuments,
      TableUserStatistics.playedGames,
      TableUserStatistics.gamesWon,
      TableUserStatistics.gamesLost,
      TableUserWallet.coins,
      TableUserWallet.multiplier,
      TableUserWallet.infinite,
      TableUserWallet.locked
    )
  }

  override suspend fun readUsers(): Collection<RawUser> {
    return concurrentTransaction {
      TableUser
        .innerJoin(TableUserStatistics)
        .innerJoin(TableUserWallet)
        .select(OP_COLUMNS)
        .map { row: ResultRow ->
          row.toRawUser()
        }
        .toList()
    }
  }

  override suspend fun readUserIdentifiers(): Collection<UUID> {
    return concurrentTransaction {
      TableUser
        .select(TableUser.identifier)
        .map { row: ResultRow ->
          row[TableUser.identifier]
        }
        .toList()
    }
  }

  override suspend fun readUserNames(): Collection<String> {
    return concurrentTransaction {
      TableUser
        .select(TableUser.name)
        .map { row: ResultRow ->
          row[TableUser.name]
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
  ): RawUser? {
    return concurrentTransaction(DatabasesConfiguration.main) {
      TableUser
        .innerJoin(TableUserStatistics)
        .innerJoin(TableUserWallet)
        .select(OP_COLUMNS)
        .where(predicate)
        .firstNotNullOfOrNull { row: ResultRow ->
          row.toRawUser()
        }
    }
  }

  override suspend fun findUserByIdentifierOrNull(
    identifier: UUID
  ): RawUser? {
    return findUserBy {
      TableUser.identifier eq identifier
    }
  }

  override suspend fun findUserByNameOrNull(
    name: String
  ): RawUser? {
    return findUserBy {
      TableUser.name.lowerCase() eq name.lowercase(Locale.US)
    }
  }

  /**
   * @since 0.1.0
   */
  private fun exists(
    identifier: UUID
  ): Boolean {
    return !TableUser
      .select(TableUser.identifier)
      .where {
        TableUser.identifier eq identifier
      }
      .empty()
  }

  override suspend fun containsUserByIdentifier(
    identifier: UUID
  ): Boolean {
    return concurrentTransaction {
      exists(identifier)
    }
  }

  override suspend fun containsUserByName(
    name: String
  ): Boolean {
    return concurrentTransaction {
      !TableUser
        .select(TableUser.name)
        .where {
          TableUser.name.lowerCase() eq name.lowercase(Locale.US)
        }
        .empty()
    }
  }

  /**
   * @param user
   * @param builder
   *
   * @since 0.1.0
   */
  private fun fillUpdateStatement(
    user: RawUser,
    builder: UpdateBuilder<Int>
  ) {
    TableUser.run {
      builder[this.displayName] = user.displayName
      builder[this.profession] = user.profession
      builder[this.locale] = user.locale
    }
  }

  /**
   * @param user
   * @param builder
   *
   * @since 0.1.0
   */
  private fun fillInsertStatement(
    user: RawUser,
    builder: UpdateBuilder<Int>
  ) {
    builder[TableUser.identifier] = user.identifier
    builder[TableUser.statisticsIdentifier] = user.statistics.identifier
    builder[TableUser.walletIdentifier] = user.wallet.identifier
    builder[TableUser.name] = user.name

    fillUpdateStatement(user, builder)
  }

  /**
   * @param user
   *
   * @since 0.1.0
   */
  private fun where(
    user: RawUser
  ): SqlExpressionBuilder.() -> Op<Boolean> {
    return {
      TableUser.identifier eq user.identifier
    }
  }

  override suspend fun insertUsers(
    users: Iterable<RawUser>
  ): Int {
    TODO("Not yet implemented")
  }

  override suspend fun insertUser(
    user: RawUser?
  ): Int {
    val result: Int = user.validate()
    // The second condition will never be checked because object validation
    // first checks if the object is a null.
    if (EVERYTHING_OK != result || null == user) {
      return result
    }

    return concurrentTransaction {
      if (exists(user.identifier)) {
        return@concurrentTransaction ResponseCodes.DUPLICATED
      }

      if (0 == DaoUserStatistics.justInsertUserStatistics(user.statistics)) {
        this.rollback()
      }

      if (0 == DaoUserWallet.justInsertUserWallet(user.wallet)) {
        this.rollback()
      }

      TableUser.insert { statement: InsertStatement<*> ->
        fillInsertStatement(
          user,
          statement
        )
      }.insertedCount
    }
  }

  override suspend fun updateUsers(
    users: Iterable<RawUser>
  ): Int {
    TODO("Not yet implemented")
  }

  override suspend fun updateUser(
    user: RawUser?
  ): Int {
    val result: Int = user.validate()
    // The second condition will never be checked because object validation
    // first checks if the object is a null.
    if (EVERYTHING_OK != result || null == user) {
      return result
    }

    DaoUserStatistics.updateUserStatistics(user.statistics)
    DaoUserWallet.updateUserWallet(user.wallet)

    return concurrentTransaction {
      if (!exists(user.identifier)) {
        return@concurrentTransaction ResponseCodes.NO_RECORD
      }

      TableUser.update({
       TableUser.identifier eq user.identifier
      }) { statement: UpdateStatement ->
        fillUpdateStatement(user, statement)
      }
    }
  }

  override suspend fun deleteUser(
    user: RawUser
  ): Boolean {
    // Zwraca wynik, który określa jak wiele wierszy zostało usuniętych.
    val result: Int = concurrentTransaction {
      TableUser.deleteWhere {
        this.identifier eq user.identifier
      }
    }

    if (0 != result) {
      DaoUserStatistics.deleteUserStatisticsByIdentifier(user.statistics.identifier)
      DaoUserWallet.deleteUserWalletByIdentifier(user.wallet.identifier)
    }

    return 0 != result
  }

  override suspend fun deleteUserByIdentifier(
    identifier: UUID
  ): Boolean {
    return findUserByIdentifierOrNull(identifier)?.let {
      deleteUser(it)
    } ?: false
  }

  override suspend fun truncateUsers(): Int {
    val result: Int = concurrentTransaction {
      TableUser.deleteAll()
    }

    DaoUserStatistics.truncateUserStatistics()
    DaoUserWallet.truncateUserWallets()

    return result
  }

  override suspend fun countUsers(): Long {
    return concurrentTransaction {
      TableUser.selectAll().count()
    }
  }
}