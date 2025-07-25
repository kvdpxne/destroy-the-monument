package me.kvdpxne.dtm.data.validation.main

import java.util.UUID
import me.kvdpxne.dtm.data.EntityFieldNames
import me.kvdpxne.dtm.data.EntityNames
import me.kvdpxne.dtm.data.raw.RawArenaMap
import me.kvdpxne.dtm.data.validation.ValidationResult
import me.kvdpxne.dtm.data.validation.ValidationResultBuilder
import me.kvdpxne.dtm.data.validation.codes.ArenaMapCodes
import me.kvdpxne.dtm.data.validation.codes.StandardCodes
import me.kvdpxne.dtm.data.validation.common.isNameValid
import me.kvdpxne.dtm.data.validation.common.isUuidValid
import me.kvdpxne.dtm.data.validation.withValidationBuilder

fun validateArenaMap(
  identifier: UUID?,
  name: String?
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
      ArenaMapCodes.INVALID_NAME,
      name
    )
  }

  builder.build()
}

/**
 * Validates an arena map object.
 *
 * Performs the following checks in order:
 * 1. Null reference check
 * 2. Map name validation
 *
 * ### Validation Flow
 * - If any check fails, returns immediately with specific error code
 * - Only returns [StandardCodes.EVERYTHING_OK] if both checks pass
 *
 * @param arenaMap The arena map object to validate
 * @return Validation result code:
 * - [StandardCodes.EVERYTHING_OK] if valid
 * - [StandardCodes.INVALID_REFERENCE] if null
 * - [ArenaMapCodes.INVALID_NAME] for invalid map name
 * @since 0.1.0
 */
fun validateArenaMap(
  arenaMap: RawArenaMap?
): ValidationResult {
  return arenaMap?.run {
    validateArenaMap(
      this.identifier,
      this.name
    )
  } ?: withValidationBuilder { builder: ValidationResultBuilder ->
    builder.addError(
      EntityNames.ARENA_MAP,
      "",
      StandardCodes.INVALID_REFERENCE
    ).build()
  }
}