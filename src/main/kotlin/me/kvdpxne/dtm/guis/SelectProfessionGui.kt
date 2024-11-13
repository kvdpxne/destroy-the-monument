package me.kvdpxne.dtm.guis

import me.kvdpxne.dtm.gui.Gui
import me.kvdpxne.dtm.gui.Rows
import me.kvdpxne.dtm.profession.ProfessionManager
import me.kvdpxne.dtm.shared.item.toBuilder
import me.kvdpxne.dtm.shared.material.toBuilder
import me.kvdpxne.dtm.translation.TranslationService
import me.kvdpxne.dtm.translation.message.EnumMessageKey
import me.kvdpxne.dtm.user.LocalUser
import org.bukkit.Material
import org.bukkit.event.inventory.InventoryClickEvent

fun createProfessionSelectionGui(user: LocalUser): Gui {

  val gui = Gui(
    TranslationService
      .findLocalMessagesOrDefault(user.locale)
      .findRawMessage(EnumMessageKey.GUI_SELECT_PROFESSION.messageKey),
    Rows.TWO
  )

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
        .name("&e&l${profession.displayName}")
        .clearAttributes()
        .build()
    ) { event: InventoryClickEvent ->
      if (!profession.enabled) {
        user.sendMessage("&6&lDTM &7> &cProfesja jest obecnie wyłączona lub niedostępna.")
        return@setItem
      }

      //
      user.updateCurrentProfession(profession.clone())

      event.isCancelled = true
      event.whoClicked.closeInventory()

      user.sendMessage("&6&lDTM &7> &fProfesja &a&l${profession.displayName} &fzostała wybrana.")

      val teammate = user.teammate ?: return@setItem

      if (teammate.currentProfession == profession) {
        return@setItem
      }

      teammate.addProfession(profession.clone())
      teammate.sendMessage("&6&lDTM &7> &fProfesja zostanie zmieniona po śmierci.")
    }
  }

  return gui
}