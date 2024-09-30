package me.kvdpxne.dtm.data.repositories

import java.util.UUID
import me.kvdpxne.dtm.user.User

/**
 * @since 0.1.0
 */
interface UserRepository {

  /**
   * @throws StackOverflowError
   *
   * @since 0.1.0
   */
  suspend fun findUsers(): List<User>

  /**
   * @throws IllegalArgumentException
   * @throws StackOverflowError
   *
   * @since 0.1.0
   */
  suspend fun getUsersNames(): List<String>

  /**
   * @param identifier
   *
   * @since 0.1.0
   */
  suspend fun findUserByIdentifier(
    identifier: UUID
  ): User?

  /**
   * @param name
   * @param ignoreCase
   *
   * @since 0.1.0
   */
  suspend fun findUserByName(
    name: String,
    ignoreCase: Boolean = true
  ): User?

  /**
   * @param user
   *
   * @since 0.1.0
   */
  suspend fun insertUser(
    user: User
  ): Int

  /**
   * @param user
   *
   * @since 0.1.0
   */
  suspend fun updateUser(
    user: User
  ): Int

  /**
   * @param identifier
   *
   * @since 0.1.0
   */
  suspend fun deleteUserByIdentifier(
    identifier: UUID
  ): Int

  /**
   * @since 0.1.0
   */
  suspend fun countUsers(): Long
}