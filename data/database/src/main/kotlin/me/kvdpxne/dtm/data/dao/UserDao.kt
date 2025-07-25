package me.kvdpxne.dtm.data.dao

import java.util.Locale
import java.util.UUID
import me.kvdpxne.dtm.data.EntityFieldNames
import me.kvdpxne.dtm.data.ResponseCodes
import me.kvdpxne.dtm.data.mapping.toRawUser
import me.kvdpxne.dtm.data.raw.RawUser
import me.kvdpxne.dtm.data.raw.RawUserStatistics
import me.kvdpxne.dtm.data.raw.RawUserWallet
import me.kvdpxne.dtm.data.repositories.UserRepository
import me.kvdpxne.dtm.data.tables.UserStatisticsTable
import me.kvdpxne.dtm.data.tables.UserTable
import me.kvdpxne.dtm.data.tables.UserWalletTable
import me.kvdpxne.dtm.data.transactions.concurrentTransaction
import me.kvdpxne.dtm.data.validation.ValidationResult
import me.kvdpxne.dtm.data.validation.failure
import me.kvdpxne.dtm.data.validation.main.validateUser
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SqlExpressionBuilder
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.deleteAll
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insertReturning
import org.jetbrains.exposed.sql.or
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.statements.InsertStatement
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import org.jetbrains.exposed.sql.statements.UpdateStatement
import org.jetbrains.exposed.sql.updateReturning
import org.jetbrains.exposed.sql.upperCase

object UserDao : UserRepository {

  override suspend fun readUsers(): Collection<Pair<RawUser, ValidationResult>> {
    return concurrentTransaction(readOnly = true) {
      UserTable
        .innerJoin(UserStatisticsTable)
        .innerJoin(UserWalletTable)
        .selectAll()
        .map(ResultRow::toRawUser)
        .toList()
    }
  }

  override suspend fun readUserIdentifiers(): Collection<UUID> {
    return concurrentTransaction(readOnly = true) {
      UserTable
        .select(UserTable.identifier)
        .map { row: ResultRow ->
          row[UserTable.identifier]
        }
        .toList()
    }
  }

  override suspend fun readUserNames(): Collection<String> {
    return concurrentTransaction(readOnly = true) {
      UserTable
        .select(UserTable.name)
        .map { row: ResultRow ->
          row[UserTable.name].uppercase(Locale.US)
        }
        .toList()
    }
  }

  /**
   * @param predicate
   *
   * @since 0.1.0
   */
  private suspend fun findBy(
    predicate: SqlExpressionBuilder.() -> Op<Boolean>
  ): Pair<RawUser, ValidationResult>? {
    return concurrentTransaction(readOnly = true) {
      UserTable
        .innerJoin(UserStatisticsTable)
        .innerJoin(UserWalletTable)
        .selectAll()
        .where(predicate)
        .firstNotNullOfOrNull(ResultRow::toRawUser)
    }
  }

  override suspend fun findUserByIdentifierOrNull(
    identifier: UUID
  ): Pair<RawUser, ValidationResult>? {
    return this.findBy {
      UserTable.identifier eq identifier
    }
  }

  override suspend fun findUserByNameOrNull(
    name: String
  ): Pair<RawUser, ValidationResult>? {
    return this.findBy {
      UserTable.name.upperCase() eq name.uppercase(Locale.US)
    }
  }

  /**
   * @since 0.1.0
   */
  private fun existsUserBy(
    predicate: SqlExpressionBuilder.() -> Op<Boolean>
  ): Boolean {
    return !UserTable
      .select(UserTable.identifier)
      .where(predicate)
      .empty()
  }

  override suspend fun containsUserByIdentifier(
    identifier: UUID
  ): Boolean {
    return concurrentTransaction(readOnly = true) {
      this@UserDao.existsUserBy {
        UserTable.identifier eq identifier
      }
    }
  }

