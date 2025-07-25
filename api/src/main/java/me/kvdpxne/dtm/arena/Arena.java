package me.kvdpxne.dtm.arena;

import java.io.Serializable;
import java.util.Collection;
import java.util.UUID;
import me.kvdpxne.dtm.capabilities.Identifiable;
import me.kvdpxne.dtm.capabilities.Nameable;
import me.kvdpxne.dtm.arena.map.ArenaMap;
import me.kvdpxne.dtm.data.validation.rules.PositioningRules;
import me.kvdpxne.dtm.position.BlockPosition;
import me.kvdpxne.dtm.position.monument.MonumentPosition;
import me.kvdpxne.dtm.position.revival.RevivalPosition;
import me.kvdpxne.dtm.team.Team;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Range;
import org.jetbrains.annotations.Unmodifiable;

public interface Arena
  extends
  Identifiable<UUID>,
  Nameable,
  Serializable {

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
  String getName();

  /**
   * @since 0.1.0
   */
  @Unmodifiable
  Collection<RevivalPosition> getRevivalPositions();

  /**
   * @since 0.1.0
   */
  @Unmodifiable
  Collection<MonumentPosition> getMonumentPositions();

  /**
   * @since 0.1.0
   */
  @NotNull
  ArenaMap getMap();

  /**
   * @since 0.1.0
   */
  @NotNull
  RevivalPosition getRevivalPositionByTeam(
    @NotNull Team team
  );

  /**
   * @since 0.1.0
   */
  @Unmodifiable
  @NotNull
  Collection<MonumentPosition> getMonumentPositionsByTeam(
    @NotNull Team team
  );

  /**
   * @since 0.1.0
   */
  @Nullable
  MonumentPosition getMonumentPositionByPositionOrNull(
    @NotNull BlockPosition position
  );

  /**
   * @since 0.1.0
   */
  @Nullable
  MonumentPosition getMonumentPositionByPositionOrNull(
    @Range(from = PositioningRules.MIN_XZ, to = PositioningRules.MAX_XZ) int x,
    int z,
    int y
  );

  /**
   * @since 0.1.0
   */
  @Range(from = 0, to = Integer.MAX_VALUE)
  int getRevivalPositionsCount();

  /**
   * @since 0.1.0
   */
  @Range(from = 0, to = Integer.MAX_VALUE)
  int getMonumentPositionsCount();
}
