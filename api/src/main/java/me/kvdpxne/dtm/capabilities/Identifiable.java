package me.kvdpxne.dtm.capabilities;

import java.io.Serializable;
import org.jetbrains.annotations.NotNull;

/**
 * Represents entities guaranteed to possess a non-null, unique identifier. Extends
 * {@link Persistable} with the strict requirement of always having a defined identifier,
 * distinguishing it from potentially transient entities. Suitable for domain objects that require
 * strong identity guarantees.
 * <p>
 * <b>Identity Guarantee Contract:</b>
 * <ul>
 *   <li>Identifier must be globally unique within the entity domain</li>
 *   <li>Identifier must be immutable throughout the entity lifecycle</li>
 *   <li>Identifier must be assigned prior to entity instantiation</li>
 *   <li>Entities are always considered persisted (non-transient)</li>
 * </ul>
 * <p>
 * <b>Important:</b> This interface explicitly prohibits the concept of "new/unsaved" state
 * through its {@code isNew()} implementation, as identifiable entities must always have
 * pre-assigned identifiers before instantiation.
 *
 * @param <T> The serializable identifier type (e.g., UUID, Long)
 * @author Łukasz Pietrzak (kvdpxne)
 * @version 0.1.0
 * @since 0.1.0
 */
public interface Identifiable<T extends Serializable> extends Persistable<T> {

  /**
   * Retrieves the immutable unique identifier for this entity. Unlike {@link Persistable},
   * implementations must always return a non-null value conforming to the uniqueness constraints.
   *
   * @return The non-null unique identifier
   * @since 0.1.0
   */
  @Override
  @NotNull
  T getIdentifier();

  /**
   * Always throws {@code UnsupportedOperationException} since identifiable entities
   * cannot exist in a "new/unsaved" state. This method is intentionally disabled
   * to enforce the contract that identifiable entities must have pre-assigned identifiers.
   *
   * @throws UnsupportedOperationException Always thrown when invoked
   * @since 0.1.0
   */
  @Override
  default boolean isNew() {
    throw new UnsupportedOperationException(
      "Identifiable entities cannot be in 'new' state - identifiers must be pre-assigned"
    );
  }
}