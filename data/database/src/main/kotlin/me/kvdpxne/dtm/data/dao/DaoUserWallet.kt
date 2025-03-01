package me.kvdpxne.dtm.data.dao

import java.util.UUID
import me.kvdpxne.dtm.data.ResponseCodes
import me.kvdpxne.dtm.data.extensions.mapping.toRawUserWallet
import me.kvdpxne.dtm.data.raw.RawUserWallet
import me.kvdpxne.dtm.data.repositories.RepositoryUserWallet
import me.kvdpxne.dtm.data.sources.DatabasesConfiguration
import me.kvdpxne.dtm.data.tables.TableUserWallet
import me.kvdpxne.dtm.data.transactions.concurrentTransaction
import me.kvdpxne.dtm.data.validation.EVERYTHING_OK
import me.kvdpxne.dtm.data.validation.context.extensions.validate
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteAll
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.statements.InsertStatement
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import org.jetbrains.exposed.sql.statements.UpdateStatement
import org.jetbrains.exposed.sql.update

object DaoUserWallet : RepositoryUserWallet {

  override suspend fun findUserWallerByIdentifierOrNull(
    identifier: UUID
  ): RawUserWallet? {
    return concurrentTransaction {
      TableUserWallet
        .selectAll()
        .where {
          TableUserWallet.identifier eq identifier
        }
        .firstNotNullOfOrNull { row: ResultRow ->
          row.toRawUserWallet()
        }
    }
  }

  /**
   * @since 0.1.0
   */
  private fun exists(
    identifier: UUID
  ): Boolean {
    return !TableUserWallet
      .select(TableUserWallet.identifier)
      .where {
        TableUserWallet.identifier eq identifier
      }
      .empty()
  }

  override suspend fun containsUserWalletByIdentifier(
    identifier: UUID
  ): Boolean {
    return concurrentTransaction {
      this@DaoUserWallet.exists(identifier)
    }
  }

  /**
   * @param userWallet
   * @param builder
   *
   * @since 0.1.0
   */
  private fun fillUpdateStatement(
    userWallet: RawUserWallet,
    builder: UpdateBuilder<*>
  ) {
    TableUserWallet.run {
      builder[this.coins] = userWallet.coins
      builder[this.multiplier] = userWallet.multiplier
      builder[this.infinite] = userWallet.infinite
      builder[this.locked] = userWallet.locked
    }
  }

  /**
   * @param userWallet
   * @param builder
   *
   * @since 0.1.0
   */
  private fun fillInsertStatement(
    userWallet: RawUserWallet,
    builder: UpdateBuilder<*>
  ) {
    builder[TableUserWallet.identifier] = userWallet.identifier
    this@DaoUserWallet.fillUpdateStatement(userWallet, builder)
  }

  /**
   * @since 0.1.0
   */
  internal fun justInsertUserWallet(
    userWallet: RawUserWallet
  ): Int {
    return TableUserWallet.insert { statement: InsertStatement<*> ->
      this@DaoUserWallet.fillInsertStatement(
        userWallet,
        statement
      )
    }.insertedCount
  }

  override suspend fun insertUserWallet(
    userWallet: RawUserWallet?
  ): Int {
    val result: Int = userWallet.validate()
    // The second condition will never be checked because object validation
    // first checks if the object is a null.
    if (EVERYTHING_OK != result || null == userWallet) {
      return result
    }

    return concurrentTransaction {
      // A new record cannot be inserted into a database table if a previously
      // inserted record has the same identifier as the record to be inserted.
      if (this@DaoUserWallet.exists(userWallet.identifier)) {
        return@concurrentTransaction ResponseCodes.DUPLICATED
      }

      TableUserWallet.insert { statement: InsertStatement<*> ->
        this@DaoUserWallet.fillInsertStatement(
          userWallet,
          statement
        )
      }.insertedCount
    }
  }

  override suspend fun updateUserWallet(
    userWallet: RawUserWallet?
  ): Int {
    val result: Int = userWallet.validate()
    // The second condition will never be checked because object validation
    // first checks if the object is a null.
    if (EVERYTHING_OK != result || null == userWallet) {
      return result
    }

    return concurrentTransaction {
      if (!this@DaoUserWallet.exists(userWallet.identifier)) {
        return@concurrentTransaction ResponseCodes.NO_RECORD
      }

      TableUserWallet.update({
        TableUserWallet.identifier eq userWallet.identifier
      }) { statement: UpdateStatement ->
        this@DaoUserWallet.fillUpdateStatement(
          userWallet,
          statement
        )
      }
    }
  }

  override suspend fun deleteUserWalletByIdentifier(
    identifier: UUID
  ): Int {
    return concurrentTransaction(DatabasesConfiguration.main) {
      TableUserWallet.deleteWhere {
        this.identifier eq identifier
      }
    }
  }

  override suspend fun truncateUserWallets(): Int {
    return concurrentTransaction {
      TableUserWallet.deleteAll()
    }
  }

  override suspend fun countUserWallets(): Long {
    return concurrentTransaction {
      TableUserWallet.selectAll().count()
    }
  }
}