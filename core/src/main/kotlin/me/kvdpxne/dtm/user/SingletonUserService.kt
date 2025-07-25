package me.kvdpxne.dtm.user

import java.util.UUID
import kotlinx.coroutines.runBlocking
import me.kvdpxne.dtm.data.dao.UserDao
import me.kvdpxne.dtm.data.raw.RawUser
import me.kvdpxne.dtm.data.validation.ValidationResult
import me.kvdpxne.dtm.data.validation.common.isNameValid
import me.kvdpxne.dtm.data.validation.rules.Ru
import me.kvdpxne.dtm.user.extensions.toUser
import org.jetbrains.annotations.Range

object SingletonUserService : UserService {

  override fun findUserByIdentifierOrNull(
    identifier: UUID
  ): User? {
    return runBlocking {
      val (user: RawUser, result: ValidationResult) =
        UserDao.findUserByIdentifierOrNull(identifier)
          ?: return@runBlocking null

      if (result.isValid) {
        return@runBlocking user.toUser()
      }

      // TODO proba naprawy danych
      result as ValidationResult.Failure
//      val errors: Collection<ValidationError> = result.errors

      throw UnsupportedOperationException(result.toString())
    }
  }

  override fun findUserByNameOrNull(
    name: String
  ): User? {
    require(isNameValid(name, Ru.MIN_NAME_LENGTH..Ru.MAX_NAME_LENGTH)) {
      "User name must be between 3..16 and below"
    }

    return runBlocking {
      val (user: RawUser, result: ValidationResult) =
        UserDao.findUserByNameOrNull(name)
          ?: return@runBlocking null

      if (result.isValid) {
        return@runBlocking user.toUser()
      }

      // TODO proba naprawy danych
      result as ValidationResult.Failure
//      val errors: Collection<ValidationError> = result.errors

      throw UnsupportedOperationException(result.toString())
    }
  }

  override fun createUser(
    user: User,
    ignoreNew: Boolean
  ): User {
    if (ignoreNew) {
      val present: Boolean = runBlocking {
        if (user.isNew) {
          UserDao.containsUserByName(user.name)
        } else {
          UserDao.containsUserByIdentifier(user.identifier)
        }
      }

      if (present) {
        throw UserDuplicationException("User already exists.")
      }
    } else {
      check(!user.isNew) {
        "The passed user object is marked as not new."
      }
    }


  }

  override fun updateUser(user: User): User {
    check(user.isNew) {
      ""
    }
  }

  override fun deleteUser(user: User): Boolean {
    TODO("Not yet implemented")
  }

  override fun deleteUserByIdentifier(identifier: UUID): Boolean {
    TODO("Not yet implemented")
  }

  override fun deleteUserByName(name: String): Boolean {
    TODO("Not yet implemented")
  }

  override fun countUsers(): @Range(from = 0, to = Long.MAX_VALUE) Long {
    return runBlocking {
      UserDao.countUsers()
    }
  }
}