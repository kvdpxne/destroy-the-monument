package me.kvdpxne.dtm.position;

import me.kvdpxne.dtm.data.validation.rules.PositioningRules;
import me.kvdpxne.dtm.position.diemesion.Dimension;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Range;

/**
 * Represents a precise entity-aligned position with floating-point coordinates and orientation.
 * Provides sub-block precision and entity rotation angles for accurate entity placement.
 * <p>
 * <b>Coordinate Ranges:</b>
 * <ul>
 *   <li>X/Z: [{@value PositioningRules#MIN_XZ}, {@value PositioningRules#MAX_XZ}] (±29,999,984)</li>
 *   <li>Y: [{@value PositioningRules#MIN_OVERWORLD_Y}, {@value PositioningRules#MAX_OVERWORLD_Y}] (-64 to 320)</li>
 * </ul>
 * <b>Orientation Ranges:</b>
 * <ul>
 *   <li>Pitch: [{@value PositioningRules#MIN_PITCH}, {@value PositioningRules#MAX_PITCH}] (-90.0° to 90.0°)</li>
 *   <li>Yaw: [{@value PositioningRules#MIN_YAW}, {@value PositioningRules#MAX_YAW}] (-180.0° to 180.0°)</li>
 * </ul>
 *
 * @author Łukasz Pietrzak (kvdpxne)
 * @version 0.1.0
 * @since 0.1.0
 */
public interface EntityPosition
  extends Position {

  /**
   * Retrieves the dimension associated with this entity position.
   *
   * @return The associated dimension or {@code null} if dimensionless
   * @since 0.1.0
   */
  @Override
  @Nullable
  Dimension getDimensionOrNull();

  /**
   * Gets the precise X-coordinate of this entity position.
   * Value is constrained to Minecraft's world boundaries.
   *
   * @return X-coordinate between {@value PositioningRules#MIN_XZ} and {@value PositioningRules#MAX_XZ}
   * @since 0.1.0
   */
  @Range(from = PositioningRules.MIN_XZ, to = PositioningRules.MAX_XZ)
  double getX();

  /**
   * Gets the precise Z-coordinate of this entity position.
   * Value is constrained to Minecraft's world boundaries.
   *
   * @return Z-coordinate between {@value PositioningRules#MIN_XZ} and {@value PositioningRules#MAX_XZ}
   * @since 0.1.0
   */
  @Range(from = PositioningRules.MIN_XZ, to = PositioningRules.MAX_XZ)
  double getZ();

  /**
   * Gets the precise Y-coordinate (height) of this entity position.
   * Value conforms to dimension-specific height limits.
   *
   * @return Y-coordinate between {@value PositioningRules#MIN_OVERWORLD_Y} and {@value PositioningRules#MAX_OVERWORLD_Y}
   * @since 0.1.0
   */
  @Range(from = PositioningRules.MIN_OVERWORLD_Y, to = PositioningRules.MAX_OVERWORLD_Y)
  double getY();

  /**
   * Gets the vertical rotation (pitch) of this entity.
   * Represents looking up/down angle.
   *
   * @return Pitch angle between {@value PositioningRules#MIN_PITCH}° and {@value PositioningRules#MAX_PITCH}°
   * @since 0.1.0
   */
  @Range(from = (long) PositioningRules.MIN_PITCH, to = (long) PositioningRules.MAX_PITCH)
  float getPitch();

  /**
   * Gets the horizontal rotation (yaw) of this entity.
   * Represents compass direction angle.
   *
   * @return Yaw angle between {@value PositioningRules#MIN_YAW}° and {@value PositioningRules#MAX_YAW}°
   * @since 0.1.0
   */
  @Range(from = (long) PositioningRules.MIN_YAW, to = (long) PositioningRules.MAX_YAW)
  float getYaw();

  /**
   * Indicates whether this position supports dimension-aware operations.
   *
   * @return {@code true} if multidimensional, {@code false} otherwise
   * @since 0.1.0
   */
  @Override
  boolean isMultidimensional();

  /**
   * Determines if this position is within a spherical radius of specified coordinates.
   * Uses 3D Euclidean distance calculation for proximity check.
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
    @Range(from = PositioningRules.MIN_XZ, to = PositioningRules.MAX_XZ) double x,
    @Range(from = PositioningRules.MIN_XZ, to = PositioningRules.MAX_XZ) double z,
    @Range(from = PositioningRules.MIN_OVERWORLD_Y, to = PositioningRules.MAX_OVERWORLD_Y) double y,
    @Range(from = 0, to = Integer.MAX_VALUE) double radius
  );

  /**
   * Determines if this position is within a spherical radius of another entity position.
   * Considers dimension equality if both positions are multidimensional.
   *
   * @param position Entity position to compare (must not be null)
   * @param radius Proximity radius (non-negative)
   * @return {@code true} if within specified radius and same dimension (if applicable), {@code false} otherwise
   * @throws NullPointerException If {@code position} is null
   * @throws IllegalArgumentException If radius is negative
   * @since 0.1.0
   */
  boolean isNear(
    @NotNull EntityPosition position,
    @Range(from = 0, to = Integer.MAX_VALUE) double radius
  );

  /**
   * Creates a deep copy of this entity position including orientation.
   *
   * @return New independent entity position instance
   * @since 0.1.0
   */
  @Override
  @NotNull
  EntityPosition copy();
}
