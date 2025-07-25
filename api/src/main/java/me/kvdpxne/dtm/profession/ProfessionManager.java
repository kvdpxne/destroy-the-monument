package me.kvdpxne.dtm.profession;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface ProfessionManager {

  @NotNull
  Profession getRandomProfession();

  @Nullable
  Profession findProfessionByNameOrNull(String name);
}
