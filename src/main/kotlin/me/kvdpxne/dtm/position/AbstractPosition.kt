package me.kvdpxne.dtm.position

abstract class AbstractPosition<T : Number>(
  override val x: T,
  override val y: T,
  override val z: T,
  override val worldName: String?
) : Position<T> {
}