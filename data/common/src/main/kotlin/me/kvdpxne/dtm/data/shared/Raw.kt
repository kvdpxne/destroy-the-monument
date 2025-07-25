package me.kvdpxne.dtm.data.shared

import me.kvdpxne.dtm.data.validation.ValidationResult

/**
 * Marker interface indicating the implementing class represents raw, unvalidated data.
 *
 * Used to distinguish domain models from their raw data representations, typically:
 * - Received from external sources
 * - About to be validated
 * - Not yet processed by business logic
 *
 * @author Łukasz Pietrzak (kvdpxne)
 * @since 0.1.0
 */
interface Raw

/**
 * Creates a pair associating this raw object with a validation result.
 *
 * Useful for tracking validation outcomes alongside raw data instances.
 *
 * @param result Validation result for this raw object
 * @return Pair where:
 *         - First: This raw object instance
 *         - Second: Associated validation result
 * @since 0.1.0
 */
fun <T : Raw> T.toPair(
  result: ValidationResult
): Pair<T, ValidationResult> =
  Pair(this, result)

/**
 * Extracts all validation results from a collection of raw object/result pairs.
 *
 * @return Array of validation results in the same order as the input collection
 * @since 0.1.0
 */
fun <T : Raw> Iterable<Pair<T, ValidationResult>>.seconds(): Array<ValidationResult> {
  return this
    .map { (_: T, second: ValidationResult) -> second }
    .toTypedArray()
}

/**
 * Extracts all raw objects from a collection of raw object/result pairs.
 *
 * @return List of raw objects in the same order as the input collection
 * @since 0.1.0
 */
fun <T : Raw> Iterable<Pair<T, ValidationResult>>.firsts(): List<T> {
  return this
    .map { (first: T, _: ValidationResult) -> first }
    .toList()
}
