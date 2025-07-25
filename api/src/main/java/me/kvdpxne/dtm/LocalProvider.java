package me.kvdpxne.dtm;

import org.jetbrains.annotations.NotNull;

public interface LocalProvider<T extends Local> {

  /**
   * @since 0.1.0
   */
  @NotNull
  T toLocal();
}
