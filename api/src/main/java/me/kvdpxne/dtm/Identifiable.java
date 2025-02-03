package me.kvdpxne.dtm;

import java.io.Serializable;
import org.jetbrains.annotations.NotNull;

/**
 * Represents an entity that can be uniquely identified by an identifier.
 * <p>
 * The {@code Identifiable} interface provides a method to retrieve this
 * identifier, which must implement the {@link Serializable} interface.
 *
 * @param <T> the type of the identifier, which must extend
 *            {@link Serializable}.
 * @since 0.1.0
 */
public interface Identifiable<T extends Serializable> {

  /**
   * Retrieves the unique identifier for this entity.
   *
   * @return a non-null value of type {@code T} representing the identifier.
   * @since 0.1.0
   */
  @NotNull
  T getIdentifier();
}
