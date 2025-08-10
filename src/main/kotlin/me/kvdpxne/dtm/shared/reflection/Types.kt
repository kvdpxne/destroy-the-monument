package me.kvdpxne.dtm.shared.reflection

import me.kvdpxne.dtm.shared.reflection.Types.BOOLEAN
import me.kvdpxne.dtm.shared.reflection.Types.BYTE
import me.kvdpxne.dtm.shared.reflection.Types.CHAR
import me.kvdpxne.dtm.shared.reflection.Types.DOUBLE
import me.kvdpxne.dtm.shared.reflection.Types.FLOAT
import me.kvdpxne.dtm.shared.reflection.Types.INT
import me.kvdpxne.dtm.shared.reflection.Types.LONG
import me.kvdpxne.dtm.shared.reflection.Types.SHORT
import me.kvdpxne.dtm.shared.reflection.Types.STRING
import me.kvdpxne.dtm.shared.reflection.Types.VOID


/**
 * **Type constants** for primitive and common Java types that:
 *
 * - Provides consistent references to primitive types
 * - Avoids autoboxing issues in reflection operations
 * - Ensures correct type matching across JVM versions
 * - Simplifies parameter type specification
 *
 * ### Why These Constants Are Needed
 * Java reflection requires `Class` objects for type matching, but:
 *
 * - Primitive types (`int`, `float`) have different representations than wrappers (`Integer`, `Float`)
 * - Autoboxing can cause unexpected mismatches
 * - Different JVM versions handle primitives slightly differently
 *
 * These constants provide:
 *
 * - **Canonical references** to primitive types
 * - **Consistent naming** across codebase
 * - **Type safety** for reflection operations
 * - **Readability** compared to `int::class.java`
 *
 * ### Non-Technical Analogy
 * Think of these as **standardized measurement units**:
 *
 * - `INT` = Standardized "inch" definition
 * - `STRING` = Standardized "text format"
 * - `VOID` = Standardized "no result" indicator
 *
 * Without standards, you might mix metric and imperial units (causing errors).
 *
 * ### Technical Implementation
 * - Maps Kotlin primitives to their Java `Class` equivalents
 * - Includes `VOID` for method return type checking
 * - Uses JVM-internal naming for consistency
 * - Immutable and thread-safe
 *
 * ### Usage Example
 * ```kotlin
 * // Correct way to specify int parameter
 * Reflection.getMethod(
 *   playerClass,
 *   "setHealth",
 *   parameterTypes = arrayOf(Types.FLOAT)
 * )
 *
 * // Incorrect - may fail due to autoboxing
 * Reflection.getMethod(
 *   playerClass,
 *   "setHealth",
 *   parameterTypes = arrayOf(Float::class.java)
 * )
 * ```
 *
 * ### Primitive vs Wrapper Types
 * | Concept | Primitive | Wrapper | Types Constant |
 * |---------|-----------|---------|----------------|
 * | Integer | `int` | `Integer` | [INT] |
 * | Floating-point | `float` | `Float` | [FLOAT] |
 * | Text | N/A | `String` | [STRING] |
 * | No value | N/A | N/A | [VOID] |
 *
 * @see [BOOLEAN] Constant for `boolean` primitive type
 * @see [BYTE] Constant for `byte` primitive type
 * @see [SHORT] Constant for `short` primitive type
 * @see [INT] Constant for `int` primitive type
 * @see [LONG] Constant for `long` primitive type
 * @see [FLOAT] Constant for `float` primitive type
 * @see [DOUBLE] Constant for `double` primitive type
 * @see [CHAR] Constant for `char` primitive type
 * @see [STRING] Constant for `String` reference type
 * @see [VOID] Constant for `void` return type
 * @since 0.1.0
 */
object Types {

  /**
   * **Constant** for the `boolean` primitive type.
   *
   * ### Why Not Use `Boolean::class.java`?
   * - `Boolean::class.java` refers to the wrapper type (`java.lang.Boolean`)
   * - Reflection requires primitive type (`boolean`) for exact matching
   * - Using wrapper type may cause `NoSuchMethodException`
   *
   * ### Usage Example
   * ```kotlin
   * // Finding a method with boolean parameter
   * Reflection.getMethod(
   *   playerClass,
   *   "setSneaking",
   *   parameterTypes = arrayOf(Types.BOOLEAN)
   * )
   * ```
   *
   * ### Common Mistake
   * ```kotlin
   * // This may fail because it uses wrapper type:
   * Reflection.getMethod(
   *   playerClass,
   *   "setSneaking",
   *   parameterTypes = arrayOf(Boolean::class.java)
   * )
   * ```
   *
   * @see [BOOLEAN] for parameter type specification
   * @see [Reflection.getMethod] for usage context
   * @since 0.1.0
   */
  val BOOLEAN: Class<*> = Boolean::class.java

