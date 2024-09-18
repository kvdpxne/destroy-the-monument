package me.kvdpxne.dtm.command

import me.kvdpxne.dtm.shared.Worlds
import me.kvdpxne.dtm.user.LocalUserManager
import me.kvdpxne.dtm.user.User
import me.kvdpxne.dtm.user.UserService
import org.bukkit.Bukkit
import org.bukkit.World

/**
 * @since 0.1.0
 */
object Parameters {

  /**
   * @since 0.1.0
   */
  fun arenaNameParameter(
    parameterName: String = "ARENA_NAME"
  ): ParameterBuilder<String> {
    return ParameterBuilder.begin<String>(parameterName)
      .validatorHandler(ParameterValidators.STRING_VALIDATOR)
      .suggestionHandler {
        emptyList()
      }
  }

  /**
   * @since 0.1.0
   */
  fun arenaWorldNameParameter(
    parameterName: String = "ARENA_WORLD_NAME"
  ): ParameterBuilder<String> {
    return ParameterBuilder.begin<String>(parameterName)
      .validatorHandler(ParameterValidators.STRING_VALIDATOR)
      .suggestionHandler {
        Bukkit.getWorlds().map { world: World ->
          world.name
        }
      }
  }

  fun gameNameParameter(
    parameterName: String = "GAME_NAME"
  ): ParameterBuilder<String> {
    return ParameterBuilder.begin<String>(parameterName)
      .validatorHandler(ParameterValidators.STRING_VALIDATOR)
      .suggestionHandler {
        emptyList()
      }
  }

  /**
   * @since 0.1.0
   */
  fun localGameNameParameter(
    parameterName: String = "GAME_NAME"
  ): ParameterBuilder<String> {
    return ParameterBuilder.begin<String>(parameterName)
      .validatorHandler(ParameterValidators.STRING_VALIDATOR)
  }

  /**
   * @since 0.1.0
   */
  fun userNameParameter(
    parameterName: String = "USER_NAME"
  ): ParameterBuilder<String> {
    return ParameterBuilder.begin<String>(parameterName)
      .validatorHandler(ParameterValidators.STRING_VALIDATOR)
      .suggestionHandler {
        UserService.findNames()
      }
  }

  /**
   * @since 0.1.0
   */
  fun teamNameParameter(
    parameterName: String = "TEAM_NAME"
  ): ParameterBuilder<String> {
    return ParameterBuilder.begin<String>(parameterName)
      .validatorHandler(ParameterValidators.STRING_VALIDATOR)
      .suggestionHandler {
        emptyList()
      }
  }

  /**
   * @since 0.1.0
   */
  fun localUserNameParameter(
    parameterName: String = "USER_NAME"
  ): ParameterBuilder<String> {
    return ParameterBuilder.begin<String>(parameterName)
      .validatorHandler(ParameterValidators.STRING_VALIDATOR)
      .suggestionHandler { input: String ->
        LocalUserManager.users
          .filter { localUser: User -> localUser.name.startsWith(input) }
          .map { localUser: User -> localUser.name }
      }
  }

  /**
   * @since 0.1.0
   */
  fun localWorldNameParameter(
    parameterName: String = "LOCAL_WORLD_NAME"
  ): ParameterBuilder<String> {
    return ParameterBuilder.begin<String>(parameterName)
      .validatorHandler(ParameterValidators.STRING_VALIDATOR)
      .suggestionHandler {
        Worlds.localWorldsNames
      }
  }
}


