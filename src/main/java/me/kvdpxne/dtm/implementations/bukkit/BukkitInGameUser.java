package me.kvdpxne.dtm.implementations.bukkit;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.Optional;
import me.kvdpxne.dtm.PluginContextKt;
import me.kvdpxne.dtm.shared.Position;
import me.kvdpxne.dtm.shared.PositionConvertException;
import me.kvdpxne.dtm.user.InGameUser;
import me.kvdpxne.dtm.user.User;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static org.bukkit.event.player.PlayerTeleportEvent.TeleportCause.PLUGIN;

public class BukkitInGameUser implements InGameUser<World> {

  private final User user;
  private Reference<Player> playerReference;

  public BukkitInGameUser(final @NotNull User user) {
    this.user = user;
    this.playerReference = new WeakReference<>(null);
  }

  public Optional<Player> getPlayer() {
    Player player = this.playerReference.get();
    if (null == player) {
      player = Bukkit.getPlayer(this.user.getIdentifier());
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