  /**
   * **Constant** for the `byte` primitive type.
   *
   * ### Technical Notes
   * - Represents 8-bit signed integer
   * - Range: -128 to 127
   * - Commonly used in NMS for compact data storage
   *
   * ### Minecraft Usage
   * Frequently appears in:
   * - Packet data structures
   * - Block state representations
   * - Entity metadata
   *
   * ### Why This Matters
   * Using `Byte::class.java` (wrapper) instead of `Types.BYTE` (primitive) can cause:
   * - `NoSuchMethodException` for methods with `byte` parameters
   * - Silent autoboxing issues in parameter matching
   * - Version-specific failures that are hard to debug
   *
   * @see [BYTE] for parameter type specification
   * @see [Reflection.getMethod] for usage context
   * @since 0.1.0
   */
  val BYTE: Class<*> = Byte::class.java

  /**
   * **Constant** for the `short` primitive type.
   *
   * ### Technical Notes
   * - Represents 16-bit signed integer
   * - Range: -32,768 to 32,767
   * - Commonly used for compact numeric storage
   *
   * ### Minecraft Usage
   * Frequently appears in:
   * - Entity IDs
   * - Item durability
   * - Chunk coordinates
   * - Protocol packet data
   *
   * ### Common Pitfall
   * In Minecraft 1.14+, some short fields were changed to ints:
   * ```kotlin
   * // Works in 1.13 but fails in 1.14+
   * val durability = Reflection.getFieldValue(itemStack, "durability", Types.SHORT)
   * ```
   *
   * Always verify field types across versions.
   *
   * @see [SHORT] for parameter type specification
   * @see [Reflection.getField] for usage context
   * @since 0.1.0
   */
  val SHORT: Class<*> = Short::class.java

  /**
   * **Constant** for the `int` primitive type.
   *
   * ### Technical Notes
   * - Represents 32-bit signed integer
   * - Range: -2,147,483,648 to 2,147,483,647
   * - Most common numeric type in Java
   *
   * ### Minecraft Usage
   * Appears everywhere:
   * - Player health (20 = full hearts)
   * - Block coordinates
   * - Item counts
   * - Game ticks
   * - Protocol packet data
   *
   * ### Critical Importance
   * Using `Types.INT` instead of `Int::class.java` is crucial because:
   * - Many NMS methods use primitive `int` parameters
   * - Autoboxing can cause signature mismatches
   * - Version changes may alter parameter types
   *
   * Example of correct usage:
   * ```kotlin
   * Reflection.invokeMethod(
   *   player,
   *   "setLevel",
   *   parameterTypes = arrayOf(Types.INT),
   *   parameters = arrayOf(10)
   * )
   * ```
   *
   * @see [INT] for parameter type specification
   * @see [Reflection.invokeMethod] for usage context
   * @since 0.1.0
   */
  val INT: Class<*> = Int::class.java

  /**
   * **Constant** for the `long` primitive type.
   *
   * ### Technical Notes
   * - Represents 64-bit signed integer
   * - Range: -9,223,372,036,854,775,808 to 9,223,372,036,854,775,807
   * - Used for large numbers and timestamps
   *
   * ### Minecraft Usage
   * Common in:
   * - World timestamps
   * - UUID components
   * - Game tick counters
   * - Protocol packet data
   *
   * ### Why This Matters
   * In Minecraft 1.14+, some integer fields became longs for larger ranges:
   * ```kotlin
   * // May need version check:
   * val gameTime = if (serverVersion >= 14) {
   *   Reflection.getFieldValue(world, "gameTime", Types.LONG)
   * } else {
   *   Reflection.getFieldValue(world, "gameTime", Types.INT).toLong()
   * }
   * ```
   *
   * @see [LONG] for parameter type specification
   * @see [Reflection.getFieldValue] for usage context
   * @since 0.1.0
   */
  val LONG: Class<*> = Long::class.java

  /**
   * **Constant** for the `float` primitive type.
   *
   * ### Technical Notes
   * - Represents single-precision 32-bit IEEE 754 floating point
   * - Less precise but more compact than double
   * - Commonly used for game coordinates and physics
   *
   * ### Minecraft Usage
   * Ubiquitous in:
   * - Player/entity positions
   * - Rotation angles
   * - Health values (20.0 = full health)
   * - Velocity vectors
   *
   * ### Critical Note
   * Minecraft primarily uses `float` (not `double`) for most numeric game state:
   * ```kotlin
   * // Correct - Minecraft uses floats for positions
   * val x = Reflection.getFieldValue(entity, "locX", Types.FLOAT)
   *
   * // May fail - entity coordinates are floats, not doubles
   * val x = Reflection.getFieldValue(entity, "locX", Types.DOUBLE)
   * ```
   *
   * @see [FLOAT] for parameter type specification
   * @see [Reflection.getFieldValue] for usage context
   * @since 0.1.0
   */
  val FLOAT: Class<*> = Float::class.java

