package me.kvdpxne.dtm.command

interface Communicative {

  /**
   * Forces a message to be sent to the performer.
   */
  fun sendMessage(message: String)

  /**
   * Forces one or more messages to be sent to the performer.
   */
  fun sendMessages(messages: Array<out String>)

  /**
   * Sends a message to the performer if possible.
   */
  fun sendMessage(message: () -> String)

  /**
   * Sends one or more messages to the performer if possible.
   */
  fun sendMessages(messages: () -> Array<out String>)
}