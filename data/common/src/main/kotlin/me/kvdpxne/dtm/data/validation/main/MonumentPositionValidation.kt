package me.kvdpxne.dtm.data.validation.main

import java.util.UUID
import me.kvdpxne.dtm.data.EntityFieldNames
import me.kvdpxne.dtm.data.EntityNames
import me.kvdpxne.dtm.data.raw.RawMonumentPosition
import me.kvdpxne.dtm.data.raw.RawTeam
import me.kvdpxne.dtm.data.validation.BasicValidationFactoryPool
import me.kvdpxne.dtm.data.validation.ReentrantValidationFactoryPool
import me.kvdpxne.dtm.data.validation.ValidationResult
import me.kvdpxne.dtm.data.validation.ValidationResultBuilder
import me.kvdpxne.dtm.data.validation.codes.MonumentPositionCodes
import me.kvdpxne.dtm.data.validation.codes.StandardCodes
import me.kvdpxne.dtm.data.validation.common.isPositionXzValid
import me.kvdpxne.dtm.data.validation.common.isPositionYValid
import me.kvdpxne.dtm.data.validation.common.isUuidValid
import me.kvdpxne.dtm.data.validation.withValidationBuilder

fun validateMonumentPosition(
  uid: UUID?,
  team: RawTeam?,
  x: Int,
  z: Int,
  y: Int,
  flat: Boolean = true
): ValidationResult = withValidationBuilder(
  if (flat) BasicValidationFactoryPool else ReentrantValidationFactoryPool
) { builder: ValidationResultBuilder ->
  if (!isUuidValid(uid)) {
    builder.addError(
      EntityFieldNames.IDENTIFIER,
      "",
      StandardCodes.INVALID_PRIMARY_KEY,
      uid
    )
  }

  var part: ValidationResult? = null
  if (!flat) {
    part = validateTeam(team)
  }

  if (!isPositionXzValid(x)) {
    builder.addError(
      EntityFieldNames.X,
      "",
      MonumentPositionCodes.INVALID_X,
      x
    )
  }

  if (!isPositionXzValid(z)) {
    builder.addError(
      EntityFieldNames.Z,
      "",
      MonumentPositionCodes.INVALID_Z,
      z
    )
  }

  if (!isPositionYValid(y)) {
    builder.addError(
      EntityFieldNames.Y,
      "",
      MonumentPositionCodes.INVALID_Y,
      y
    )
  }

  builder.build().combine(part)
}

/**
 * Validates a monument position's coordinates and associated team reference.
 *
 * Performs the following checks in order:
 * 1. Null check of the monument position object
 * 2. Team reference validation
 * 3. X-coordinate validation
 * 4. Z-coordinate validation
 * 5. Y-coordinate validation
 *
 * @param monumentPosition The monument position data to validate
 * @return Validation result code:
 * - [StandardCodes.EVERYTHING_OK] if all checks pass
 * - [StandardCodes.INVALID_REFERENCE] if monumentPosition is null
 * - Team validation error code if team is invalid
 * - [MonumentPositionCodes.INVALID_X] if X-coordinate is invalid
 * - [MonumentPositionCodes.INVALID_Z] if Z-coordinate is invalid
 * - [MonumentPositionCodes.INVALID_Y] if Y-coordinate is invalid
 * @since 0.1.0
 */
fun validateMonumentPosition(
  monumentPosition: RawMonumentPosition?,
  flat: Boolean = true
): ValidationResult = monumentPosition?.run {
  validateMonumentPosition(
    this.identifier,
    this.team,
    this.x,
    this.z,
    this.y,
    flat
  )
} ?: withValidationBuilder { builder: ValidationResultBuilder ->
  builder.addError(
    EntityNames.MONUMENT_POSITION,
    "",
    StandardCodes.INVALID_REFERENCE,
  ).build()
}