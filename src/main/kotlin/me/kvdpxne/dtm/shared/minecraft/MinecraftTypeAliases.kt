package me.kvdpxne.dtm.shared.minecraft

import net.minecraft.server.v1_7_R4.EnumClientCommand
import net.minecraft.server.v1_7_R4.NBTTagCompound
import net.minecraft.server.v1_7_R4.NBTTagList
import net.minecraft.server.v1_7_R4.PacketPlayInClientCommand
import org.bukkit.craftbukkit.v1_7_R4.CraftWorld
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer
import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftItemStack

typealias BukkitItemStack = CraftItemStack
typealias BukkitPlayer = CraftPlayer
typealias BukkitWorld = CraftWorld

typealias MinecraftPacketPlayInClientCommand = PacketPlayInClientCommand
typealias MinecraftNBTTagCompound = NBTTagCompound
typealias MinecraftNBTTagList = NBTTagList
typealias MinecraftEnumClientCommand = EnumClientCommand