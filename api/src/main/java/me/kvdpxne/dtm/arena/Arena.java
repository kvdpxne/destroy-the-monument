package me.kvdpxne.dtm.arena;

import java.io.Serializable;
import java.util.Collection;
import java.util.UUID;
import me.kvdpxne.dtm.DisplayableNameable;
import me.kvdpxne.dtm.Identifiable;
import me.kvdpxne.dtm.arena.map.ArenaMap;
import me.kvdpxne.dtm.arena.settings.ArenaSettings;
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
  DisplayableNameable,
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
  @Override
  @Nullable
  String getDisplayName();

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
  ArenaSettings getSettings();

  /**
   * @since 0.1.0
   */
  @NotNull
  RevivalPosition getRevivalPositionByTeam(
    final @NotNull Team team
  );

  /**
   * @since 0.1.0
   */
  @Unmodifiable
  @NotNull
  Collection<MonumentPosition> getMonumentPositionsByTeam(
    final @NotNull Team team
  );

  /**
   * @since 0.1.0
   */
  @Nullable
  MonumentPosition getMonumentPositionByPositionOrNull(
    final @NotNull BlockPosition position
  );

  /**
   * @since 0.1.0
   */
  @Nullable
  MonumentPosition getMonumentPositionByPositionOrNull(
    final int x,
    final int y,
    final int z
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
