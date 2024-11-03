package me.kvdpxne.dtm.data.sources

import org.jetbrains.exposed.jdbc.ExposedConnectionImpl
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
  val main: Database by lazy {
    Database.connect(
      "jdbc:postgresql://localhost:5432/postgres",
      "org.postgresql.Driver",
      "postgres",
      "postgres",
      databaseConfig = DatabaseConfig {
        this.explicitDialect = PostgreSQLDialect()
      },
      connectionAutoRegistration = ExposedConnectionImpl()
    )
  }
}