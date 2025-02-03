package me.kvdpxne.dtm.position;

import java.util.UUID;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Range;

/**
 * @since 0.1.0
 */
public interface EntityPosition
  extends Position {

  /**
   * @since 0.1.0
   */
  @Override
  @NotNull
  UUID getWorldIdentifier();

  /**
   * @since 0.1.0
   */
  @Override
  @Nullable
  Object getWorldOrNull();

  /**
   * @since 0.1.0
   */
  @Range(from = Integer.MIN_VALUE, to = Integer.MAX_VALUE)
  double getX();

  /**
   * @since 0.1.0
   */
  double getY();

  /**
   * @since 0.1.0
   */
  @Range(from = Integer.MIN_VALUE, to = Integer.MAX_VALUE)
  double getZ();

  /**
   * @since 0.1.0
   */
  float getPitch();

  /**
   * @since 0.1.0
   */
  float getYaw();

  /**
   * @since 0.1.0
   */
  boolean isNear(
    final double x,
    final double y,
    final double z,
    final double radius
  );

  /**
   * @since 0.1.0
   */
  boolean isNear(
    final EntityPosition position,
    final double radius
  );

  @Override
  @NotNull
  EntityPosition copy();
}
