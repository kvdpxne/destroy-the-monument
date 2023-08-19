package me.kvdpxne.dtm.command

import me.kvdpxne.dtm.command.overt.HelpCommand
import me.kvdpxne.dtm.command.overt.JoinCommand
import me.kvdpxne.dtm.command.overt.KitCommand
import me.kvdpxne.dtm.command.overt.LeaveCommand
import me.kvdpxne.dtm.command.restricted.AddArenaCommand
import me.kvdpxne.dtm.command.restricted.AddMonumentCommand
import me.kvdpxne.dtm.command.restricted.AddTeamCommand
import me.kvdpxne.dtm.command.restricted.CreateArenaCommand
import me.kvdpxne.dtm.command.restricted.CreateGameCommand
import me.kvdpxne.dtm.command.restricted.CreateTeamCommand
import me.kvdpxne.dtm.command.restricted.SetArenaMapCommand
import me.kvdpxne.dtm.command.restricted.SetSpawnPointCommand
import me.kvdpxne.dtm.command.restricted.StartCommand
import me.kvdpxne.dtm.command.restricted.StopCommand
import me.kvdpxne.dtm.command.restricted.TeleportBackCommand
import me.kvdpxne.dtm.command.restricted.TeleportCommand
import me.kvdpxne.dtm.command.restricted.WandCommand

object BaseCommand : Command("dtm"), Executor<Performer> {

  val nameSubCommandMap: Map<String, Executor<*>>

  init {
    // TODO XDDD
    nameSubCommandMap = buildMap {
      this["help"] = HelpCommand
      this["join"] = JoinCommand
      this["kit"] = KitCommand
      this["leave"] = LeaveCommand

      this["addArena"] = AddArenaCommand
      this["addMonument"] = AddMonumentCommand
      this["addTeam"] = AddTeamCommand
      this["createArena"] = CreateArenaCommand
      this["createGame"] = CreateGameCommand
      this["createTeam"] = CreateTeamCommand
      this["setArenaMap"] = SetArenaMapCommand
      this["setSpawnPoint"] = SetSpawnPointCommand
      this["start"] = StartCommand
      this["stop"] = StopCommand
      this["teleportBack"] = TeleportBackCommand
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
    val executor = nameSubCommandMap.entries.find {
      it.key.equals(name, true)
    }?.value

    if (null == executor) {
      performer.sendMessage("not found command called $name")
      return
    }

    executor.execute(performer, parameter.asParameter())
  }
}