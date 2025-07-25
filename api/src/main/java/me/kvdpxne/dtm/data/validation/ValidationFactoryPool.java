package me.kvdpxne.dtm.data.validation;

import org.jetbrains.annotations.NotNull;

/**
 * Manages a pool of reusable validation result builders to optimize resource usage.
 * Provides thread-safe acquisition and release of builder instances, reducing object
 * creation overhead during validation operations.
 *
 * @author Łukasz Pietrzak (kvdpxne)
 * @version 0.1.0
 * @since 0.1.0
 */
public interface ValidationFactoryPool {

  /**
   * Acquires a validation result builder from the pool.
   * The returned builder is reset and ready for use. Implementations should ensure
   * thread-safe access to builders.
   *
   * @return A ready-to-use validation result builder (never null)
   * @since 0.1.0
   */
  @NotNull
  ValidationResultBuilder acquire();

  /**
   * Releases a builder back to the pool after use.
   * Implementations may reset or clean the builder before making it available for reuse.
   *
   * @param builder The builder to return to the pool (must not be null)
   * @since 0.1.0
   */
  void release(
    @NotNull ValidationResultBuilder builder
  );
}
