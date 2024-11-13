package me.kvdpxne.dtm.translation

import java.util.Locale
import me.kvdpxne.dtm.command.ConsolePerformer
import me.kvdpxne.dtm.command.Performer
import me.kvdpxne.dtm.user.LocalUserPerformer

/**
 * Retrieves the locale associated with this performer.
 *
 * If the performer is a [LocalUserPerformer], their specific locale is returned.
 * If the performer is a [ConsolePerformer], the system's default locale is returned.
 * For any other unsupported performer type, an [IllegalStateException] is thrown.
 *
 * @return The locale of the performer or the system default for console users.
 * @throws IllegalStateException if the performer type is unsupported.
 * @since 0.1.0
 */
val Performer.locale: Locale
  get() {
    return when (this) {
      is LocalUserPerformer -> this.locale
      is ConsolePerformer -> TranslationService.defaultLocale
      else -> error("Unsupported receiver type: ${this::class.simpleName}")
    }
  }