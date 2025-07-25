package me.kvdpxne.dtm.capabilities;

import java.io.Serializable;
import org.jetbrains.annotations.Nullable;

/**
 * Represents entities that can be persisted to durable storage systems. Defines the fundamental
 * capability for persistence operations by providing identifier-based state management. This
 * interface establishes the foundation for distinguishing between transient and persistent entities
 * within the system.
 * <p>
 * <b>Persistence State Contract:</b>
 * <ul>
 *   <li>{@code null} identifier indicates a new/unsaved entity</li>
 *   <li>Non-null identifier represents a previously persisted entity</li>
 *   <li>Identifier must remain immutable after persistence operations</li>
 * </ul>
 *
 * @param <T> The serializable identifier type (e.g., UUID, Integer)
 * @author Łukasz Pietrzak (kvdpxne)
 * @version 0.1.0
 * @since 0.1.0
 */
public interface Persistable<T extends Serializable> {

  /**
   * Retrieves the persistent identifier associated with this entity. Returns {@code null} for
   * entities not yet persisted to storage.
   *
   * @return Persistent identifier or {@code null} for unsaved entities
   * @since 0.1.0
   */
  @Nullable
  T getIdentifier();

  /**
   * Determines whether this entity requires initial persistence. Equivalent to
   * {@code getIdentifier() == null}.
   *
   * @return {@code true} if unsaved/transient, {@code false} if persistent
   * @since 0.1.0
   */
  default boolean isNew() {
    return null == this.getIdentifier();
  }
}