package me.kvdpxne.dtm.position;

import me.kvdpxne.dtm.capabilities.Copyable;
import me.kvdpxne.dtm.position.diemesion.Dimension;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents a coordinate point within a game world, potentially spanning multiple dimensions.
 * Provides a foundation for both block-based and entity-based positions with support for dimension-aware
 * operations. Implementations must ensure coordinate consistency and proper dimension handling.
 * <p>
 * <b>Implementation Note:</b> Positions may be either single-dimension (traditional coordinates)
 * or multidimensional (supporting cross-dimension operations).
 *
 * @author Łukasz Pietrzak (kvdpxne)
 * @version 0.1.0
 * @since 0.1.0
 */
public interface Position extends Copyable<Position> {

  /**
   * Retrieves the dimension associated with this position, if available.
   * Returns {@code null} if the position exists in a dimension-less context or
   * when dimension information is unavailable.
   *
   * @return The associated dimension or {@code null} if not applicable
   * @since 0.1.0
   */
  @Nullable
  Dimension getDimensionOrNull();

  /**
   * Determines whether this position exists within a multidimensional context.
   * Returns {@code true} if dimension-aware operations are supported, {@code false}
   * for traditional single-dimension positions.
   *
   * @return {@code true} for multidimensional positions, {@code false} otherwise
   * @since 0.1.0
   */
  boolean isMultidimensional();

  /**
   * Creates a deep copy of this position instance. The copy must maintain identical
   * coordinate values and dimension associations while being an independent instance.
   *
   * @return A new, independent position instance with identical properties
   * @since 0.1.0
   */
  @Override
  @NotNull
  Position copy();
}
