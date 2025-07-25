package me.kvdpxne.dtm.team;

import java.util.Collection;
import me.kvdpxne.dtm.game.LocalGame;
import me.kvdpxne.dtm.team.teammate.Teammate;
import me.kvdpxne.dtm.translation.Communicable;
import me.kvdpxne.dtm.user.LocalUser;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Range;

/**
 * @since 0.1.0
 */
public interface LocalTeam
  extends
  Communicable,
  Team {

  /**
   * @since 0.1.0
   */
  @NotNull
  LocalGame getLocalGame();

  /**
   * @since 0.1.0
   */
  @NotNull
  Collection<Teammate> getTeammates();

  /**
   * @since 0.1.0
   */
  @Range(from = 0, to = Integer.MAX_VALUE)
  int getHealth();

  /**
   * @since 0.1.0
   */
  @Range(from = 0, to = Integer.MAX_VALUE)
  int getSize();

  /**
   * @since 0.1.0
   */
  boolean hasTeammate(
    final @NotNull LocalUser user
  );

  /**
   * @since 0.1.0
   */
  boolean hasTeammate(
    final @NotNull Teammate teammate
  );

  /**
   * @since 0.1.0
   */
  @Nullable
  Teammate getTeammate(
    final @NotNull LocalUser user
  );

  /**
   * @since 0.1.0
   */
  boolean addTeammate(
    final @NotNull Teammate teammate
  );

  /**
   * @since 0.1.0
   */
  boolean removeTeammate(
    final @NotNull Teammate teammate
  );

  /**
   * @since 0.1.0
   */
  boolean removeTeammate(
    final @NotNull LocalUser localUser
  );

  /**
   * @since 0.1.0
   */
  @Range(from = 0, to = Integer.MAX_VALUE)
  int clearTeammates();

  /**
   * @since 0.1.0
   */
  boolean injure();
}
