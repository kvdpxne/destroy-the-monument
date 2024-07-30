package me.kvdpxne.dtm.game

import me.kvdpxne.dtm.data.DaoTeam

object TeamService {

  val teamIdentities = mutableMapOf<String, TeamIdentity>()

  fun findTeamIdentity(identifier: String): TeamIdentity? {
    var teamIdentity = this.teamIdentities[identifier]
    if (null != teamIdentity) {
      return teamIdentity
    }

    teamIdentity = DaoTeam.findTeamByIdentifier(identifier)
    if (null != teamIdentity) {
      return teamIdentity
    }

    return null
  }

  fun findTeamIdentityByName(teamName: String): TeamIdentity? {
    var teamIdentity = this.teamIdentities.values.find {
      it.name.equals(teamName, true)
    }

    if (null != teamIdentity) {
      return teamIdentity
    }

    teamIdentity = DaoTeam.findTeamByName(teamName)
    if (null != teamIdentity) {
      return teamIdentity
    }

    return null
  }

  fun insertTeamIdentity(team: TeamIdentity) {
    this.teamIdentities[team.identifier] = team
  }

  fun existsTeamIdentityByIdentifier(
    identifier: String
  ): Boolean {
    var found = teamIdentities.containsKey(identifier)
    if (found) {
      return true
    }

    return DaoTeam.findTeamByIdentifier(identifier) != null
  }
}