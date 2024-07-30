package me.kvdpxne.dtm.user

import java.util.UUID
import me.kvdpxne.dtm.data.DaoUser

/**
 * @since 0.1.0
 */
object OfflineUserService {

  /**
   * @since 0.1.0
   */
  fun findUserByIdentifier(
    identifier: UUID
  ): User? {
    return DaoUser.findUserByIdentifierOrNull(identifier)
  }

  /**
   * @since 0.1.0
   */
  fun createUser(identifier: UUID, name: String): User {
    require(name.isNotBlank()) {
      "The given name must not be blank."
    }

    val user = User(name = name, identifier = identifier)
    DaoUser.insertUser(user)

    return user
  }
}