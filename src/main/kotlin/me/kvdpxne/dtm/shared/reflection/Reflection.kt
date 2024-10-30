package me.kvdpxne.dtm.shared.reflection

import org.bukkit.Bukkit

object Reflection {

  val NMS_PACKAGE: String
  val CRAFTBUKKIT_PACKAGE: String

  init {
    val versionPackageName: String = Bukkit.getServer().javaClass
      .`package`.name.split(".")[3]

    NMS_PACKAGE = "net.minecraft.server.$versionPackageName"
    CRAFTBUKKIT_PACKAGE = "org.bukkit.craftbukkit.$versionPackageName"
  }

  fun getNmsClass(path: String): Class<*> {
    return Class.forName("$NMS_PACKAGE.$path")
  }

  fun getCraftBukkitClass(path: String): Class<*> {
    return Class.forName("$CRAFTBUKKIT_PACKAGE.$path")
  }
}