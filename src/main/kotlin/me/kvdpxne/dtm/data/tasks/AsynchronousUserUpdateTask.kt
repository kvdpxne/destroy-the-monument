package me.kvdpxne.dtm.data.tasks

import kotlinx.coroutines.runBlocking
import me.kvdpxne.dtm.data.UserDao
import me.kvdpxne.dtm.shared.debug.Debug
import me.kvdpxne.dtm.shared.task.AbstractAsynchronousTask
import me.kvdpxne.dtm.user.LocalUserManager
import me.kvdpxne.dtm.user.User

object AsynchronousUserUpdateTask : AbstractAsynchronousTask() {

  override fun execute() {
    runBlocking {
      var count = 0
      for (user: User in LocalUserManager.users) {
        if (0 != UserDao.updateUser(user)) {
          ++count
        }
      }

      Debug.log {
        "$count users have been updated."
      }
    }
  }
}