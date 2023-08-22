package me.kvdpxne.dtm.game

import java.util.UUID
import me.kvdpxne.dtm.data.ArenaDao
import me.kvdpxne.dtm.data.GameArenasDao

/**
 * Service responsible for the life cycle of the [Arena].
 */
object ArenaLifecycleService {

  /**
   *
   */
  fun getArenasByGameIdentifier(identifier: UUID): Collection<Arena> {
    return GameArenasDao.findAllByGameIdentifier(identifier)
  }

  /**
   *
   * @throws ArenaNotFoundException If no [Arena] with the given [identifier]
   * could be found.
   */
  @Throws(ArenaNotFoundException::class)
  fun getArenaByIdentifier(identifier: UUID): Arena {
    // Tries to find arena by arena identifier, returns null on failure.
    val arena: Arena? = ArenaDao.findByIdentifierOrNull(identifier)

    return arena ?: throw ArenaNotFoundException(
      "Arena with identifier \"$identifier\" not found."
    )
  }

  /**
   *
   * @throws ArenaNotFoundException If no [Arena] with the given [name]
   * could be found.
   */
  @Throws(ArenaNotFoundException::class)
  fun getArenaByName(name: String): Arena {
    // Tries to find arena by arena name, returns null on failure.
    val arena: Arena? = ArenaDao.findByNameOrNull(name)

    return arena ?: throw ArenaNotFoundException(
      "Arena with name \"$name\" not found."
    )
  }

  @Throws(ArenaAlreadyExistsException::class)
  fun createArena(arena: Arena) {

  }

  fun removeArena(arena: Arena) {

  }

  fun size(): Int {
    return ArenaDao.count()
  }
}