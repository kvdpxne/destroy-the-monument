package me.kvdpxne.dtm.capabilities;

import org.jetbrains.annotations.NotNull;

/**
 * Represents the capability of an object to construct and deliver a finalized instance of a
 * specified type. This interface defines the standard mechanism for implementing the Builder
 * pattern, providing a clear termination point for object configuration and ensuring the production
 * of valid, immutable instances.
 * <p>
 * <b>Implementation Contract:</b>
 * <ul>
 *   <li>The {@code build()} method must always return a non-null, fully initialized instance</li>
 *   <li>Implementations should validate configuration state before constructing the object</li>
 *   <li>Built objects should be immutable where possible</li>
 * </ul>
 *
 * @param <T> The type of object to be constructed
 * @author Łukasz Pietrzak (kvdpxne)
 * @version 0.1.0
 * @since 0.1.0
 */
public interface Buildable<T> {

  /**
   * Constructs and returns a finalized instance of type {@code T} based on the current
   * configuration state. This method serves as the termination point for builder operations,
   * triggering the actual object creation process.
   * <p>
   * <b>Preconditions:</b>
   * <ul>
   *   <li>All required parameters must be properly configured</li>
   *   <li>Configuration state must be internally consistent</li>
   * </ul>
   *
   * <b>Postconditions:</b>
   * <ul>
   *   <li>Returns a fully initialized, valid instance</li>
   *   <li>Builder state remains unchanged (may be reused if implemented accordingly)</li>
   * </ul>
   *
   * @return A non-null, fully constructed instance of type {@code T}
   * @throws IllegalStateException If required configuration is incomplete or invalid
   * @since 0.1.0
   */
  @NotNull
  T build();
}