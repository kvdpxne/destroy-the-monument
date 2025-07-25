package me.kvdpxne.dtm.arena.map;

import java.util.UUID;
import me.kvdpxne.dtm.capabilities.Identifiable;
import me.kvdpxne.dtm.capabilities.Nameable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface ArenaMap
  extends
  Identifiable<UUID>,
  Nameable {

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
  @NotNull
  String getName();

  /**
   * @since 0.1.0
   */
  @Nullable
  Object getWorldOrNull();

  /**
   * @since 0.1.0
   */
  boolean isLoaded();

  /**
   * @since 0.1.0
   */
  boolean isPresent();

  /**
   * @since 0.1.0
   */
  void load();

  /**
   * @since 0.1.0
   */
  void unload();

  /**
   * @since 0.1.0
   */
  void save();
}
