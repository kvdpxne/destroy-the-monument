package me.kvdpxne.dtm;

import org.jetbrains.annotations.NotNull;

/**
 * Defines an interface for objects that can create a copy of themselves.
 * <p>
 * Implementing this interface allows an object to generate a new instance with
 * the same state as the current instance.
 *
 * @param <T> the type of the object that implements this interface.
 * @since 0.1.0
 */
public interface Copyable<T> {

  /**
   * Creates a copy of the current object.
   * <p>
   * The returned instance should represent a new object with identical state to
   * the current instance, but modifications to one will not affect the other.
   *
   * @return a new instance of type {@code T} that is a copy of this object.
   * @since 0.1.0
   */
  @NotNull
  T copy();
}
