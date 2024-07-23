package me.kvdpxne.dtm.user

import java.util.UUID
import me.kvdpxne.dtm.data.DaoUser

/**
 * @since 0.1.0
 */
object UserManager {

  /**
   * @since 0.1.0
   */
  private val _activeUsers: MutableMap<UUID, User> = mutableMapOf()

  /**
   * @since 0.1.0
   */
  val activeUsers: List<User>
    get() = this._activeUsers.values.toList()

  /**
   * @since 0.1.0
   */
  val activeUsersCount: Int
    get() = this._activeUsers.size

  /**
   * Tries to find a [User] by the given unique user identifier.
   */
  fun findByIdentifier(identifier: UUID): User? {
    var user = _activeUsers[identifier]
    if (null != user) {
      return user
    }

    user = DaoUser.findByIdentifier(identifier)
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
    return _activeUsers.values.find {
      it.name.equals(name, ignoreCase)
    }
  }

  /**
   * @since 0.1.0
   */
  fun addUser(user: User) {
    _activeUsers[user.identifier] = user
  }

  /**
   * @since 0.1.0
   */
  fun removeUser(user: User) {
    _activeUsers.remove(user.identifier)
  }

  /**
   * @since 0.1.0
   */
  fun createUser(identifier: UUID, name: String): User {
    require(name.isNotBlank()) {
      "name can not be blank."
    }
    val user = User(identifier, name)
    addUser(user)
    return user
  }

  /**
   *
   */
  fun updateActiveUsers() {
    DaoUser.updateUsers(this._activeUsers.values)
  }
}