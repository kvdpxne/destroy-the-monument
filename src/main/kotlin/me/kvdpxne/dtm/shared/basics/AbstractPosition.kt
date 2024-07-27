package me.kvdpxne.dtm.shared.basics

abstract class AbstractPosition<T : Number>(
  override val x: T,
  override val y: T,
  override val z: T,
) : Position<T> {
}