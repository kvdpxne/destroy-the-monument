package me.kvdpxne.dtm.data.sources

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jetbrains.exposed.sql.Database

/**
 * @since 0.1.0
 */
object DatabasesConfiguration {

  val hikariDataSource: HikariDataSource by lazy {
    HikariDataSource(
      HikariConfig().apply {
        this.jdbcUrl = "jdbc:postgresql://localhost:5432/development_destroy_the_monument"
        this.username = "postgres"
        this.password = this.username

        this.schema = "public"
      }
    )
  }

  /**
   * @since 0.1.0
   */
  val main: Database by lazy {
    Database.connect(this.hikariDataSource)

    Database.connect(
      "jdbc:postgresql://localhost:5432/development_destroy_the_monument",
      "org.postgresql.Driver",
      "postgres",
      "postgres"
    )
  }
}