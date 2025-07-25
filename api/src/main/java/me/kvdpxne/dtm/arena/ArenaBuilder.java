package me.kvdpxne.dtm.arena;

import java.util.UUID;
import me.kvdpxne.dtm.capabilities.Buildable;
import org.jetbrains.annotations.NotNull;

public interface ArenaBuilder
  extends
  Buildable<Arena> {

  ArenaBuilder withIdentifier(UUID identifier);

  ArenaBuilder withRandomIdentifier();

  ArenaBuilder withName(String name);

  ArenaBuilder withDisplayName(String displayName);

  @Override
  @NotNull
  Arena build();
}
