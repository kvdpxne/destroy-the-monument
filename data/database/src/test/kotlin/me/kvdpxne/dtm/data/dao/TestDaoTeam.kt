package me.kvdpxne.dtm.data.dao

import kotlin.random.Random
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue
import kotlinx.coroutines.runBlocking
import me.kvdpxne.dtm.data.ResponseCodes
import me.kvdpxne.dtm.data.raw.RawTeam
import me.kvdpxne.dtm.data.util.UniqueUuid
import me.kvdpxne.dtm.data.validation.INVALID_TEAM_COLOR_OF_ARMOR
import me.kvdpxne.dtm.data.validation.INVALID_TEAM_COLOR_OF_PROFESSION
import me.kvdpxne.dtm.data.validation.INVALID_TEAM_COLOR_ON_CHAT
import me.kvdpxne.dtm.data.validation.INVALID_TEAM_COLOR_ON_PLAYER_LIST
import me.kvdpxne.dtm.data.validation.INVALID_TEAM_NAME
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestMethodOrder

@Order(0)
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TestDaoTeam {

  companion object {

    // Creating an unacceptable object via the constructor is always possible
    // but should be used only for testing.
    internal val TEAM = RawTeam(
      // @formatter:off
      identifier        = UniqueUuid.v4(),
      name              = "black",
      colorOfArmor      = "#000000",
      colorOfProfession = "&8",
      colorOnChat       = "&7",
      colorOnPlayerList = "&7"
      // @formatter:on
    )
  }

  @Order(0)
  @Test
  fun `insert team`() {
    assertEquals(
      1,
      runBlocking {
        DaoTeam.insertTeam(TEAM)
      }
    )
  }

  @Order(1)
  @Test
  fun `insert duplicated team`() {
    assertEquals(
      ResponseCodes.DUPLICATED,
      runBlocking {
        DaoTeam.insertTeam(TEAM)
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
        DaoTeam.insertTeam(tooLong)
      }
    )

    val tooShort = TEAM.copy(
      // The minimum allowed length of the team name is 2 characters.
      name = "A"
    )

    assertEquals(
      INVALID_TEAM_NAME,
      runBlocking {
        DaoTeam.insertTeam(tooShort)
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
        DaoTeam.insertTeam(illegalCharacters)
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
        DaoTeam.insertTeam(obfuscated)
      }
    )

    val many = TEAM.copy(
      colorOfArmor = "&0&l"
    )

    assertEquals(
      INVALID_TEAM_COLOR_OF_ARMOR,
      runBlocking {
        DaoTeam.insertTeam(many)
      }
    )

    val invalid = TEAM.copy(
      colorOfArmor = "a"
    )

    assertEquals(
      INVALID_TEAM_COLOR_OF_ARMOR,
      runBlocking {
        DaoTeam.insertTeam(invalid)
      }
    )

    val hexTooLongNotation = TEAM.copy(
      colorOfArmor = "#0000000"
    )

    assertEquals(
      INVALID_TEAM_COLOR_OF_ARMOR,
      runBlocking {
        DaoTeam.insertTeam(hexTooLongNotation)
      }
    )

    val hexTooShortNotation = TEAM.copy(
      colorOfArmor = "&00"
    )

    assertEquals(
      INVALID_TEAM_COLOR_OF_ARMOR,
      runBlocking {
        DaoTeam.insertTeam(hexTooShortNotation)
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
        DaoTeam.insertTeam(obfuscated)
      }
    )

    val many = TEAM.copy(
       colorOfProfession = "&0&l"
    )

    assertEquals(
      INVALID_TEAM_COLOR_OF_PROFESSION,
      runBlocking {
        DaoTeam.insertTeam(many)
      }
    )

    val invalid = TEAM.copy(
       colorOfProfession = "a"
    )

    assertEquals(
      INVALID_TEAM_COLOR_OF_PROFESSION,
      runBlocking {
        DaoTeam.insertTeam(invalid)
      }
    )

    val hexTooLong = TEAM.copy(
       colorOfProfession = "#0000000"
    )

    assertEquals(
      INVALID_TEAM_COLOR_OF_PROFESSION,
      runBlocking {
        DaoTeam.insertTeam(hexTooLong)
      }
    )

    val hexTooShort = TEAM.copy(
       colorOfProfession = "&00"
    )

    assertEquals(
      INVALID_TEAM_COLOR_OF_PROFESSION,
      runBlocking {
        DaoTeam.insertTeam(hexTooShort)
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
        DaoTeam.insertTeam(obfuscated)
      }
    )

    val many = TEAM.copy(
      colorOnChat = "&0&l"
    )

    assertEquals(
      INVALID_TEAM_COLOR_ON_CHAT,
      runBlocking {
        DaoTeam.insertTeam(many)
      }
    )

    val invalid = TEAM.copy(
      colorOnChat = "a"
    )

    assertEquals(
      INVALID_TEAM_COLOR_ON_CHAT,
      runBlocking {
        DaoTeam.insertTeam(invalid)
      }
    )

    val hexTooLong = TEAM.copy(
      colorOnChat = "#0000000"
    )

    assertEquals(
      INVALID_TEAM_COLOR_ON_CHAT,
      runBlocking {
        DaoTeam.insertTeam(hexTooLong)
      }
    )

    val hexTooShort = TEAM.copy(
      colorOnChat = "&00"
    )

    assertEquals(
      INVALID_TEAM_COLOR_ON_CHAT,
      runBlocking {
        DaoTeam.insertTeam(hexTooShort)
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
        DaoTeam.insertTeam(obfuscated)
      }
    )

    val many = TEAM.copy(
      colorOnPlayerList = "&0&l"
    )

    assertEquals(
      INVALID_TEAM_COLOR_ON_PLAYER_LIST,
      runBlocking {
        DaoTeam.insertTeam(many)
      }
    )

    val invalid = TEAM.copy(
      colorOnPlayerList = "a"
    )

    assertEquals(
      INVALID_TEAM_COLOR_ON_PLAYER_LIST,
      runBlocking {
        DaoTeam.insertTeam(invalid)
      }
    )

    val hexTooLong = TEAM.copy(
      colorOnPlayerList = "#0000000"
    )

    assertEquals(
      INVALID_TEAM_COLOR_ON_PLAYER_LIST,
      runBlocking {
        DaoTeam.insertTeam(hexTooLong)
      }
    )

    val hexTooShort = TEAM.copy(
      colorOnPlayerList = "&00"
    )

    assertEquals(
      INVALID_TEAM_COLOR_ON_PLAYER_LIST,
      runBlocking {
        DaoTeam.insertTeam(hexTooShort)
      }
    )
  }

  @Order(7)
  @Test
  fun `find team by identifier`() {
    assertEquals(
      TEAM,
      runBlocking {
        DaoTeam.findTeamByIdentifierOrNull(
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
        DaoTeam.findTeamByIdentifierOrNull(
          UniqueUuid.v4(TEAM.identifier)
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
        DaoTeam.findTeamByNameOrNull(
          TEAM.name
        )
      }
    )

    assertEquals(
      TEAM,
      runBlocking {
        DaoTeam.findTeamByNameOrNull(
          TEAM.name.uppercase()
        )
      }
    )

    assertEquals(
      TEAM,
      runBlocking {
        DaoTeam.findTeamByNameOrNull(
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
        DaoTeam.findTeamByNameOrNull(
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
        DaoTeam.containsTeamByIdentifier(TEAM.identifier)
      }
    )
  }

  @Order(12)
  @Test
  fun `contains non existent team by identifier`() {
    assertFalse(
      runBlocking {
        DaoTeam.containsTeamByIdentifier(
          UniqueUuid.v4(TEAM.identifier)
        )
      }
    )
  }

  @Order(13)
  @Test
  fun `contains team by name`() {
    assertTrue(
      runBlocking {
        DaoTeam.containsTeamByName(
          TEAM.name
        )
      }
    )

    assertTrue(
      runBlocking {
        DaoTeam.containsTeamByName(
          TEAM.name.uppercase()
        )
      }
    )

    assertTrue(
      runBlocking {
        DaoTeam.containsTeamByName(
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
        DaoTeam.containsTeamByName(
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
        DaoTeam.updateTeam(updated)
      }
    )

    assertEquals(
      updated,
      runBlocking {
        DaoTeam.findTeamByIdentifierOrNull(
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
      identifier        = UniqueUuid.v4(TEAM.identifier),
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
        DaoTeam.updateTeam(updated)
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
        DaoTeam.updateTeam(tooLong)
      }
    )

    val tooShort = TEAM.copy(
      // The minimum allowed length of the team name is 2 characters.
      name = "A"
    )

    assertEquals(
      INVALID_TEAM_NAME,
      runBlocking {
        DaoTeam.updateTeam(tooShort)
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
        DaoTeam.updateTeam(illegalCharacters)
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
        DaoTeam.updateTeam(obfuscated)
      }
    )

    val many = TEAM.copy(
      colorOfArmor = "&0&l"
    )

    assertEquals(
      INVALID_TEAM_COLOR_OF_ARMOR,
      runBlocking {
        DaoTeam.updateTeam(many)
      }
    )

    val invalid = TEAM.copy(
      colorOfArmor = "a"
    )

    assertEquals(
      INVALID_TEAM_COLOR_OF_ARMOR,
      runBlocking {
        DaoTeam.updateTeam(invalid)
      }
    )

    val hexTooLongNotation = TEAM.copy(
      colorOfArmor = "#0000000"
    )

    assertEquals(
      INVALID_TEAM_COLOR_OF_ARMOR,
      runBlocking {
        DaoTeam.updateTeam(hexTooLongNotation)
      }
    )

    val hexTooShortNotation = TEAM.copy(
      colorOfArmor = "&00"
    )

    assertEquals(
      INVALID_TEAM_COLOR_OF_ARMOR,
      runBlocking {
        DaoTeam.updateTeam(hexTooShortNotation)
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
        DaoTeam.updateTeam(obfuscated)
      }
    )

    val many = TEAM.copy(
      colorOfProfession = "&0&l"
    )

    assertEquals(
      INVALID_TEAM_COLOR_OF_PROFESSION,
      runBlocking {
        DaoTeam.updateTeam(many)
      }
    )

    val invalid = TEAM.copy(
      colorOfProfession = "a"
    )

    assertEquals(
      INVALID_TEAM_COLOR_OF_PROFESSION,
      runBlocking {
        DaoTeam.updateTeam(invalid)
      }
    )

    val hexTooLong = TEAM.copy(
      colorOfProfession = "#0000000"
    )

    assertEquals(
      INVALID_TEAM_COLOR_OF_PROFESSION,
      runBlocking {
        DaoTeam.updateTeam(hexTooLong)
      }
    )

    val hexTooShort = TEAM.copy(
      colorOfProfession = "&00"
    )

    assertEquals(
      INVALID_TEAM_COLOR_OF_PROFESSION,
      runBlocking {
        DaoTeam.updateTeam(hexTooShort)
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
        DaoTeam.updateTeam(obfuscated)
      }
    )

    val many = TEAM.copy(
      colorOnChat = "&0&l"
    )

    assertEquals(
      INVALID_TEAM_COLOR_ON_CHAT,
      runBlocking {
        DaoTeam.updateTeam(many)
      }
    )

    val invalid = TEAM.copy(
      colorOnChat = "a"
    )

    assertEquals(
      INVALID_TEAM_COLOR_ON_CHAT,
      runBlocking {
        DaoTeam.updateTeam(invalid)
      }
    )

    val hexTooLong = TEAM.copy(
      colorOnChat = "#0000000"
    )

    assertEquals(
      INVALID_TEAM_COLOR_ON_CHAT,
      runBlocking {
        DaoTeam.updateTeam(hexTooLong)
      }
    )

    val hexTooShort = TEAM.copy(
      colorOnChat = "&00"
    )

    assertEquals(
      INVALID_TEAM_COLOR_ON_CHAT,
      runBlocking {
        DaoTeam.updateTeam(hexTooShort)
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
        DaoTeam.updateTeam(obfuscated)
      }
    )

    val many = TEAM.copy(
      colorOnPlayerList = "&0&l"
    )

    assertEquals(
      INVALID_TEAM_COLOR_ON_PLAYER_LIST,
      runBlocking {
        DaoTeam.updateTeam(many)
      }
    )

    val invalid = TEAM.copy(
      colorOnPlayerList = "a"
    )

    assertEquals(
      INVALID_TEAM_COLOR_ON_PLAYER_LIST,
      runBlocking {
        DaoTeam.updateTeam(invalid)
      }
    )

    val hexTooLong = TEAM.copy(
      colorOnPlayerList = "#0000000"
    )

    assertEquals(
      INVALID_TEAM_COLOR_ON_PLAYER_LIST,
      runBlocking {
        DaoTeam.updateTeam(hexTooLong)
      }
    )

    val hexTooShort = TEAM.copy(
      colorOnPlayerList = "&00"
    )

    assertEquals(
      INVALID_TEAM_COLOR_ON_PLAYER_LIST,
      runBlocking {
        DaoTeam.updateTeam(hexTooShort)
      }
    )
  }

  @Order(22)
  @Test
  fun `count teams`() {
    assertEquals(
      1,
      runBlocking {
        DaoTeam.countTeams()
      }
    )
  }

  @Order(23)
  @Test
  fun `delete team by identifier`() {
    assertEquals(
      1,
      runBlocking {
        DaoTeam.deleteTeamByIdentifier(
          TEAM.identifier
        )
      }
    )

    assertNull(
      runBlocking {
        DaoTeam.findTeamByIdentifierOrNull(
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
        DaoTeam.deleteTeamByIdentifier(
          UniqueUuid.v4(TEAM.identifier)
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
        DaoTeam.truncateTeams()
      }
    )
  }

  @AfterAll
  fun `delete teams after all`() {
    try {
      runBlocking {
        DaoTeam.truncateTeams()
      }
    } catch (_ : Throwable) {
    }
  }
}