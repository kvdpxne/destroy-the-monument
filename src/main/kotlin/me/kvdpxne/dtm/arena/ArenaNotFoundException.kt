package me.kvdpxne.dtm.arena

open class ArenaNotFoundException(
  // @formatter:off
  message: String     = "",
  cause  : Throwable? = null
  // @formatter:on
) : ArenaException(message, cause) {

  companion object {

    private const val serialVersionUID: Long = 1656065159401641722L
  }
}