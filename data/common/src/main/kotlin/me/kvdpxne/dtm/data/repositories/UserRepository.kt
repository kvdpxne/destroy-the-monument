package me.kvdpxne.dtm.data.repositories

import java.util.UUID
import me.kvdpxne.dtm.data.raw.RawUser
import me.kvdpxne.dtm.data.validation.ValidationResult

/**
 * @author Łukasz Pietrzak (kvdpxne)
 * @since 0.1.0
 */
interface UserRepository {

  /**
   * @throws StackOverflowError
   */
  suspend fun readUsers(): Collection<Pair<RawUser, ValidationResult>>

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
  ): Pair<RawUser, ValidationResult>?

  /**
   * @since 0.1.0
   */
  suspend fun findUserByNameOrNull(
    name: String
  ): Pair<RawUser, ValidationResult>?

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
  suspend fun insertUser(
    user: RawUser?
  ): Pair<RawUser?, ValidationResult>

  /**
   * @since 0.1.0
   */
  suspend fun updateUser(
    user: RawUser?
  ): Pair<RawUser?, ValidationResult>

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
  suspend fun deleteUserByName(
    name: String
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