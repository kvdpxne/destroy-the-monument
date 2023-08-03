package me.kvdpxne.dtm.command

import me.kvdpxne.dtm.command.overt.HelpCommand
import me.kvdpxne.dtm.command.overt.JoinCommand
import me.kvdpxne.dtm.command.overt.KitCommand
import me.kvdpxne.dtm.command.overt.LeaveCommand
import me.kvdpxne.dtm.command.restricted.*

object BaseCommand : Command("dtm"), Executor<Performer> {

  val nameSubCommandMap: Map<String, Executor<*>>

  init {
    nameSubCommandMap = buildMap {
      this["help"] = HelpCommand
      this["join"] = JoinCommand
      this["kit"] = KitCommand
      this["leave"] = LeaveCommand

      this["create"] = CreateCommand
      this["set"] = SetCommand
      this["start"] = StartCommand
      this["teleportback"] = TeleportBackCommand
      this["teleport"] = TeleportCommand
      this["wand"] = WandCommand
    }
  }

  override fun execute(performer: Performer, parameter: Parameter) {
    if (parameter.isEmpty()) {
      performer.sendMessage("0 args")
      return
    }

    val name = parameter.asText()
    val executor = nameSubCommandMap[name]

    if (null == executor) {
      performer.sendMessage("not found command called $name")
      return
    }

    executor.execute(performer, parameter.asParameter())
  }
}