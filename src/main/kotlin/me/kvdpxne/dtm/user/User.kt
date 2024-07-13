package me.kvdpxne.dtm.user

import java.util.UUID
import me.kvdpxne.dtm.command.Communicative
import me.kvdpxne.dtm.profession.Profession
import me.kvdpxne.dtm.profession.ProfessionManager
import me.kvdpxne.dtm.wallet.Wallet

class User(
  val identifier: UUID,
  var name: String,
  var statistics: UserStatistics = UserStatistics(),
  val wallet: Wallet = Wallet(),
) : Communicative {

  val performer: UserPerformer
  var profession: Profession = ProfessionManager.getRandomProfession()

  init {
    performer = UserPerformer(identifier, name, this)
  }

  /**
   * Alias for [UserPerformer.sendMessage]
   *
   * @since 0.1
   */
  override fun sendMessage(
    message: String
  ) {
    this.performer.sendMessage(message)
  }

  /**
   * Alias for [UserPerformer.sendMessages]
   *
   * @since 0.1
   */
  override fun sendMessages(
    vararg messageArray: String
  ) {
    this.performer.sendMessages(*messageArray)
  }

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as User

    if (identifier != other.identifier) return false
    if (name != other.name) return false

    return true
  }

  override fun hashCode(): Int {
    var result = identifier.hashCode()
    result = 31 * result + name.hashCode()
    return result
  }

  override fun toString(): String {
    return "User(identifier=$identifier, name='$name')"
  }
}