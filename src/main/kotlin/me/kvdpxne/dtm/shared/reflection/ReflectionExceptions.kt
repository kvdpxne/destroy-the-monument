package me.kvdpxne.dtm.shared.reflection

/**
 * **Base class** for all reflection-related exceptions that:
 *
 * - Provides consistent error handling
 * - Maintains original exception cause
 * - Includes contextual information in messages
 * - Enables precise error categorization
 *
 * ### Exception Taxonomy
 *
 * ```
 * RuntimeException
 * └── ReflectionException (base)
 *     ├── ReflectionSecurityException
 *     ├── ClassNotFoundReflectionException
 *     ├── ConstructorNotFoundReflectionException
 *     ├── FieldNotFoundReflectionException
 *     └── MethodNotFoundReflectionException
 * ```
 *
 * ### Why Custom Exceptions?
 * Standard Java reflection exceptions are:
 *
 * - **Generic**: `IllegalAccessException`, `NoSuchMethodException`, etc.
 * - **Context-poor**: Little information about what went wrong
 * - **Hard to differentiate**: Same exception for different scenarios
 *
 * This hierarchy provides:
 *
 * - **Clear categorization** of error types
 * - **Rich context** in error messages
 * - **Precise handling** through exception types
 *
 * ### Non-Technical Analogy
 * Think of these as **specialized diagnostic codes** rather than generic "error" lights:
 *
 * - `MethodNotFoundReflectionException` = "Button not found on control panel"
 * - `ReflectionSecurityException` = "Security lock preventing access"
 * - `ClassNotFoundReflectionException` = "Blueprint not found in library"
 *
 * Without these, you'd only get "Something went wrong" messages.
 *
 * ### Error Handling Strategies
 *
 * | Exception Type | When It Occurs | Recommended Action |
 * |----------------|----------------|---------------------|
 * | [ReflectionSecurityException] | Access denied by JVM security | Check security policy, avoid modifying final fields |
 * | [ClassNotFoundReflectionException] | Class path incorrect or missing | Verify package structure, check Minecraft version |
 * | [MethodNotFoundReflectionException] | Method signature mismatch | Check parameter types, consider version differences |
 * | [FieldNotFoundReflectionException] | Field name/type mismatch | Verify field name, check for renamed fields |
 * | [ConstructorNotFoundReflectionException] | Constructor parameters incorrect | Check parameter count and types |
 *
 * ### Technical Implementation
 * - All exceptions extend this base class
 * - Include contextual information in messages
 * - Preserve original exception cause
 * - Designed for both programmatic and human consumption
 *
 * @param message Human-readable error description with context
 * @param cause Optional underlying exception that caused this error
 *   - Preserved for debugging and root cause analysis
 *
 * @see [ReflectionSecurityException] Security-related failures
 * @see [ClassNotFoundReflectionException] Class lookup failures
 * @see [ConstructorNotFoundReflectionException] Constructor lookup failures
 * @see [FieldNotFoundReflectionException] Field lookup failures
 * @see [MethodNotFoundReflectionException] Method lookup failures
 * @since 0.1.0
 */
open class ReflectionException(
  message: String,
  cause: Throwable? = null
) : RuntimeException(message, cause)

/**
 * **Exception** thrown when reflection operations are blocked by JVM security.
 *
 * ### Common Causes
 *
 * | Scenario | Java Version | Explanation |
 * |----------|--------------|-------------|
 * | Accessing private members | All | Security manager restriction |
 * | Modifying `final` fields | Java 12+ | Stricter reflection security |
 * | Package-private access | Java 12+ | Special handling required |
 *
 * ### Non-Technical Explanation
 * Imagine trying to open a locked drawer (private field) in an office:
 *
 * - **Old offices (Java 8)**: Master key works always
 * - **Modern offices (Java 9-11)**: Need proper access badge
 * - **High-security offices (Java 12+)**: Some drawers need special override
 *
 * This exception means your "access badge" was denied.
 *
 * ### Why This Happens in Minecraft
 * Bukkit/Spigot servers often run with security managers that:
 * - Restrict access to NMS (Net Minecraft Server) classes
 * - Prevent modification of critical game state
 * - Enforce module boundaries in newer Java versions
 *
 * ### Prevention Strategies
 *
 * 1. **For fields**:
 *    - Avoid modifying `final` fields when possible
 *    - Use getters/setters if available
 *    - Consider alternative approaches
 *
 * 2. **For methods**:
 *    - Prefer Bukkit API over NMS when possible
 *    - Verify method visibility in target version
 *
 * 3. **General**:
 *    - Test on target Java version
 *    - Check server security policy
 *    - Consider if reflection is truly necessary
 *
 * @param message Human-readable error description with context
 *   - Includes class/method/field name
 *   - Specifies Java version if relevant
 * @param cause Underlying security exception
 *   - Typically `IllegalAccessException`
 *   - Contains JVM-specific details
 *
 * @see [AccessController] Handles cross-version accessibility
 * @see [ReflectionException] Base exception class
 * @since 0.1.0
 */
