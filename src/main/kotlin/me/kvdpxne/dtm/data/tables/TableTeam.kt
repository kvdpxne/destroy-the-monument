package me.kvdpxne.dtm.data.tables

import org.bukkit.ChatColor
import org.bukkit.DyeColor
import org.ktorm.schema.Table
import org.ktorm.schema.enum
import org.ktorm.schema.text

object TableTeam : Table<Nothing>("team") {

  val name = text("name")
  val colorInChat = enum<ChatColor>("color_in_chat")
  val professionColor = enum<ChatColor>("profession_color")
  val dyeColor = enum<DyeColor>("dye_color")

  val identifier = text("identifier").primaryKey()
}