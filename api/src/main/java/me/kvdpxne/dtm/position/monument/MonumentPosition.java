package me.kvdpxne.dtm.position.monument;

import java.util.UUID;
import me.kvdpxne.dtm.Identifiable;
import me.kvdpxne.dtm.position.BlockPosition;
import me.kvdpxne.dtm.team.Teamable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

public interface MonumentPosition
  extends
  BlockPosition,
  Identifiable<UUID>,
  Teamable {

  @Override
  @NotNull
  UUID getIdentifier();

  @Override
  @Range(from = Integer.MIN_VALUE, to = Integer.MAX_VALUE)
  int getX();

  @Override
  int getY();

  @Override
  @Range(from = Integer.MIN_VALUE, to = Integer.MAX_VALUE)
  int getZ();



  boolean isDestroyed();

  void setDestroyed(
    final boolean destroyed
  );

  void markAsDestroyed();

  void unmarkAsDestroyed();
}
