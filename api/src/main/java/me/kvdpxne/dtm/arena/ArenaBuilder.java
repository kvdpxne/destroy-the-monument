package me.kvdpxne.dtm.arena;

import java.util.UUID;
import me.kvdpxne.dtm.Buildable;
import org.jetbrains.annotations.NotNull;

public interface ArenaBuilder
  extends
  Buildable<Arena> {

  ArenaBuilder withIdentifier(final UUID identifier);

  ArenaBuilder withRandomIdentifier();

  ArenaBuilder withName(final String name);

  ArenaBuilder withDisplayName(final String displayName);

  @Override
  @NotNull
  Arena build();
}
