package me.kvdpxne.dtm.data.transactions

import kotlinx.coroutines.Dispatchers
import me.kvdpxne.dtm.data.sources.DatabasesConfiguration
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

/**
 * @param database
 * @param body
 *
 * @since 0.1.0
 */
suspend fun <T> concurrentTransaction(
  database: Database? = DatabasesConfiguration.main,
  body: suspend () -> T
): T {
  return newSuspendedTransaction(Dispatchers.IO, database) {
    body()
  }
}