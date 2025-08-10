package me.kvdpxne.dtm.shared.reflection.accessors

/**
 * Interface for **safe runtime access to class fields** (variables), supporting:
 * - Reading field values (`get`)
 * - Modifying field values (`set`)
 * - Both instance fields (object-specific) and static fields (class-wide)
 *
 * ### Technical Purpose
 * Enables interaction with class fields regardless of:
 * - Visibility modifiers (`private`, `protected`, etc.)
 * - Final status (though modification of `final` fields may fail)
 * - Static vs instance context
 *
 * ### Non-Technical Analogy
 * Fields are like **compartments in a safe**:
 * - `get()` = Peek inside a compartment
 * - `set()` = Replace contents of a compartment
 * This interface provides a master key that works even if:
 * - The compartment is hidden (`private`)
 * - The safe is locked (`final` fields)
 * - You're accessing the bank's master safe (`static` fields)
 *
 * ### Usage Context
 * ```kotlin
 * // Modify player's health (normally private field)
 * val player = Bukkit.getPlayer("Steve")
 * val healthField = Reflection.getField(player::class.java, "health")
 * healthField.set(player, 20.0f)
 * ```
 *
 * ### Security Notes
 * - Automatically handles Java's access control checks
 * - Restores original accessibility after each operation
 * - Throws [ReflectionSecurityException] for illegal modifications
 *
 * @see [Reflection.getField] Factory method to obtain implementations
 * @see [Reflection.getFieldValue] Convenience method for direct value access
 * @see [CachedFieldAccessor] Default implementation with access caching
 * @since 0.1.0
 */
interface FieldAccessor {

  /**
   * Reads the current value of the target field.
   *
   * ### Target Context
   * - For **instance fields**: `target` must be a valid object instance
   * - For **static fields**: `target` should be `null` (ignored internally)
   *
   * ### Return Behavior
   * - Returns `null` for:
   *   - Fields of nullable types containing `null`
   *   - Fields of reference types (`String`, `Object`, etc.) with no value
   * - Returns boxed primitives for primitive fields (e.g., `Int` → `java.lang.Integer`)
   *
   * @param target Optional object instance (default = `null`)
   *   - Required for instance fields
   *   - Ignored for static fields (pass `null` for clarity)
   *
   * @return Current field value, or `null` if:
   *   - Field contains `null`
   *   - Field is of primitive type with default value (0, false, etc.)
   *   - Access failed (exceptions thrown instead of returning `null`)
   *
   * @throws [ReflectionException]
   *   When field access fails due to:
   *   - Invalid `target` type for instance fields
   *   - Field not found in class hierarchy
   *
   * @throws [ReflectionSecurityException]
   *   When Java's security manager blocks field access
   *
   * @sample me.kvdpxne.dtm.shared.reflection.samples.FieldSamples.readPlayerHealth
   * @since 0.1.0
   */
  fun get(
    target: Any? = null
  ): Any?

  /**
   * Updates the value of the target field.
   *
   * ### Target Context
   * - For **instance fields**: `target` must be a valid object instance
   * - For **static fields**: `target` should be `null` (ignored internally)
   *
   * ### Value Requirements
   * - `null` allowed for nullable fields
   * - Primitive values auto-boxed (e.g., `20` → `java.lang.Float`)
   * - Type must match field declaration (or be a subtype)
   *
   * ### Special Cases
   * - `final` fields: Modification *may* succeed but is not guaranteed
   * - `static` fields: Affects all instances of the class
   *
   * @param target Optional object instance (default = `null`)
   *   - Required for instance fields
   *   - Ignored for static fields (pass `null` for clarity)
   * @param value New value to store in field (default = `null`)
   *   - Must be compatible with field type
   *   - `null` allowed for nullable fields
   *
   * @throws [ReflectionException]
   *   When field modification fails due to:
   *   - Type mismatch between `value` and field
   *   - Invalid `target` for instance fields
   *
   * @throws [ReflectionSecurityException]
   *   When Java's security manager blocks field modification
   *   - Common for `final` fields in modern Java versions
   *
   * @sample me.kvdpxne.dtm.shared.reflection.samples.FieldSamples.setPlayerHealth
   * @since 0.1.0
   */
  fun set(
    target: Any? = null,
    value: Any? = null
  )
}