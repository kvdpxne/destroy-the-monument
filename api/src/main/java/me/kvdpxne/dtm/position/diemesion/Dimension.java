package me.kvdpxne.dtm.position.diemesion;

import java.io.Serializable;
import java.util.UUID;
import me.kvdpxne.dtm.capabilities.Copyable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

/**
 * Represents a game dimension or world within a platform-agnostic execution environment. Provides a
 * unified interface for accessing dimension properties and performing world identity comparisons
 * across different representations. Implementations must ensure consistent behavior for
 * serialization/deserialization operations and support both UUID-based and name-based world
 * identification. Implementations should handle platform-specific world objects while maintaining
 * cross-platform compatibility.
 * <p>
 * <b>Thread Safety:</b> Implementations must be immutable or thread-safe if
 * shared across threads.
 *
 * @author Łukasz Pietrzak (kvdpxne)
 * @version 0.1.0
 * @since 0.1.0
 */
public interface Dimension
  extends
  Copyable<Dimension>,
  Serializable {

  /**
   * Retrieves the universally unique identifier (UUID) associated with this dimension. The UUID
   * provides a persistent, platform-independent identifier that remains consistent across server
   * restarts and world reloads.
   *
   * @return The dimension's non-null UUID identifier
   * @since 0.1.0
   */
  @NotNull
  UUID getWorldIdentifier();

  /**
   * Retrieves the canonical name of this dimension. The name should match the world's storage
   * directory name and remain consistent across platform implementations.
   *
   * @return The non-null, case-sensitive world name
   * @since 0.1.0
   */
  @NotNull
  String getWorldName();

  /**
   * Retrieves the platform-specific world object representation. This method provides access to
   * native API objects (e.g., Bukkit's {@code World} or Sponge's {@code ServerWorld}).
   * Implementations must document the concrete return type for their target platform.
   *
   * @return The non-null platform-specific world object
   * @throws UnsupportedOperationException If the underlying platform doesn't expose world objects
   *                                       or if called in a context where platform objects are
   *                                       unavailable (e.g., during serialization)
   * @since 0.1.0
   */
  @NotNull
  Object getWorld();

  /**
   * Returns the classification type of this dimension according to standard Minecraft dimension
   * categories. The value should correspond to one of the constants defined in
   * {@link DimensionTypes}.
   *
   * @return A byte constant representing the dimension type, ranging from
   * {@link DimensionTypes#OVERWORLD} to {@link DimensionTypes#UNKNOWN}
   * @throws UnsupportedOperationException If dimension typing is not implemented for the current
   *                                       platform configuration
   * @since 0.1.0
   */
  @Range(from = DimensionTypes.OVERWORLD, to = DimensionTypes.UNKNOWN)
  byte getType();

  /**
   * Determines whether this dimension represents the same physical world as the specified UUID
   * identifier. Comparison should be based on exact UUID matching.
   *
   * @param worldIdentifier The UUID to compare against (must not be null)
   * @return {@code true} if the UUIDs match exactly, {@code false} otherwise
   * @throws NullPointerException If {@code worldIdentifier} is {@code null}
   * @since 0.1.0
   */
  boolean sameWorld(
    @NotNull UUID worldIdentifier
  );

  /**
   * Determines whether this dimension represents the same physical world as the specified world
   * name. Comparison should be case-sensitive and match exact storage names.
   *
   * @param worldName The world name to compare against (must not be null)
   * @return {@code true} if names match exactly (case-sensitive), {@code false} otherwise
   * @throws NullPointerException     If {@code worldName} is {@code null}
   * @throws IllegalArgumentException If {@code worldName} is empty or contains only whitespace
   * @since 0.1.0
   */
  boolean sameWorld(
    @NotNull String worldName
  );

  /**
   * Determines whether this dimension represents the same physical world as the specified
   * platform-specific world object. Implementations must perform deep equivalence checks
   * appropriate to the platform's world object model.
   *
   * @param world The platform-specific world object to compare (must not be null)
   * @return {@code true} if objects represent the same world, {@code false} otherwise
   * @throws NullPointerException          If {@code world} is {@code null}
   * @throws IllegalArgumentException      If {@code world} is not a valid platform world object
   * @throws UnsupportedOperationException If platform object comparison is not implemented
   * @since 0.1.0
   */
  boolean sameWorld(
    @NotNull Object world
  );

  /**
   * Determines whether this dimension represents the same physical world as another
   * {@code Dimension} instance. Comparison should use the most reliable available identifier
   * (preferably UUID).
   *
   * @param dimension The dimension instance to compare against (must not be null)
   * @return {@code true} if both instances represent the same world, {@code false} otherwise
   * @throws NullPointerException If {@code dimension} is {@code null}
   * @since 0.1.0
   */
  boolean sameWorld(
    @NotNull Dimension dimension
  );

  /**
   * Creates a deep copy of this dimension instance. The copy must be functionally equivalent but
   * independent of the original, with no shared mutable state.
   *
   * @return A new, independent {@code Dimension} instance with identical properties
   * @since 0.1.0
   */
  @Override
  @NotNull
  Dimension copy();
}
