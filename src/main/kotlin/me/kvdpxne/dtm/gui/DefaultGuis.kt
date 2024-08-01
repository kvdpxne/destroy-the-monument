package me.kvdpxne.dtm.gui

import me.kvdpxne.dtm.data.DaoGameArena
import me.kvdpxne.dtm.data.DaoGameTeam
import me.kvdpxne.dtm.game.Game
import me.kvdpxne.dtm.game.GameManager
import me.kvdpxne.dtm.game.Team
import me.kvdpxne.dtm.profession.ProfessionManager
import me.kvdpxne.dtm.shared.ItemsClipboard
import me.kvdpxne.dtm.shared.minecraft.bukkit.equipB
import me.kvdpxne.dtm.shared.minecraft.bukkit.reset
import me.kvdpxne.dtm.shared.minecraft.bukkit.toBuilder
import me.kvdpxne.dtm.user.User
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent

fun createTeamSelectionGui(
  game: Game,
  user: User
): Gui {

  //
  val gui = Gui("Wybierz drużynę", Rows.ONE)

  //
  val teams = game.teams

  //
  val arrangement = GuiArrangement.SINGLE

  //
  if (teams.size > arrangement.size) {
    throw IllegalArgumentException("There is more than one team selected.")
  }

  //
  val iterator: IntIterator = arrangement.iterator()

  for (team: Team in teams) {

    val identity = team.identity

    val name = identity.name
    val color = identity.colorInChat

    gui.setItem(
      iterator.next(),
      Material.WOOL.toBuilder()
        .generation(identity.dyeColor.woolData.toInt())
        .name {
          "$color&l$name &8| &6${team.size}/bez limitu"
        }
        .lore(
          "&7Zostaniesz dodany bezpośrednio",
          "&7do drużyny $color&l$name&7."
        )
        .build()
    ) { event: InventoryClickEvent ->
      //
      game.addTeammate(identity, user)

      game.sendMessage {
        val displayName = user.performer.player?.displayName
        "&6&lDTM &7> &fGracz &6$displayName &fdołączył do drużyny $color&l$name&f."
      }

      event.whoClicked.closeInventory()
    }
  }

  gui.setItem(
    4,
    ItemsClipboard.ITEM_TEAM_SELECT_RANDOM
  ) { event: InventoryClickEvent ->
    //
    val team = if (game.isTeamsSameSize) {
      game.randomTeam.identity
    } else {
      game.smallestTeam.identity
    }

    game.addTeammate(team, user)

    val player = event.whoClicked as Player
    player.closeInventory()

    game.sendMessage {
      val displayName = player.displayName
      val teamColor = team.colorInChat

      val teamName = if (team.name.equals("blue", true)) {
        "Niebieskich"
      } else {
        "Czerwonych"
      }

      "&6&lDTM &7> &fGracz &6$displayName &fdołączył do drużyny $teamColor&l$teamName&f."
    }
  }

  return gui
}

fun createGameSelectionGui(user: User) = GameManager.games.let {
  Gui("Game selection", Rows.findRowBySize(it.size)).apply {
    it.onEachIndexed { index, game ->

      setItem(index, Material.STAINED_CLAY.toBuilder()
        .generation(5)
        .name {
          val name = game.name
          val hostagesCount = game.numberOfHostages

          "&7> &f$name &6$hostagesCount/bez limitu"
        }
        .lore(
          "&7Join the game lobby to be able to",
          "&7interact in the game.",
          "",
          "&7Current map: &6${game.currentArena?.name ?: "unknown"}",
          "&8Uid: ${game.identifier}"
        )
        .build()
      ) { event ->
        game.addHostage(user)

        DaoGameTeam.findGameTeamByGameIdentifier(game.identifier).forEach {
          game.addTeam(Team(it, game))
        }

        DaoGameArena.findGameArenaByGameIdentifier(game.identifier).forEach {
          game.addArena(it)
        }

        user.sendMessage("&6&lDTM &7> &fDołączyłeś do gry &a${game.name}&f.")

        val player = event.whoClicked as Player
        player.closeInventory()
        player.reset()

        player.equipB()

        createTeamSelectionGui(game, user).open(player)
      }
    }
  }
}

fun createProfessionSelectionGui(user: User): Gui {

  val gui = Gui("Choose your profession", Rows.TWO)
  val itemBuilder = Material.STAINED_CLAY.toBuilder()

  ProfessionManager.professions.forEachIndexed { index, profession ->

    gui.setItem(
      index, if (user.currentProfession == profession) {
        itemBuilder.generation(5)
          .name("&a&lWYBRANO")
          .build()
      } else if (!profession.enabled) {
        itemBuilder.generation(14)
          .name("&c&lNIEDOSTĘPNA")
          .build()
      } else {
        itemBuilder.generation(4)
          .name("&6&lDOSTĘPNA")
          .build()
      }
    )

    gui.setItem(
      9 + index,
      profession.icon.toBuilder()
        .name("&7${profession.displayName}")
        .build()
    ) { event: InventoryClickEvent ->
      if (!profession.enabled) {
        user.sendMessage("&6&lDTM &7> &cProfesja jest obecnie wyłączona lub niedostępna.")
        return@setItem
      }

      //
      user.currentProfession = profession.clone()

      event.isCancelled = true
      event.whoClicked.closeInventory()

      user.sendMessage("&6&lDTM &7> &fProfesja &a&l${profession.displayName} &fzostała wybrana.")

      val game = GameManager.findByUser(user) ?: return@setItem
      val team = game.findTeam(user) ?: return@setItem
      val teammate = team.findTeammate(user) ?: return@setItem

      teammate.professionQueuingPair.apply {
        if (this.current == profession) {
          return@apply
        }

        this.next = profession.clone()
        teammate.sendMessage("&6&lDTM &7> &fProfesja zostanie zmieniona po śmierci.")
      }
    }
  }

  return gui
}