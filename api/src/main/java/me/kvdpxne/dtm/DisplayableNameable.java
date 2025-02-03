package me.kvdpxne.dtm;

import org.jetbrains.annotations.Nullable;

/**
 * Extends the {@link Nameable} interface to include a display name.
 * <p>
 * The {@code DisplayableNameable} interface allows entities to have both a
 * technical name and a more user-friendly display name.
 *
 * @since 0.1.0
 */
public interface DisplayableNameable
  extends Nameable {

  /**
   * Retrieves the display name associated with this entity.
   * <p>
   * This name is typically a user-friendly representation of the entity.
   *
   * @return a {@link String} representing the display name, or {@code null} if
   * no display name is set.
   * @since 0.1.0
   */
  @Nullable
  String getDisplayName();
}
