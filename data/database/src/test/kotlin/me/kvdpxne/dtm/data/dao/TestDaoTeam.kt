package me.kvdpxne.dtm.data.dao

import kotlin.random.Random
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue
import kotlinx.coroutines.runBlocking
import me.kvdpxne.dtm.data.ResponseCodes
import me.kvdpxne.dtm.data.raw.RawTeam
import me.kvdpxne.dtm.data.validation.INVALID_TEAM_COLOR_OF_ARMOR
import me.kvdpxne.dtm.data.validation.INVALID_TEAM_COLOR_OF_PROFESSION
import me.kvdpxne.dtm.data.validation.INVALID_TEAM_COLOR_ON_CHAT
import me.kvdpxne.dtm.data.validation.INVALID_TEAM_COLOR_ON_PLAYER_LIST
import me.kvdpxne.dtm.data.validation.INVALID_TEAM_NAME
import me.kvdpxne.dtm.raw.factories.makeRawTeam
import me.kvdpxne.dtm.shared.uniqueUuid
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestMethodOrder

/**
 * @since 0.1.0
 */
private val TEAM: RawTeam = makeRawTeam()

/**
 * @since 0.1.0
 */
@Order(0)
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TestDaoTeam {

  /**
   * @since 0.1.0
   */
  @AfterAll
  fun `cleanup battlefield after battle`() {
    runBlocking {
      TeamDao.truncateTeams()
    }
  }

  /**
   * @since 0.1.0
   */
  @BeforeAll
  fun `prepare battlefield`() {
    this.`cleanup battlefield after battle`()
    println(TEAM.toStylishString().listed(2))
  }

  @Order(0)
  @Test
  fun `insert team`() {
    assertEquals(
      1,
      runBlocking {
        TeamDao.insertTeam(TEAM)
      }
    )
  }

  @Order(1)
  @Test
  fun `insert duplicated team`() {
    assertEquals(
      ResponseCodes.DUPLICATED,
      runBlocking {
        TeamDao.insertTeam(TEAM)
      }
    )
  }

  @Order(2)
  @Test
  fun `insert team with invalid name`() {
    val tooLong = TEAM.copy(
      // The maximum allowed length of the team name is 24 characters.
      name = "AAAAAAAAAAAAAAAAAAAAAAAAA"
    )

    assertEquals(
      INVALID_TEAM_NAME,
      runBlocking {
        TeamDao.insertTeam(tooLong)
      }
    )

    val tooShort = TEAM.copy(
      // The minimum allowed length of the team name is 2 characters.
      name = "A"
    )

    assertEquals(
      INVALID_TEAM_NAME,
      runBlocking {
        TeamDao.insertTeam(tooShort)
      }
    )

    val illegalCharacters = TEAM.copy(
      // Allowed characters going into the team name are upper and lower case
      // letters, numbers and underscore all others are not allowed.
      name = "AA*()420$$#@! WASD"
    )

    assertEquals(
      INVALID_TEAM_NAME,
      runBlocking {
        TeamDao.insertTeam(illegalCharacters)
      }
    )
  }

  @Order(3)
  @Test
  fun `insert team with invalid color of armor`() {
    val obfuscated = TEAM.copy(
      colorOfArmor = "&k"
    )

    assertEquals(
      INVALID_TEAM_COLOR_OF_ARMOR,
      runBlocking {
        TeamDao.insertTeam(obfuscated)
      }
    )

    val many = TEAM.copy(
      colorOfArmor = "&0&l"
    )

    assertEquals(
      INVALID_TEAM_COLOR_OF_ARMOR,
      runBlocking {
        TeamDao.insertTeam(many)
      }
    )

    val invalid = TEAM.copy(
      colorOfArmor = "a"
    )

    assertEquals(
      INVALID_TEAM_COLOR_OF_ARMOR,
      runBlocking {
        TeamDao.insertTeam(invalid)
      }
    )

    val hexTooLongNotation = TEAM.copy(
      colorOfArmor = "#0000000"
    )

    assertEquals(
      INVALID_TEAM_COLOR_OF_ARMOR,
      runBlocking {
        TeamDao.insertTeam(hexTooLongNotation)
      }
    )

    val hexTooShortNotation = TEAM.copy(
      colorOfArmor = "&00"
    )

    assertEquals(
      INVALID_TEAM_COLOR_OF_ARMOR,
      runBlocking {
        TeamDao.insertTeam(hexTooShortNotation)
      }
    )
  }

  @Order(4)
  @Test
  fun `insert team with invalid color of profession`() {
    val obfuscated = TEAM.copy(
      colorOfProfession = "&k"
    )

    assertEquals(
      INVALID_TEAM_COLOR_OF_PROFESSION,
      runBlocking {
        TeamDao.insertTeam(obfuscated)
      }
    )

    val many = TEAM.copy(
       colorOfProfession = "&0&l"
    )

    assertEquals(
      INVALID_TEAM_COLOR_OF_PROFESSION,
      runBlocking {
        TeamDao.insertTeam(many)
      }
    )

    val invalid = TEAM.copy(
       colorOfProfession = "a"
    )

    assertEquals(
      INVALID_TEAM_COLOR_OF_PROFESSION,
      runBlocking {
        TeamDao.insertTeam(invalid)
      }
    )

    val hexTooLong = TEAM.copy(
       colorOfProfession = "#0000000"
    )

    assertEquals(
      INVALID_TEAM_COLOR_OF_PROFESSION,
      runBlocking {
        TeamDao.insertTeam(hexTooLong)
      }
    )

    val hexTooShort = TEAM.copy(
       colorOfProfession = "&00"
    )

    assertEquals(
      INVALID_TEAM_COLOR_OF_PROFESSION,
      runBlocking {
        TeamDao.insertTeam(hexTooShort)
      }
    )
  }

  @Order(5)
  @Test
  fun `insert team with invalid color on chat`() {
    val obfuscated = TEAM.copy(
      colorOnChat = "&k"
    )

    assertEquals(
      INVALID_TEAM_COLOR_ON_CHAT,
      runBlocking {
        TeamDao.insertTeam(obfuscated)
      }
    )

    val many = TEAM.copy(
      colorOnChat = "&0&l"
    )

    assertEquals(
      INVALID_TEAM_COLOR_ON_CHAT,
      runBlocking {
        TeamDao.insertTeam(many)
      }
    )

    val invalid = TEAM.copy(
      colorOnChat = "a"
    )

    assertEquals(
      INVALID_TEAM_COLOR_ON_CHAT,
      runBlocking {
        TeamDao.insertTeam(invalid)
      }
    )

    val hexTooLong = TEAM.copy(
      colorOnChat = "#0000000"
    )

    assertEquals(
      INVALID_TEAM_COLOR_ON_CHAT,
      runBlocking {
        TeamDao.insertTeam(hexTooLong)
      }
    )

    val hexTooShort = TEAM.copy(
      colorOnChat = "&00"
    )

    assertEquals(
      INVALID_TEAM_COLOR_ON_CHAT,
      runBlocking {
        TeamDao.insertTeam(hexTooShort)
      }
    )
  }

  @Order(6)
  @Test
  fun `insert team with invalid color on player list`() {
    val obfuscated = TEAM.copy(
      colorOnPlayerList = "&k"
    )

    assertEquals(
      INVALID_TEAM_COLOR_ON_PLAYER_LIST,
      runBlocking {
        TeamDao.insertTeam(obfuscated)
      }
    )

    val many = TEAM.copy(
      colorOnPlayerList = "&0&l"
    )

    assertEquals(
      INVALID_TEAM_COLOR_ON_PLAYER_LIST,
      runBlocking {
        TeamDao.insertTeam(many)
      }
    )

    val invalid = TEAM.copy(
      colorOnPlayerList = "a"
    )

    assertEquals(
      INVALID_TEAM_COLOR_ON_PLAYER_LIST,
      runBlocking {
        TeamDao.insertTeam(invalid)
      }
    )

    val hexTooLong = TEAM.copy(
      colorOnPlayerList = "#0000000"
    )

    assertEquals(
      INVALID_TEAM_COLOR_ON_PLAYER_LIST,
      runBlocking {
        TeamDao.insertTeam(hexTooLong)
      }
    )

    val hexTooShort = TEAM.copy(
      colorOnPlayerList = "&00"
    )

    assertEquals(
      INVALID_TEAM_COLOR_ON_PLAYER_LIST,
      runBlocking {
        TeamDao.insertTeam(hexTooShort)
      }
    )
  }

  @Order(7)
  @Test
  fun `find team by identifier`() {
    assertEquals(
      TEAM,
      runBlocking {
        TeamDao.findTeamByIdentifierOrNull(
          TEAM.identifier
        )
      }
    )
  }

  @Order(8)
  @Test
  fun `find non existent team by identifier`() {
    assertNull(
      runBlocking {
        TeamDao.findTeamByIdentifierOrNull(
          uniqueUuid(TEAM.identifier)
        )
      }
    )
  }

  @Order(9)
  @Test
  fun `find team by name`() {
    assertEquals(
      TEAM,
      runBlocking {
        TeamDao.findTeamByNameOrNull(
          TEAM.name
        )
      }
    )

    assertEquals(
      TEAM,
      runBlocking {
        TeamDao.findTeamByNameOrNull(
          TEAM.name.uppercase()
        )
      }
    )

    assertEquals(
      TEAM,
      runBlocking {
        TeamDao.findTeamByNameOrNull(
          TEAM.name.lowercase()
        )
      }
    )
  }

  @Order(10)
  @Test
  fun `find non existent team by name`() {
    assertNull(
      runBlocking {
        TeamDao.findTeamByNameOrNull(
          Random.nextInt(1_000, 1_000_000).toString()
        )
      }
    )
  }

  @Order(11)
  @Test
  fun `contains team by identifier`() {
    assertTrue(
      runBlocking {
        TeamDao.containsTeamByIdentifier(TEAM.identifier)
      }
    )
  }

  @Order(12)
  @Test
  fun `contains non existent team by identifier`() {
    assertFalse(
      runBlocking {
        TeamDao.containsTeamByIdentifier(
          uniqueUuid(TEAM.identifier)
        )
      }
    )
  }

  @Order(13)
  @Test
  fun `contains team by name`() {
    assertTrue(
      runBlocking {
        TeamDao.containsTeamByName(
          TEAM.name
        )
      }
    )

    assertTrue(
      runBlocking {
        TeamDao.containsTeamByName(
          TEAM.name.uppercase()
        )
      }
    )

    assertTrue(
      runBlocking {
        TeamDao.containsTeamByName(
          TEAM.name.lowercase()
        )
      }
    )
  }

  @Order(14)
  @Test
  fun `contains non existent team by name`() {
    assertFalse(
      runBlocking {
        TeamDao.containsTeamByName(
          Random.nextInt(1_000, 1_000_000).toString()
        )
      }
    )
  }

  @Order(15)
  @Test
  fun `update team`() {
    val updated = TEAM.copy(
      // @formatter:off
      name              = "white",
      colorOfArmor      = "#fff",
      colorOfProfession = "&f",
      colorOnChat       = "&f",
      colorOnPlayerList = "&f"
      // @formatter:on
    )

    assertEquals(
      1,
      runBlocking {
        TeamDao.updateTeam(updated)
      }
    )

    assertEquals(
      updated,
      runBlocking {
        TeamDao.findTeamByIdentifierOrNull(
          updated.identifier
        )
      }
    )
  }

  @Order(16)
  @Test
  fun `update non existent team`() {
    val updated = TEAM.copy(
      // @formatter:off
      identifier        = uniqueUuid(TEAM.identifier),
      name              = "white",
      colorOfArmor      = "#fff",
      colorOfProfession = "&f",
      colorOnChat       = "&f",
      colorOnPlayerList = "&f"
      // @formatter:on
    )

    assertEquals(
      ResponseCodes.NO_RECORD,
      runBlocking {
        TeamDao.updateTeam(updated)
      }
    )
  }

  @Order(17)
  @Test
  fun `update team with invalid name`() {
    val tooLong = TEAM.copy(
      // The maximum allowed length of the team name is 24 characters.
      name = "AAAAAAAAAAAAAAAAAAAAAAAAA"
    )

    assertEquals(
      INVALID_TEAM_NAME,
      runBlocking {
        TeamDao.updateTeam(tooLong)
      }
    )

    val tooShort = TEAM.copy(
      // The minimum allowed length of the team name is 2 characters.
      name = "A"
    )

    assertEquals(
      INVALID_TEAM_NAME,
      runBlocking {
        TeamDao.updateTeam(tooShort)
      }
    )

    val illegalCharacters = TEAM.copy(
      // Allowed characters going into the team name are upper and lower case
      // letters, numbers and underscore all others are not allowed.
      name = "AA*()420$$#@! WASD"
    )

    assertEquals(
      INVALID_TEAM_NAME,
      runBlocking {
        TeamDao.updateTeam(illegalCharacters)
      }
    )
  }

  @Order(18)
  @Test
  fun `update team with invalid color of armor`() {
    val obfuscated = TEAM.copy(
      colorOfArmor = "&k"
    )

    assertEquals(
      INVALID_TEAM_COLOR_OF_ARMOR,
      runBlocking {
        TeamDao.updateTeam(obfuscated)
      }
    )

    val many = TEAM.copy(
      colorOfArmor = "&0&l"
    )

    assertEquals(
      INVALID_TEAM_COLOR_OF_ARMOR,
      runBlocking {
        TeamDao.updateTeam(many)
      }
    )

    val invalid = TEAM.copy(
      colorOfArmor = "a"
    )

    assertEquals(
      INVALID_TEAM_COLOR_OF_ARMOR,
      runBlocking {
        TeamDao.updateTeam(invalid)
      }
    )

    val hexTooLongNotation = TEAM.copy(
      colorOfArmor = "#0000000"
    )

    assertEquals(
      INVALID_TEAM_COLOR_OF_ARMOR,
      runBlocking {
        TeamDao.updateTeam(hexTooLongNotation)
      }
    )

    val hexTooShortNotation = TEAM.copy(
      colorOfArmor = "&00"
    )

    assertEquals(
      INVALID_TEAM_COLOR_OF_ARMOR,
      runBlocking {
        TeamDao.updateTeam(hexTooShortNotation)
      }
    )
  }

  @Order(19)
  @Test
  fun `update team with invalid color of profession`() {
    val obfuscated = TEAM.copy(
      colorOfProfession = "&k"
    )

    assertEquals(
      INVALID_TEAM_COLOR_OF_PROFESSION,
      runBlocking {
        TeamDao.updateTeam(obfuscated)
      }
    )

    val many = TEAM.copy(
      colorOfProfession = "&0&l"
    )

    assertEquals(
      INVALID_TEAM_COLOR_OF_PROFESSION,
      runBlocking {
        TeamDao.updateTeam(many)
      }
    )

    val invalid = TEAM.copy(
      colorOfProfession = "a"
    )

    assertEquals(
      INVALID_TEAM_COLOR_OF_PROFESSION,
      runBlocking {
        TeamDao.updateTeam(invalid)
      }
    )

    val hexTooLong = TEAM.copy(
      colorOfProfession = "#0000000"
    )

    assertEquals(
      INVALID_TEAM_COLOR_OF_PROFESSION,
      runBlocking {
        TeamDao.updateTeam(hexTooLong)
      }
    )

    val hexTooShort = TEAM.copy(
      colorOfProfession = "&00"
    )

    assertEquals(
      INVALID_TEAM_COLOR_OF_PROFESSION,
      runBlocking {
        TeamDao.updateTeam(hexTooShort)
      }
    )
  }

  @Order(20)
  @Test
  fun `update team with invalid color on chat`() {
    val obfuscated = TEAM.copy(
      colorOnChat = "&k"
    )

    assertEquals(
      INVALID_TEAM_COLOR_ON_CHAT,
      runBlocking {
        TeamDao.updateTeam(obfuscated)
      }
    )

    val many = TEAM.copy(
      colorOnChat = "&0&l"
    )

    assertEquals(
      INVALID_TEAM_COLOR_ON_CHAT,
      runBlocking {
        TeamDao.updateTeam(many)
      }
    )

    val invalid = TEAM.copy(
      colorOnChat = "a"
    )

    assertEquals(
      INVALID_TEAM_COLOR_ON_CHAT,
      runBlocking {
        TeamDao.updateTeam(invalid)
      }
    )

    val hexTooLong = TEAM.copy(
      colorOnChat = "#0000000"
    )

    assertEquals(
      INVALID_TEAM_COLOR_ON_CHAT,
      runBlocking {
        TeamDao.updateTeam(hexTooLong)
      }
    )

    val hexTooShort = TEAM.copy(
      colorOnChat = "&00"
    )

    assertEquals(
      INVALID_TEAM_COLOR_ON_CHAT,
      runBlocking {
        TeamDao.updateTeam(hexTooShort)
      }
    )
  }

  @Order(21)
  @Test
  fun `update team with invalid color on player list`() {
    val obfuscated = TEAM.copy(
      colorOnPlayerList = "&k"
    )

    assertEquals(
      INVALID_TEAM_COLOR_ON_PLAYER_LIST,
      runBlocking {
        TeamDao.updateTeam(obfuscated)
      }
    )

    val many = TEAM.copy(
      colorOnPlayerList = "&0&l"
    )

    assertEquals(
      INVALID_TEAM_COLOR_ON_PLAYER_LIST,
      runBlocking {
        TeamDao.updateTeam(many)
      }
    )

    val invalid = TEAM.copy(
      colorOnPlayerList = "a"
    )

    assertEquals(
      INVALID_TEAM_COLOR_ON_PLAYER_LIST,
      runBlocking {
        TeamDao.updateTeam(invalid)
      }
    )

    val hexTooLong = TEAM.copy(
      colorOnPlayerList = "#0000000"
    )

    assertEquals(
      INVALID_TEAM_COLOR_ON_PLAYER_LIST,
      runBlocking {
        TeamDao.updateTeam(hexTooLong)
      }
    )

    val hexTooShort = TEAM.copy(
      colorOnPlayerList = "&00"
    )

    assertEquals(
      INVALID_TEAM_COLOR_ON_PLAYER_LIST,
      runBlocking {
        TeamDao.updateTeam(hexTooShort)
      }
    )
  }

  @Order(22)
  @Test
  fun `count teams`() {
    assertEquals(
      1,
      runBlocking {
        TeamDao.countTeams()
      }
    )
  }

  @Order(23)
  @Test
  fun `delete team by identifier`() {
    assertEquals(
      1,
      runBlocking {
        TeamDao.deleteTeamByIdentifier(
          TEAM.identifier
        )
      }
    )

    assertNull(
      runBlocking {
        TeamDao.findTeamByIdentifierOrNull(
          TEAM.identifier
        )
      }
    )
  }

  @Order(24)
  @Test
  fun `delete non existent team by identifier`() {
    assertEquals(
      0,
      runBlocking {
        TeamDao.deleteTeamByIdentifier(
          uniqueUuid(TEAM.identifier)
        )
      }
    )
  }

  @Order(25)
  @Test
  fun `delete teams`() {
    assertEquals(
      0,
      runBlocking {
        TeamDao.truncateTeams()
      }
    )
  }
}