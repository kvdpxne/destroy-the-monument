package me.kvdpxne.dtm.user

import java.util.UUID
import me.kvdpxne.dtm.data.UserDao

/**
 * Service responsible for the life cycle of the [User].
 */
object UserLifecycleService {

  @Throws(UserNotFoundException::class)
  fun getUserByIdentifier(identifier: UUID): User {
    val user: User? = UserDao.findUserByIdentifierOrNull(identifier)

    return user ?: throw UserNotFoundException(
      "No user with the same identifier as \"$identifier\" was found."
    )
  }

  @Throws(UserNotFoundException::class)
  fun getUserByName(name: String): User {
    val user: User? = UserDao.findUserByNameOrNull(name)

    return user ?: throw UserNotFoundException(
      "No user with the same name as \"$name\" was found."
    )
  }

  @Throws(UserAlreadyExistsException::class)
  fun createUser(user: User) {
    UserDao.insert(user)
  }

  @Throws(UserNotFoundException::class)
  fun updateUser(user: User): Boolean {
    return true
  }

  @Throws(UserNotFoundException::class)
  fun deleteUser(user: User): Boolean {
    return true
  }

  fun size(): Int {
    return UserDao.count()
  }
}