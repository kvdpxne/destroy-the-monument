package me.kvdpxne.dtm.shared

fun <T> of(
  previous: T? = null,
  mapper: () -> T,
  predicate: (T) -> Boolean = { it == previous }
): T {
  var next: T
  do {
    next = mapper()
  } while (predicate(next))
  return next
}


/**
 * Returns a unique random element from the given array, ensuring that the
 * selected element is not the same as the `previous` element (if provided).
 *
 * This function is useful for scenarios where you need to randomly select an
 * element from an array but want to avoid consecutive duplicates. For example,
 * it can be used to shuffle items in a way that no two identical items appear
 * back-to-back.
 *
 * @param from     The array from which a random element will be selected.
 * @param previous The element that should not be selected again. If `null`, any
 *                 element from the array can be selected (default is `null`).
 * @return A randomly selected element from the array that is not equal to
 *         `previous`.
 * @throws IllegalArgumentException If the array is empty or contains only one
 *                                  element that matches `previous`.
 *
 * @since 0.1.0
 */
fun <T> uniqueOf(
  from: Array<T>,
  previous: T? = null
): T {
  var next: T
  do {
    next = from.random()
  } while (next == previous)
  return next
}