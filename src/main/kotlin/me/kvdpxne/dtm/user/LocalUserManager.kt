package me.kvdpxne.dtm.user

import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap
import me.kvdpxne.dtm.configuration.GeneralConfiguration
import me.kvdpxne.dtm.shared.PlayerUuid
import me.kvdpxne.dtm.shared.debug.Debug

/**
 * Singleton object managing [LocalUser] instances.
 *
 * [LocalUserManager] provides methods to add, remove, and retrieve users
 * based on their unique identifiers or names. The users are stored in
 * concurrent maps, allowing thread-safe operations across multiple threads.
 *
 * @since 0.1.0
 */
object LocalUserManager {

  /**
   * Concurrent map to store users by their unique identifiers.
   *
   * @since 0.1.0
   */
  private val _usersByIdentifier: ConcurrentMap<PlayerUuid, User> =
    ConcurrentHashMap(GeneralConfiguration.USER_MANAGER_INITIAL_CAPACITY)

  /**
   * Concurrent map to store users by their names in lowercase format.
   *
   * @since 0.1.0
   */
  private val _usersByName: ConcurrentMap<String, User> =
    ConcurrentHashMap(GeneralConfiguration.USER_MANAGER_INITIAL_CAPACITY)

  /**
   * Returns a list of all registered users in the system.
   *
   * @since 0.1.0
   */
  val users: List<User>
    get() = this._usersByIdentifier.values.toList()

  /**
   * Returns the current number of registered users.
   *
   * @since 0.1.0
   */
  val size: Int
    get() = this._usersByIdentifier.size

  /**
   * Attempts to find a [LocalUser] by their unique identifier.
   *
   * @param identifier The unique identifier of the user.
   * @return The [LocalUser] if found, or `null` if no user is associated
   *         with the identifier.
   * @since 0.1.0
   */
  fun findUserByIdentifierOrNull(
    identifier: PlayerUuid
  ): LocalUser? {
    return this._usersByIdentifier[identifier] as LocalUser?
  }

  /**
   * Finds a [LocalUser] by their unique identifier or
   * throws [UserNotFoundException] if not found.
   *
   * @param identifier The unique identifier of the user.
   * @return The [LocalUser] associated with the identifier.
   * @throws UserNotFoundException if no user is associated with the identifier.
   * @since 0.1.0
   */
  fun findUserByIdentifier(
    identifier: PlayerUuid
  ): LocalUser {
    return this.findUserByIdentifierOrNull(identifier)
      ?: throw UserNotFoundException(
        "The user with the given identifier \"$identifier\" was not found"
          + " in local memory."
      )
  }

  /**
   * Attempts to find a [LocalUser] by their name (case-insensitive).
   *
   * @param name The name of the user.
   * @return The [LocalUser] if found, or `null` if no user is associate
   *         with the name.
   * @since 0.1.0
   */
  fun findUserByNameOrNull(
    name: String
  ): LocalUser? {
    return this._usersByName[name.lowercase()] as LocalUser?
  }

  /**
   * Finds a [LocalUser] by their name or throws
   * [UserNotFoundException] if not found.
   *
   * @param name The name of the user.
   * @return The [LocalUser] associated with the name.
   * @throws UserNotFoundException if no user is associated with the name.
   * @since 0.1.0
   */
  fun findUserByName(
    name: String
  ): LocalUser {
    return this.findUserByNameOrNull(name)
      ?: throw UserNotFoundException(
        "The user with the given name \"$name\" was not found in local memory."
      )
  }

  /**
   * Adds a [User] to local storage, making them available by
   * identifier and name.
   *
   * Converts the [User] instance to [LocalUser], adds it to internal
   * storage maps, and logs the addition using the debug utility.
   *
   * @param user The [User] to be added.
   * @since 0.1.0
   */
  fun addUser(
    user: User
  ) {
    val localUser: LocalUser = user.asLocalUser()

    this._usersByIdentifier[localUser.identifier] = localUser
    this._usersByName[localUser.name] = localUser

    Debug.log {
      "The user $localUser was added to local storage."
    }
  }

  /**
   * Removes a [User] from local storage.
   *
   * Removes the user by both identifier and name and logs the removal.
   *
   * @param user The [User] to be removed.
   * @since 0.1.0
   */
  fun removeUser(
    user: User
  ) {
    this._usersByIdentifier.remove(user.identifier)
    this._usersByName.remove(user.name)

    Debug.log {
      "The user $user was removed from local storage."
    }
  }

  /**
   * Removes all users from local storage.
   *
   * Clears both identifier and name maps and logs the clearance.
   *
   * @since 0.1.0
   */
  fun removeUsers() {
    this._usersByIdentifier.clear()
    this._usersByName.clear()

    Debug.log {
      "All users have been removed from local storage."
    }
  }
}