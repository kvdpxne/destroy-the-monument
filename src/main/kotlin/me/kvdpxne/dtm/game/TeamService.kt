package me.kvdpxne.dtm.game

import me.kvdpxne.dtm.data.DaoTeam

/**
 * @since 0.1.0
 */
object TeamService {

  /**
   * @since 0.1.0
   */
  fun findTeamByIdentifier(
    identifier: String
  ): Team? {
    require(identifier.isNotBlank()) {
      "identifier cannot be blank"
    }

    return DaoTeam.findTeamByIdentifier(identifier)
  }

  /**
   * @since 0.1.0
   */
  fun findTeamByName(
    name: String
  ): Team? {
    return DaoTeam.findTeamByName(name)
  }

  /**
   * @since 0.1.0
   */
  fun insertTeamIdentity(
    team: Team
  ) {
    DaoTeam.insertTeam(team)
  }

//  fun existsTeamIdentityByIdentifier(
//    identifier: String
//  ): Boolean {
//    var found = teamIdentities.containsKey(identifier)
//    if (found) {
//      return true
//    }
//
//    return DaoTeam.findTeamByIdentifier(identifier) != null
//  }
}