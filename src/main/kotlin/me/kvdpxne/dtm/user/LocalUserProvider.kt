package me.kvdpxne.dtm.user

import me.kvdpxne.dtm.Providable

interface LocalUserProvider : Providable {

  /**
   * Converts the current user instance into a [LocalUser], representing a more
   * specific type of user with additional, localized functionalities or
   * properties.
   *
   * @return An instance of [LocalUser], representing the user in a localized
   *         context.
   * @since 0.1.0
   */
  fun toLocalUser(): LocalUser
}