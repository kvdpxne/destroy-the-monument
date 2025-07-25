package me.kvdpxne.dtm.data.validation.main

import java.util.UUID
import me.kvdpxne.dtm.data.EntityFieldNames
import me.kvdpxne.dtm.data.EntityNames
import me.kvdpxne.dtm.data.raw.RawTeam
import me.kvdpxne.dtm.data.validation.ValidationResult
import me.kvdpxne.dtm.data.validation.ValidationResultBuilder
import me.kvdpxne.dtm.data.validation.codes.StandardCodes
import me.kvdpxne.dtm.data.validation.codes.TeamCodes
import me.kvdpxne.dtm.data.validation.common.isHexColorValid
import me.kvdpxne.dtm.data.validation.common.isMinecraftColorValid
import me.kvdpxne.dtm.data.validation.common.isNameValid
import me.kvdpxne.dtm.data.validation.common.isUuidValid
import me.kvdpxne.dtm.data.validation.withValidationBuilder

fun validateTeam(
  identifier: UUID?,
  name: String?,
  colorOfArmor: String?,
  colorOfProfession: String?,
  colorOnChat: String?,
  colorOnPlayerList: String?
): ValidationResult = withValidationBuilder { builder: ValidationResultBuilder ->
  if (!isUuidValid(identifier)) {
    builder.addError(
      EntityFieldNames.IDENTIFIER,
      "",
      StandardCodes.INVALID_PRIMARY_KEY,
      identifier
    )
  }

  if (!isNameValid(name)) {
    builder.addError(
      EntityFieldNames.NAME,
      "",
      TeamCodes.INVALID_NAME,
      name
    )
  }

  if (!isHexColorValid(colorOfArmor)) {
    builder.addError(
      EntityFieldNames.COLOR_OF_ARMOR,
      "",
      TeamCodes.INVALID_COLOR_OF_ARMOR,
      colorOfArmor
    )
  }

  if (!isMinecraftColorValid(colorOfProfession)) {
    builder.addError(
      EntityFieldNames.COLOR_OF_PROFESSION,
      "",
      TeamCodes.INVALID_COLOR_OF_PROFESSION,
      colorOfProfession
    )
  }

  if (!isMinecraftColorValid(colorOnChat)) {
    builder.addError(
      EntityFieldNames.COLOR_ON_CHAT,
      "",
      TeamCodes.INVALID_COLOR_ON_CHAT,
      colorOnChat
    )
  }

  if (!isMinecraftColorValid(colorOnPlayerList)) {
    builder.addError(
      EntityFieldNames.COLOR_ON_PLAYER_LIST,
      "",
      TeamCodes.INVALID_COLOR_ON_PLAYER_LIST,
      colorOnPlayerList
    )
  }

  builder.build()
}

fun validateTeam(
  team: RawTeam?
): ValidationResult = team?.run {
  validateTeam(
    this.identifier,
    this.name,
    this.colorOfArmor,
    this.colorOfProfession,
    this.colorOnChat,
    this.colorOnPlayerList
  )
} ?: withValidationBuilder { builder: ValidationResultBuilder ->
  builder.addError(
    EntityNames.TEAM,
    "",
    StandardCodes.INVALID_REFERENCE
  ).build()
}