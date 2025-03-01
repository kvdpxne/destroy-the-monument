package me.kvdpxne.dtm.data.validation.context

import me.kvdpxne.dtm.data.raw.RawTeam
import me.kvdpxne.dtm.data.validation.EVERYTHING_OK
import me.kvdpxne.dtm.data.validation.INVALID_REFERENCE
import me.kvdpxne.dtm.data.validation.INVALID_TEAM_COLOR_OF_ARMOR
import me.kvdpxne.dtm.data.validation.INVALID_TEAM_COLOR_OF_PROFESSION
import me.kvdpxne.dtm.data.validation.INVALID_TEAM_COLOR_ON_CHAT
import me.kvdpxne.dtm.data.validation.INVALID_TEAM_COLOR_ON_PLAYER_LIST
import me.kvdpxne.dtm.data.validation.INVALID_TEAM_NAME
import me.kvdpxne.dtm.data.validation.LEGAL_NAME_CHARACTERS
import me.kvdpxne.dtm.data.validation.REGEX_COLOR_HEX_NOTATION
import me.kvdpxne.dtm.data.validation.REGEX_COLOR_MINECRAFT_NOTATION

/**
 * @since 0.1.0
 */
fun isTeamNameValid(
  name: String?
): Boolean {
  if (null == name) {
    return false
  }

  if (name.length !in 2..24) {
    return false
  }

  for (character in name.toCharArray()) {
    if (character !in LEGAL_NAME_CHARACTERS) {
      return false
    }
  }

  return true
}

/**
 * @since 0.1.0
 */
fun isTeamColorFieldValid(
  color: String?
): Boolean {
  if (null == color) {
    return false
  }

  if (REGEX_COLOR_MINECRAFT_NOTATION.matches(color)) {
    return true
  }

  if (REGEX_COLOR_HEX_NOTATION.matches(color)) {
    return true
  }

  return false
}

/**
 * @since 0.1.0
 */
fun isTeamValid(
  team: RawTeam?
): Int {
  if (null == team) {
    return INVALID_REFERENCE
  }

  if (!isTeamNameValid(team.name)) {
    return INVALID_TEAM_NAME
  }

  if (!isTeamColorFieldValid(team.colorOfArmor)) {
    return INVALID_TEAM_COLOR_OF_ARMOR
  }

  if (!isTeamColorFieldValid(team.colorOfProfession)) {
    return INVALID_TEAM_COLOR_OF_PROFESSION
  }

  if (!isTeamColorFieldValid(team.colorOnChat)) {
    return INVALID_TEAM_COLOR_ON_CHAT
  }

  if (!isTeamColorFieldValid(team.colorOnPlayerList)) {
    return INVALID_TEAM_COLOR_ON_PLAYER_LIST
  }

  return EVERYTHING_OK
}