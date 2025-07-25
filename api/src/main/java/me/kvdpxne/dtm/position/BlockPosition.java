package me.kvdpxne.dtm.position;

import me.kvdpxne.dtm.data.validation.rules.PositioningRules;
import me.kvdpxne.dtm.position.diemesion.Dimension;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Range;

/**
 * Represents a discrete block-aligned position within a game world. Provides integer-based
 * coordinates conforming to Minecraft's world boundaries and dimension-specific height limits.
 * Supports proximity checks and containment verification within defined coordinate ranges.
 * <p>
 * <b>Coordinate Ranges:</b>
 * <ul>
 *   <li>X/Z: [{@value PositioningRules#MIN_XZ}, {@value PositioningRules#MAX_XZ}] (±29,999,984)</li>
 *   <li>Y: [{@value PositioningRules#MIN_OVERWORLD_Y}, {@value PositioningRules#MAX_OVERWORLD_Y}] (-64 to 320)</li>
 * </ul>
 *
 * @author Łukasz Pietrzak (kvdpxne)
 * @version 0.1.0
 * @since 0.1.0
 */
public interface BlockPosition
  extends Position {

  /**
   * Retrieves the dimension associated with this block position.
   *
   * @return The associated dimension or {@code null} if dimensionless
   * @since 0.1.0
   */
  @Override
  @Nullable
  Dimension getDimensionOrNull();

  /**
   * Gets the discrete X-coordinate of this block position.
   * Value is constrained to Minecraft's world boundaries.
   *
   * @return X-coordinate between {@value PositioningRules#MIN_XZ} and {@value PositioningRules#MAX_XZ}
   * @since 0.1.0
   */
  @Range(from = PositioningRules.MIN_XZ, to = PositioningRules.MAX_XZ)
  int getX();

  /**
   * Gets the discrete Z-coordinate of this block position.
   * Value is constrained to Minecraft's world boundaries.
   *
   * @return Z-coordinate between {@value PositioningRules#MIN_XZ} and {@value PositioningRules#MAX_XZ}
   * @since 0.1.0
   */
  @Range(from = PositioningRules.MIN_XZ, to = PositioningRules.MAX_XZ)
  int getZ();

  /**
   * Gets the discrete Y-coordinate (height) of this block position.
   * Value conforms to dimension-specific height limits.
   *
   * @return Y-coordinate between {@value PositioningRules#MIN_OVERWORLD_Y} and {@value PositioningRules#MAX_OVERWORLD_Y}
   * @since 0.1.0
   */
  @Range(from = PositioningRules.MIN_OVERWORLD_Y, to = PositioningRules.MAX_OVERWORLD_Y)
  int getY();

  /**
   * Indicates whether this position supports dimension-aware operations.
   *
   * @return {@code true} if multidimensional, {@code false} otherwise
   * @since 0.1.0
   */
  @Override
  boolean isMultidimensional();

  /**
   * Determines if this position is within a spherical radius of another coordinate.
   * Uses Euclidean distance calculation for proximity check.
   *
   * @param x X-coordinate to compare (must be within world boundaries)
   * @param z Z-coordinate to compare (must be within world boundaries)
   * @param y Y-coordinate to compare (must be within height limits)
   * @param radius Proximity radius (non-negative)
   * @return {@code true} if within specified radius, {@code false} otherwise
   * @throws IllegalArgumentException If coordinates exceed valid ranges or radius is negative
   * @since 0.1.0
   */
  boolean isNear(
    @Range(from = PositioningRules.MIN_XZ, to = PositioningRules.MAX_XZ) int x,
    @Range(from = PositioningRules.MIN_XZ, to = PositioningRules.MAX_XZ) int z,
    @Range(from = PositioningRules.MIN_OVERWORLD_Y, to = PositioningRules.MAX_OVERWORLD_Y) int y,
    @Range(from = 0, to = Integer.MAX_VALUE) int radius
  );

  /**
   * Determines if this position is within a spherical radius of another block position.
   * Considers dimension equality if both positions are multidimensional.
   *
   * @param position Block position to compare (must not be null)
   * @param radius Proximity radius (non-negative)
   * @return {@code true} if within specified radius and same dimension (if applicable), {@code false} otherwise
   * @throws NullPointerException If {@code position} is null
   * @throws IllegalArgumentException If radius is negative
   * @since 0.1.0
   */
  boolean isNear(
    @NotNull BlockPosition position,
    @Range(from = 0, to = Integer.MAX_VALUE) int radius
  );

  /**
   * Determines if this position matches exactly with specified coordinates.
   *
   * @param x X-coordinate to compare (must be within world boundaries)
   * @param z Z-coordinate to compare (must be within world boundaries)
   * @param y Y-coordinate to compare (must be within height limits)
   * @return {@code true} if coordinates match exactly, {@code false} otherwise
   * @throws IllegalArgumentException If coordinates exceed valid ranges
   * @since 0.1.0
   */
  boolean isIn(
    @Range(from = PositioningRules.MIN_XZ, to = PositioningRules.MAX_XZ) int x,
    @Range(from = PositioningRules.MIN_XZ, to = PositioningRules.MAX_XZ) int z,
    @Range(from = PositioningRules.MIN_OVERWORLD_Y, to = PositioningRules.MAX_OVERWORLD_Y) int y
  );

  /**
   * Determines if this position matches exactly with another block position.
   * Considers dimension equality if both positions are multidimensional.
   *
   * @param position Block position to compare (must not be null)
   * @return {@code true} if coordinates and dimension match exactly, {@code false} otherwise
   * @throws NullPointerException If {@code position} is null
   * @since 0.1.0
   */
  boolean isIn(
    @NotNull BlockPosition position
  );

  /**
   * Creates a deep copy of this block position.
   *
   * @return New independent block position instance
   * @since 0.1.0
   */
  @Override
  @NotNull
  BlockPosition copy();
}
