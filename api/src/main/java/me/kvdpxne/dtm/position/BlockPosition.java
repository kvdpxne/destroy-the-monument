package me.kvdpxne.dtm.position;

import java.util.UUID;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Range;

/**
 * @since 0.1.0
 */
public interface BlockPosition
  extends Position {

  @Override
  @NotNull
  UUID getWorldIdentifier();

  @Override
  @Nullable
  Object getWorldOrNull();

  /**
   * @since 0.1.0
   */
  @Range(from = Integer.MIN_VALUE, to = Integer.MAX_VALUE)
  int getX();

  /**
   * @since 0.1.0
   */
  int getY();

  /**
   * @since 0.1.0
   */
  @Range(from = Integer.MIN_VALUE, to = Integer.MAX_VALUE)
  int getZ();

  /**
   * @since 0.1.0
   */
  boolean isNear(
    final int x,
    final int y,
    final int z,
    final int radius
  );

  /**
   * @since 0.1.0
   */
  boolean isNear(
    final BlockPosition position,
    final int radius
  );

  /**
   * @since 0.1.0
   */
  boolean isIn(
    final @Range(from = Integer.MIN_VALUE, to = Integer.MAX_VALUE) int x,
    final int y,
    final @Range(from = Integer.MIN_VALUE, to = Integer.MAX_VALUE) int z
  );

  /**
   * @since 0.1.0
   */
  boolean isIn(
    final BlockPosition position
  );

  @Override
  @NotNull
  BlockPosition copy();
}
