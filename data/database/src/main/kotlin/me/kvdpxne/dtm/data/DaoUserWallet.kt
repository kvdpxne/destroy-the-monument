package me.kvdpxne.dtm.data

import java.util.UUID
import me.kvdpxne.dtm.data.raw.RawUserWallet
import me.kvdpxne.dtm.data.repositories.RepositoryUserWallet
import me.kvdpxne.dtm.data.sources.DatabasesConfiguration
import me.kvdpxne.dtm.data.tables.TableUserWallet
import me.kvdpxne.dtm.data.transactions.concurrentTransaction
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SqlExpressionBuilder
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteAll
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import org.jetbrains.exposed.sql.update

object DaoUserWallet : RepositoryUserWallet {

  /**
   * @param userWallet
   * @param builder
   *
   * @since 0.1.0
   */
  private fun fillUserWalletStatement(
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

  private fun where(
    userWallet: RawUserWallet
  ): SqlExpressionBuilder.() -> Op<Boolean> {
    return {
      TableUserWallet.identifier eq userWallet.identifier
    }
  }

  /**
   * @since 0.1.0
   */
  internal fun ResultRow.toRawUserWallet(
    identifier: UUID
  ): RawUserWallet {
    val coins: Long = this[TableUserWallet.coins]
    require(0 <= coins) {
      "The database returned an invalid number of coins ($coins)."
    }

    val multiplier: Float = this[TableUserWallet.multiplier]
    require(0 <= multiplier) {
      "The database returned an invalid multiplier ($multiplier)."
    }

    val infinite: Boolean = this[TableUserWallet.infinite]
    val locked: Boolean = this[TableUserWallet.locked]

    return RawUserWallet(
      identifier,
      coins,
      multiplier,
      infinite,
      locked
    )
  }

  /**
   * @since 0.1.0
   */
  private fun ResultRow.toRawUserWallet(): RawUserWallet {
    return this.toRawUserWallet(
      this[TableUserWallet.identifier]
    )
  }

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

  override suspend fun containsUserWalletByIdentifier(
    identifier: UUID
  ): Boolean {
    return concurrentTransaction {
      0L != TableUserWallet
        .select(TableUserWallet.identifier)
        .where {
          TableUserWallet.identifier eq identifier
        }
        .count()
    }
  }

  override suspend fun insertUserWallet(
    userWallet: RawUserWallet
  ): Int {
    if (this.containsUserWalletByIdentifier(userWallet.identifier)) {
      return Fsfsfsf.ALREADY_EXISTS
    }

    return concurrentTransaction {
      TableUserWallet.insert { builder: UpdateBuilder<*> ->
        builder[this.identifier] = userWallet.identifier
        this@DaoUserWallet.fillUserWalletStatement(
          userWallet,
          builder
        )
      }.insertedCount
    }
  }

  override suspend fun updateUserWallet(
    userWallet: RawUserWallet
  ): Int {
    return concurrentTransaction(DatabasesConfiguration.main) {
      //
      TableUserWallet.update(
        where(userWallet)
      ) {
        //
        fillUserWalletStatement(
          userWallet,
          it
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

  override suspend fun deleteUserWallets(): Int {
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