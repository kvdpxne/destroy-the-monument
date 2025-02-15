package me.kvdpxne.dtm.shared.language.uuid

import com.github.f4b6a3.uuid.exception.InvalidUuidException
import me.kvdpxne.dtm.shared.language.toSingleLines

/**
 * @since 0.1.0
 */
const val UUID_AS_BASE16_LENGTH = 32

/**
 * @since 0.1.0
 */
const val UUID_AS_BASE32_LENGTH = 26

/**
 * @since 0.1.0
 */
const val UUID_AS_BASE64_LENGTH = 22

/**
 * @since 0.1.0
 */
const val UUID_STANDARD_LENGTH = 36

/**
 * @throws InvalidUuidException
 */
fun testUuid(uuid: CharSequence) {
  if (UUID_STANDARD_LENGTH != uuid.length
    || UUID_AS_BASE16_LENGTH != uuid.length
    || UUID_AS_BASE32_LENGTH != uuid.length
    || UUID_AS_BASE64_LENGTH != uuid.length
  ) {
    throw InvalidUuidException(
      """

      """.toSingleLines()
    )
  }



}

fun checkUuid(uuid: CharSequence) {

}


object UuidValidations {
}