package me.kvdpxne.dtm.game;

import java.util.Collection;
import java.util.UUID;
import me.kvdpxne.dtm.capabilities.Identifiable;
import me.kvdpxne.dtm.capabilities.Nameable;
import me.kvdpxne.dtm.arena.Arena;
import me.kvdpxne.dtm.team.Team;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Range;
import org.jetbrains.annotations.UnmodifiableView;

public interface Game
  extends
  Identifiable<UUID>,
  Nameable,
  LocalGameProvider {

  @NotNull
  @Override
  UUID getIdentifier();

  @NotNull
  @Override
  String getName();

  @NotNull
  GameSettings getSettings();

  @NotNull
  @UnmodifiableView
  Collection<@NotNull Arena> getArenas();

  @NotNull
  @UnmodifiableView
  Collection<@NotNull Team> getTeams();

  @Range(from = 0, to = Integer.MAX_VALUE)
  int getNumberOfArenas();

  @Range(from = 0, to = Integer.MAX_VALUE)
  int getNumberOfTeams();

  boolean hasArena(
    final @NotNull Arena arena
  );

  boolean hasArenaByIdentifier(
    final @NotNull UUID identifier
  );

  boolean hasTeam(
    final @NotNull Team team
  );

  boolean hasTeamByIdentifier(
    final @NotNull UUID identifier
  );

  default boolean isLocal() {
    return false;
  }

  @NotNull
  @Override
  default LocalGame toLocalGame() {
    final LocalGame local = this.toLocal();
    if (null == local) {
      throw new LocalGameException(
        "The game cannot be converted to a local game."
      );
    }
    return local;
  }

  @Nullable
  LocalGame toLocal();
}
