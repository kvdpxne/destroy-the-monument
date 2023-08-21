package me.kvdpxne.dtm.shared

/**
 * @param W
 * @param L
 */
interface PositionConverter<W : Any, L> {

  /**
   * @param world
   * @param position
   *
   * @throws PositionConvertException if the information to convert the item
   * is missing or the conversion is not possible.
   */
  @Throws(PositionConvertException::class)
  fun fromPosition(
    world: W,
    position: Position
  ): L

  /**
   * @param location
   * @param type
   *
   * @throws PositionConvertException
   */
  @Throws(PositionConvertException::class)
  fun toPosition(
    location: L,
    type: PositionType = DefaultPositionType.ENTITY_LOCKED_POSITION
  ): Position
}