package me.kvdpxne.dtm.command

/**
 * An exception class for signaling errors related to command execution.
 *
 * This exception extends `RuntimeException` and is intended to be thrown when
 * unexpected situations arise during the processing of commands.
 *
 * It provides a clear message to aid in debugging and identifying the root
 * cause of the issue.
 *
 * @since 0.1.0
 */
class CommandException(message: String) : RuntimeException(message)