package me.kvdpxne.dtm.command

import me.kvdpxne.dtm.game.ArenaManager
import me.kvdpxne.dtm.game.GameManager
import me.kvdpxne.dtm.user.UserManager

fun builderUserNameParameter(
  name: String = "user_name"
): ParameterBuilder<String> {
  return ParameterBuilder<String>()
    .name(name)
    .validationBy(ParameterValidators.STRING_VALIDATOR)
    .autocompletedWith { begin, _ ->
      //
      UserManager.users
        .filter { it.name.startsWith(begin, true) }
        .map { it.name }
    }
}

/**
 *
 */
fun builderArenaNameParameter(
  name: String = "arena_name"
): ParameterBuilder<String> {
  return ParameterBuilder<String>()
    .name(name)
    .validationBy(ParameterValidators.STRING_VALIDATOR)
    .autocompletedWith { begin, _ ->
      //
      ArenaManager.registeredArenas
        .filter { it.name.startsWith(begin) }
        .map { it.name }
    }
}

fun builderGameNameParameter(
  name: String = "game_name"
): ParameterBuilder<String> {
  return ParameterBuilder<String>()
    .name(name)
    .validationBy(ParameterValidators.STRING_VALIDATOR)
    .autocompletedWith { begin, _ ->
      //
      GameManager.registeredGames
        .filter { it.name.startsWith(begin) }
        .map { it.name }
    }
}