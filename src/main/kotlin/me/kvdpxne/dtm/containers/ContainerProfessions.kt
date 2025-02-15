package me.kvdpxne.dtm.containers

import me.kvdpxne.dtm.container.Container
import me.kvdpxne.dtm.container.ContainerBuilder
import me.kvdpxne.dtm.container.ContainerTypes
import me.kvdpxne.dtm.container.Rows
import me.kvdpxne.dtm.container.displayName
import me.kvdpxne.dtm.placeholder.Placeholders
import me.kvdpxne.dtm.profession.Profession
import me.kvdpxne.dtm.profession.ProfessionManager
import me.kvdpxne.dtm.profession.translateName
import me.kvdpxne.dtm.shared.item.ItemBuilder
import me.kvdpxne.dtm.shared.item.displayName
import me.kvdpxne.dtm.shared.item.toBuilder
import me.kvdpxne.dtm.translation.formatter.Formatter
import me.kvdpxne.dtm.translation.message.EnumTranslationKey
import me.kvdpxne.dtm.user.LocalUser
import me.kvdpxne.dtm.user.LocalUserPerformer

fun createProfessionsContainer(
  user: LocalUser
): Container<LocalUserPerformer> {
  // Creates an empty (for now) container with basic parameters and a
  // display name translated into the language of the user who will open
  // the container.
  val containerBuilder: ContainerBuilder<LocalUserPerformer> =
    ContainerBuilder.begin<LocalUserPerformer>()
      .owner(user.identifier)
      .type(ContainerTypes.GENERIC_9X2)
      .size(Rows.TWO)
      .displayName(user.locale, EnumTranslationKey.GUI_SELECT_PROFESSION)

  //
  val itemBuilder: ItemBuilder = ItemBuilder.begin("STAINED_CLAY")

  // An item reprising the currently selected profession.
  val selected: Any = itemBuilder.copy()
    .generation(5)
    .displayName(user.locale, EnumTranslationKey.PROFESSION_STATE_SELECTED)
    .raw()

  // An item that reprises an unavailable profession.
  val unavailable: Any = itemBuilder.copy()
    .generation(14)
    .displayName(user.locale, EnumTranslationKey.PROFESSION_STATE_UNAVAILABLE)
    .raw()

  // An item that reprises an available profession.
  val available: Any = itemBuilder.copy()
    .generation(4)
    .displayName(user.locale, EnumTranslationKey.PROFESSION_STATE_AVAILABLE)
    .raw()

  var index: Byte = 0

  @Suppress("UseWithIndex", "RedundantSuppression")
  for (profession: Profession in ProfessionManager.professions) {
    containerBuilder.slot(
      index,
      if (user.currentProfession == profession) {
        selected
      } else if (profession.enabled) {
        available
      } else {
        unavailable
      }
    )

    // The name of the profession translated into the language of the user
    // who will open this container.
    val translatedName: String = profession.translateName(user.locale)

    containerBuilder.slot(
      (index + 9).toByte(),
      profession.icon.toBuilder()
        .name("&e&l$translatedName")
        .clearAttributes()
        .raw()
    ) { _: LocalUserPerformer ->
      if (!profession.enabled) {
        user.prepareMessage(EnumTranslationKey.PROFESSION_SELECT_UNAVAILABLE)
          .format(
            Formatter.begin(1)
              .with(Placeholders.PROFESSION_NAME, translatedName)
          )
          .useChat()
          .send()
        return@slot
      }

      if (user.currentProfession == profession) {
        user.prepareMessage(EnumTranslationKey.PROFESSION_SELECT_SELECTED)
          .format(
            Formatter.begin(2)
              .with(Placeholders.CURRENT_PROFESSION_NAME, user.currentProfession.translateName(user.locale))
              .with(Placeholders.PROFESSION_NAME, translatedName)
          )
          .useChat()
          .send()
        return@slot
      }

      val copiedProfession: Profession = profession.clone()

      // Updates the currently selected user's profession to a new copy of
      // the currently selected user's profession.
      user.updateCurrentProfession(copiedProfession)

      //
      user.performer.player?.closeInventory()

      user.prepareMessage(EnumTranslationKey.PROFESSION_SELECT_SUCCESS)
        .format(
          Formatter.begin(1)
            .with(Placeholders.PROFESSION_NAME, translatedName)
        )
        .useChat()
        .send()

      //
      user.teammate?.addProfession(copiedProfession)
        ?: return@slot

      user.prepareMessage(EnumTranslationKey.PROFESSION_CHANGE_AFTER_DEATH)
        .withoutFormat()
        .useChat()
        .send()
    }
    ++index
  }

  return containerBuilder.build()
}