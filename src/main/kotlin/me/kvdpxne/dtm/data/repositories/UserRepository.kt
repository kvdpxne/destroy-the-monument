package me.kvdpxne.dtm.data.repositories

import me.kvdpxne.dtm.shared.PlayerUuid
import me.kvdpxne.dtm.user.User

/**
 * Repository interface for managing [User] data operations.
 *
 * The [UserRepository] interface defines methods for CRUD (Create, Read,
 * Update, Delete) operations on user data. It serves as the primary access
 * point for retrieving, inserting, updating, and deleting user information in
 * the data store, as well as counting user entries.
 *
 * Implementations of this interface should handle data persistence specifics
 * and ensure efficient and consistent access to user records.
 *
 * @since 0.1.0
 */
interface UserRepository {

  /**
   * Retrieves a collection of all users.
   *
   * This method fetches all users from the data store. It's useful for
   * operations that require working with complete user data. In the case of
   * extremely large user collections, consider implementing pagination to
   * manage memory effectively.
   *
   * @return A collection of [User] objects.
   * @throws StackOverflowError If the result set is excessively large,
   *                            causing stack overflow.
   * @since 0.1.0
   */
  suspend fun findUsers(): Collection<User>

  /**
   * Retrieves a collection of all usernames.
   *
   * This method is optimized to fetch only usernames rather than full user
   * records, which can be beneficial for cases where only names are needed,
   * such as dropdowns or lists.
   *
   * @return A collection of [User] objects with only the name field populated.
   *
   * @throws StackOverflowError If the result set is excessively large,
   *                            causing stack overflow.
   * @since 0.1.0
   */
  suspend fun findUsersNames(): Collection<String>

  /**
   * Finds a user by their unique identifier.
   *
   * Searches the data store for a user with the specified unique [PlayerUuid].
   * If no user with the given identifier is found, `null` is returned.
   *
   * @param identifier The unique identifier of the user to locate.
   * @return The matching [User] object or `null` if no match is found.
   * @since 0.1.0
   */
  suspend fun findUserByIdentifier(
    identifier: PlayerUuid
  ): User?

  /**
   * Finds a user by their name.
   *
   * Searches the data store for a user by their name, with an option to ignore
   * case sensitivity. This is helpful for user searches in scenarios where
   * case-insensitive matching is desired.
   *
   * @param name The name of the user to search for.
   * @param ignoreCase Whether to ignore case sensitivity in the name search.
   *                   Defaults to `true`.
   * @return The matching [User] object or `null` if no match is found.
   * @since 0.1.0
   */
  suspend fun findUserByName(
    name: String,
    ignoreCase: Boolean = true
  ): User?

  /**
   * Inserts a new user into the data store.
   *
   * This method adds a new `User` record to the data store and returns an
   * integer indicating the result, typically representing the number of rows
   * affected or the ID of the inserted user.
   *
   * @param user The [User] object to insert.
   * @return An integer representing the result of the insert operation,
   *         such as rows affected.
   * @since 0.1.0
   */
  suspend fun insertUser(
    user: User
  ): Int

  /**
   * Updates an existing user record in the data store.
   *
   * This method modifies an existing [User] record with updated information
   * and returns an integer representing the result, typically the number
   * of rows affected.
   *
   * @param user The [User] object with updated data to persist.
   * @return An integer representing the result of the update operation.
   * @since 0.1.0
   */
  suspend fun updateUser(
    user: User
  ): Int

  /**
   * Deletes a user from the data store by their unique identifier.
   *
   * This method removes a user record based on the provided [PlayerUuid]
   * identifier and returns an integer representing the result, usually the
   * number of rows affected.
   *
   * @param identifier The unique identifier of the user to delete.
   * @return An integer representing the result of the delete operation.
   * @since 0.1.0
   */
  suspend fun deleteUserByIdentifier(
    identifier: PlayerUuid
  ): Int

  /**
   * Counts the total number of users in the data store.
   *
   * This method returns the total count of user entries, which can be
   * useful for analytics or pagination purposes.
   *
   * @return A long integer representing the total number of users.
   * @since 0.1.0
   */
  suspend fun countUsers(): Long
}