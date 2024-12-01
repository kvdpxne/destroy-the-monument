package me.kvdpxne.dtm.container

internal class InternalContainerType internal constructor(
  override val type: Byte,
  override val name: CharSequence
) : ContainerType {

  init {
    require(0 <= this.type) {
      "The passed type must not be a negative number."
    }
  }
}