package me.kvdpxne.dtm.state;

/**
 * Represents a mutable state that tracks whether an object has been modified.
 * This interface extends {@link State} and allows for explicitly setting or
 * toggling the modified state of the object.
 * <p>
 * Useful in scenarios where changes to an object need to be tracked and
 * controlled, such as managing persistence layers or undo/redo operations.
 *
 * @since 0.1.0
 */
public interface MutableState
  extends State {

  /**
   * Checks if the object has been modified.
   * <p>
   * This overrides the method from {@link State} to retain its contract while
   * being part of a mutable state interface.
   *
   * @return {@code true} if the object has been modified; {@code false}
   * otherwise.
   * @since 0.1.0
   */
  @Override
  boolean wasModified();

  /**
   * Sets the modified state of the object explicitly.
   *
   * @param modified the new modified state to set.
   * @since 0.1.0
   */
  void setModified(
    final boolean modified
  );

  /**
   * Marks the object as modified.
   * <p>
   * This is a convenience method to set the modified state to {@code true}.
   *
   * @since 0.1.0
   */
  void markAsModified();

  /**
   * Unmarks the object as modified.
   * <p>
   * This is a convenience method to set the modified state to {@code false}.
   *
   * @since 0.1.0
   */
  void unmarkAsModified();
}
