package me.kvdpxne.dtm.shared.basics.position

import java.util.UUID
import me.kvdpxne.dtm.shared.ancillary.AbstractIdentifiable

/**
 * @since 0.1.0
 */
abstract class AbstractIdentifiablePosition<T : Number>(
  // @formatter:off
  override val x         : T,
  override val y         : T,
  override val z         : T,
  override val worldName : String?,
               identifier: UUID = UUID.randomUUID()
  // @formatter:on
) : AbstractIdentifiable<UUID>(identifier), IdentifiablePosition<T>