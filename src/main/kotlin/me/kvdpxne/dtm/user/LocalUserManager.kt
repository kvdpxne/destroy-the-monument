package me.kvdpxne.dtm.user

import me.kvdpxne.dtm.shared.debug.Debug

/**
 * @since 0.1.0
 */
object LocalUserManager {

  /**
   * @since 0.1.0
   */
  private val _usersByIdentifier: MutableMap<String, User> = mutableMapOf()

  /**
   * @since 0.1.0
   */
  private val _usersByName: MutableMap<String, User> = mutableMapOf()

  /**
   * @since 0.1.0
   */
  val users: List<User>
    get() = this._usersByIdentifier.values.toList()

  /**
   * @since 0.1.0
   */
  val size: Int
    get() = this._usersByIdentifier.size

  /**
   * Tries to find a [User] by the given unique user identifier.
   */
  fun findUserByIdentifier(
    identifier: String
  ): LocalUser? {
    return this._usersByIdentifier[identifier.lowercase()] as LocalUser?
  }

  /**
   * @since 0.1.0
   */
  fun findUserByName(
    name: String
  ): LocalUser? {
    return this._usersByName[name.lowercase()] as LocalUser?
  }

  /**
   * @since 0.1.0
   */
  fun addUser(
    user: User
  ) {
    val localUser: LocalUser = try {
      //
      //
      user.asLocalUser()
    } catch (exception: UserException) {
      return
    }

    this._usersByIdentifier[localUser.identifier] = localUser
    this._usersByName[localUser.name] = localUser

    Debug.log {
      "The user $localUser was added to local storage."
    }
  }

  /**
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