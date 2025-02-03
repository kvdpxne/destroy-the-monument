package me.kvdpxne.dtm.profession;

import me.kvdpxne.dtm.Copyable;
import me.kvdpxne.dtm.DisplayableNameable;
import me.kvdpxne.dtm.Identifiable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface Profession
  extends
  Identifiable<String>,
  DisplayableNameable,
  Copyable<Profession> {

  @Override
  @NotNull
  String getIdentifier();

  @Override
  @NotNull
  String getName();

  @Override
  @Nullable
  String getDisplayName();

  @Override
  @NotNull
  Profession copy();
}
