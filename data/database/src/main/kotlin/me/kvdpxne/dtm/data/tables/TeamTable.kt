package me.kvdpxne.dtm.data.tables

import java.util.UUID
import me.kvdpxne.dtm.data.Efn
import me.kvdpxne.dtm.data.En
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

/**
 * @since 0.1.0
 */
object TeamTable : Table(En.TEAM) {

  /**
   * @since 0.1.0
   */
  val identifier: Column<UUID> = this.uuid(Efn.IDENTIFIER)
    .uniqueIndex()

  /**
   * @since 0.1.0
   */
  val name: Column<String> = this.varchar(Efn.NAME, 24)
    .uniqueIndex()

  /**
   * @since 0.1.0
   */
  val colorOfArmor: Column<String> = this.varchar(Efn.COLOR_OF_ARMOR, 7)

  /**
   * @since 0.1.0
   */
  val colorOfProfession: Column<String> = this.varchar(Efn.COLOR_OF_PROFESSION, 7)

  /**
   * @since 0.1.0
   */
  val colorOnChat: Column<String> = this.varchar(Efn.COLOR_ON_CHAT, 7)

  /**
   * @since 0.1.0
   */
  val colorOnPlayerList: Column<String?> = this.varchar(Efn.COLOR_ON_PLAYER_LIST, 7)
    .nullable()

  /**
   * @since 0.1.0
   */
  override val primaryKey: PrimaryKey
    get() = PrimaryKey(this.identifier)
}