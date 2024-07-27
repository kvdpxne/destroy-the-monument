package me.kvdpxne.dtm.shared.basics

import me.kvdpxne.dtm.shared.ancillary.AbstractIdentifiable
import me.kvdpxne.dtm.uid.Uid

/**
 * @since 0.1.0
 */
abstract class AbstractIdentifiablePosition<T : Number>(
  // @formatter:off
  override val x         : T,
  override val y         : T,
  override val z         : T,
               identifier: String = Uid.next()
  // @formatter:on
) : AbstractIdentifiable<String>(identifier), IdentifiablePosition<T>