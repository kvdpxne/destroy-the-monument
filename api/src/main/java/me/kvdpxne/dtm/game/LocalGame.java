package me.kvdpxne.dtm.game;

import java.util.Collection;
import java.util.UUID;
import me.kvdpxne.dtm.Local;
import me.kvdpxne.dtm.arena.Arena;
import me.kvdpxne.dtm.arena.map.ArenaMap;
import me.kvdpxne.dtm.arena.voting.ArenaVotingRegistry;
import me.kvdpxne.dtm.team.LocalTeam;
import me.kvdpxne.dtm.team.teammate.Teammate;
import me.kvdpxne.dtm.user.LocalUser;
import me.kvdpxne.dtm.user.LocalUserProvider;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Range;
import org.jetbrains.annotations.UnmodifiableView;

public interface LocalGame extends Game, Local {

  @NotNull
  @UnmodifiableView
  Collection<@NotNull LocalUser> getParticipants();

  @NotNull
  @UnmodifiableView
  Collection<@NotNull Teammate> getTeammates();

  @NotNull
  LocalTeam getSmallestTeam();

  @NotNull
  LocalTeam getLargestTeam();

  @NotNull
  LocalTeam getRandomTeam();

  @NotNull
  LocalTeam getTeamByDefaultCriteria();

  @Nullable
  ArenaVotingRegistry getVotingRegistry();

  @Nullable
  Arena getCurrentArena();

  @Nullable
  ArenaMap getCurrentArenaMap();

  @Range(from = 0, to = Integer.MAX_VALUE)
  int getState();

  @Range(from = 0, to = Integer.MAX_VALUE)
  int getNumberOfSpectators();

  @Range(from = 0, to = Integer.MAX_VALUE)
  int getNumberOfParticipants();

  @Range(from = 0, to = Integer.MAX_VALUE)
  int getNumberOfTeammates();

  boolean isTeamsSameSize();

  default boolean hasSpectators() {
    return 0 < this.getNumberOfSpectators();
  }

  default boolean hasParticipants() {
    return 0 < this.getNumberOfParticipants();
  }

  default boolean hasTeammates() {
    return 0 < this.getNumberOfTeammates();
  }

  default boolean isInitialized() {
    return GameStates.INITIALIZED == this.getState();
  }

  default boolean isStarting() {
    return GameStates.STARTING == this.getState();
  }

  default boolean isRunning() {
    return GameStates.RUNNING == this.getState();
  }

  default boolean isEnding() {
    return GameStates.ENDING == this.getState();
  }

  default boolean isStopping() {
    return GameStates.STOPPING == this.getState();
  }

  void setAsInitialized();

  void setAsStarting();

  void setAsRunning();

  void setAsEnding();

  void setAsStopping();

  @Nullable
  LocalUser findParticipantByIdentifier(
    @NotNull UUID identifier
  );

  @Nullable
  LocalTeam findParticipantTeamByParticipant(
    @NotNull LocalUserProvider userProvider
  );

  boolean addParticipant(
    @NotNull LocalUser participant
  );

  boolean addTeammate(
    @NotNull LocalTeam team,
    @NotNull LocalUser participant
  );

  boolean removeParticipant(
    @NotNull LocalUser participant
  );

  boolean removeTeammate(
    @NotNull LocalTeam team,
    @NotNull LocalUser participant
  );

  boolean removeTeammate(
    @NotNull LocalUser participant
  );

  /**
   * Efficiently relocates a teammate from their current team to a specified
   * team.
   * <p>
   * This method directly transfers a teammate from their current [LocalTeam]
   * to a target team ([to]), bypassing unnecessary logic present in the
   * standard [removeTeammate] and [addTeammate] methods. This optimization
   * makes it faster for cases where simple relocation is needed.
   * <p>
   * The relocation is successful if:
   * - The current team is not the same as the target team.
   * - The teammate is present in their current team (`from`).
   * - The teammate is not already in the target team.
   * <p>
   * If all conditions are met, the teammate is removed from their original team
   * and added to the target team, and a log entry is generated upon successful
   * relocation.
   * <p>
   * @param teammate The teammate to be relocated.
   * @param to The target team to which the teammate will be moved.
   * @return `true` if the relocation was successful, `false` otherwise.
   *
   * @since 0.1.0
   */
  boolean relocateTeammateToTeam(
    @NotNull Teammate teammate,
    @NotNull LocalTeam to
  );

  void start();

  void stop();

  void freeze();
}
