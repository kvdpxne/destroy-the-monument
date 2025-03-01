package me.kvdpxne.dtm.teammate.color;

import org.jetbrains.annotations.NotNull;

public interface Colour<T> {

  @NotNull
  String getLiteralColor();

  @NotNull
  T getPlatformColor();

  boolean isHex();

  boolean isMinecraft();
}
