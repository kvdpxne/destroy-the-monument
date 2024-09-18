package me.kvdpxne.dtm.user

import java.util.UUID
import me.kvdpxne.dtm.shared.ancillary.Communicative
import me.kvdpxne.dtm.command.Performer
import me.kvdpxne.dtm.shared.ancillary.Identifiable
import org.bukkit.entity.Player

interface LocalUserPerformer : Identifiable<UUID>, Communicative, Performer {

  override val identifier: UUID

  val user: LocalUser

  override val name: String

  val player: Player?

  val isOnline: Boolean

  val isOperator: Boolean

  override fun hasPermission(permission: String): Boolean
}