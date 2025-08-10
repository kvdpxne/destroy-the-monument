package me.kvdpxne.dtm.shared.reflection.bukkit

import me.kvdpxne.dtm.shared.reflection.Reflection

/**
 * **Retrieves a Minecraft NMS (Net Minecraft Server) class** with version-agnostic path resolution.
 *
 * ### Technical Workflow
 * 1. Validates path is not blank
 * 2. Constructs full path based on detected version:
 *    - Legacy (≤1.16.x): `"net.minecraft.server.$versionPackageName.$path"`
 *    - Modern (≥1.17): `"net.minecraft.$path"`
 * 3. Uses [Reflection.getClass] to load the class
 *
 * ### Path Examples
 * | Requested Path | Minecraft 1.16.5 | Minecraft 1.19.4 |
 * |----------------|------------------|------------------|
 * | `"EntityPlayer"` | `net.minecraft.server.v1_16_R3.EntityPlayer` | `net.minecraft.world.entity.player.Player` |
 * | `"PacketPlayOutChat"` | `net.minecraft.server.v1_16_R3.PacketPlayOutChat` | `net.minecraft.network.protocol.game.PacketPlayOutChat` |
 *
 * ### Why This Is Risky but Sometimes Necessary
 * NMS classes are **Mojang's internal implementation**:
 * - No backward compatibility guarantees
 * - Frequent renames and reorganizations
 * - Can break with any Minecraft update
 *
 * However, they're needed for:
 * - Advanced packet manipulation
 * - Custom entity behavior
 * - Performance-critical operations
 * - Features not exposed in Bukkit API
 *
 * ### Non-Technical Explanation
 * Think of this as asking for a book in the **author's draft notes**:
 * - "Find draft notes in section net.minecraft, titled EntityPlayer"
 * - The catalog system (this method) knows the current draft's organization
 * - Works even when authors completely reorganize their drafts
 *
 * Without this, you'd need to:
 * - Know exactly how the author organized each draft version
 * - Constantly update your requests for new drafts
 * - Risk asking for notes that no longer exist
 *
 * ### Best Practices for NMS Access
 *
 * 1. **Implement fallback strategies**:
 *    ```kotlin
 *    fun getPlayerConnection(player: Player): Any? {
 *        return try {
 *            // Modern approach
 *            Reflection.getFieldValue(player, "b")
 *        } catch (e: FieldNotFoundReflectionException) {
 *            // Legacy approach
 *            Reflection.getFieldValue(player, "playerConnection")
 *        }
 *    }
 *    ```
 *
 * 2. **Isolate NMS code**:
 *    ```kotlin
 *    // Good - NMS isolated in one class
 *    class PlayerNMSAdapter(player: Player) {
 *        private val handle = getHandle(player)
 *        fun sendPacket(packet: Any) { /* NMS-specific implementation */ }
 *    }
 *
 *    // Bad - NMS scattered throughout code
 *    fun doSomething(player: Player) {
 *        val nmsPlayer = Reflection.getFieldValue(player, "handle")
 *        // ... NMS operations
 *    }
 *    ```
 *
 * 3. **Document version dependencies**:
 *    ```kotlin
 *    /**
 *     * Works for Minecraft 1.16-1.18
 *     * For 1.19+, use getChatComponentModern()
 *     */
 *    fun getChatComponentLegacy(message: String): Any {
 *        // NMS implementation
 *    }
 *    ```
 *
 * ### Error Handling
 * | Error Scenario | Exception | Debugging Tip |
 * |----------------|-----------|---------------|
 * | Blank path | `IllegalArgumentException` | Check path parameter |
 * | Class not found | [ClassNotFoundReflectionException] | Verify Minecraft version compatibility |
 *
 * @param path Relative Minecraft class path (e.g., "EntityPlayer")
 *   - Must not be blank (validated internally)
 *   - Uses Minecraft's internal package structure
 *   - Varies significantly between versions
 *
 * @return The requested Minecraft NMS class
 *
 * @throws [IllegalArgumentException] When path is blank
 *   - Message includes example format
 *
 * @throws [ClassNotFoundReflectionException] When class doesn't exist
 *   - Typically means path incompatible with current Minecraft version
 *
 * @sample me.kvdpxne.dtm.shared.reflection.samples.BukkitSamples.getNmsPlayerClass
 * @see [BukkitPackageResolver.MINECRAFT_PACKAGE] Base package used
 * @see [Reflection.getClass] Underlying implementation
 * @since 0.1.0
 */
fun Reflection.getMinecraftClass(path: String): Class<*> {
  requirePathNotBlank(path, "Minecraft class path")
  return this.getClass("${BukkitPackageResolver.MINECRAFT_PACKAGE}.$path")
}

/**
 * **Retrieves a Bukkit API class** with version-agnostic path resolution.
 *
 * ### Technical Workflow
 * 1. Validates path is not blank
 * 2. Constructs full path: `"org.bukkit.$path"`
 * 3. Uses [Reflection.getClass] to load the class
 *
 * ### Path Examples
 * | Requested Path | Full Resolved Path |
 * |----------------|--------------------|
 * | `"entity.Player"` | `org.bukkit.entity.Player` |
 * | `"World"` | `org.bukkit.World` |
 * | `"event/player/PlayerJoinEvent"` | `org.bukkit.event.player.PlayerJoinEvent` |
 *
 * ### Why This Is Safer Than Direct Access
 * The Bukkit API maintains **backward compatibility** across versions:
 * - Class locations rarely change
 * - Public API remains stable
 * - Preferred over NMS access when possible
 *
 * ### Non-Technical Explanation
 * Think of this as asking for a book using the **standard catalog number**:
 * - "Find book in section org.bukkit, subsection entity, titled Player"
 * - The catalog system (this method) knows exactly where to look
 * - Works regardless of library edition (Minecraft version)
 *
 * Without this, you'd need to know:
 * - The exact shelf location for each Minecraft version
 * - How the catalog system changed between versions
 *
 * ### Best Practices
 *
 * 1. **Prefer Bukkit API over NMS** when possible:
 *    ```kotlin
 *    // Good - stable across versions
 *    val playerClass = Reflection.getBukkitClass("entity.Player")
 *
 *    // Risky - may break across versions
 *    val nmsPlayerClass = Reflection.getMinecraftClass("EntityPlayer")
 *    ```
 *
 * 2. **Use Bukkit types in signatures**:
 *    ```kotlin
 *    // Good - uses Bukkit API
 *    fun getPlayerName(player: Player): String {
 *        return player.name
 *    }
 *
 *    // Risky - uses NMS
 *    fun getPlayerName(nmsPlayer: Any): String {
 *        return Reflection.getFieldValue(nmsPlayer, "name")
 *    }
 *    ```
 *
 * ### Error Handling
 * | Error Scenario | Exception | Debugging Tip |
 * |----------------|-----------|---------------|
 * | Blank path | `IllegalArgumentException` | Check path parameter |
 * | Class not found | [ClassNotFoundReflectionException] | Verify Bukkit API documentation |
 *
 * @param path Relative Bukkit class path (e.g., "entity.Player")
 *   - Must not be blank (validated internally)
 *   - Uses Bukkit's package structure
 *   - Should match Bukkit API documentation
 *
 * @return The requested Bukkit class
 *
 * @throws [IllegalArgumentException] When path is blank
 *   - Message includes example format
 *
 * @throws [ClassNotFoundReflectionException] When class doesn't exist
 *   - Typically means invalid path or Bukkit API change
 *
 * @sample me.kvdpxne.dtm.shared.reflection.samples.BukkitSamples.getPlayerClass
 * @see [BukkitPackageResolver.BUKKIT_PACKAGE] Base package used
 * @see [Reflection.getClass] Underlying implementation
 * @since 0.1.0
 */
fun Reflection.getBukkitClass(path: String): Class<*> {
  requirePathNotBlank(path, "Bukkit class path")
  return this.getClass("${BukkitPackageResolver.BUKKIT_PACKAGE}.$path")
}

/**
 * **Retrieves a CraftBukkit implementation class** with version-agnostic path resolution.
 *
 * ### Technical Workflow
 * 1. Validates path is not blank
 * 2. Constructs full path: `"org.bukkit.craftbukkit.$versionPackageName.$path"`
 * 3. Uses [Reflection.getClass] to load the class
 *
 * ### Path Examples
 * | Requested Path | Minecraft 1.16.5 | Minecraft 1.19.4 |
 * |----------------|------------------|------------------|
 * | `"entity.CraftPlayer"` | `org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer` | `org.bukkit.craftbukkit.v1_19_R1.entity.CraftPlayer` |
 * | `"CraftWorld"` | `org.bukkit.craftbukkit.v1_16_R3.CraftWorld` | `org.bukkit.craftbukkit.v1_19_R1.CraftWorld` |
 *
 * ### Why This Is Necessary
 * CraftBukkit is the **implementation layer** between:
 * - Bukkit API (stable, public)
 * - Minecraft NMS (volatile, internal)
 *
 * It contains:
 * - Bukkit interface implementations
 * - NMS integration points
 * - Version-specific adaptations
 *
 * ### Non-Technical Explanation
 * Think of this as asking for a book in the **translator's working notes**:
 * - "Find translation notes in section org.bukkit.craftbukkit, subsection entity, titled CraftPlayer"
 * - The catalog system (this method) knows the current edition's structure
 * - Works even when translators change their organization
 *
 * Without this, you'd need to know:
 * - The exact translator edition (Minecraft version)
 * - How their notes are organized for that edition
 *
 * ### When to Use This
 *
 * 1. **Accessing Bukkit implementation details**:
 *    ```kotlin
 *    val craftPlayer = player as CraftPlayer
 *    ```
 *
 * 2. **Working with Bukkit-to-NMS bridges**:
 *    ```kotlin
 *    val nmsPlayer = (player as CraftPlayer).handle
 *    ```
 *
 * 3. **When Bukkit API lacks needed functionality**:
 *    ```kotlin
 *    // When Player API doesn't expose needed functionality
 *    val playerConnection = Reflection.getFieldValue(
 *        (player as CraftPlayer).handle,
 *        "playerConnection"
 *    )
 *    ```
 *
 * ### Caveats
 * - Less stable than Bukkit API
 * - More likely to change between versions
 * - Should be fallback when Bukkit API is insufficient
 *
 * @param path Relative CraftBukkit class path (e.g., "entity.CraftPlayer")
 *   - Must not be blank (validated internally)
 *   - Uses CraftBukkit's package structure
 *   - Should match CraftBukkit implementation
 *
 * @return The requested CraftBukkit class
 *
 * @throws [IllegalArgumentException] When path is blank
 *   - Message includes example format
 *
 * @throws [ClassNotFoundReflectionException] When class doesn't exist
 *   - Typically means invalid path or version mismatch
 *
 * @sample me.kvdpxne.dtm.shared.reflection.samples.BukkitSamples.getCraftPlayerClass
 * @see [BukkitPackageResolver.CRAFT_BUKKIT_PACKAGE] Base package used
 * @see [Reflection.getClass] Underlying implementation
 * @since 0.1.0
 */
fun Reflection.getCraftBukkitClass(path: String): Class<*> {
  requirePathNotBlank(path, "CraftBukkit class path")
  return this.getClass("${BukkitPackageResolver.CRAFT_BUKKIT_PACKAGE}.$path")
}
