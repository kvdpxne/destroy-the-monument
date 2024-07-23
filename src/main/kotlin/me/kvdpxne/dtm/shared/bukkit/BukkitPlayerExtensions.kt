package me.kvdpxne.dtm.shared.bukkit

import me.kvdpxne.dtm.shared.minecraft.BukkitPlayer
import me.kvdpxne.dtm.shared.minecraft.MinecraftEnumClientCommand
import me.kvdpxne.dtm.shared.minecraft.MinecraftPacketPlayInClientCommand
import org.bukkit.Bukkit
import org.bukkit.GameMode
import org.bukkit.Location
import org.bukkit.entity.Player
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause
import org.bukkit.inventory.ItemStack

fun Player.hardClean() {
  this.inventory.also {
    it.clear()
    it.armorContents = arrayOfNulls(it.armorContents.size)
  }

  this.activePotionEffects.forEach {
    this.removePotionEffect(it.type)
  }

  this.resetMaxHealth()
  this.setHealth(20.0)
  this.isHealthScaled = false
  this.foodLevel = 20
  this.exhaustion = 0.0F
  this.saturation = 1000.0F
  this.exp = 0.0F
  this.totalExperience = 0
  this.level = 0
//  this.maximumAir
//  this.remainingAir

  // Damage
  this.lastDamageCause = null
  this.maximumNoDamageTicks = 20
  this.noDamageTicks = 20
  this.fireTicks = 0
  this.fallDistance = 0.0F

  // Abilities
  this.gameMode = GameMode.SURVIVAL
  this.isSneaking = false
  this.isSprinting = false
  this.isFlying = false
  this.allowFlight = false
  this.flySpeed = 0.1F
  this.walkSpeed = 0.2F

  //
  if (this.isInsideVehicle) {
    this.leaveVehicle()
  }
}

fun Player.moveTo(location: Location) {
  this.teleport(location, TeleportCause.PLUGIN)
}

fun Player.moveToDefaultSpawnPosition() {
  //
  val position = Bukkit.getWorlds().firstOrNull()?.spawnLocation
    ?: this.world.spawnLocation

  //
  this.moveTo(position)
}

/**
 * @since 0.1.0
 */
fun Player.setItem(
  index: Int,
  itemStack: ItemStack,
  force: Boolean = true
) {
  if (force || null != this.inventory.getItem(index)) {
    this.inventory.setItem(index, itemStack)
  }
}

/**
 * Fills a player's experience bar to its maximum level.
 *
 * This function sets the player's experience level to `1.0F`, which effectively
 * fills the experience bar to its maximum.
 *
 * @receiver The player whose experience bar should be filled.
 * @since 0.1.0
 */
fun Player.fillExperienceBar() {
  this.exp = 1.0F
}

/**
 * @since 0.1.0
 */
fun Player.resetExperienceBar() {
  this.exp = 0.0F
}

fun Player.respawn() {
  this as BukkitPlayer
  if (0.0 < this.health || !this.isOnline) {
    return
  }

  val packet = MinecraftPacketPlayInClientCommand(MinecraftEnumClientCommand.PERFORM_RESPAWN)
  this.handle.playerConnection.a(packet)
}