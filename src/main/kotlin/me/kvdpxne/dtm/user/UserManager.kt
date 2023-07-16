package me.kvdpxne.dtm.user

import me.kvdpxne.dtm.shared.isTrue
import me.kvdpxne.dtm.statistics.Statistics
import java.util.UUID

/**
 * @since 0.1.0
 */
object UserManager {

  private val identifierUserMap = mutableMapOf<UUID, User>()

  /**
   * @since 0.1.0
   */
  fun findByIdentifier(identifier: UUID): User? {
    return identifierUserMap[identifier]
  }

  /**
   * @since 0.1.0
   */
  fun findByName(name: String, ignoreCase: Boolean = true): User? {
    return identifierUserMap.values.find {
      it.name.equals(name, ignoreCase)
    }
  }

  /**
   * @since 0.1.0
   */
  fun addUser(user: User) {
    identifierUserMap[user.identifier] = user
  }

  /**
   * @since 0.1.0
   */
  fun removeUser(user: User) {
    identifierUserMap.remove(user.identifier)
  }

  /**
   * @since 0.1.0
   */
  fun createUser(identifier: UUID, name: String): User {
    isTrue(name.isBlank(), "name can not be blank.")
    val user = User(identifier, name, Statistics.empty())
    addUser(user)
    return user
  }

  /**
   * @since 0.1.0
   */
  fun count(): Int {
    return identifierUserMap.size
  }
}