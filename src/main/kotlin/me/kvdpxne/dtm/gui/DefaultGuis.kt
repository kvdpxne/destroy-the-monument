package me.kvdpxne.dtm.gui

import me.kvdpxne.dtm.colorize
import me.kvdpxne.dtm.colorizeAll
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
import org.bukkit.inventory.ItemStack

fun createTeamSelectionGui(game: Game, user: User): Gui {
  return Gui("Team selection", Rows.ONE).apply {

    val iterator = game.teams.iterator()

    //
    val firstTeam = iterator.next()

    setItem(
      0,
      Material.WOOL.toBuilder()
        .generation(14)
        .name {
          val teamSize = firstTeam.size
          val teamColor = firstTeam.identity.colorInChat
          val teamName = if (firstTeam.identity.name.equals("blue", true)) {
            "Niebieskich"
          } else {
            "Czerwonych"
          }
          "$teamColor&l$teamName &8| &6$teamSize/bez limitu".colorize()
        }
        .lore(
          "&7Zostaniesz dodany bezpośrednio",
          "&7do drużyny &c&lCzerwonych&7."
        )
        .build()
    ) {
      game.addTeammate(firstTeam.identity, user)

      val player = it.whoClicked as Player
      player.closeInventory()

      game.sendMessage {
        val displayName = player.displayName
        "&6&lDTM &7> &fGracz &6$displayName &fdołączył do drużyny &c&lCzerwonych&f."
      }
    }

    val secondTeam = iterator.next()

    setItem(
      8,
      Material.WOOL.toBuilder()
        .generation(11)
        .name {
          val teamSize = secondTeam.size
          val teamColor = secondTeam.identity.colorInChat
          val teamName = if (secondTeam.identity.name.equals("blue", true)) {
            "Niebieskich"
          } else {
            "Czerwonych"
          }
          "$teamColor&l$teamName &8| &6$teamSize/bez limitu".colorize()
        }
        .lore(
          "&7Zostaniesz dodany bezpośrednio",
          "&7do drużyny &b&lNiebieskich&7."
        )
        .build()
    ) {
      game.addTeammate(secondTeam.identity, user)

      val player = it.whoClicked as Player
      player.closeInventory()

      game.sendMessage {
        val displayName = player.displayName
        "&6&lDTM &7> &fGracz &6$displayName &fdołączył do drużyny &b&lNiebieskich&f."
      }
    }

    setItem(4, ItemsClipboard.ITEM_TEAM_SELECT_RANDOM) {

      val team = if (game.isTeamsSameSize) {
        game.randomTeam.identity
      } else {
        game.smallestTeam.identity
      }

      game.addTeammate(team, user)

      val player = it.whoClicked as Player
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
  }
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

    gui.setItem(index, if (user.currentProfession == profession) {
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
    })

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