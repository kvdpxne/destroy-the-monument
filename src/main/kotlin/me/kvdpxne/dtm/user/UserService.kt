package me.kvdpxne.dtm.user

import me.kvdpxne.dtm.data.DaoUser

object UserService {

  fun findNames(): List<String> {
    return DaoUser.findNames()
  }

  /**
   * @since 0.1.0
   */
  fun findUserByIdentifier(
    identifier: String
  ): User? {
    return DaoUser.findUserByIdentifierOrNull(identifier)
  }

  fun findUserByName(
    name: String
  ): User? {
    return DaoUser.findUserByNameOrNull(name)
  }

  /**
   * @since 0.1.0
   */
  fun createUser(
    identifier: String,
    name: String
  ): User {
    //
    val user: User = UserBuilder.create(identifier, name)
      .build()

    DaoUser.insertUser(user)

    return user
  }
}