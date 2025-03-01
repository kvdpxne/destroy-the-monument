package me.kvdpxne.dtm.data.validation.context

import me.kvdpxne.dtm.data.raw.RawRevivalPosition
import me.kvdpxne.dtm.data.validation.EVERYTHING_OK
import me.kvdpxne.dtm.data.validation.INVALID_REFERENCE
import me.kvdpxne.dtm.data.validation.INVALID_REVIVAL_POSITION_PITCH
import me.kvdpxne.dtm.data.validation.INVALID_REVIVAL_POSITION_YAW
import me.kvdpxne.dtm.data.validation.context.extensions.validate

/**
 * @since 0.1.0
 */
fun isRevivalPositionPitchValid(
  pitch: Float
): Boolean {
  return pitch in -90.0F..90.0F
}

/**
 * @since 0.1.0
 */
fun isRevivalPositionYawValid(
  yaw: Float
): Boolean {
  return yaw in -180.0F..180.0F
}

/**
 * @since 0.1.0
 */
fun isRevivalPositionValid(
  revivalPosition: RawRevivalPosition?
): Int {
  if (null == revivalPosition) {
    return INVALID_REFERENCE
  }

  val result: Int = revivalPosition.team.validate()
  if (EVERYTHING_OK != result) {
    return result
  }

  if (!isRevivalPositionPitchValid(revivalPosition.pitch)) {
    return INVALID_REVIVAL_POSITION_PITCH
  }

  if (!isRevivalPositionYawValid(revivalPosition.yaw)) {
    return INVALID_REVIVAL_POSITION_YAW
  }

  return EVERYTHING_OK
}