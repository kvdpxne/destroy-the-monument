package me.kvdpxne.dtm.command.overt

import me.kvdpxne.dtm.command.BaseCommand
import me.kvdpxne.dtm.command.Executor
import me.kvdpxne.dtm.command.Parameter
import me.kvdpxne.dtm.command.Performer

object HelpCommand : Executor<Performer> {

  override fun execute(performer: Performer, parameter: Parameter) {
    BaseCommand.nameSubCommandMap.keys
      .sorted()
      .forEach { performer.sendMessage(it) }
  }
}