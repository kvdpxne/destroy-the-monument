package me.kvdpxne.dtm.data.source

import org.ktorm.database.Database

val database = Database.connect(
  url = "jdbc:sqlite:run/fs.db",
  driver = "org.sqlite.JDBC"
)