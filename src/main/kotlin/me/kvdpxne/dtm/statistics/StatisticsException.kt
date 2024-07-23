package me.kvdpxne.dtm.statistics

/**
 * A custom exception class for handling errors related to statistics objects.
 *
 * This exception extends `RuntimeException` and is intended to be used for
 * signaling errors that may occur during the processing or manipulation of
 * statistics data. The exception takes a message string as a constructor
 * argument to provide more context about the error.
 *
 * By using a custom exception class, developers can clearly distinguish
 * between general runtime exceptions and those specifically related to
 * statistics operations. This can aid in easier debugging and error handling.
 *
 * @since 0.1.0
 */
class StatisticsException(message: String) : RuntimeException(message)