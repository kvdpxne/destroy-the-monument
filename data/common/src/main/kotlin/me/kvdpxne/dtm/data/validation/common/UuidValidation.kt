package me.kvdpxne.dtm.data.validation.common

import java.util.UUID
import me.kvdpxne.dtm.data.EntityFieldNames
import me.kvdpxne.dtm.data.validation.BasicValidationResult
import me.kvdpxne.dtm.data.validation.ValidationResult
import me.kvdpxne.dtm.data.validation.ValidationResultBuilder
import me.kvdpxne.dtm.data.validation.codes.StandardCodes
import me.kvdpxne.dtm.data.validation.withValidationBuilder
import me.kvdpxne.dtm.util.Uuids

/**
 * Checks if a UUID represents a valid, non-placeholder identifier.
 *
 * Returns false for special-case UUIDs like nil (null-equivalent) or all-zero
 * values, which are often used as defaults or placeholders in systems. A valid
 * UUID must be neither nil nor all zeros.
 *
 * @param uuid The UUID to validate (nullable)
 * @return True if the UUID is a valid non-placeholder identifier, false otherwise
 * @since 0.1.0
 */
fun isUuidValid(
  uuid: UUID?
): Boolean {
  return !Uuids.isNil(uuid) && !Uuids.isAll(uuid)
}

/**
 * @since 0.1.0
 */
fun validateUuid(
  uuid: UUID?
): ValidationResult {
  return if (isUuidValid(uuid)) BasicValidationResult.Success
  else withValidationBuilder { builder: ValidationResultBuilder ->
    builder.addError(
      EntityFieldNames.IDENTIFIER,
      "",
      StandardCodes.INVALID_PRIMARY_KEY,
      uuid
    ).build()
  }
}