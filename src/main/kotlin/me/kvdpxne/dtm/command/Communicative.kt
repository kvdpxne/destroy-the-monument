package me.kvdpxne.dtm.command

interface Communicative {

  /**
   * Forces a message to be sent to the performer.
   */
  fun sendMessage(message: String)

  /**
   * Sends a message to the performer if possible.
   */
  fun sendMessage(message: () -> String) {
    this.sendMessage(message())
  }

  /**
   * Forces one or more messages to be sent to the performer.
   */
  fun sendMessages(vararg messageArray: String)
}