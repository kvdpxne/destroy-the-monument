package me.kvdpxne.dtm;

import org.jetbrains.annotations.NotNull;

/**
 * Represents an entity that can be identified by a name.
 * <p>
 * Classes implementing the {@code Nameable} interface must provide a method to
 * retrieve their name.
 *
 * @since 0.1.0
 */
public interface Nameable {

  /**
   * Retrieves the name associated with this entity.
   *
   * @return a {@link String} representing the name of the entity. This value is
   * guaranteed to be non-null.
   * @since 0.1.0
   */
  @NotNull
  String getName();
}
