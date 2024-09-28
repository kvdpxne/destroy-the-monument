package me.kvdpxne.dtm.user

import java.util.UUID
import kotlinx.coroutines.runBlocking
import me.kvdpxne.dtm.data.UserDao

object UserService {

  fun findNames(): List<String> {
    return runBlocking {
      UserDao.getUsersNames()
    }
  }

  /**
   * @since 0.1.0
   */
  fun findUserByIdentifier(
    identifier: UUID
  ): User? {
    return runBlocking {
      UserDao.findUserByIdentifier(identifier)
    }
  }

  /**
   * @throws IllegalArgumentException
   */
  fun findUserByName(
    name: String,
    ignoreCase: Boolean = true
  ): User? {
    require(name.isNotBlank()) {
      "The username cannot be blank."
    }

    require(16 >= name.length) {
      "The username cannot be longer than 16 characters."
    }

    return runBlocking {
      UserDao.findUserByName(name, ignoreCase)
    }
  }

  fun createUser(user: User): User {
    return this.createUser(user.identifier, user.name)
  }

  /**
   * @since 0.1.0
   */
  fun createUser(
    identifier: UUID,
    name: String
  ): User {
    //
    val user: User = UserBuilder.create(identifier, name)
      .build()

    runBlocking {
      UserDao.insertUser(user)
    }



    return user
  }

  fun updateUser(
    user: User
  ) {

  }
}