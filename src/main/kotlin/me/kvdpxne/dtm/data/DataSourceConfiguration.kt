package me.kvdpxne.dtm.data

import org.ktorm.database.Database

internal val database = Database.connect(
  url = "jdbc:sqlite:run/fs.db",
  driver = "org.sqlite.JDBC"
)