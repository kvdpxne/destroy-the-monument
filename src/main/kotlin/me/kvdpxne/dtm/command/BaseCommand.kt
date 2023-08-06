package me.kvdpxne.dtm.command

import me.kvdpxne.dtm.command.overt.HelpCommand
import me.kvdpxne.dtm.command.overt.JoinCommand
import me.kvdpxne.dtm.command.overt.KitCommand
import me.kvdpxne.dtm.command.overt.LeaveCommand
import me.kvdpxne.dtm.command.restricted.*

object BaseCommand : Command("dtm"), Executor<Performer> {

  val nameSubCommandMap: Map<String, Executor<*>>

  init {
    // TODO XDDD
    nameSubCommandMap = buildMap {
      this["help"] = HelpCommand
      this["join"] = JoinCommand
      this["kit"] = KitCommand
      this["leave"] = LeaveCommand

      this["addarena"] = AddArenaCommand
      this["addmonument"] = AddMonumentCommand
      this["addteam"] = AddTeamCommand
      this["createarena"] = CreateArenaCommand
      this["creategame"] = CreateGameCommand
      this["setspawnpoint"] = SetSpawnPointCommand
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