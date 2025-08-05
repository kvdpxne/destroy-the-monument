package me.kvdpxne.dtm.user

import java.util.Collections
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap
import me.kvdpxne.dtm.configuration.AdvancedConfiguration
import me.kvdpxne.dtm.shared.PlayerUuid
import me.kvdpxne.dtm.shared.debug.Debug
import me.kvdpxne.dtm.shared.text.toSingleLines
import org.jetbrains.annotations.UnmodifiableView

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
   * @since 0.1.0
   */
  private val usersByIdentifierDelegate: Lazy<ConcurrentMap<PlayerUuid, User>> = lazy {
    ConcurrentHashMap(AdvancedConfiguration.USER_INITIAL_CAPACITY)
  }

  /**
   * @since 0.1.0
   */
  private val usersByNameDelegate: Lazy<ConcurrentMap<String, User>> = lazy {
    ConcurrentHashMap(AdvancedConfiguration.USER_INITIAL_CAPACITY)
  }

  /**
   * Concurrent map to store users by their unique identifiers.
   *
   * @since 0.1.0
   */
  private val usersByIdentifier: ConcurrentMap<PlayerUuid, User> by this.usersByIdentifierDelegate

  /**
   * Concurrent map to store users by their names in lowercase format.
   *
   * @since 0.1.0
   */
  private val usersByName: ConcurrentMap<String, User> by this.usersByNameDelegate

  /**
   * Returns a list of all registered users in the system.
   *
   * @since 0.1.0
   */
  val users: @UnmodifiableView Collection<User>
    get() {
      if (this.usersByIdentifierDelegate.isInitialized()) {
        return Collections.unmodifiableCollection(this.usersByIdentifier.values)
      }
      return Collections.emptyList()
    }

  /**
   * Returns the current number of registered users.
   *
   * @since 0.1.0
   */
  val size: Int
    get() {
      if (this.usersByIdentifierDelegate.isInitialized()) {
        return this.usersByIdentifier.size
      }

      return 0
    }

  /**
   * @since 0.1.0
   */
  val initialized: Boolean
    get() = this.usersByIdentifierDelegate.isInitialized()
      && this.usersByNameDelegate.isInitialized()

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
    if (this.usersByIdentifierDelegate.isInitialized()) {
      return this.usersByIdentifier[identifier] as LocalUser?
    }

    return null
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
    if (this.usersByNameDelegate.isInitialized()) {
      return this.usersByName[name.lowercase()] as LocalUser?
    }

    return null
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
    val localUser: LocalUser = user.toLocalUser()

    this.usersByIdentifier[localUser.identifier] = localUser
    this.usersByName[localUser.name] = localUser

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
    if (!this.initialized) {
      return
    }

    this.usersByIdentifier.remove(user.identifier)
    this.usersByName.remove(user.name)

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
    if (!this.initialized) {
      Debug.log {
        """
          No user was removed from local memory because the local user
          manager did not require initialization.
        """.toSingleLines()
      }
      return
    }

    this.usersByIdentifier.clear()
    this.usersByName.clear()

    Debug.log {
      "All users have been removed from local storage."
    }
  }
}