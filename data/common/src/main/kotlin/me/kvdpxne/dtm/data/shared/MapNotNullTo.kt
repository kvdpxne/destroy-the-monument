package me.kvdpxne.dtm.data.shared

/**
 * Transforms non-null elements from an iterable into a new list of mapped values.
 *
 * Processes each non-null element in the input, applies a mapping function, and
 * collects the results. Skips null values and optionally ensures unique
 * elements before mapping.
 *
 * @param items The input collection containing nullable elements
 * @param initialCapacity Expected number of elements to optimize list creation
 * @param distinct When true, processes only unique elements from the input
 * @param mapper Function that converts non-null input elements to target type
 * @return A list containing all mapped non-null values
 * @since 0.1.0
 */
internal fun <T, R> mapNotNullTo(
  items: Iterable<T?>,
  initialCapacity: Int = 12,
  distinct: Boolean = true,
  mapper: (T) -> R,
): List<R> = (if (items is Collection<T?>) items.size else initialCapacity).run {
  if (0 >= this@run) emptyList() else buildList(this@run) {
    this@buildList.addAll(
      (if (distinct) items.distinct().asIterable() else items)
        .filterNotNull()
        .map { item: T -> mapper(item) }
    )
  }
}