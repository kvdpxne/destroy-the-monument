package me.kvdpxne.dtm.team

import java.util.UUID
import kotlinx.coroutines.runBlocking
import me.kvdpxne.dtm.data.daos.TeamDao

/**
 * @since 0.1.0
 */
object TeamService {

  /**
   * @since 0.1.0
   */
  fun findTeamByIdentifier(
    identifier: UUID
  ): Team? {
    return runBlocking {
      TeamDao.findTeamByIdentifier(identifier)
    }
  }

  /**
   * @since 0.1.0
   */
  fun findTeamByName(
    name: String,
    ignoreCase: Boolean = true
  ): Team? {
    return runBlocking {
      TeamDao.findTeamByName(name, ignoreCase)
    }
  }

  fun createTeam(
    team: Team
  ) {
    runBlocking {
      TeamDao.insertTeam(team)
    }
  }
}