class ReflectionSecurityException(
  message: String,
  cause: Throwable? = null
) : ReflectionException(message, cause)

/**
 * **Exception** thrown when a class cannot be found during reflection operations.
 *
 * ### Common Causes
 *
 * | Cause | Example | Solution |
 * |-------|---------|----------|
 * | Incorrect package path | Using "net.minecraft.server" in 1.17+ | Use version-agnostic helpers |
 * | Version mismatch | 1.16 path in 1.18 server | Use BukkitPackageResolver |
 * | Typo in class name | "Player" vs "EntityPlayer" | Verify class naming |
 *
 * ### Non-Technical Explanation
 * Like searching for a book in a library with these issues:
 *
 * - Looking in the wrong section (incorrect package)
 * - Using an outdated catalog (version mismatch)
 * - Misspelling the title (typo in class name)
 *
 * This exception means the "book" (class) simply isn't where we expected it to be.
 *
 * ### Why This Happens in Minecraft
 * Mojang frequently changes class structures between versions:
 *
 * | Minecraft Version | Player Class Path |
 * |-------------------|-------------------|
 * | 1.16.x | net.minecraft.server.v1_16_R3.EntityPlayer |
 * | 1.17.x | net.minecraft.server.Player |
 * | 1.18.x | net.minecraft.world.entity.player.Player |
 *
 * Plugins using hard-coded paths will fail across versions.
 *
 * ### Prevention Strategies
 *
 * 1. **Use version-agnostic helpers**:
 *    ```kotlin
 *    // Instead of hard-coded path:
 *    val playerClass = Reflection.getMinecraftClass("EntityPlayer")
 *
 *    // Or better yet:
 *    val playerClass = Reflection.getBukkitClass("entity.Player")
 *    ```
 *
 * 2. **Leverage BukkitPackageResolver**:
 *    ```kotlin
 *    val versionedPath = "${BukkitPackageResolver.MINECRAFT_PACKAGE}.EntityPlayer"
 *    val playerClass = Reflection.getClass(versionedPath)
 *    ```
 *
 * 3. **Implement fallback strategies**:
 *    ```kotlin
 *    try {
 *        return Reflection.getMinecraftClass("world.entity.player.Player")
 *    } catch (e: ClassNotFoundReflectionException) {
 *        return Reflection.getMinecraftClass("server.Player")
 *    }
 *    ```
 *
 * @param path The class path that couldn't be found
 * @param cause Underlying `ClassNotFoundException`
 *
 * @see [Reflection.getClass] Method that throws this exception
 * @see [BukkitPackageResolver] Helps construct version-specific paths
 * @see [ReflectionException] Base exception class
 * @since 0.1.0
 */
class ClassNotFoundReflectionException(
  path: String,
  cause: Throwable? = null
) : ReflectionException("Class not found: $path", cause)

