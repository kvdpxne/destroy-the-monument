package me.kvdpxne.dtm.damage

import java.util.UUID
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap
import me.kvdpxne.dtm.shared.debug.Debug

object DamageManager {

  private val _histories: ConcurrentMap<UUID, DamageOwner> =
    ConcurrentHashMap()

  fun findFs(identifier: UUID): DamageOwner {
    var oldValue: DamageOwner? = this._histories[identifier]
    if (null != oldValue) {
      return oldValue
    }

    val damageOwner = DamageOwner(identifier)
    oldValue = this._histories.put(identifier, damageOwner)

    if (null != oldValue) {
      return oldValue
    }

    Debug.log {
      "A new ${damageOwner.owner} message key has been registered."
    }

    return damageOwner
  }
}