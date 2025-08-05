package me.kvdpxne.dtm.commands

import me.kvdpxne.dtm.command.Command
import me.kvdpxne.dtm.command.CommandBuilder
import me.kvdpxne.dtm.command.Performer
import me.kvdpxne.dtm.translation.formatter.Formatter
import me.kvdpxne.dtm.translation.message.EnumTranslationKey
import me.kvdpxne.dtm.user.performer.LocalUserPerformer
import me.kvdpxne.dtm.user.User
import me.kvdpxne.dtm.user.UserService

fun createUserCommand(): Command<Performer> {
  return CommandBuilder.begin<Performer>("user")
    .hub()
    .children(
      createUserInformationCommand()
    )
    .build()
}

/**
 * @param receiver
 * @param parameters
 * @param index
 *
 * @since 0.1.0
 */
internal fun attemptObtainUser(
  receiver: Performer,
  parameters: Array<Any>,
  index: Int = 0
): User {
  val userName: String = parameters[index] as String

  val user: User = UserService.findUserByName(userName)
    ?: receiver.throwMessage(EnumTranslationKey.USER_NO_FOUND) {
      this@throwMessage.format(
        Formatter.begin(1)
          .with("USER_NAME", userName)
      )
    }

  return user
}

/**
 * @param receiver
 *
 * @since 0.1.0
 */
internal fun attemptObtainUserAsSelf(
  receiver: Performer
): User {
  if (receiver is LocalUserPerformer) {
    return receiver.user
  }

  receiver.throwMessage(EnumTranslationKey.COMMAND_IN_GAME) {
    this@throwMessage.withoutFormat()
  }
}