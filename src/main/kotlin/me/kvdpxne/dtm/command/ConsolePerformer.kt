package me.kvdpxne.dtm.command

interface ConsolePerformer : Performer {

  /**
   * @param permission
   *
   * @since 0.1.0
   */
  override fun hasPermission(permission: String): Boolean {
    return true
  }
}