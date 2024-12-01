package me.kvdpxne.dtm.shared.player

import com.comphenix.protocol.PacketType
import com.comphenix.protocol.ProtocolLibrary
import com.comphenix.protocol.ProtocolManager
import com.comphenix.protocol.events.PacketContainer
import com.comphenix.protocol.wrappers.EnumWrappers
import java.lang.reflect.Constructor
import java.lang.reflect.Field
import java.lang.reflect.Method
import me.kvdpxne.dtm.configuration.GeneralConfiguration
import me.kvdpxne.dtm.shared.Tick
import me.kvdpxne.dtm.shared.item.ItemsClipboard
import me.kvdpxne.dtm.shared.reflection.Reflection
import me.kvdpxne.dtm.shared.text.colorize
import me.kvdpxne.dtm.shared.world.WorldsHolder
import me.kvdpxne.dtm.user.LocalUser
import me.kvdpxne.dtm.user.LocalUserManager
import me.kvdpxne.dtm.user.UserNotFoundException
import org.bukkit.Bukkit
import org.bukkit.GameMode
import org.bukkit.Location
import org.bukkit.World
import org.bukkit.entity.Player
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause
import org.bukkit.inventory.ItemStack
import org.bukkit.potion.PotionEffect

fun Player.reset() {
  this.inventory.also {
    it.clear()
    it.armorContents = arrayOfNulls(it.armorContents.size)
  }

  for (potionEffect: PotionEffect in this.activePotionEffects) {
    this.removePotionEffect(potionEffect.type)
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
}

/**
 * @since 0.1.0
 */
fun Player.moveTo(
  location: Location,
  cause: TeleportCause = TeleportCause.PLUGIN
) {
  this.teleport(location, cause)
}

/**
 * @since 0.1.0
 */
fun Player.moveToLobby() {
  //
  var world: World? = WorldsHolder.lobbyWorld

  if (null == world) {
    //
    world = Bukkit.getWorlds().firstOrNull()
      // Prawdopodobnie
      //
      ?: throw NullPointerException("The world is not loaded!")
  }

  //
  this.moveTo(world.spawnLocation)
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
 * @since 0.1.0
 */
fun Player.fill(item: ItemStack) {
  repeat(36) { i: Int ->
    this.inventory.setItem(i, item)
  }
}

/**
 * @since 0.1.0
 */
fun Player.equipItemsOfGameSelection() {
  this.setItem(0, ItemsClipboard.ITEM_GAME_JOIN)
}

/**
 * @since 0.1.0
 */
fun Player.equipItemsOfTeamSelection() {
  this.setItem(0, ItemsClipboard.ITEM_TEAM_SELECT)
  this.setItem(1, ItemsClipboard.ITEM_PROFESSION_SELECT)
  this.setItem(8, ItemsClipboard.ITEM_GAME_LEAVE)
}

fun Player.equipItemsOf() {
  this.setItem(8, ItemsClipboard.ITEM_TEAM_LEAVE)
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

fun Player.resetExperienceBarLevel() {
  this.level = 0
}

fun Player.sendPacket(
  packet: Any
) {
  // org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer
  val craftPlayerClass: Class<*> = Reflection.getCraftBukkitClass("entity.CraftPlayer")
  val craftPlayer: Any = craftPlayerClass.cast(this)
  val getHandleMethod: Method = craftPlayerClass.getMethod("getHandle")

  // net.minecraft.server.v1_7_R4.EntityPlayer
  val entityPlayer: Any = getHandleMethod.invoke(craftPlayer)
  val playerConnectionField: Field = entityPlayer.javaClass.getField("playerConnection")
  val playerConnection: Any = playerConnectionField.get(entityPlayer)

  val aMethod: Method = playerConnection.javaClass.getMethod("sendPacket", Reflection.getMinecraftClass("Packet"))

  aMethod.invoke(playerConnection, packet)
}

fun Player.respawn() {
  if (!this.isDead || !this.isOnline) {
    return
  }

  if (GeneralConfiguration.USE_PROTOCOL_LIB) {
    val protocolManager: ProtocolManager = ProtocolLibrary.getProtocolManager()
    val packet: PacketContainer = protocolManager.createPacket(PacketType.Play.Client.CLIENT_COMMAND)

    packet.clientCommands.write(0, EnumWrappers.ClientCommand.PERFORM_RESPAWN)
    protocolManager.recieveClientPacket(this, packet)
    return
  }

  // net.minecraft.server.v1_7_R4.EnumClientCommand
  val enumClientCommandClass: Class<*> = Reflection.getMinecraftClass("EnumClientCommand")
  val enumClientCommandField: Field = enumClientCommandClass.getDeclaredField("PERFORM_RESPAWN")
  val enumClientCommand: Any = enumClientCommandField.get(null)

  // net.minecraft.server.v1_7_R4.PacketPlayInClientCommand
  val packetPlayInClientCommandClass: Class<*> = Reflection.getMinecraftClass("PacketPlayInClientCommand")
  val packetPlayInClientCommandConstructor: Constructor<*> = packetPlayInClientCommandClass.getConstructor(enumClientCommandClass)
  val packetPlayInClientCommand: Any = packetPlayInClientCommandConstructor.newInstance(enumClientCommand)

  // org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer
  val craftPlayerClass: Class<*> = Reflection.getCraftBukkitClass("entity.CraftPlayer")
  val craftPlayer: Any = craftPlayerClass.cast(this)
  val getHandleMethod: Method = craftPlayerClass.getMethod("getHandle")

  // net.minecraft.server.v1_7_R4.EntityPlayer
  val entityPlayer: Any = getHandleMethod.invoke(craftPlayer)
  val playerConnectionField: Field = entityPlayer.javaClass.getField("playerConnection")
  val playerConnection: Any = playerConnectionField.get(entityPlayer)
  val aMethod: Method = playerConnection.javaClass.getMethod("a", packetPlayInClientCommandClass)

  aMethod.invoke(playerConnection, packetPlayInClientCommand)
}

fun Player.send(
  fadeIn: Tick,
  show: Tick,
  fadeOut: Tick,
  title: String,
  subtitle: String? = null
) {
  val chatBaseComponentClass = Reflection.getMinecraftClass("IChatBaseComponent")
  val chatSerializerClass = chatBaseComponentClass.declaredClasses[0]
  val serializeMethod = Reflection.getMethod(chatSerializerClass, "a", chatBaseComponentClass, arrayOf(String::class.java))

  //
  val packetPlayOutTitleClass: Class<*> = Reflection.getMinecraftClass("PacketPlayOutTitle")
  val titleActionClass = packetPlayOutTitleClass.declaredClasses[0]

  // Times
  val times = Reflection.getConstructor(
    packetPlayOutTitleClass,
    Int::class.java,
    Int::class.java,
    Int::class.java
  ).invoke(fadeIn, show, fadeOut)

  this.sendPacket(times)

  if (!subtitle.isNullOrBlank()) {
    val subtitlePacket = Reflection.getConstructor(
      packetPlayOutTitleClass,
      titleActionClass,
      chatBaseComponentClass
    ).invoke(
      Reflection.getField(titleActionClass, "SUBTITLE").get(),
      serializeMethod.invoke(null, "{\"text\": \"${subtitle.colorize}\"}")
    )

    this.sendPacket(subtitlePacket)
  }

  val titlePacket = Reflection.getConstructor(
    packetPlayOutTitleClass,
    titleActionClass,
    chatBaseComponentClass
  ).invoke(
    Reflection.getField(titleActionClass, "TITLE").get(),
    serializeMethod.invoke(null, "{\"text\": \"${title.colorize}\"}")
  )

  this.sendPacket(titlePacket)
}

fun Player.sendM(
  content: String,
  position: Byte = MessagePosition.CHAT
) {
  val chatBaseComponentClass = Reflection.getMinecraftClass("IChatBaseComponent")
  val chatSerializerClass = chatBaseComponentClass.declaredClasses[0]
  val serializeMethod = Reflection.getMethod(chatSerializerClass, "a", chatBaseComponentClass, arrayOf(String::class.java))

  //
  val packetPlayOutTitleClass: Class<*> = Reflection.getMinecraftClass("PacketPlayOutChat")
  val packetPlayOutTitle = Reflection
    .getConstructor(packetPlayOutTitleClass, chatBaseComponentClass, Byte::class.java)
    .invoke(
      serializeMethod.invoke(null, """{"text": "${content.colorize}"}"""),
      position
    )

  this.sendPacket(packetPlayOutTitle)
}

/**
 * Retrieves the [LocalUser] associated with this [Player].
 *
 * This property provides a way to access the [LocalUser] instance that
 * corresponds to the current [Player] within the game's user management
 * system. It leverages the [LocalUserManager] to perform a lookup based on
 * the player's unique identifier.
 *
 * @throws UserNotFoundException if no [LocalUser] is found for the player's
 *         unique identifier. This exception indicates that the player is not
 *         currently registered as a local user in the system, which might
 *         occur if they have not logged in or have not been added
 *         to the [LocalUserManager].
 *
 * @since 0.1.0
 */
val Player.localUser: LocalUser
  get() = LocalUserManager.findUserByIdentifier(this.uniqueId)