package me.kvdpxne.dtm.data.dao

import java.util.UUID
import me.kvdpxne.dtm.data.EntityFieldNames
import me.kvdpxne.dtm.data.ResponseCodes
import me.kvdpxne.dtm.data.mapping.toRawUserWallet
import me.kvdpxne.dtm.data.raw.RawUserWallet
import me.kvdpxne.dtm.data.repositories.UserWalletRepository
import me.kvdpxne.dtm.data.tables.UserWalletTable
import me.kvdpxne.dtm.data.transactions.concurrentTransaction
import me.kvdpxne.dtm.data.validation.ValidationResult
import me.kvdpxne.dtm.data.validation.failure
import me.kvdpxne.dtm.data.validation.main.validateUserWallet
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteAll
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insertReturning
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.statements.InsertStatement
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import org.jetbrains.exposed.sql.statements.UpdateStatement
import org.jetbrains.exposed.sql.updateReturning

object UserWalletDao : UserWalletRepository {

  override suspend fun findUserWallerByIdentifierOrNull(
    identifier: UUID
  ): Pair<RawUserWallet, ValidationResult>? {
    return concurrentTransaction(readOnly = true) {
      UserWalletTable
        .selectAll()
        .where {
          UserWalletTable.identifier eq identifier
        }
        .firstNotNullOfOrNull(ResultRow::toRawUserWallet)
    }
  }

  /**
   * Checks if a user wallet with the given identifier exists in the database.
   *
   * This internal helper method verifies existence without retrieving full data.
   * Used to prevent duplicate inserts and validate references.
   *
   * @param identifier The UUID to check in the database
   * @return True if a record exists with this identifier, false otherwise
   * @since 0.1.0
   */
  private fun exists(
    identifier: UUID
  ): Boolean {
    return !UserWalletTable
      .select(UserWalletTable.identifier)
      .where {
        UserWalletTable.identifier eq identifier
      }
      .empty()
  }

  override suspend fun containsUserWalletByIdentifier(
    identifier: UUID
  ): Boolean {
    return concurrentTransaction(readOnly = true) {
      this@UserWalletDao.exists(identifier)
    }
  }

  /**
   * Populates an SQL insert statement with user wallet data.
   *
   * Sets all required fields for creating a new database record, including the identifier.
   * Used internally during wallet creation operations.
   *
   * @param userWallet The wallet containing values to insert
   * @param builder The SQL statement builder to populate
   * @since 0.1.0
   */
  private fun fillUpdateStatement(
    userWallet: RawUserWallet,
    builder: UpdateBuilder<*>
  ) {
    UserWalletTable.run {
      builder[this.coins] = userWallet.coins
      builder[this.multiplier] = userWallet.multiplier
      builder[this.infinite] = userWallet.infinite
      builder[this.locked] = userWallet.locked
    }
  }

  /**
   * Populates an SQL update statement with wallet values.
   *
   * Sets non-identifier fields (coins, multiplier, flags) for database updates.
   * Used internally when modifying existing records.
   *
   * @param userWallet The wallet containing updated values
   * @param builder The SQL statement builder to populate
   * @since 0.1.0
   */
  private fun fillInsertStatement(
    userWallet: RawUserWallet,
    builder: UpdateBuilder<*>
  ) {
    builder[UserWalletTable.identifier] = userWallet.identifier
    this.fillUpdateStatement(userWallet, builder)
  }

  /**
   * Inserts a wallet into the database without prior validation.
   *
   * Checks for identifier conflicts before insertion. Returns failure result if the identifier already exists.
   * Returns the inserted wallet and success status upon completion.
   *
   * @param userWallet The wallet to insert into database
   * @return Pair containing inserted wallet (or null on failure) and validation result
   * @since 0.1.0
   */
  suspend fun insertUserWalletWithoutValidation(
    userWallet: RawUserWallet,
  ): Pair<RawUserWallet?, ValidationResult> {
    return concurrentTransaction {
      if (this@UserWalletDao.exists(userWallet.identifier)) {
        return@concurrentTransaction failure(
          EntityFieldNames.IDENTIFIER,
          "User wallet already exists",
          ResponseCodes.DUPLICATED,
          userWallet.identifier
        )
      }

      UserWalletTable
        .insertReturning { statement: InsertStatement<*> ->
          this@UserWalletDao.fillInsertStatement(userWallet, statement)
        }
        .firstNotNullOf(ResultRow::toRawUserWallet)
    }
  }

  override suspend fun insertUserWallet(
    userWallet: RawUserWallet?
  ): Pair<RawUserWallet?, ValidationResult> {
    val result: ValidationResult = validateUserWallet(userWallet)
    return if (!result.isValid || null == userWallet) failure(result)
    else this.insertUserWalletWithoutValidation(userWallet)
  }

  /**
   * Updates an existing wallet without prior validation.
   *
   * Verifies the wallet exists before attempting update. Returns failure result if no matching record found.
   * Returns the updated wallet and success status upon completion.
   *
   * @param userWallet The wallet with updated values
   * @return Pair containing updated wallet (or null on failure) and validation result
   * @since 0.1.0
   */
  suspend fun updateUserWalletWithoutValidation(
    userWallet: RawUserWallet,
  ): Pair<RawUserWallet?, ValidationResult> {
    return concurrentTransaction {
      if (!this@UserWalletDao.exists(userWallet.identifier)) {
        return@concurrentTransaction failure(
          EntityFieldNames.WALLET_IDENTIFIER,
          "User wallet does not exist",
          ResponseCodes.NO_RECORD,
          userWallet.identifier
        )
      }

      UserWalletTable
        .updateReturning(where = {
          UserWalletTable.identifier eq userWallet.identifier
        }) { statement: UpdateStatement ->
          this@UserWalletDao.fillUpdateStatement(userWallet, statement)
        }
        .firstNotNullOf(ResultRow::toRawUserWallet)
    }
  }

  override suspend fun updateUserWallet(
    userWallet: RawUserWallet?
  ): Pair<RawUserWallet?, ValidationResult> {
    val result: ValidationResult = validateUserWallet(userWallet)
    return if (!result.isValid || null == userWallet) failure(result)
    else this.insertUserWalletWithoutValidation(userWallet)
  }

  override suspend fun deleteUserWalletByIdentifier(
    identifier: UUID
  ): Int {
    return concurrentTransaction {
      UserWalletTable.deleteWhere {
        this.identifier eq identifier
      }
    }
  }

  override suspend fun truncateUserWallets(): Int {
    return concurrentTransaction {
      UserWalletTable.deleteAll()
    }
  }

  override suspend fun countUserWallets(): Long {
    return concurrentTransaction(readOnly = true) {
      UserWalletTable
        .select(UserWalletTable.identifier)
        .count()
    }
  }
}