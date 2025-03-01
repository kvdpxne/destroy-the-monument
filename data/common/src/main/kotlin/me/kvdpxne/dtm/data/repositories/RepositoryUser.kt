package me.kvdpxne.dtm.data.repositories

import java.util.UUID
import me.kvdpxne.dtm.data.raw.RawUser

/**
 * @since 0.1.0
 */
interface RepositoryUser {

  /**
   * @throws StackOverflowError
   */
  suspend fun readUsers(): Collection<RawUser>

  /**
   * @throws StackOverflowError
   */
  suspend fun readUserIdentifiers(): Collection<UUID>

  /**
   * @throws StackOverflowError
   */
  suspend fun readUserNames(): Collection<String>

  /**
   * @since 0.1.0
   */
  suspend fun findUserByIdentifierOrNull(
    identifier: UUID
  ): RawUser?

  /**
   * @since 0.1.0
   */
  suspend fun findUserByNameOrNull(
    name: String
  ): RawUser?

  /**
   * @since 0.1.0
   */
  suspend fun containsUserByIdentifier(
    identifier: UUID
  ): Boolean

  /**
   * @since 0.1.0
   */
  suspend fun containsUserByName(
    name: String
  ): Boolean

  /**
   * @since 0.1.0
   */
  suspend fun insertUsers(
    users: Iterable<RawUser>
  ): Int

  /**
   * @since 0.1.0
   */
  suspend fun insertUser(
    user: RawUser?
  ): Int

  /**
   * @since 0.1.0
   */
  suspend fun updateUsers(
    users: Iterable<RawUser>
  ): Int

  /**
   * @since 0.1.0
   */
  suspend fun updateUser(
    user: RawUser?
  ): Int

  /**
   * @since 0.1.0
   */
  suspend fun deleteUser(
    user: RawUser
  ): Boolean

  /**
   * @since 0.1.0
   */
  suspend fun deleteUserByIdentifier(
    identifier: UUID
  ): Boolean

  /**
   * @since 0.1.0
   */
  suspend fun truncateUsers(): Int

  /**
   * @since 0.1.0
   */
  suspend fun countUsers(): Long
}