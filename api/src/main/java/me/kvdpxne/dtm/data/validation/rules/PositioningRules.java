package me.kvdpxne.dtm.data.validation.rules;

import me.kvdpxne.dtm.UnsubstantiatedInitializationError;

/**
 * Defines validation boundaries for positioning and orientation values, primarily for Minecraft environments.
 * Contains constants representing valid ranges for coordinates, angles, and dimension-specific height limits.
 * <p>
 * This class serves as a static utility container for positioning validation rules and cannot be instantiated.
 *
 * @author Łukasz Pietrzak (kvdpxne)
 * @version 0.1.0
 * @since 0.1.0
 */
public final class PositioningRules {

  /**
   * The minimum valid pitch angle (vertical rotation) in degrees.
   * <p>
   * Represents looking straight down (-90.0°).
   *
   * @since 0.1.0
   */
  public static final float MIN_PITCH = -90.0F;

  /**
   * The maximum valid pitch angle (vertical rotation) in degrees.
   * <p>
   * Represents looking straight up (90.0°).
   *
   * @since 0.1.0
   */
  public static final float MAX_PITCH = 90.0F;

  /**
   * The minimum valid yaw angle (horizontal rotation) in degrees.
   * Represents due west (-180.0°).
   *
   * @since 0.1.0
   */
  public static final float MIN_YAW = -180.0F;

  /**
   * The maximum valid yaw angle (horizontal rotation) in degrees.
   * Represents due east (180.0°).
   *
   * @since 0.1.0
   */
  public static final float MAX_YAW = 180.0F;

  /**
   * The minimum valid coordinate value for the X and Z axes.
   * Represents the world boundary in the negative direction (-29,999,984).
   *
   * @since 0.1.0
   */
  public static final int MIN_XZ = -29_999_984;

  /**
   * The maximum valid coordinate value for the X and Z axes.
   * Represents the world boundary in the positive direction (29,999,984).
   *
   * @since 0.1.0
   */
  public static final int MAX_XZ = 29_999_984;

  /**
   * The minimum valid Y-coordinate in the Overworld dimension.
   * Represents the lowest buildable height (-64).
   *
   * @since 0.1.0
   */
  public static final int MIN_OVERWORLD_Y = -64;

  /**
   * The maximum valid Y-coordinate in the Overworld dimension.
   * Represents the highest buildable height (320).
   *
   * @since 0.1.0
   */
  public static final int MAX_OVERWORLD_Y = 320;

  /**
   * The minimum valid Y-coordinate in non-Overworld dimensions (Nether, End).
   * Represents the lowest buildable height (0).
   *
   * @since 0.1.0
   */
  public static final int MIN_OTHER_Y = 0;

  /**
   * The maximum valid Y-coordinate in non-Overworld dimensions (Nether, End).
   * Represents the highest buildable height (256).
   *
   * @since 0.1.0
   */
  public static final int MAX_OTHER_Y = 256;

  /**
   * The minimum valid Y-coordinate for legacy Overworld worlds.
   * Represents the lowest buildable height in older world formats (0).
   *
   * @since 0.1.0
   */
  public static final int MIN_OVERWORLD_LEGACY_Y = 0;

  /**
   * The maximum valid Y-coordinate for legacy Overworld worlds.
   * Represents the highest buildable height in older world formats (256).
   *
   * @since 0.1.0
   */
  public static final int MAX_OVERWORLD_LEGACY_Y = 256;

  /**
   * Private constructor to prevent instantiation.
   * <p>
   * Throws {@link UnsubstantiatedInitializationError} if invoked,
   * enforcing this class's nature as a static utility container.
   *
   * @throws UnsubstantiatedInitializationError Always thrown upon invocation
   * @since 0.1.0
   */
  private PositioningRules() {
    throw new UnsubstantiatedInitializationError(this.getClass());
  }
}
