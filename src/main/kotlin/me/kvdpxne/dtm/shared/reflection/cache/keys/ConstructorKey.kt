package me.kvdpxne.dtm.shared.reflection.cache.keys

/**
 * **Immutable cache key** that uniquely identifies a class constructor across different Java/Minecraft versions.
 *
 * ### Key Composition
 * This key combines:
 *
 * - **Class identity**: Fully-qualified class name (`className`)
 * - **Signature fingerprint**: Parameter types as strings (`parameterTypes`)
 *
 * Together, these form a unique "blueprint ID" that can:
 * - Distinguish between multiple constructors in the same class
 * - Survive across Minecraft version updates
 * - Work without active class references (safe for caching)
 *
 * ### Non-Technical Analogy
 * Think of this as a **3D printer blueprint specification**:
 *
 * ```
 * "net.minecraft.entity.Player" + [World, Vector3f, float]
 * = "Player blueprint for World-based spawning at position with rotation"
 * ```
 *
 * Just as different blueprints create different objects, different parameter sets create different constructors.
 *
 * ### Why Strings Instead of Class References?
 * Using string representations of types (instead of `Class` objects) enables:
 *
 * - **Cross-version compatibility**: Works even when class structures change between Minecraft versions
 * - **Memory safety**: Prevents classloader leaks during plugin reloading
 * - **Cache longevity**: Keys remain valid even if original classes are garbage collected
 * - **Version-agnostic identification**: Same key works for "net.minecraft.server.v1_19_R1.Player" and "net.minecraft.server.Player" in newer versions
 *
 * ### Technical Implementation Notes
 * - Implements `equals()`/`hashCode()` automatically via Kotlin data class
 * - Uses `List<String>` for parameter types (ordered collection is essential)
 * - Immutable structure guarantees cache consistency
 * - Works with Java's generic type erasure limitations
 *
 * @property className Fully-qualified class name (e.g., "net.minecraft.entity.Player")
 *   - Must include package structure
 *   - Version-specific when applicable (e.g., "v1_19_R1")
 *
 * @property parameterTypes Ordered list of parameter type names (e.g., ["org.bukkit.World", "float", "float"])
 *   - Position matters (constructor parameters are ordered)
 *   - Uses fully-qualified names for non-primitive types
 *   - Primitive types use Java naming ("int", "float", etc.)
 *
 * @see [ConstructorCache] Uses this key to store [ConstructorInvoker] instances
 * @see [CachedConstructorInvoker] Implementation being cached
 * @since 0.1.0
 */
internal data class ConstructorKey(
  val className: String,
  val parameterTypes: List<String>
)