package me.kvdpxne.dtm.data.transactions

import kotlinx.coroutines.Dispatchers
import me.kvdpxne.dtm.data.sources.DatabasesConfiguration
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.Transaction
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

/**
 * Executes a database transaction concurrently within a coroutine.
 *
 * This function ensures that the provided database object is not null before
 * proceeding with the transaction. If the database is null, a descriptive
 * [NullPointerException] is thrown to indicate a configuration issue.
 *
 * The transaction is executed using [newSuspendedTransaction] on the
 * [Dispatchers.IO] dispatcher for efficient handling of I/O operations.
 *
 * @param database The database object to use for the transaction.
 * @param body The suspending lambda containing the transaction logic.
 * @throws NullPointerException if the [database] is null.
 *
 * @since 0.1.0
 */
suspend fun <T> concurrentTransaction(
  database: Database? = DatabasesConfiguration.main,
  readOnly: Boolean = false,
  body: suspend Transaction.() -> T
): T {
  requireNotNull(database) {
    "Database cannot be null for concurrent transaction."
  }

  return newSuspendedTransaction(
    Dispatchers.IO, database, readOnly = readOnly, statement = body
  )
}