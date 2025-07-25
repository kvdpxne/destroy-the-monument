package me.kvdpxne.dtm.team.teammate;

import me.kvdpxne.boujee.TranslationKeyProvider;
import me.kvdpxne.dtm.game.LocalGame;
import me.kvdpxne.dtm.profession.Profession;
import me.kvdpxne.dtm.statistics.Statistics;
import me.kvdpxne.dtm.team.LocalTeam;
import me.kvdpxne.dtm.translation.Communicable;
import me.kvdpxne.dtm.user.LocalUser;
import me.kvdpxne.dtm.user.LocalUserProvider;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface Teammate
  extends
  Communicable,
  LocalUserProvider {

  /**
   * @since 0.1.0
   */
  @NotNull
  LocalTeam getLocalTeam();

  /**
   * @since 0.1.0
   */
  @NotNull
  LocalGame getLocalGame();

  /**
   * @since 0.1.0
   */
  @Override
  LocalUser getLocalUser();

  /**
   * @since 0.1.0
   */
  @NotNull
  Statistics getStatistics();

  /**
   * @since 0.1.0
   */
  @NotNull
  Profession getCurrentProfession();

  /**
   * @since 0.1.0
   */
  @Nullable
  Profession getNextProfession();

  /**
   * @since 0.1.0
   */
  void shiftProfession();

  /**
   * @since 0.1.0
   */
  void addProfession(
    final @NotNull Profession profession
  );

  /**
   * @since 0.1.0
   */
  boolean hasNextProfession();

  /**
   * Increments the kill count by one.
   *
   * @throws ArithmeticException if the operation results in an overflow.
   * @since 0.1.0
   */
  void incrementKills();

  /**
   * Increments the assist count by one.
   *
   * @throws ArithmeticException if the operation results in an overflow.
   * @since 0.1.0
   */
  void incrementAssists();

  /**
   * Increments the death count by one.
   *
   * @throws ArithmeticException if the operation results in an overflow.
   * @since 0.1.0
   */
  void incrementDeaths();

  /**
   * Increments the destroyed monuments count by one.
   *
   * @throws ArithmeticException if the operation results in an overflow.
   * @since 0.1.0
   */
  void incrementDestroyedMonuments();

  /**
   * @since 0.1.0
   */
  @Override
  void chat(
    final @NotNull TranslationKeyProvider keyProvider
  );

  /**
   * @since 0.1.0
   */
  @Override
  void title(
    final @NotNull TranslationKeyProvider keyProvider
  );

  /**
   * @since 0.1.0
   */
  @Override
  void subtitle(
    final @NotNull TranslationKeyProvider keyProvider
  );

  /**
   * @since 0.1.0
   */
  @Override
  void action(
    final @NotNull TranslationKeyProvider keyProvider
  );

  /**
   * @since 0.1.0
   */
  void leaveTeam();

  /**
   * @since 0.1.0
   */
  void switchTeam(
    final @NotNull LocalTeam team
  );
}
