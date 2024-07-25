package me.kvdpxne.dtm.shared.bukkit

import org.bukkit.block.Block

fun Block.isRich(): Boolean {
  return this.type.isRich()
}

fun Block.isNature(): Boolean {
  return this.type.isNature()
}

fun Block.hasInventory(): Boolean {
  return this.type.hasInventory()
}

fun Block.isMonument(): Boolean {
  return this.type.isMonument()
}