  /**
   * **Constant** for the `double` primitive type.
   *
   * ### Technical Notes
   * - Represents double-precision 64-bit IEEE 754 floating point
   * - More precise but larger than float
   * - Used when high precision is needed
   *
   * ### Minecraft Usage
   * Appears in:
   * - Some world generation algorithms
   * - Precise coordinate calculations
   * - Physics simulations
   * - Bukkit API (e.g., Location uses doubles)
   *
   * ### Important Distinction
   * - **Bukkit API**: Uses `double` for coordinates (Location class)
   * - **NMS**: Typically uses `float` for entity coordinates
   *
   * Conversion is often needed:
   * ```kotlin
   * // From NMS float to Bukkit double
   * val nmsX = Reflection.getFieldValue(entity, "locX", Types.FLOAT)
   * val bukkitX = nmsX.toDouble()
   * ```
   *
   * @see [DOUBLE] for parameter type specification
   * @see [Reflection.invokeMethod] for usage context
   * @since 0.1.0
   */
  val DOUBLE: Class<*> = Double::class.java

  /**
   * **Constant** for the `char` primitive type.
   *
   * ### Technical Notes
   * - Represents a single 16-bit Unicode character
   * - Range: '\u0000' (0) to '\uffff' (65,535)
   * - Less common in Minecraft reflection operations
   *
   * ### Minecraft Usage
   * Rare but appears in:
   * - Text processing
   * - Chat formatting
   * - Special character handling
   *
   * ### When You Might Need This
   * Mostly for specialized operations like:
   * ```kotlin
   * // Finding a method that processes single characters
   * Reflection.getMethod(
   *   textProcessorClass,
   *   "processCharacter",
   *   parameterTypes = arrayOf(Types.CHAR)
   * )
   * ```
   *
   * Most text operations in Minecraft use Strings rather than individual chars.
   *
   * @see [CHAR] for parameter type specification
   * @see [Reflection.getMethod] for usage context
   * @since 0.1.0
   */
  val CHAR: Class<*> = Char::class.java

  /**
   * **Constant** for the `String` reference type.
   *
   * ### Technical Notes
   * - Represents Java's `java.lang.String` class
   * - Reference type (not primitive)
   * - Immutable sequence of Unicode characters
   *
   * ### Minecraft Usage
   * Extremely common in:
   * - Player names
   * - Chat messages
   * - Configuration values
   * - Entity/display names
   *
   * ### Why This Constant?
   * While `String::class.java` works, `Types.STRING`:
   * - Provides consistency with primitive type constants
   * - Makes code more readable in reflection contexts
   * - Follows the same pattern as other type constants
   *
   * Example usage:
   * ```kotlin
   * // Finding a method that takes a String parameter
   * Reflection.getMethod(
   *   playerClass,
   *   "sendMessage",
   *   parameterTypes = arrayOf(Types.STRING)
   * )
   * ```
   *
   * @see [STRING] for parameter type specification
   * @see [Reflection.invokeMethod] for usage context
   * @since 0.1.0
   */
  val STRING: Class<*> = String::class.java

  /**
   * **Constant** for the `void` return type.
   *
   * ### Technical Notes
   * - Represents methods that don't return a value
   * - Not a real class but `Void.TYPE` in Java
   * - Used exclusively for method return type checking
   *
   * ### Minecraft Usage
   * Common for:
   * - Event handlers
   * - State modification methods
   * - Packet sending operations
   *
   * ### Critical Usage Pattern
   * When finding methods with no return value:
   * ```kotlin
   * // Finding a void method
   * Reflection.getMethod(
   *   playerClass,
   *   "sendMessage",
   *   returnType = Types.VOID,
   *   parameterTypes = arrayOf(Types.STRING)
   * )
   * ```
   *
   * ### Why Not Use null?
   * Using `null` for return type means "match any return type":
   * ```kotlin
   * // This would match ANY sendMessage method, regardless of return type
   * Reflection.getMethod(
   *   playerClass,
   *   "sendMessage",
   *   returnType = null,
   *   parameterTypes = arrayOf(Types.STRING)
   * )
   * ```
   *
   * Use `Types.VOID` when you specifically need a void method.
   *
   * @see [VOID] for return type specification
   * @see [Reflection.getMethod] for usage context
   * @since 0.1.0
   */
  val VOID: Class<*> = Void.TYPE
}