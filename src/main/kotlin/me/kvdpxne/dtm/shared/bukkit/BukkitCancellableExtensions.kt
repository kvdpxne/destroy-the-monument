package me.kvdpxne.dtm.shared.bukkit

import org.bukkit.event.Cancellable

fun Cancellable.cancel() {
  this.isCancelled = true
}