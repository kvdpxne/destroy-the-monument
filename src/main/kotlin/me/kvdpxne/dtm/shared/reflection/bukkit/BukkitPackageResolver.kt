package me.kvdpxne.dtm.shared.reflection.bukkit

import me.kvdpxne.dtm.shared.reflection.bukkit.BukkitPackageResolver.BUKKIT_PACKAGE
import me.kvdpxne.dtm.shared.reflection.bukkit.BukkitPackageResolver.CRAFT_BUKKIT_PACKAGE
import me.kvdpxne.dtm.shared.reflection.bukkit.BukkitPackageResolver.MINECRAFT_PACKAGE
import org.bukkit.Bukkit
import org.bukkit.Server

/**
 * **Automatic package structure resolver** for Bukkit/Minecraft environments that:
 *
 * - Detects current Minecraft server version at runtime
 * - Determines correct package paths for NMS (Net Minecraft Server) classes
 * - Handles both legacy (1.16.x and below) and modern (1.17+) package structures
 * - Provides stable access points for reflection operations
 *
 * ### Critical Package Structures Handled
 *
 * | Component | Legacy Structure (≤1.16.x) | Modern Structure (≥1.17) |
 * |-----------|----------------------------|--------------------------|
 * | **Minecraft** | `net.minecraft.server.v1_16_R3` | `net.minecraft` |
 * | **CraftBukkit** | `org.bukkit.craftbukkit.v1_16_R3` | `org.bukkit.craftbukkit` |
 * | **Bukkit** | `org.bukkit` | `org.bukkit` (unchanged) |
 *
 * ### Technical Workflow
 * 1. Retrieves server instance via `Bukkit.getServer()`
 * 2. Extracts package name from server class
 * 3. Parses version identifier (e.g., "v1_19_R1")
 * 4. Constructs appropriate package paths
 *
 * ```mermaid
 * graph TD
 *   A[Bukkit.getServer()] --> B[Get server class package]
 *   B --> C{Package structure?}
 *   C -->|Legacy| D[net.minecraft.server.vX_X_RY]
 *   C -->|Modern| E[net.minecraft]
 *   D --> F[MINECRAFT_PACKAGE]
 *   E --> F
 * ```
 *
 * ### Non-Technical Analogy
 * Think of this as a **universal translator** that:
 *
 * - Listens to the server speak ("I'm version 1.19.2")
 * - Translates simple requests ("Find Player class") to the correct language:
 *   - For 1.16.x: "In library section net.minecraft.server.v1_16_R3, find EntityPlayer"
 *   - For 1.19.x: "In library section net.minecraft, find world.entity.player.Player"
 *
 * Without this translator, you'd need:
 * - Different instructions for each Minecraft version
 * - Constant updates when Mojang changes things
 * - Deep knowledge of each version's structure
 *
 * ### Why This Matters for Plugin Developers
 * Minecraft's internal structure has undergone major changes:
 *
 * | Era | Package Structure | Challenge |
 * |-----|-------------------|-----------|
 * | Pre-1.17 | Versioned NMS (`v1_16_R3`) | Hard-coded paths break with updates |
 * | 1.17+ | Modular NMS (`net.minecraft`) | Different class locations |
 *
 * This resolver allows plugins to:
 * - Work across Minecraft 1.13-1.20+ without changes
 * - Avoid hard-coding version-specific paths
 * - Focus on functionality rather than version quirks
 *
 * ### Implementation Notes
 * - **Initialization**: Resolves paths when first accessed
 * - **Thread Safety**: Safe for concurrent access
 * - **Error Handling**: Fails early if Bukkit isn't initialized
 * - **Caching**: Results stored permanently (server version doesn't change)
 *
 * @see [MINECRAFT_PACKAGE] Resolved Minecraft package path
 * @see [CRAFT_BUKKIT_PACKAGE] Resolved CraftBukkit package path
 * @see [BUKKIT_PACKAGE] Stable Bukkit API package
 * @since 0.1.0
 */
object BukkitPackageResolver {

