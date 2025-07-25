package me.kvdpxne.dtm.arena;

import java.util.Collection;
import java.util.UUID;
import me.kvdpxne.dtm.position.monument.MonumentPosition;
import me.kvdpxne.dtm.position.revival.RevivalPosition;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Range;
import org.jetbrains.annotations.Unmodifiable;

public interface ArenaService {

  /**
   * @since 0.1.0
   */
  @Unmodifiable
  @NotNull
  Collection<Arena> findArenas();

  /**
   * @throws NullPointerException
   * @throws IllegalArgumentException
   *
   * @since 0.1.0
   */
  @Nullable
  Arena findArenaByIdentifierOrNull(
    @NotNull UUID identifier
  );

  /**
   * @throws NullPointerException
   * @throws IllegalArgumentException
   * @throws ArenaNoFoundException
   *
   * @since 0.1.0
   */
  @NotNull
  Arena findArenaByIdentifier(
    @NotNull UUID identifier
  );

  /**
   * @throws NullPointerException
   * @throws IllegalArgumentException
   *
   * @since 0.1.0
   */
  @Nullable
  Arena findArenaByNameOrNull(
    @NotNull UUID name
  );

  /**
   * @throws NullPointerException
   * @throws IllegalArgumentException
   * @throws ArenaNoFoundException
   *
   * @since 0.1.0
   */
  @NotNull
  Arena findArenaByName(
    @NotNull String name
  );

  void insertArena(
    @NotNull Arena arena
  );

  void updateArenaRevivalPosition(
    @NotNull Arena arena,
    @NotNull RevivalPosition revivalPosition
  );

  void updateArenaMonumentPosition(
    @NotNull Arena arena,
    @NotNull MonumentPosition monumentPosition
  );

  void updateArena(
    @NotNull Arena arena
  );

  void deleteArenaByIdentifier(
    @NotNull UUID identifier
  );

  void deleteArenas();

  @Range(from = 0, to = Long.MAX_VALUE)
  long countArenas();
}
