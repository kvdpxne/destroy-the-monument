package me.kvdpxne.dtm.data.validation.main

import java.util.UUID
import me.kvdpxne.dtm.data.EntityFieldNames
import me.kvdpxne.dtm.data.EntityNames
import me.kvdpxne.dtm.data.raw.RawArena
import me.kvdpxne.dtm.data.raw.RawArenaMap
import me.kvdpxne.dtm.data.raw.RawMonumentPosition
import me.kvdpxne.dtm.data.raw.RawRevivalPosition
import me.kvdpxne.dtm.data.validation.BasicValidationFactoryPool
import me.kvdpxne.dtm.data.validation.ReentrantValidationFactoryPool
import me.kvdpxne.dtm.data.validation.ValidationResult
import me.kvdpxne.dtm.data.validation.ValidationResultBuilder
import me.kvdpxne.dtm.data.validation.codes.ArenaCodes
import me.kvdpxne.dtm.data.validation.codes.StandardCodes
import me.kvdpxne.dtm.data.validation.common.isNameValid
import me.kvdpxne.dtm.data.validation.common.isUuidValid
import me.kvdpxne.dtm.data.validation.withValidationBuilder

fun validateArena(
  identifier: UUID?,
  map: RawArenaMap?,
  monumentPositions: Iterable<RawMonumentPosition?>?,
  revivalPositions: Iterable<RawRevivalPosition?>?,
  name: String?,
  flat: Boolean = true
): ValidationResult = withValidationBuilder(
  if (flat) BasicValidationFactoryPool else ReentrantValidationFactoryPool
) { builder: ValidationResultBuilder ->
  if (!isUuidValid(identifier)) {
    builder.addError(
      EntityFieldNames.IDENTIFIER,
      "",
      StandardCodes.INVALID_PRIMARY_KEY,
      identifier
    )
  }

  var part: ValidationResult? = null
  if (!flat) {
    part = validateArenaMap(map)

    monumentPositions
      ?.map(::validateMonumentPosition)
      ?.toTypedArray()
      ?.run { part.combine(*this) }
      ?: builder.addError(
        EntityNames.MONUMENT_POSITION,
        "",
        ArenaCodes.INVALID_MONUMENT_POSITIONS,
      )

    revivalPositions
      ?.map(::validateRevivalPosition)
      ?.toTypedArray()
      ?.run { part.combine(*this) }
      ?: builder.addError(
        EntityNames.REVIVAL_POSITION,
        "",
        ArenaCodes.INVALID_REVIVAL_POSITIONS,
      )
  }

  if (!isNameValid(name)) {
    builder.addError(
      EntityFieldNames.NAME,
      "",
      ArenaCodes.INVALID_NAME,
      name
    )
  }

  builder.build().combine(part)
}

/**
 * Validates a complete arena object.
 *
 * Performs the following checks in order:
 * 1. Null reference check
 * 2. Map validation
 * 3. Validation of all monument positions
 * 4. Validation of all revival positions
 * 5. Arena name validation
 *
 * ### Validation Flow
 * - If any check fails, returns immediately with specific error code
 * - Only returns [StandardCodes.EVERYTHING_OK] if all checks pass
 * - Nested validations return their own specific codes
 *
 * ### Important Notes
 * - Monument positions are validated in iteration order
 * - Revival positions are validated in iteration order
 * - Validation stops at first failure in any collection
 *
 * @param arena The arena object to validate
 * @return Validation result code:
 * - [StandardCodes.EVERYTHING_OK] if valid
 * - [StandardCodes.INVALID_REFERENCE] if null
 * - Map validation error code (if invalid)
 * - Monument position error code (first invalid position)
 * - Revival position error code (first invalid position)
 * - [ArenaCodes.INVALID_NAME] for invalid arena name
 * @since 0.1.0
 */
fun validateArena(
  arena: RawArena?,
  flat: Boolean = true
): ValidationResult = arena?.run {
  validateArena(
    this.identifier,
    this.map,
    this.monumentPositions,
    this.revivalPositions,
    this.name,
    flat
  )
} ?: withValidationBuilder { builder: ValidationResultBuilder ->
  builder.addError(
    EntityNames.ARENA,
    "",
    StandardCodes.INVALID_REFERENCE
  ).build()
}