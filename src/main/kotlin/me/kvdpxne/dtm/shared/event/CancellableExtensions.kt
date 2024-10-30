package me.kvdpxne.dtm.shared.event

import org.bukkit.event.Cancellable

/**
 * Extension function for the `Cancellable` interface, allowing easy
 * cancellation of events.
 *
 * Sets `isCancelled` to `true`, marking the event as cancelled.
 *
 * @since 0.1.0
 */
fun Cancellable.cancel() {
  this.isCancelled = true
}