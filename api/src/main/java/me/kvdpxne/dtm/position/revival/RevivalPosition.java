package me.kvdpxne.dtm.position.revival;

import java.util.UUID;
import me.kvdpxne.dtm.capabilities.Identifiable;
import me.kvdpxne.dtm.position.EntityPosition;
import me.kvdpxne.dtm.position.diemesion.Dimension;
import me.kvdpxne.dtm.team.Team;
import me.kvdpxne.dtm.team.Teamable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Range;

public interface RevivalPosition
  extends
  EntityPosition,
  Identifiable<UUID>,
  Teamable {

  /**
   * @since 0.1.0
   */
  @Override
  @NotNull
  UUID getIdentifier();

  /**
   * @since 0.1.0
   */
  @Override
  @NotNull
  Team getTeam();

  /**
   * @since 0.1.0
   */
  @Override
  @Nullable
  Dimension getDimensionOrNull();

  /**
   * @since 0.1.0
   */
  @Override
  @Range(from = Integer.MIN_VALUE, to = Integer.MAX_VALUE)
  double getX();

  /**
   * @since 0.1.0
   */
  @Override
  double getY();

  /**
   * @since 0.1.0
   */
  @Override
  @Range(from = Integer.MIN_VALUE, to = Integer.MAX_VALUE)
  double getZ();

  /**
   * @since 0.1.0
   */
  @Override
  float getPitch();

  /**
   * @since 0.1.0
   */
  @Override
  float getYaw();

  /**
   * @since 0.1.0
   */
  @Override
  boolean isMultidimensional();

  /**
   * @since 0.1.0
   */
  @Override
  boolean isNear(double x, double y, double z, double radius);

  /**
   * @since 0.1.0
   */
  @Override
  boolean isNear(EntityPosition position, double radius);

  /**
   * @since 0.1.0
   */
  @Override
  @NotNull
  EntityPosition copy();
}
