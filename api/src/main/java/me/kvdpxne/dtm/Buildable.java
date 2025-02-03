package me.kvdpxne.dtm;

import org.jetbrains.annotations.NotNull;

/**
 * @since 0.1.0
 */
public interface Buildable<T> {

  /**
   * @since 0.1.0
   */
  @NotNull
  T build();
}