  override suspend fun containsUserByName(
    name: String
  ): Boolean {
    return concurrentTransaction(readOnly = true) {
      this@UserDao.existsUserBy {
        UserTable.name.upperCase() eq name.uppercase(Locale.US)
      }
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
    UserTable.run {
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
    builder[UserTable.identifier] = user.identifier
    builder[UserTable.statisticsIdentifier] = user.statistics.identifier
    builder[UserTable.walletIdentifier] = user.wallet.identifier
    builder[UserTable.name] = user.name

    this.fillUpdateStatement(user, builder)
  }

  override suspend fun insertUser(
    user: RawUser?
  ): Pair<RawUser?, ValidationResult> {
    val result: ValidationResult = validateUser(user, false)
    if (!result.isValid || null == user) {
      return failure(result)
    }

    return concurrentTransaction {
      if (this@UserDao.existsUserBy {
          (UserTable.identifier eq user.identifier) or
            (UserTable.name.upperCase() eq user.name.uppercase(Locale.US))
        }
      ) return@concurrentTransaction failure(
        "${EntityFieldNames.IDENTIFIER} or ${EntityFieldNames.NAME}",
        "A user with such an identifier or name already exists in the dataset.",
        ResponseCodes.DUPLICATED,
        Pair(user.identifier, user.name)
      )

      val statistics: Pair<RawUserStatistics?, ValidationResult> =
        UserStatisticsDao.insertUserStatisticsWithoutValidation(user.statistics)
      if (null == statistics.first && !statistics.second.isValid) {
        return@concurrentTransaction failure(statistics.second)
      }

      val wallet: Pair<RawUserWallet?, ValidationResult> =
        UserWalletDao.insertUserWalletWithoutValidation(user.wallet)
      if (null == wallet.first || !wallet.second.isValid) {
        return@concurrentTransaction failure(wallet.second)
      }

      UserTable
        .insertReturning { statement: InsertStatement<*> ->
          this@UserDao.fillInsertStatement(user, statement)
        }
        .firstNotNullOf { row: ResultRow ->
          row.toRawUser(statistics.first, wallet.first)
        }
    }
  }

  override suspend fun updateUser(
    user: RawUser?
  ): Pair<RawUser?, ValidationResult> {
    val result: ValidationResult = validateUser(user, false)
    if (!result.isValid || null == user) {
      return failure(result)
    }

    return concurrentTransaction {
      if (!this@UserDao.existsUserBy {
          (UserTable.identifier eq user.identifier) and
            (UserTable.name.upperCase() eq user.name.uppercase(Locale.US))
        }
      ) return@concurrentTransaction failure(
        "${EntityFieldNames.IDENTIFIER} and ${EntityFieldNames.NAME}",
        "A user with such an identifier and name does not exist in the dataset.",
        ResponseCodes.NO_RECORD,
        Pair(user.identifier, user.name)
      )

      val statistics: Pair<RawUserStatistics?, ValidationResult> =
        UserStatisticsDao.updateUserStatisticsWithoutValidation(user.statistics)
      if (null == statistics.first || !statistics.second.isValid) {
        return@concurrentTransaction failure(statistics.second)
      }

      val wallet: Pair<RawUserWallet?, ValidationResult> =
        UserWalletDao.updateUserWalletWithoutValidation(user.wallet)
      if (null == wallet.first || !wallet.second.isValid) {
        return@concurrentTransaction failure(wallet.second)
      }

      UserTable
        .updateReturning(where = {
          UserTable.identifier eq user.identifier
        }) { statement: UpdateStatement ->
          this@UserDao.fillUpdateStatement(user, statement)
        }
        .firstNotNullOf { row: ResultRow ->
          row.toRawUser(statistics.first, wallet.first)
        }
    }
  }

  override suspend fun deleteUser(
    user: RawUser
  ): Boolean {
    // Zwraca wynik, który określa jak wiele wierszy zostało usuniętych.
    val result: Int = concurrentTransaction {
      UserTable.deleteWhere {
        this.identifier eq user.identifier
      }
    }

    if (0 != result) {
      UserStatisticsDao.deleteUserStatisticsByIdentifier(user.statistics.identifier)
      UserWalletDao.deleteUserWalletByIdentifier(user.wallet.identifier)
    }

    return 0 != result
  }

  override suspend fun deleteUserByIdentifier(
    identifier: UUID
  ): Boolean {
    return findUserByIdentifierOrNull(identifier)?.let {
      deleteUser(it.first)
    } ?: false
  }

  override suspend fun deleteUserByName(name: String): Boolean {
    return this.findUserByNameOrNull(name)
      ?.let { (user: RawUser, _: ValidationResult) ->
        this.deleteUser(user)
      } ?: false
  }

  override suspend fun truncateUsers(): Int {
    val result: Int = concurrentTransaction {
      UserTable.deleteAll()
    }

    UserStatisticsDao.truncateUserStatistics()
    UserWalletDao.truncateUserWallets()

    return result
  }

  override suspend fun countUsers(): Long {
    return concurrentTransaction(readOnly = true) {
      UserTable
        .select(UserTable.identifier)
        .count()
    }
  }
}