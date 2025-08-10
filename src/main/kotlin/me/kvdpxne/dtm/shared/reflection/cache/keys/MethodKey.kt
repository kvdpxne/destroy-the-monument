package me.kvdpxne.dtm.shared.reflection.cache.keys

/**
 * **Immutable cache key** that uniquely identifies a class method across different Java/Minecraft versions.
 *
 * ### Key Composition
 * This comprehensive key combines four critical elements:
 *
 * - **Class identity**: Fully-qualified class name (`className`)
 * - **Method name**: Exact method identifier (`methodName`)
 * - **Signature fingerprint**: Parameter types as strings (`parameterTypes`)
 * - **Return specification**: Optional expected return type (`returnType`)
 *
 * Together, these form a unique "machine button ID" that can:
 * - Distinguish between overloaded methods with same name
 * - Handle cases where method signatures change between versions
 * - Work without active class references (safe for caching)
 * - Support both exact and flexible matching strategies
 *
 * ### Non-Technical Analogy
 * Think of this as a **control panel button specification**:
 *
 * ```
 * "net.minecraft.entity.Player" + "sendMessage" + [String] + "void"
 * = "Button to send text messages on Player control panel"
 * ```
 *
 * Just as different machines might have buttons with the same label but different functions,
 * different classes might have methods with the same name but different purposes.
 *
 * ### Why This Specific Composition?
 * Method identification is the most complex reflection operation because:
 *
 * 1. **Overloading**: Multiple methods can share the same name
 * 2. **Version drift**: Mojang frequently changes method signatures between Minecraft updates
 * 3. **Return type ambiguity**: Java allows methods with same name/signature but different return types (rare but possible)
 * 4. **Parameter evolution**: New parameters might be added in newer versions
 *
 * ### Flexible Matching Strategy
 * The key supports multiple matching approaches:
 *
 * | Component         | When `null`/empty               | When specified                     |
 * |-------------------|---------------------------------|------------------------------------|
 * | `returnType`      | Match any return type           | Match only compatible return types |
 * | `parameterTypes`  | Match no-arg methods only       | Match exact parameter signature    |
 *
 * This flexibility is essential for cross-version Minecraft plugin development.
 *
 * ### Technical Implementation Notes
 * - Implements `equals()`/`hashCode()` automatically via Kotlin data class
 * - Uses `List<String>` for parameter types (order is critical for method signatures)
 * - `returnType` is optional to support scenarios where return type isn't known
 * - Immutable structure guarantees cache consistency
 * - Handles Java's generic type erasure through string representation
 *
 * ### Method Signature Representation
 * Consider a method:
 * ```java
 * public List<String> getNearbyEntities(World world, double radius)
 * ```
 *
 * Would be represented as:
 * - `methodName`: "getNearbyEntities"
 * - `parameterTypes`: ["org.bukkit.World", "double"]
 * - `returnType`: "java.util.List"
 *
 * Note that generic type parameters ("String") are erased in the representation.
 *
 * @property className Fully-qualified class name (e.g., "net.minecraft.entity.Player")
 *   - Must include package structure
 *   - Version-specific when applicable (e.g., "v1_19_R1")
 *
 * @property methodName Exact method name as declared in class (e.g., "sendMessage")
 *   - Case-sensitive (Java method names are case-sensitive)
 *   - Must match exactly (no fuzzy matching)
 *
 * @property parameterTypes Ordered list of parameter type names
 *   - Position matters (method parameters are ordered)
 *   - Uses fully-qualified names for non-primitive types
 *   - Primitive types use Java naming ("int", "float", etc.)
 *   - Empty list means no parameters
 *
 * @property returnType Optional fully-qualified return type name
 *   - `null` means match any return type
 *   - When provided, only matches methods with compatible return types
 *   - Uses JVM internal naming for arrays and special types
 *
 * @see [MethodCache] Uses this key to store [MethodInvoker] instances
 * @see [CachedMethodInvoker] Implementation being cached
 * @since 0.1.0
 */
internal data class MethodKey(
  val className: String,
  val methodName: String,
  val parameterTypes: List<String>,
  val returnType: String?
)