package me.kvdpxne.dtm.command

import me.kvdpxne.dtm.command.restricted.WandCommand

object BaseCommand : Command("dtm"), Executor<Performer> {

  private val nameSubCommandMap: Map<String, Executor<*>>

  init {
    nameSubCommandMap = buildMap {
      this["wand"] = WandCommand
    }
  }

  override fun execute(performer: Performer, parameter: Parameter) {
    val name = parameter.asText()
    val commandExecutor = nameSubCommandMap[name] ?: return
    commandExecutor.execute(performer, parameter)
  }
}