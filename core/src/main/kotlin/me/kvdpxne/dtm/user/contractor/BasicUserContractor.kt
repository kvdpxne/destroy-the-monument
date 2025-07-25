package me.kvdpxne.dtm.user.contractor

import java.lang.ref.Reference
import java.lang.ref.WeakReference
import java.util.UUID
import me.kvdpxne.boujee.TranslationKeyProvider
import me.kvdpxne.dtm.user.LocalUser

open class BasicUserContractor(
  private val localUser: LocalUser
) : UserContractor {

  protected var playerReference: Reference<Any?>? = null

  protected fun <T> updateReference(referent: T?): T? {
    this.playerReference = WeakReference(referent)
    return referent
  }

  override fun chat(keyProvider: TranslationKeyProvider) {
    TODO("Not yet implemented")
  }

  override fun title(keyProvider: TranslationKeyProvider) {
    TODO("Not yet implemented")
  }

  override fun subtitle(keyProvider: TranslationKeyProvider) {
    TODO("Not yet implemented")
  }

  override fun action(keyProvider: TranslationKeyProvider) {
    TODO("Not yet implemented")
  }

  override fun getIdentifier(): UUID {
    return this.localUser.getIdentifier()
  }

  override fun getName(): String {
    return this.localUser.name
  }

  override fun getDisplayName(): String? {
    return this.localUser.displayName
  }

  override fun getLocalUser(): LocalUser {
    return this.localUser
  }

  override fun getPlayerOrNull(): Any? {
    throw NotImplementedError()
  }

  override fun isOnline(): Boolean {
    throw NotImplementedError()
  }

  override fun isOperator(): Boolean {
    throw NotImplementedError()
  }

  override fun hasPrivilegeAsOperator(
    privilege: String?,
    offline: Boolean
  ): Byte {
    throw NotImplementedError()
  }

  override fun hasPrivilege(
    privilege: String?,
    operator: Boolean,
    offline: Boolean
  ): Byte {
    throw NotImplementedError()
  }

  /**
   * @since 0.1.0
   */
  @Synchronized
  fun destroy() {
    this.playerReference = null
  }
}