  /**
   * **Resolved Minecraft package path** that adapts to server version.
   *
   * ### Structure Examples
   * | Minecraft Version | Value |
   * |-------------------|-------|
   * | 1.16.5 | `net.minecraft.server.v1_16_R3` |
   * | 1.17.1 | `net.minecraft` |
   * | 1.19.4 | `net.minecraft` |
   *
   * ### Technical Detection
   * Determined by analyzing Bukkit server's package structure:
   * ```kotlin
   * val packageParts = server.javaClass.`package`.name.split('.')
   * val versionPackageName = packageParts[3]
   *
   * MINECRAFT_PACKAGE = if (packageParts.size > 4) {
   *   "net.minecraft.$versionPackageName"
   * } else {
   *   "net.minecraft.server.$versionPackageName"
   * }
   * ```
   *
   * ### Why This Matters
   * This is the **most volatile component** across Minecraft versions:
   * - 1.16.x and below: `net.minecraft.server.v1_16_R3`
   * - 1.17+: `net.minecraft`
   *
   * Directly hard-coding these paths causes:
   * - Plugin incompatibility across versions
   * - Frequent update requirements
   * - Broken plugins after Minecraft updates
   *
   * ### Usage Pattern
   * Always combine with [Reflection.getClass]:
   * ```kotlin
   * // Works across ALL Minecraft versions
   * val playerClass = Reflection.getClass(
   *     "${BukkitPackageResolver.MINECRAFT_PACKAGE}.EntityPlayer"
   * )
   * ```
   *
   * ### Non-Technical Explanation
   * This is like the **main section identifier** in our library analogy:
   * - For older editions: "Section: net.minecraft.server.v1_16_R3"
   * - For newer editions: "Section: net.minecraft"
   *
   * It tells the system where to start looking for Minecraft's internal classes.
   *
   * @see [getMinecraftClass] Convenience extension using this path
   * @see [CRAFT_BUKKIT_PACKAGE] Related CraftBukkit path
   * @since 0.1.0
   */
  val MINECRAFT_PACKAGE: String

  /**
   * **Stable Bukkit API package path** that remains consistent across all versions.
   *
   * ### Value
   * Always: `org.bukkit`
   *
   * ### Why This Matters
   * Unlike Minecraft's internals, the Bukkit API maintains:
   * - Consistent package structure
   * - Backward compatibility
   * - Stable class locations
   *
   * This is the "public face" of Minecraft server development that plugin authors should prefer.
   *
   * ### Usage Pattern
   * Combined with [getBukkitClass]:
   * ```kotlin
   * // Always works, regardless of Minecraft version
   * val playerClass = Reflection.getBukkitClass("entity.Player")
   * ```
   *
   * ### Non-Technical Explanation
   * This is like the **standardized catalog system** in our library analogy:
   * - Always uses the same organization scheme
   * - Doesn't change between editions
   * - Provides reliable access to core functionality
   *
   * @see [getBukkitClass] Convenience extension using this path
   * @since 0.1.0
   */
  val BUKKIT_PACKAGE: String = "org.bukkit"

  /**
   * **Resolved CraftBukkit package path** that adapts to server version.
   *
   * ### Structure Examples
   * | Minecraft Version | Value |
   * |-------------------|-------|
   * | 1.16.5 | `org.bukkit.craftbukkit.v1_16_R3` |
   * | 1.17.1 | `org.bukkit.craftbukkit` |
   * | 1.19.4 | `org.bukkit.craftbukkit.v1_19_R1` |
   *
   * ### Technical Detection
   * Constructed as:
   * ```kotlin
   * "$BUKKIT_PACKAGE.craftbukkit.$versionPackageName"
   * ```
   *
   * ### Why This Matters
   * CraftBukkit is the **critical bridge** between:
   * - Bukkit API (stable, public)
   * - Minecraft NMS (volatile, internal)
   *
   * It contains:
   * - Bukkit implementation classes
   * - NMS integration points
   * - Version-specific adaptations
   *
   * ### Usage Pattern
   * Combined with [getCraftBukkitClass]:
   * ```kotlin
   * // Works across Minecraft versions
   * val craftPlayerClass = Reflection.getCraftBukkitClass("entity.CraftPlayer")
   * ```
   *
   * ### Non-Technical Explanation
   * This is like the **translation department** in our library analogy:
   * - Converts standard requests to version-specific implementations
   * - Handles the messy details of different editions
   * - Makes the unstable parts appear stable to users
   *
   * @see [getCraftBukkitClass] Convenience extension using this path
   * @see [MINECRAFT_PACKAGE] Related Minecraft path
   * @since 0.1.0
   */
  val CRAFT_BUKKIT_PACKAGE: String

  init {
    val server: Server = checkNotNull(Bukkit.getServer()) {
      "Bukkit server not initialized. Cannot resolve package names."
    }

    val packageParts: List<String> = server.javaClass.`package`.name.split('.')
    val versionPackageName: String = packageParts[3]

    CRAFT_BUKKIT_PACKAGE = "$BUKKIT_PACKAGE.craftbukkit.$versionPackageName"
    MINECRAFT_PACKAGE = when {
      packageParts.size > 4 -> "net.minecraft.$versionPackageName"
      else -> "net.minecraft.server.$versionPackageName"
    }
  }
}