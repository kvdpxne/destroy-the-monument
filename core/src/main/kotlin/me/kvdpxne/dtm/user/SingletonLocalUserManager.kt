package me.kvdpxne.dtm.user

import java.util.Locale
import java.util.UUID
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap

/**
 * @since 0.1.0
 */
object SingletonLocalUserManager : LocalUserManager {

  /**
   * @since 0.1.0
   */
  private val byIdentifierDelegate: Lazy<ConcurrentMap<UUID, LocalUser>> =
    lazy { ConcurrentHashMap() }

  /**
   * @since 0.1.0
   */
  private val byNameDelegate: Lazy<ConcurrentMap<String, LocalUser>> =
    lazy { ConcurrentHashMap() }

  /**
   * Concurrent map to store users by their unique identifiers.
   *
   * @since 0.1.0
   */
  private val byIdentifier: ConcurrentMap<UUID, LocalUser>
    by this.byIdentifierDelegate

  /**
   * Concurrent map to store users by their names in lowercase format.
   *
   * @since 0.1.0
   */
  private val byName: ConcurrentMap<String, LocalUser>
    by this.byNameDelegate

  override fun getLocalUsersByIdentifier(): Map<UUID, LocalUser> {
    if (this.byIdentifierDelegate.isInitialized()) {
      return HashMap(this.byIdentifier)
    }

    return emptyMap()
  }

  override fun getLocalUsersByName(): Map<String, LocalUser> {
    if (this.byNameDelegate.isInitialized()) {
      return HashMap(this.byName)
    }

    return emptyMap()
  }

  override fun getLocalUserIdentifiers(): Iterable<UUID> {
    return this.localUsersByIdentifier.keys.asIterable()
  }

  override fun getLocalUserNames(): Iterable<String> {
    return this.localUsersByName.keys.asIterable()
  }

  override fun findUserByIdentifierOrNull(
    identifier: UUID
  ): LocalUser? {
    if (this.byIdentifierDelegate.isInitialized()) {
      return this.byIdentifier[identifier]
    }

    return null
  }

  override fun findUserByIdentifier(
    identifier: UUID
  ): LocalUser {
    return this.findUserByIdentifierOrNull(identifier)
      ?: throw UserNoFoundException("User $identifier not found", "DTM-UNF")
  }

  override fun findUserByNameOrNull(
    name: String
  ): LocalUser? {
    if (this.byNameDelegate.isInitialized()) {
      return this.byName[name.lowercase(Locale.US)]
    }

    return null
  }

  override fun findUserByName(
    name: String
  ): LocalUser {
    if (this.byNameDelegate.isInitialized()) {
      return this.byName[name.lowercase(Locale.US)]
        ?: throw UserNoFoundException("User $name not found", "DTM-UNF")
    }

    throw UserNoFoundException("User $name not found", "DTM-UNF")
  }

  override fun addLocalUser(
    localUserProvider: LocalUserProvider
  ): Boolean {
    TODO("Not yet implemented")
  }

  override fun removeLocalUser(
    localUser: LocalUser
  ): Boolean {
    TODO("Not yet implemented")
  }

  override fun removeLocalUserByIdentifier(identifier: UUID): Boolean {
    if (!this.byIdentifierDelegate.isInitialized()) {
      return false
    }

    return true
  }

  override fun removeLocalUserByName(name: String): Boolean {
    if (!this.byNameDelegate.isInitialized()) {
      return false
    }

    return true
  }

  override fun getNumberOfLocalUsersByIdentifier(): Int {
    if (this.byIdentifierDelegate.isInitialized()) {
      return this.byIdentifier.size
    }

    return 0
  }

  override fun getNumberOfLocalUsersByName(): Int {
    if (this.byNameDelegate.isInitialized()) {
      return this.byName.size
    }

    return 0
  }

  override fun clearLocalUsers() {
    if (this.byIdentifierDelegate.isInitialized()) {
      this.byIdentifier.clear()
    }

    if (this.byNameDelegate.isInitialized()) {
      this.byName.clear()
    }
  }
}