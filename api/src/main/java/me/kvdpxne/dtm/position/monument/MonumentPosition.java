package me.kvdpxne.dtm.position.monument;

import java.util.UUID;
import me.kvdpxne.dtm.capabilities.Identifiable;
import me.kvdpxne.dtm.data.validation.rules.PositioningRules;
import me.kvdpxne.dtm.position.BlockPosition;
import me.kvdpxne.dtm.team.Teamable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

/**
 * @since 0.1.0
 */
public interface MonumentPosition
  extends
  BlockPosition,
  Identifiable<UUID>,
  Teamable {

  /**
   * @since 0.1.0
   */
  @Override
  @NotNull
  UUID getIdentifier();

  /**
   * @since 0.1.0
   */
  @Override
  @Range(from = PositioningRules.MIN_XZ, to = PositioningRules.MAX_XZ)
  int getX();

  /**
   * @since 0.1.0
   */
  @Override
  @Range(from = PositioningRules.MIN_OVERWORLD_Y, to = PositioningRules.MAX_OVERWORLD_Y)
  int getY();

  /**
   * @since 0.1.0
   */
  @Override
  @Range(from = PositioningRules.MIN_XZ, to = PositioningRules.MAX_XZ)
  int getZ();

  /**
   * @since 0.1.0
   */
  boolean isDestroyed();

  /**
   * @since 0.1.0
   */
  void setDestroyed(
    boolean destroyed
  );

  /**
   * @since 0.1.0
   */
  void markAsDestroyed();

  /**
   * @since 0.1.0
   */
  void unmarkAsDestroyed();
}
