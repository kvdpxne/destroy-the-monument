package me.kvdpxne.dtm.statistics;

import java.io.Serializable;
import me.kvdpxne.dtm.Identifiable;
import org.jetbrains.annotations.NotNull;

public interface IdentifiableStatistics<T extends Serializable>
  extends
  Identifiable<T>,
  Statistics {

  @Override
  @NotNull
  T getIdentifier();
}