/**
 * **Exception** thrown when a constructor cannot be found during reflection operations.
 *
 * ### Common Causes
 *
 * | Cause | Example | Solution |
 * |-------|---------|----------|
 * | Parameter mismatch | Expecting 3 params but providing 2 | Verify parameter count |
 * | Type mismatch | Using `Int` instead of `Integer` | Use proper boxing |
 * | Visibility | Constructor is private/protected | Check accessibility |
 *
 * ### Non-Technical Explanation
 * Like trying to use a 3D printer with:
 *
 * - The wrong number of materials (parameter count)
 * - Incorrect material types (parameter types)
 * - A locked blueprint (inaccessible constructor)
 *
 * This exception means we can't find the right "blueprint" (constructor) for the job.
 *
 * ### Parameter Representation
 * Parameters are shown in Java format:
 * - Primitives: `int`, `float`, `boolean`
 * - Objects: Fully-qualified class names
 * - Arrays: `[Lfully.qualified.Class;`
 *
 * Example: `[int, net.minecraft.world.World, float]`
 *
 * ### Why This Matters for Performance
 * Constructor lookups are expensive operations. Getting the signature right the first time:
 * - Avoids repeated failed lookups
 * - Prevents unnecessary cache entries
 * - Reduces error handling overhead
 *
 * ### Prevention Strategies
 *
 * 1. **Verify parameter types**:
 *    ```kotlin
 *    // Use Types constants for primitives
 *    val constructor = Reflection.getConstructor(
 *        entityClass,
 *        arrayOf(Types.WORLD, Types.VECTOR3F, Types.FLOAT)
 *    )
 *    ```
 *
 * 2. **Check constructor visibility**:
 *    - Public constructors work across all environments
 *    - Protected/private may require security policy changes
 *
 * 3. **Consider alternatives**:
 *    - Factory methods instead of direct construction
 *    - Bukkit API equivalents when available
 *
 * @param className The class where constructor was sought
 * @param parameterTypes The parameter types that were searched for
 *
 * @see [Reflection.getConstructor] Method that throws this exception
 * @see [Reflection.invokeConstructor] Related method
 * @see [ReflectionException] Base exception class
 * @since 0.1.0
 */
class ConstructorNotFoundReflectionException(
  className: String,
  parameterTypes: Array<out Class<*>>
) : ReflectionException(
  "Constructor not found for $className with parameters: ${parameterTypes.contentToString()}"
)

/**
 * **Exception** thrown when a field cannot be found during reflection operations.
 *
 * ### Common Causes
 *
 * | Cause | Example | Solution |
 * |-------|---------|----------|
 * | Incorrect field name | "health" vs "playerHealth" | Verify field naming |
 * | Version differences | Field renamed in newer versions | Implement version checks |
 * | Type mismatch | Expecting float but field is double | Adjust type expectation |
 *
 * ### Non-Technical Explanation
 * Like searching for a specific compartment in a safe:
 *
 * - Looking for the wrong label (incorrect name)
 * - The compartment was moved (version changes)
 * - Expecting a different size (type mismatch)
 *
 * This exception means we can't find the right "compartment" (field) in the safe (class).
 *
 * ### Error Message Composition
 * Messages include progressively more detail:
 *
 * 1. Basic: "Field 'health' not found in net.minecraft.server.Player"
 * 2. With type: "Field 'health' not found in net.minecraft.server.Player (expected type: float)"
 *
 * This helps pinpoint whether the issue is:
 * - A simple naming error
 * - A version-specific change
 * - A type compatibility issue
 *
 * ### Why Field Names Change
 * Mojang frequently renames fields between Minecraft versions:
 *
 * | Version | Player Health Field | Notes |
 * |---------|---------------------|-------|
 * | 1.16.x | `field_71093_bC` | Obfuscated name |
 * | 1.17.x | `health` | Clean name |
 * | 1.18.x | `attributeManager` | Complete restructuring |
 *
 * Plugins must adapt to these changes.
 *
 * ### Prevention Strategies
 *
 * 1. **Implement fallback strategies**:
 *    ```kotlin
 *    fun getPlayerHealth(player: Any): Float {
 *        return try {
 *            Reflection.getFieldValue(player, "health", Types.FLOAT)
 *        } catch (e: FieldNotFoundReflectionException) {
 *            Reflection.getFieldValue(player, "field_71093_bC", Types.FLOAT)
 *        }
 *    }
 *    ```
 *
 * 2. **Use type-based searching**:
 *    ```kotlin
 *    // Find any float field that might represent health
 *    val healthField = Reflection.getField(
 *        playerClass,
 *        "health",
 *        Types.FLOAT
 *    )
 *    ```
 *
 * 3. **Consider Bukkit API alternatives**:
 *    ```kotlin
 *    // Instead of reflection:
 *    val health = player.health
 *    ```
 *
 * @param className The class where field was sought
 * @param fieldName The field name that was searched for
 * @param fieldType Optional expected field type
 *
 * @see [Reflection.getField] Method that throws this exception
 * @see [Reflection.getFieldValue] Related method
 * @see [Reflection.setFieldValue] Related method
 * @see [ReflectionException] Base exception class
 * @since 0.1.0
 */
