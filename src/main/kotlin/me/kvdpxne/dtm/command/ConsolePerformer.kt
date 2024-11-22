package me.kvdpxne.dtm.command

import me.kvdpxne.dtm.translation.receiver.DefaultReceiver

interface ConsolePerformer : Performer, DefaultReceiver {

  /**
   * @param permission
   *
   * @since 0.1.0
   */
  override fun hasPermission(permission: String): Boolean {
    return true
  }
}