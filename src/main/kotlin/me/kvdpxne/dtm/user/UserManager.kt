package me.kvdpxne.dtm.user

import java.util.UUID
import me.kvdpxne.dtm.data.UserDao
import me.kvdpxne.dtm.statistics.Statistics

/**
 * @since 0.1.0
 */
object UserManager {

  private val identifierUserMap = mutableMapOf<UUID, User>()

  /**
   * Tries to find a [User] by the given unique user identifier.
   */
  fun findByIdentifier(identifier: UUID): User? {
    var user = identifierUserMap[identifier]
    if (null != user) {
      return user
    }

    user = UserDao.findByIdentifier(identifier)
    if (null != user) {
      this.addUser(user)
      return user
    }

    return null
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
    require(name.isNotBlank()) {
      "name can not be blank."
    }
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