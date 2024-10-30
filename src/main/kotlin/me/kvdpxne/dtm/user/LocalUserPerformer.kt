package me.kvdpxne.dtm.user

import java.util.UUID
import me.kvdpxne.dtm.command.Performer
import me.kvdpxne.dtm.shared.Communicative
import me.kvdpxne.dtm.shared.Identifiable
import org.bukkit.entity.Player

interface LocalUserPerformer : Identifiable<UUID>, Communicative, Performer {

  val user: LocalUser

  val player: Player?

  val isOnline: Boolean

  val isOperator: Boolean
}