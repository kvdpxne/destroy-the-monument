package me.kvdpxne.dtm.game

import org.bukkit.Location
import org.bukkit.World

fun SpawnPoint.toLocation(world: World) = Location(world, x, y, z, yaw, pitch)