package me.kvdpxne.dtm.team;

import java.util.UUID;
import me.kvdpxne.dtm.capabilities.Identifiable;
import me.kvdpxne.dtm.capabilities.Nameable;
import me.kvdpxne.dtm.team.color.TeamColor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @since 0.1.0
 */
public interface Team
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
  @NotNull
  TeamColor getColorOfArmor();

  /**
   * @since 0.1.0
   */
  @NotNull
  TeamColor getColorOfProfession();

  /**
   * @since 0.1.0
   */
  @NotNull
  TeamColor getColorOnChat();

  /**
   * @since 0.1.0
   */
  @Nullable
  TeamColor getColorOnPlayerList();

  /**
   * @since 0.1.0
   */
  @NotNull
  LocalTeam toLocalTeam();
}
