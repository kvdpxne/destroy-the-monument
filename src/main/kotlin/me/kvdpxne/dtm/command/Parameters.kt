package me.kvdpxne.dtm.command

import me.kvdpxne.dtm.game.GameManager
import me.kvdpxne.dtm.user.UserManager

fun builderWorldNameParameter(
  name: String = "MAP_ARENA"
): ParameterBuilder<String> {
  return ParameterBuilder<String>()
    .name(name)
    .validationBy(ParameterValidators.STRING_VALIDATOR)
    .autocompletedWith { begin ->
      //
      emptyList()
    }
}

/**
 * @since 0.1.0
 */
fun builderUserNameParameter(
  name: String = "user_name"
): ParameterBuilder<String> {
  return ParameterBuilder<String>()
    .name(name)
    .validationBy(ParameterValidators.STRING_VALIDATOR)
    .autocompletedWith { begin ->
      //
      UserManager.activeUsers
        .filter { it.name.startsWith(begin, true) }
        .map { it.name }
    }
}

/**
 * @since 0.1.0
 */
fun builderArenaNameParameter(
  name: String = "arena_name"
): ParameterBuilder<String> {
  return ParameterBuilder<String>()
    .name(name)
    .validationBy(ParameterValidators.STRING_VALIDATOR)
    .autocompletedWith { begin ->
      //
      me.kvdpxne.dtm.game.ArenaService.findArenas()
        .filter { it.name.startsWith(begin) }
        .map { it.name }
    }
}

/**
 * @since 0.1.0
 */
fun builderGameNameParameter(
  name: String = "game_name"
): ParameterBuilder<String> {
  return ParameterBuilder<String>()
    .name(name)
    .validationBy(ParameterValidators.STRING_VALIDATOR)
    .autocompletedWith { begin ->
      //
      GameManager.games
        .filter { it.name.startsWith(begin) }
        .map { it.name }
    }
}