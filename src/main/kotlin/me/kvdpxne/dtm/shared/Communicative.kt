package me.kvdpxne.dtm.shared

/**
 * Defines an interface for objects capable of sending messages to a recipient.
 *
 * This interface supports sending single or multiple messages, either
 * directly or lazily, allowing flexibility in how messages are generated
 * and delivered.
 *
 * @since 0.1.0
 */
interface Communicative {

  /**
   * Sends a single message to the recipient immediately.
   *
   * @param message The message to send.
   *
   * @since 0.1.0
   */
  fun sendMessage(
    message: String
  )

  /**
   * Sends a single message to the recipient, generated lazily by the
   * provided lambda function.
   *
   * @param message A lambda function that generates the message to send.
   *
   * @since 0.1.0
   */
  fun sendMessage(
    message: () -> String
  ) {
    this.sendMessage(message())
  }

  /**
   * Sends multiple messages to the recipient immediately.
   *
   * @param messages The array of messages to send.
   *
   * @since 0.1.0
   */
  fun sendMessages(
    messages: Array<out String>
  ) {
    for (message: String in messages) {
      this.sendMessage(message)
    }
  }

  /**
   * Sends multiple messages to the recipient, generated lazily by the
   * provided lambda function.
   *
   * @param messages A lambda function that generates an array of messages
   *                 to send.
   *
   * @since 0.1.0
   */
  fun sendMessages(
    messages: () -> Array<out String>
  ) {
    this.sendMessages(messages())
  }
}