package me.kvdpxne.dtm.data.validation

object BasicValidationFactoryPool : ValidationFactoryPool {

  /**
   * Thread-local storage for validation result builders.
   * Each thread gets its own builder instance.
   *
   * @since 0.1.0
   */
  private val pool: ThreadLocal<ValidationResultBuilder> =
    ThreadLocal.withInitial {
      BasicValidationResultBuilder()
    }

  override fun acquire(): ValidationResultBuilder {
    return this.pool.get().apply {
      this.reset()
    }
  }

  override fun release(builder: ValidationResultBuilder) {
    // Thread-local builders don't require explicit release
  }
}

