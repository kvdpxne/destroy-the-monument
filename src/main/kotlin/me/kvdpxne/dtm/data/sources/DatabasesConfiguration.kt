package me.kvdpxne.dtm.data.sources

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.DatabaseConfig
import org.jetbrains.exposed.sql.vendors.PostgreSQLDialect

/**
 * @since 0.1.0
 */
object DatabasesConfiguration {

  /**
   * @since 0.1.0
   */
  val main: Database = Database.connect(
    url = "jdbc:postgresql://localhost:5432/postgres",
    driver = "org.postgresql.Driver",
    user = "postgres",
    password = "postgres",
    databaseConfig = DatabaseConfig {
////        this.defaultIsolationLevel = Connection.TRANSACTION_NONE
      this.explicitDialect = PostgreSQLDialect()
    }
  )
}