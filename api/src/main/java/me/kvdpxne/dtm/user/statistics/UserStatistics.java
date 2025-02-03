package me.kvdpxne.dtm.user.statistics;

import java.util.UUID;
import me.kvdpxne.dtm.statistics.IdentifiableStatistics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

public interface UserStatistics
  extends
  IdentifiableStatistics<UUID> {

  @Override
  @NotNull
  UUID getIdentifier();

  @Override
  @Range(from = 0, to = Integer.MAX_VALUE)
  int getKills();

  @Override
  void setKills(
    @Range(from = 0, to = Integer.MAX_VALUE) final int kills
  );

  @Override
  @Range(from = 0, to = Integer.MAX_VALUE)
  int getAssists();

  @Override
  void setAssists(@Range(from = 0, to = Integer.MAX_VALUE) final int assists);

  @Override
  @Range(from = 0, to = Integer.MAX_VALUE)
  int getDeaths();

  @Override
  void setDeaths(@Range(from = 0, to = Integer.MAX_VALUE) final int deaths);

  @Override
  @Range(from = 0, to = Integer.MAX_VALUE)
  int getDestroyedMonuments();

  @Range(from = 0, to = Integer.MAX_VALUE)
  int getPlayedGames();

  void setPlayedGames(
    final @Range(from = 0, to = Integer.MAX_VALUE) int value
  );

  @Range(from = 0, to = Integer.MAX_VALUE)
  int getGamesWon();

  void setGamesWon(
    final @Range(from = 0, to = Integer.MAX_VALUE) int value
  );

  @Range(from = 0, to = Integer.MAX_VALUE)
  int getGamesLost();

  void setGamesLost(
    final @Range(from = 0, to = Integer.MAX_VALUE) int value
  );

  @Override
  @Range(from = 0, to = Long.MAX_VALUE)
  float calculateKdRatio();

  @Override
  @Range(from = 0, to = Long.MAX_VALUE)
  float calculateKdaRatio();

  @Override
  void addKills(@Range(from = 0, to = Integer.MAX_VALUE) final int kills);

  @Override
  void addAssists(@Range(from = 0, to = Integer.MAX_VALUE) final int assists);

  @Override
  void addDeaths(@Range(from = 0, to = Integer.MAX_VALUE) final int deaths);

  @Override
  void addDestroyedMonuments(@Range(from = 0, to = Integer.MAX_VALUE) final int monuments);

  void addPlayedGames(
    final @Range(from = 0, to = Integer.MAX_VALUE) int value
  );

  void addGamesWon(
    final @Range(from = 0, to = Integer.MAX_VALUE) int value
  );

  void addGamesLost(
    final @Range(from = 0, to = Integer.MAX_VALUE) int value
  );

  @Override
  void subtractKills(@Range(from = 0, to = Integer.MAX_VALUE) final int kills);

  @Override
  void subtractAssists(@Range(from = 0, to = Integer.MAX_VALUE) final int assists);

  @Override
  void subtractDeaths(@Range(from = 0, to = Integer.MAX_VALUE) final int deaths);

  @Override
  void subtractDestroyedMonuments(@Range(from = 0, to = Integer.MAX_VALUE) final int monuments);

  void subtractPlayedGames(
    final @Range(from = 0, to = Integer.MAX_VALUE) int value
  );

  void subtractGamesWon(
    final @Range(from = 0, to = Integer.MAX_VALUE) int value
  );

  void subtractGamesLost(
    final @Range(from = 0, to = Integer.MAX_VALUE) int value
  );

  void incrementPlayedGames();

  void incrementGamesWon();

  void incrementGamesLost();

  @Override
  boolean wasModified();

  @Override
  void reset();
}
