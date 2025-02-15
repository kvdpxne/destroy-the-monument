package me.kvdpxne.dtm.container

/**
 * Provides predefined container types representing various inventory sizes.
 *
 * Each container type corresponds to a specific layout, identified by a unique
 * type identifier and a Minecraft-specific name.
 *
 * @since 0.1.0
 */
object ContainerTypes {

  /**
   * Represents a generic container with 1 row (9 slots).
   *
   * @since 0.1.0
   */
  val CHEST: ContainerType by lazy {
    InternalContainerType(0, "minecraft:generic_9x1")
  }

  /**
   * Represents a generic container with 2 rows (18 slots).
   *
   * @since 0.1.0
   */
  val GENERIC_9X2: ContainerType by lazy {
    InternalContainerType(1, "minecraft:generic_9x2")
  }

  /**
   * Represents a generic container with 3 rows (27 slots).
   *
   * @since 0.1.0
   */
  val GENERIC_9X3: ContainerType by lazy {
    InternalContainerType(3, "minecraft:generic_9x3")
  }

  /**
   * Represents a generic container with 4 rows (36 slots).
   *
   * @since 0.1.0
   */
  val GENERIC_9X4: ContainerType by lazy {
    InternalContainerType(4, "minecraft:generic_9x4")
  }

  /**
   * Represents a generic container with 5 rows (45 slots).
   *
   * @since 0.1.0
   */
  val GENERIC_9X5: ContainerType by lazy {
    InternalContainerType(5, "minecraft:generic_9x5")
  }

  /**
   * Represents a generic container with 6 rows (54 slots).
   *
   * @since 0.1.0
   */
  val GENERIC_9X6: ContainerType by lazy {
    InternalContainerType(6, "minecraft:generic_9x6")
  }
}