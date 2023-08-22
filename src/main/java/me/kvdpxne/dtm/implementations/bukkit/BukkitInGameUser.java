package me.kvdpxne.dtm.implementations.bukkit;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.Optional;
import me.kvdpxne.dtm.PluginContextKt;
import me.kvdpxne.dtm.shared.DefaultPositionType;
import me.kvdpxne.dtm.shared.Position;
import me.kvdpxne.dtm.shared.PositionConvertException;
import me.kvdpxne.dtm.user.InGameUser;
import me.kvdpxne.dtm.user.User;
import me.kvdpxne.dtm.user.UserException;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static org.bukkit.event.player.PlayerTeleportEvent.TeleportCause.PLUGIN;

public class BukkitInGameUser implements InGameUser<World> {

  private final User membership;
  private Reference<Player> playerReference;

  public BukkitInGameUser(final @NotNull User membership) {
    this.membership = membership;
    this.playerReference = new WeakReference<>(null);
  }

  public Optional<Player> getPlayer() {
    Player player = this.playerReference.get();
    if (null == player) {
      player = Bukkit.getPlayer(this.membership.getIdentifier());
      if (null == player) {
        this.playerReference = new WeakReference<>(null);
        return Optional.empty();
      }
      this.playerReference = new WeakReference<>(player);
      return Optional.of(player);
    }
    return Optional.of(player);
  }

  @Override
  public Position getPosition() throws UserException {
    return this.getPlayer().map(player -> {
        final BukkitPositionConverter converter = (BukkitPositionConverter)
          PluginContextKt.getPositionConverter();

      final Location location = player.getLocation();

        try {
          return converter.toPosition(location, DefaultPositionType.ENTITY_POSITION);
        } catch (PositionConvertException e) {
          throw new RuntimeException(e);
        }
      })
      .orElseThrow(() -> new UserException(""));
  }

  @Override
  public boolean isOnline() {
    return this.getPlayer()
      .map(Player::isOnline)
      .orElse(false);
  }

  @Override
  public boolean isHidden() {
    return false;
  }

  @Override
  public void teleport(
    @NotNull final World world,
    @NotNull final Position position
  ) {
    this.getPlayer().ifPresent(player -> {
      final BukkitPositionConverter converter = (BukkitPositionConverter)
        PluginContextKt.getPositionConverter();

      final Location location;
      try {
        location = converter.fromPosition(world, position);
      } catch (final PositionConvertException exception) {
        return;
      }

      if (location.equals(player.getLocation())) {
        return;
      }

      player.teleport(location, PLUGIN);
    });
  }

  @Override
  public void teleport(
    final @NotNull Position position
  ) {
    final World defaultWorld = Bukkit.getWorlds().get(0);
    this.teleport(defaultWorld, position);
  }
}
