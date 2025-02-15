package me.kvdpxne.dtm.arena;

import java.util.Collection;
import me.kvdpxne.dtm.position.monument.MonumentPosition;
import me.kvdpxne.dtm.position.revival.RevivalPosition;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
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
    final @NotNull CharSequence identifier
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
    final @NotNull CharSequence identifier
  );

  /**
   * @throws NullPointerException
   * @throws IllegalArgumentException
   *
   * @since 0.1.0
   */
  @Nullable
  Arena findArenaByNameOrNull(
    final @NotNull CharSequence name
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
    final @NotNull CharSequence name
  );

  void insertArena(final Arena arena);

  void insertArenaRevivalPosition(final Arena arena, final RevivalPosition revivalPosition);

  void insertArenaMonumentPosition(final Arena arena, final MonumentPosition monumentPosition);

  void updateArena(final Arena arena);

  void deleteArenaByIdentifier(final CharSequence identifier);

  void deleteArenas();

  long countArenas();
}
