package me.kvdpxne.dtm.implementations.bukkit;

import me.kvdpxne.dtm.shared.BlockPosition;
import me.kvdpxne.dtm.shared.EntityPosition;
import me.kvdpxne.dtm.shared.Position;
import me.kvdpxne.dtm.shared.PositionConvertException;
import me.kvdpxne.dtm.shared.PositionConverter;
import me.kvdpxne.dtm.shared.PositionType;
import org.bukkit.Location;
import org.bukkit.World;

import static me.kvdpxne.dtm.shared.DefaultPositionType.BLOCK_POSITION;
import static me.kvdpxne.dtm.shared.DefaultPositionType.ENTITY_LOCKED_POSITION;
import static me.kvdpxne.dtm.shared.DefaultPositionType.ENTITY_POSITION;

public final class BukkitPositionConverter
  implements PositionConverter<World, Location> {

  @Override
  public Location fromPosition(
    final World world,
    final Position position
  ) throws PositionConvertException {


    if (position instanceof BlockPosition) {
      final BlockPosition blockPosition = (BlockPosition) position;
      return new Location(
        world,
        blockPosition.getX(),
        blockPosition.getY(),
        blockPosition.getZ()
      );
    }

    if (position instanceof EntityPosition) {
      final EntityPosition entityPosition = (EntityPosition) position;
      final Location location = new Location(
        world,
        entityPosition.getX(),
        entityPosition.getY(),
        entityPosition.getZ()
      );

      // In some versions of bukkit there is a reverse assignment of the yaw
      // and pitch fields via the constructor of the object.
      location.setPitch(entityPosition.getPitch());
      location.setY(entityPosition.getYaw());

      return location;
    }

    throw new PositionConvertException("");
  }

  @Override
  public Position toPosition(
    final Location location,
    final PositionType type
  ) throws PositionConvertException {

    if (BLOCK_POSITION == type) {
      return new BlockPosition(
        location.getBlockX(),
        location.getBlockY(),
        location.getBlockZ()
      );
    }

    // converts given location to worldless and camera locked position.
    //
    if (ENTITY_LOCKED_POSITION == type) {
      return new EntityPosition(
        location.getX(),
        location.getY(),
        location.getZ(),
        0.0F,
        0.0F
      );
    }

    if (ENTITY_POSITION == type) {
      return new EntityPosition(
        location.getX(),
        location.getY(),
        location.getZ(),
        location.getPitch(),
        location.getYaw()
      );
    }

    return null;
  }
}
