package me.kvdpxne.dtm.data.validation.main

import java.util.UUID
import me.kvdpxne.dtm.data.EntityFieldNames
import me.kvdpxne.dtm.data.EntityNames
import me.kvdpxne.dtm.data.raw.RawRevivalPosition
import me.kvdpxne.dtm.data.raw.RawTeam
import me.kvdpxne.dtm.data.validation.BasicValidationFactoryPool
import me.kvdpxne.dtm.data.validation.BasicValidationResult
import me.kvdpxne.dtm.data.validation.ReentrantValidationFactoryPool
import me.kvdpxne.dtm.data.validation.ValidationResult
import me.kvdpxne.dtm.data.validation.ValidationResultBuilder
import me.kvdpxne.dtm.data.validation.codes.RevivalPositionCodes
import me.kvdpxne.dtm.data.validation.codes.StandardCodes
import me.kvdpxne.dtm.data.validation.codes.TeamCodes
import me.kvdpxne.dtm.data.validation.common.isPositionXzValid
import me.kvdpxne.dtm.data.validation.common.isUuidValid
import me.kvdpxne.dtm.data.validation.rules.PositioningRules
import me.kvdpxne.dtm.data.validation.withValidationBuilder

/**
 * Validates a revival position's pitch value.
 *
 * ### Validation Rules
 * - Pitch must be between [PositioningRules.MIN_PITCH] and [PositioningRules.MAX_PITCH]
 * - Inclusive boundaries are valid
 *
 * @param pitch The pitch value to validate (in degrees)
 * @return `true` if pitch is within valid range, `false` otherwise
 * @since 0.1.0
 */
fun isRevivalPositionPitchValid(
  pitch: Float
): Boolean {
  return pitch in PositioningRules.MIN_PITCH..PositioningRules.MAX_PITCH
}

/**
 * Validates a revival position's yaw value.
 *
 * ### Validation Rules
 * - Yaw must be between [PositioningRules.MIN_YAW] and [PositioningRules.MAX_YAW]
 * - Inclusive boundaries are valid
 *
 * @param yaw The yaw value to validate (in degrees)
 * @return `true` if yaw is within valid range, `false` otherwise
 * @since 0.1.0
 */
fun isRevivalPositionYawValid(
  yaw: Float
): Boolean {
  return yaw in PositioningRules.MIN_YAW..PositioningRules.MAX_YAW
}

fun validateRevivalPosition(
  uid: UUID?,
  team: RawTeam?,
  x: Double,
  z: Double,
  y: Double,
  pitch: Float,
  yaw: Float,
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
      RevivalPositionCodes.INVALID_X,
      x
    )
  }

  if (!isPositionXzValid(z)) {
    builder.addError(
      EntityFieldNames.Z,
      "",
      RevivalPositionCodes.INVALID_Z,
      z
    )
  }

  if (!isPositionXzValid(y)) {
    builder.addError(
      EntityFieldNames.Y,
      "",
      RevivalPositionCodes.INVALID_Y,
      y
    )
  }

  if (!isRevivalPositionPitchValid(pitch)) {
    builder.addError(
      EntityFieldNames.PITCH,
      "",
      RevivalPositionCodes.INVALID_PITCH,
      pitch
    )
  }

  if (!isRevivalPositionYawValid(yaw)) {
    builder.addError(
      EntityFieldNames.YAW,
      "",
      RevivalPositionCodes.INVALID_YAW,
      yaw
    )
  }

  builder.build().combine(part)
}

/**
 * Validates a complete revival position object.
 *
 * Performs the following checks in order:
 * 1. Null reference check
 * 2. Team reference validation
 * 3. X-coordinate validation
 * 4. Z-coordinate validation
 * 5. Y-coordinate validation
 * 6. Pitch validation
 * 7. Yaw validation
 *
 * ### Validation Flow
 * - If any check fails, returns immediately with specific error code
 * - Only returns [StandardCodes.EVERYTHING_OK] if all checks pass
 *
 * @param revivalPosition The revival position to validate
 * @return Validation result code:
 * - [StandardCodes.EVERYTHING_OK] if valid
 * - [StandardCodes.INVALID_REFERENCE] if null
 * - [TeamCodes.INVALID_NAME] for invalid team name
 * - [TeamCodes.INVALID_COLOR_OF_ARMOR] for invalid armor color
 * - [TeamCodes.INVALID_COLOR_OF_PROFESSION] for invalid profession color
 * - [TeamCodes.INVALID_COLOR_ON_CHAT] for invalid chat color
 * - [TeamCodes.INVALID_COLOR_ON_PLAYER_LIST] for invalid player list color
 * - [RevivalPositionCodes.INVALID_X] for invalid X-coordinate
 * - [RevivalPositionCodes.INVALID_Z] for invalid Z-coordinate
 * - [RevivalPositionCodes.INVALID_Y] for invalid Y-coordinate
 * - [RevivalPositionCodes.INVALID_PITCH] for invalid pitch
 * - [RevivalPositionCodes.INVALID_YAW] for invalid yaw
 * @since 0.1.0
 */
fun validateRevivalPosition(
  revivalPosition: RawRevivalPosition?,
  flat: Boolean = true
): ValidationResult {
  return revivalPosition?.run {
    validateRevivalPosition(
      this.identifier,
      this.team,
      this.x,
      this.z,
      this.y,
      this.pitch,
      this.yaw,
      flat
    )
  } ?: withValidationBuilder { builder: ValidationResultBuilder ->
    builder.addError(
      EntityNames.REVIVAL_POSITION,
      "",
      StandardCodes.INVALID_REFERENCE
    ).build()
  }
}