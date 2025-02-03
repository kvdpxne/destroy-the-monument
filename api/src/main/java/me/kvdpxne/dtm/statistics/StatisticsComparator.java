package me.kvdpxne.dtm.statistics;

import java.util.Comparator;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * @since 0.1.0
 */
public final class StatisticsComparator {

  /**
   * @since 0.1.0
   */
  private StatisticsComparator() {
    throw new AssertionError("No " + this.getClass().getName() + " instances for you!");
  }

  /**
   * @since 0.1.0
   */
  @Contract(pure = true)
  @NotNull
  public static Comparator<Statistics> compare(
    final byte by
  ) {
    return (final Statistics a, final Statistics b) -> {
      switch (by) {
        case StatisticsCriteria.BY_KILLS:
          return a.getKills() - b.getKills();
        case StatisticsCriteria.BY_ASSISTS:
          return a.getAssists() - b.getAssists();
        case StatisticsCriteria.BY_DEATHS:
          return a.getDeaths() - b.getDeaths();
        case StatisticsCriteria.BY_DESTROYED_MONUMENTS:
          return a.getDestroyedMonuments() - b.getDestroyedMonuments();
        case StatisticsCriteria.BY_KDA_RATIO:
          return (int) (a.calculateKdaRatio() - b.calculateKdaRatio());
        case StatisticsCriteria.BY_KD_RATIO:
          return (int) (a.calculateKdRatio() - b.calculateKdRatio());
        default:
          throw new IllegalArgumentException("Unknown statistics criteria: " + by);
      }
    };
  }
}
