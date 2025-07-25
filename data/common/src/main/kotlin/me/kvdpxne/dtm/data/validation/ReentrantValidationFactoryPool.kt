package me.kvdpxne.dtm.data.validation

object ReentrantValidationFactoryPool : ValidationFactoryPool {

  /**
   * Maximum number of builders preserved per thread.
   *
   * Prevents unbounded memory growth while maintaining reasonable reuse potential.
   *
   * @since 0.1.0
   */
  private const val MAX_RECYCLED_BUILDERS = 5

  /**
   * Thread-local stack of recyclable builders.
   *
   * Each thread maintains independent builders to avoid synchronization overhead.
   *
   * @since 0.1.0
   */
  private val pool: ThreadLocal<ArrayDeque<ValidationResultBuilder>> =
    ThreadLocal<ArrayDeque<ValidationResultBuilder>>()

  /**
   * Retrieves or creates a thread-specific builder stack.
   *
   * Initializes new stacks for threads encountering this pool for the first time.
   *
   * @return An initialized deque for storing builders
   * @since 0.1.0
   */
  private fun getOrCreateStack(): ArrayDeque<ValidationResultBuilder> {
    return this.pool.get()
      ?: ArrayDeque<ValidationResultBuilder>().also { builders: ArrayDeque<ValidationResultBuilder> ->
        this.pool.set(builders)
      }
  }

  override fun acquire(): ValidationResultBuilder {
    val stack: ArrayDeque<ValidationResultBuilder> = this.getOrCreateStack()
    return if (stack.isNotEmpty()) {
      stack.removeLast()
    } else {
      BasicValidationResultBuilder()
    }.apply {
      this.reset()
    }
  }

  override fun release(builder: ValidationResultBuilder) {
    val stack: ArrayDeque<ValidationResultBuilder> = this.getOrCreateStack()
    if (MAX_RECYCLED_BUILDERS > stack.size) {
      stack.addLast(builder)
    }
  }
}