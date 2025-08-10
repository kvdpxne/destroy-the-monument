package me.kvdpxne.dtm.shared.reflection.cache.keys

/**
 * **Immutable cache key** that uniquely identifies a class field across different Java/Minecraft versions.
 *
 * ### Key Composition
 * This key combines three critical elements:
 *
 * - **Class identity**: Fully-qualified class name (`className`)
 * - **Field name**: Exact field identifier (`fieldName`)
 * - **Field type**: Optional expected type (`fieldType`)
 *
 * Together, these form a unique "compartment ID" that can:
 * - Distinguish between fields with the same name in inheritance hierarchies
 * - Handle cases where field types change between Minecraft versions
 * - Work without active class references (safe for caching)
 *
 * ### Non-Technical Analogy
 * Think of this as a **safe deposit box specification**:
 *
 * ```
 * "net.minecraft.entity.Player" + "health" + "float"
 * = "Health value storage compartment in Player safe"
 * ```
 *
 * Just as different compartments in a safe might have the same label but different contents,
 * different classes might have fields with the same name but different purposes.
 *
 * ### Why Optional Field Type?
 * The `fieldType` parameter is optional because:
 *
 * 1. **Exact matching**: When provided, ensures we get the field with *exactly* that type
 * 2. **Version flexibility**: When `null`, finds any field with matching name (useful when types change between versions)
 * 3. **Future-proofing**: Allows adaptation when Mojang changes field types between Minecraft updates
 *
 * ### Technical Implementation Notes
 * - Implements `equals()`/`hashCode()` automatically via Kotlin data class
 * - Uses `String?` for `fieldType` to represent optional type constraint
 * - Immutable structure guarantees cache consistency
 * - Handles Java's generic type erasure through string representation
 * - Supports both instance and static fields through same identification mechanism
 *
 * ### Field Type Representation
 * | Type Category      | Example Representation       | Notes                                  |
 * |--------------------|------------------------------|----------------------------------------|
 * | Primitive          | "int", "float", "boolean"    | Uses Java primitive naming             |
 * | Reference          | "java.lang.String"           | Fully-qualified class name             |
 * | Array              | "[Ljava.lang.String;"        | JVM internal representation            |
 * | Generic            | "java.util.List"             | Type parameters erased (as in Java)    |
 *
 * @property className Fully-qualified class name (e.g., "net.minecraft.entity.Player")
 *   - Must include package structure
 *   - Version-specific when applicable
 *
 * @property fieldName Exact field name as declared in class (e.g., "health")
 *   - Case-sensitive (Java field names are case-sensitive)
 *   - Must match exactly (no fuzzy matching)
 *
 * @property fieldType Optional fully-qualified type name (e.g., "float" or "java.lang.String")
 *   - `null` means match any field with this name
 *   - When provided, only matches fields assignable to this type
 *   - Uses JVM internal naming for arrays and special types
 *
 * @see [FieldCache] Uses this key to store [FieldAccessor] instances
 * @see [CachedFieldAccessor] Implementation being cached
 * @since 0.1.0
 */
internal data class FieldKey(
  val className: String,
  val fieldName: String,
  val fieldType: String?
)