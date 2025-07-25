package me.kvdpxne.dtm.team.color;

import org.jetbrains.annotations.NotNull;

public interface TeamColor {

  /**
   * @since 0.1.0
   */
  @NotNull
  String getAsFullFormat();

  /**
   * @since 0.1.0
   */
  @NotNull
  String getAsSimpleFormat();
}
