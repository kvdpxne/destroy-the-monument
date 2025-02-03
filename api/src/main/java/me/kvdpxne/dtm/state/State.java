package me.kvdpxne.dtm.state;

/**
 * Represents the state of an object, specifically whether it has been modified
 * since its initial retrieval or creation.
 * <p>
 * This interface is particularly useful for tracking changes to objects
 * retrieved from a database or freshly created objects that are not yet
 * persisted.
 *
 * @since 0.1.0
 */
public interface State {

  /**
   * Determines if the object has been modified.
   * <p>
   * For example, this could check if a field like {@code name} has been changed
   * since the object was fetched from a database or if the object was newly
   * created and does not yet exist in the database.
   *
   * @return {@code true} if the object has been modified; {@code false}
   * otherwise.
   * @since 0.1.0
   */
  boolean wasModified();
}
