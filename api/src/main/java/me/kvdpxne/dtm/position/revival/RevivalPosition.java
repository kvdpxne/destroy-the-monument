package me.kvdpxne.dtm.position.revival;

import java.util.UUID;
import me.kvdpxne.dtm.Identifiable;
import me.kvdpxne.dtm.position.EntityPosition;
import me.kvdpxne.dtm.position.PositionMultidimensionalException;
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
   * @throws PositionMultidimensionalException
   * @since 0.1.0
   */
  @Override
  @NotNull
  UUID getWorldIdentifier();

  /**
   * @throws PositionMultidimensionalException
   * @since 0.1.0
   */
  @Override
  @NotNull
  String getWorldName();

  /**
   * @throws PositionMultidimensionalException
   * @since 0.1.0
   */
  @Override
  @Nullable
  Object getWorldOrNull();

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
  boolean isNear(final double x, final double y, final double z, final double radius);

  /**
   * @since 0.1.0
   */
  @Override
  boolean isNear(final EntityPosition position, final double radius);

  /**
   * @throws PositionMultidimensionalException
   * @since 0.1.0
   */
  @Override
  boolean sameWorld(final @NotNull UUID worldIdentifier);

  /**
   * @throws PositionMultidimensionalException
   * @since 0.1.0
   */
  @Override
  boolean sameWorld(final @NotNull String worldName);

  /**
   * @throws PositionMultidimensionalException
   * @since 0.1.0
   */
  @Override
  boolean sameWorld(final @NotNull Object world);

  /**
   * @throws PositionMultidimensionalException
   * @since 0.1.0
   */
  @Override
  boolean sameWorld(final @NotNull Dimension dimension);

  /**
   * @since 0.1.0
   */
  @Override
  @NotNull
  EntityPosition copy();
}
