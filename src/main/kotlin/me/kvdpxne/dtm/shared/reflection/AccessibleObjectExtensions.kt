package me.kvdpxne.dtm.shared.reflection

import java.lang.reflect.AccessibleObject

/**
 * Temporarily grants accessibility to an otherwise inaccessible member for
 * the duration of the provided block of code, ensuring the original
 * accessibility state is restored afterward.
 *
 * This utility is particularly useful when accessing private or protected
 * members of a class without permanently altering their accessibility status.
 *
 * @param whileBlock A lambda function to execute while the object
 *                   is accessible.
 * @return The result of the `whileBlock` execution, or `null` if `whileBlock`
 *         returns `null`.
 *
 * @since 0.1.0
 */
fun <T> AccessibleObject.accessWhile(
  whileBlock: () -> T?
): T? {
  @Suppress("DEPRECATION")
  if (this.isAccessible) {
    return whileBlock()
  }
  this.isAccessible = true
  val result: T? = whileBlock()
  this.isAccessible = false

  return result
}