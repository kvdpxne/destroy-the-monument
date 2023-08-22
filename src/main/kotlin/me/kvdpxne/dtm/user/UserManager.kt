package me.kvdpxne.dtm.user

import java.util.UUID

/**
 * Manager responsible for managing [User]s.
 *
 * @since 1.0.0
 */
object UserManager : Iterable<User> {

  /**
   * A set of users that temporarily stores user data that is needed for some
   * function to work.
   */
  var users: MutableSet<User> = mutableSetOf()
    private set

  /**
   * Tries to find a [User] by the given unique user identifier.
   */
  fun getUserByIdentifierOrNull(
    identifier: UUID,
    load: Boolean = false
  ): User? {
    var user: User? = this.users.find {
      it.identifier == identifier
    }

    if (null != user) {
      return user
    }

    if (load) {
      return try {
        user = UserLifecycleService.getUserByIdentifier(identifier)
        this.addUser(user)
        user
      } catch (exception: UserNotFoundException) {
        null
      }
    }

    return null
  }

  /**
   * @since 0.1.0
   */
  fun getUserByNameOrNull(
    name: String,
    ignoreCase: Boolean = true,
    load: Boolean = false
  ): User? {
    var user: User? = this.users.find {
      it.name.equals(name, ignoreCase)
    }

    if (null != user) {
      return user
    }

    if (load) {
      return try {
        user = UserLifecycleService.getUserByName(name)
        this.addUser(user)
        user
      } catch (exception: UserNotFoundException) {
        null
      }
    }

    return null
  }

  /**
   * @since 0.1.0
   */
  fun addUser(user: User) {
    this.users.add(user)
  }

  /**
   * @since 0.1.0
   */
  fun removeUser(user: User) {
    this.users.remove(user)
  }

  /**
   * @since 0.1.0
   */
  fun count(): Int {
    return users.size
  }

  override fun iterator(): Iterator<User> {
    return this.users.iterator()
  }
}