package me.kvdpxne.dtm.shared.basics.position

import me.kvdpxne.dtm.shared.ancillary.AbstractIdentifiable

/**
 * @since 0.1.0
 */
abstract class AbstractIdentifiablePosition<T : Number>(
  // @formatter:off
  override val x         : T,
  override val y         : T,
  override val z         : T,
  override val worldName: String?,
               identifier: String
  // @formatter:on
) : AbstractIdentifiable<String>(identifier), IdentifiablePosition<T>