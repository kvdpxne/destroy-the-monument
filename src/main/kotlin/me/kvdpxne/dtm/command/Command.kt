package me.kvdpxne.dtm.command

open class Command(
  val name: String,
  val description: String = "",
  val usage: String = "",
  val aliases: Array<out String> = emptyArray(),
  val permission: String = "",
  val executionType: ExecutionPlaceType = ExecutionPlaceType.EVERYWHERE
) {

  /**
   * @param content The name or alias of the command.
   */
  fun contains(content: String): Boolean {
    //
    //
    if (content.isBlank()) {
      return false
    }

    //
    if (this.name.equals(content, true)) {
      return true
    }

    //
    return this.aliases.any {
      it.equals(content, true)
    }
  }


}