package me.kvdpxne.dtm.data.validation

/**
 * Executes an action with a validation builder from the pool.
 * Guarantees proper acquisition and release of the builder resource.
 *
 * @param pool The builder pool to use (defaults to singleton)
 * @param action Operation to perform with the builder
 * @return Result of the action
 * @since 0.1.0
 */
inline fun <T> withValidationBuilder(
  pool: ValidationFactoryPool = BasicValidationFactoryPool,
  action: (ValidationResultBuilder) -> T
): T {
  val builder: ValidationResultBuilder = pool.acquire()
  try {
    return action(builder)
  } finally {
    pool.release(builder)
  }
}