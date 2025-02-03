package me.kvdpxne.dtm.user.cache;

import java.util.ArrayDeque;
import me.kvdpxne.dtm.position.BlockPosition;
import me.kvdpxne.dtm.position.EntityPosition;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @since 0.1.0
 */
public interface UserCache {

  /**
   * @since 0.1.0
   */
  @Nullable
  BlockPosition getSelectedBlockPosition();

  /**
   * @since 0.1.0
   */
  void setSelectedBlockPosition(
    final @NotNull BlockPosition position
  );

  /**
   * @since 0.1.0
   */
  @Nullable
  ArrayDeque<EntityPosition> getTeleportationHistory();

  /**
   * @since 0.1.0
   */
  void clearTeleportationHistory();
}
