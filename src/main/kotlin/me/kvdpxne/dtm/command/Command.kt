package me.kvdpxne.dtm.command

open class Command(
  // @formatter:off
  val name          : String,
  val description   : String             = "",
  val usage         : String             = "",
  val aliases       : Array<out String>  = emptyArray(),
  val permission    : String             = "",
  val executionType : ExecutionPlaceType = ExecutionPlaceType.EVERYWHERE,
  val handler       : CommandHandler<Any>,
  var parent        : Command?           = null
  // @formatter:on
) {

  fun matches(
    name: String
  ): Boolean {
    if (this.name.equals(name, true)) {
      return true
    }

    return this.aliases.any {
      it.equals(name, true)
    }
  }
}