class FieldNotFoundReflectionException(
  className: String,
  fieldName: String,
  fieldType: Class<*>? = null
) : ReflectionException(
  buildString {
    append("Field '$fieldName' not found in $className")
    fieldType?.let { append(" (expected type: ${it.name})") }
  }
)

/**
 * **Exception** thrown when a method cannot be found during reflection operations.
 *
 * ### Common Causes
 *
 * | Cause | Example | Solution |
 * |-------|---------|----------|
 * | Incorrect method name | "sendMessage" vs "sendChatMessage" | Verify method naming |
 * | Parameter mismatch | Wrong count or types | Check signature |
 * | Return type mismatch | Expecting String but method returns Component | Adjust expectation |
 * | Version differences | Method added/removed in newer versions | Implement version checks |
 *
 * ### Non-Technical Explanation
 * Like trying to press a button on a control panel:
 *
 * - The button label is wrong (incorrect name)
 * - The button requires different settings (parameters)
 * - The machine responds differently (return type)
 * - The panel layout changed (version differences)
 *
 * This exception means we can't find the right "button" (method) to press.
 *
 * ### Error Message Composition
 * Messages include progressively more detail:
 *
 * 1. Basic: "Method 'sendMessage' not found in net.minecraft.server.Player"
 * 2. With return type: "Method 'sendMessage' not found in net.minecraft.server.Player (expected return type: void)"
 * 3. With parameters: "Method 'sendMessage' not found in net.minecraft.server.Player (expected parameters: [java.lang.String])"
 *
 * This helps pinpoint whether the issue is:
 * - A simple naming error
 * - A signature mismatch
 * - A version-specific change
 *
 * ### Why Method Signatures Change
 * Mojang frequently modifies method signatures between versions:
 *
 * | Version | `sendMessage` Signature | Notes |
 * |---------|-------------------------|-------|
 * | 1.16.x | `void sendMessage(String)` | Simple string |
 * | 1.17.x | `void sendMessage(Component)` | Component API |
 * | 1.18.x | `void sendMessage(Component, UUID)` | Added sender ID |
 *
 * Plugins must adapt to these changes.
 *
 * ### Prevention Strategies
 *
 * 1. **Implement version-specific logic**:
 *    ```kotlin
 *    fun sendPlayerMessage(player: Any, message: String) {
 *        if (serverVersion < 17) {
 *            Reflection.invokeMethod(
 *                player,
 *                "sendMessage",
 *                parameterTypes = arrayOf(Types.STRING),
 *                parameters = arrayOf(message)
 *            )
 *        } else {
 *            val component = LegacyComponentSerializer.legacySection().deserialize(message)
 *            Reflection.invokeMethod(
 *                player,
 *                "sendMessage",
 *                parameterTypes = arrayOf(Types.COMPONENT),
 *                parameters = arrayOf(component)
 *            )
 *        }
 *    }
 *    ```
 *
 * 2. **Use flexible parameter matching**:
 *    ```kotlin
 *    // Match any method named "sendMessage" that takes one parameter
 *    Reflection.getMethod(
 *        playerClass,
 *        "sendMessage",
 *        parameterTypes = null
 *    )
 *    ```
 *
 * 3. **Prefer Bukkit API when possible**:
 *    ```kotlin
 *    // Instead of reflection:
 *    player.sendMessage(message)
 *    ```
 *
 * @param className The class where method was sought
 * @param methodName The method name that was searched for
 * @param returnType Optional expected return type
 * @param parameterTypes Optional expected parameter types
 *
 * @see [Reflection.getMethod] Method that throws this exception
 * @see [Reflection.invokeMethod] Related method
 * @see [ReflectionException] Base exception class
 * @since 0.1.0
 */
class MethodNotFoundReflectionException(
  className: String,
  methodName: String,
  returnType: Class<*>? = null,
  parameterTypes: Array<Class<*>>? = null
) : ReflectionException(
  buildString {
    append("Method '$methodName' not found in $className")
    returnType?.let { append(" (expected return type: ${it.name})") }
    parameterTypes?.let {
      append(" (expected parameters: ${it.contentToString()})")
    }
  }
)