package me.kvdpxne.dtm.shared.minecraft.bukkit

import org.bukkit.event.Cancellable

fun Cancellable.cancel() {
  this.isCancelled = true
}