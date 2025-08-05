package me.kvdpxne.dtm.containers

import java.util.Locale
import me.kvdpxne.dtm.container.Container
import me.kvdpxne.dtm.container.ContainerBuilder
import me.kvdpxne.dtm.container.ContainerTypes
import me.kvdpxne.dtm.container.Rows
import me.kvdpxne.dtm.container.displayName
import me.kvdpxne.dtm.game.LocalGame
import me.kvdpxne.dtm.placeholder.Placeholders
import me.kvdpxne.dtm.shared.item.Items
import me.kvdpxne.dtm.shared.item.displayName
import me.kvdpxne.dtm.shared.item.lore
import me.kvdpxne.dtm.shared.player.addItemToLeaveTeam
import me.kvdpxne.dtm.shared.text.toSingleLines
import me.kvdpxne.dtm.team.LocalTeam
import me.kvdpxne.dtm.team.Teammate
import me.kvdpxne.dtm.team.translateName
import me.kvdpxne.dtm.translation.GrammaticalCases
import me.kvdpxne.dtm.translation.formatter.Formatter
import me.kvdpxne.dtm.translation.message.EnumTranslationKey
import me.kvdpxne.dtm.user.LocalUser
import me.kvdpxne.dtm.user.performer.LocalUserPerformer

/**
 * Defines the layout positions for team slots in the selection menu.
 * Determines where each team's icon appears in the interface.
 *
 * @since 0.1.0
 */
private val arrangement: ByteArray by lazy {
  byteArrayOf(0, 8, 1, 7, 2, 6, 3, 5)
}

/**
 * Moves a player from their current team to a new team.
 * Shows error message if move fails and notifies all players about the change.
 *
 * @param teammate Player to move between teams
 * @param team Target team to join
 * @param genitiveName Genitive form of team name for messages
 * @param game Current game context
 * @param user Player requesting the move
 * @since 0.1.0
 */
private fun relocate(
  teammate: Teammate,
  team: LocalTeam,
  genitiveName: String,
  game: LocalGame,
  user: LocalUser
) {
  if (!game.relocateTeammateToTeam(teammate, team)) {
    user.prepareMessage(EnumTranslationKey.TEAM_RELOCATE_FAILED)
      .withoutFormat()
      .useChat()
      .send()
    return
  }

  game.prepareMessage(EnumTranslationKey.TEAM_RELOCATE_BROADCAST)
    .format(
      Formatter.begin(2)
        .with(Placeholders.USER_NAME, user.displayName)
        .with(Placeholders.TEAM_NAME, genitiveName)
    )
    .useChat()
    .send()

  user.prepareMessage(EnumTranslationKey.TEAM_RELOCATE_SELF)
    .format(
      Formatter.begin(1)
        .with(Placeholders.TEAM_NAME, genitiveName)
    )
    .useChat()
    .send()

  user.performer.close()
}

/**
 * Adds a player to a team for the first time.
 * Notifies all players and equips team-specific items if the game hasn't started.
 *
 * @param team Target team to join
 * @param genitiveName Genitive form of team name for messages
 * @param game Current game context
 * @param user Player joining the team
 * @since 0.1.0
 */
private fun join(
  team: LocalTeam,
  genitiveName: String,
  game: LocalGame,
  user: LocalUser
) {
  check(game.addTeammate(team, user)) {
    """
      Error: for an unsupported reason, the "${user.name}" user could not be
      added to the "${team.name}" team in the "${game.name}" game.
    """.toSingleLines()
  }

  game.prepareMessage(EnumTranslationKey.TEAM_JOIN_BROADCAST)
    .format(
      Formatter.begin(2)
        .with(Placeholders.USER_NAME, user.displayName)
        .with(Placeholders.TEAM_NAME, genitiveName)
    )
    .useChat()
    .send()

  user.prepareMessage(EnumTranslationKey.TEAM_JOIN_SELF)
    .format(
      Formatter.begin(1)
        .with(Placeholders.TEAM_NAME, genitiveName)
    )
    .useChat()
    .send()

  if (!game.isRunning) {
    user.performer.player?.addItemToLeaveTeam()
  }

  user.performer.close()
}

/**
 * Decides whether to add a player to a team or move them between teams.
 * Uses existing team membership status to determine proper action.
 *
 * @param teammate Existing team membership (if any)
 * @param team Target team to join
 * @param genitiveName Genitive form of team name for messages
 * @param game Current game context
 * @param user Player making the selection
 * @since 0.1.0
 */
private fun joinOrRelocate(
  teammate: Teammate?,
  team: LocalTeam,
  genitiveName: String,
  game: LocalGame,
  user: LocalUser
) {
  if (null != teammate) {
    relocate(teammate, team, genitiveName, game, user)
    return
  }

  join(team, genitiveName, game, user)
}

/**
 * Creates the team selection menu for a player.
 * Displays all available teams with color-coded names and handles team selection logic.
 * Includes a center button for random team assignment based on team sizes.
 * Shows empty message when no teams are available.
 *
 * @param user Player viewing the team selection menu
 * @param game Current game context for team assignments
 * @return Interactive menu container for team selection
 * @since 0.1.0
 */
fun createTeamsContainer(
  user: LocalUser,
  game: LocalGame,
): Container<LocalUserPerformer> {
  val containerBuilder: ContainerBuilder<LocalUserPerformer> =
    ContainerBuilder.begin<LocalUserPerformer>()
      .owner(user.identifier)
      .type(ContainerTypes.CHEST)
      .size(Rows.nearestRows(game.numberOfTeams))
      .displayName(user.locale, EnumTranslationKey.CONTAINER_TEAMS_TITLE)

  if (0 >= game.numberOfTeams) {
    return containerBuilder
      .centerSlot(
        Items.stainedClayItem.copy()
          .generation(14)
          .displayName(user.locale, EnumTranslationKey.CONTAINER_TEAMS_ITEM_EMPTY_NAME)
          .lore(user.locale, EnumTranslationKey.CONTAINER_TEAMS_ITEM_EMPTY_NAME)
          .raw()
      )
      .build()
  }

  val iterator: ByteIterator = arrangement.iterator()

  val teammate: Teammate? = game.findTeammateByHostage(user)
  val locale: Locale = user.locale

  for (team: LocalTeam in game.teams) {

    val format = "${team.colorInChat}&l"

    val nominativeTeamName = "$format${team.translateName(locale, GrammaticalCases.NOMINATIVE)}"
    val genitiveName = "$format${team.translateName(locale, GrammaticalCases.GENITIVE)}"

    containerBuilder.slot(
      iterator.next(),
      Items.createTeamRepresentationItem(
        team,
        nominativeTeamName,
        genitiveName,
        locale
      ),
    ) { _: LocalUserPerformer ->
      joinOrRelocate(teammate, team, genitiveName, game, user)
    }
  }

  containerBuilder.slot(
    4,
    Items.createRandomTeamItem(locale)
  ) { _: LocalUserPerformer ->
    // Selects a random team according to the criteria, if all in the teams in
    // the game have the same size (the same number of players) then a random
    // team will be selected from among these teams, if the teams do not have
    // the same size, the team with the smallest size will be selected.
    val team: LocalTeam = game.criterionTeam

    val format = "${team.colorInChat}&l"
    val genitiveName = "$format${team.translateName(locale, GrammaticalCases.GENITIVE)}"

    joinOrRelocate(teammate, team, genitiveName, game, user)
  }

  return containerBuilder.build()
}