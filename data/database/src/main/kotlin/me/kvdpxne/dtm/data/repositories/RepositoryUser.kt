package me.kvdpxne.dtm.data.repositories

import java.util.UUID
import me.kvdpxne.dtm.data.raw.RawUser

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
  suspend fun insertUsers(
    users: Iterable<RawUser>
  )

  /**
   * @since 0.1.0
   */
  suspend fun insertUser(user: RawUser)

  /**
   * @since 0.1.0
   */
  suspend fun updateUsers(users: Iterable<RawUser>): Int

  /**
   * @since 0.1.0
   */
  suspend fun updateUser(user: RawUser)

  /**
   * @since 0.1.0
   */
  suspend fun deleteUser(user: RawUser): Boolean

  /**
   * @since 0.1.0
   */
  suspend fun deleteUsers(): Int

  /**
   * @since 0.1.0
   */
  suspend fun countUsers(): Long
}