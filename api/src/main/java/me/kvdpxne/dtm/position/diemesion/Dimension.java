package me.kvdpxne.dtm.position.diemesion;

import java.io.Serializable;
import java.util.UUID;
import me.kvdpxne.dtm.Copyable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @since 0.1.0
 */
public interface Dimension
  extends
  Copyable<Dimension>,
  Serializable {

  /**
   * @since 0.1.0
   */
  @NotNull
  UUID getWorldIdentifier();

  /**
   * @since 0.1.0
   */
  @NotNull
  String getWorldName();

  /**
   * @since 0.1.0
   */
  @Nullable
  Object getWorldOrNull();

  /**
   * @since 0.1.0
   */
  boolean sameWorld(final @NotNull UUID worldIdentifier);

  /**
   * @since 0.1.0
   */
  boolean sameWorld(final @NotNull String worldName);

  /**
   * @since 0.1.0
   */
  boolean sameWorld(final @NotNull Object world);

  /**
   * @since 0.1.0
   */
  boolean sameWorld(final @NotNull Dimension dimension);

  /**
   * @since 0.1.0
   */
  @Override
  @NotNull
  Dimension copy();
}
