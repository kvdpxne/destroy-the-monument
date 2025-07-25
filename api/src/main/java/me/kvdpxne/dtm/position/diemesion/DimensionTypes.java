package me.kvdpxne.dtm.position.diemesion;

import me.kvdpxne.dtm.UnsubstantiatedInitializationError;

/**
 * Defines standard dimension type identifiers aligned with Minecraft's dimension system. These
 * constants provide a unified classification scheme for different world types across platform
 * implementations. Values correspond to Minecraft's internal dimension numbering where applicable.
 * <p>
 * <b>Usage Note:</b> These identifiers should be used when categorizing worlds
 * for game mechanics that depend on dimension-specific behavior (e.g., portal mechanics, spawn
 * rules, or environmental effects).
 *
 * @author Łukasz Pietrzak (kvdpxne)
 * @version 0.1.0
 * @since 0.1.0
 */
public final class DimensionTypes {

  /**
   * Identifier for the Overworld dimension (standard surface dimension). Corresponds to Minecraft's
   * default dimension with ID 0.
   *
   * @since 0.1.0
   */
  public static final byte OVERWORLD = 0;

  /**
   * Identifier for the Nether dimension (hell-like dimension). Corresponds to Minecraft's nether
   * dimension with ID -1.
   *
   * @since 0.1.0
   */
  public static final byte NETHER = 1;

  /**
   * Identifier for the End dimension (floating islands dimension). Corresponds to Minecraft's end
   * dimension with ID 1.
   *
   * @since 0.1.0
   */
  public static final byte THE_END = 2;

  /**
   * Identifier for unknown or unsupported dimension types. This value should be used when a
   * dimension doesn't match standard Minecraft types or when dimension typing is unavailable. The
   * value {@code Byte.MAX_VALUE} (127) is chosen to avoid conflicts with potential future standard
   * dimension types.
   *
   * @since 0.1.0
   */
  public static final byte UNKNOWN = Byte.MAX_VALUE;

  /**
   * Private constructor to enforce non-instantiability. Throws an
   * {@link UnsubstantiatedInitializationError} if instantiation is attempted, reinforcing this
   * class's nature as a static constant holder.
   *
   * @since 0.1.0
   */
  private DimensionTypes() {
    throw new UnsubstantiatedInitializationError(DimensionTypes.class);
  }
}
