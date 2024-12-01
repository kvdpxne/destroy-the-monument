package me.kvdpxne.dtm.team

import java.util.UUID
import me.kvdpxne.dtm.shared.StylishToStringBuilder
import me.kvdpxne.dtm.shared.debug.Debug
import me.kvdpxne.dtm.user.LocalUser

class LocalTeamImpl(
  // @formatter:off
  name      : String,
  color     : TeamColor,
  identifier: UUID,
  teammates : MutableSet<Teammate>
  // @formatter:on
) : TeamImpl(name, color, identifier), LocalTeam {

  // TODO should be mutable map
  private val _teammates: MutableSet<Teammate> = teammates

  override val teammates: List<Teammate>
    get() = this._teammates.toList()

  override var health: Int = -1

  override val size: Int
    get() = this._teammates.size

  override fun hasTeammate(
    teammate: Teammate
  ): Boolean {
    return this._teammates.contains(teammate)
  }

  override fun hasTeammate(
    user: LocalUser
  ): Boolean {
    return this._teammates.any {
      it.user == user
    }
  }

  override fun getTeammate(
    user: LocalUser
  ): Teammate? {
    return this._teammates.find {
      it.user == user
    }
  }

  override fun addTeammate(
    teammate: Teammate
  ): Boolean {
    if (!this._teammates.add(teammate)) {
      return false
    }

    Debug.log {
      "${teammate.user.name} user has been added to the ${this.name} team."
    }
    return true
  }

  override fun removeTeammate(
    user: LocalUser
  ): Boolean {



    //
//    val wasRemoved: Boolean = this._teammates.removeIf { teammate: Teammate ->
//      teammate.user == user
//    }


    return getTeammate(user)?.let {
      removeTeammate(it)
    } ?: false
  }

  override fun removeTeammate(
    teammate: Teammate
  ): Boolean {
    if (!this._teammates.remove(teammate)) {
      return false
    }

    Debug.log {
      "${teammate.user.name} user has been removed from the ${this.name} team."
    }
    return true
  }

  override fun removeTeammates() {
    this._teammates.clear()

    Debug.log {
      "All teammates were removed in the $this team."
    }
  }

  override fun injure(): Boolean {
    if (0 >= this.health) {
      return false
    }

    --this.health
    return true
  }

  /**
   * @since 0.1.0
   */
  override fun sendMessage(
    message: String
  ) {
    for (teammate: Teammate in this._teammates) {
      teammate.sendMessage(message)
    }
  }

  /**
   * @since 0.1.0
   */
  override fun sendMessage(
    message: () -> String
  ) {
    if (this._teammates.isEmpty()) {
      return
    }

    this.sendMessage(message())
  }

  /**
   * @since 0.1.0
   */
  override fun sendMessages(
    messages: Array<out String>
  ) {
    for (message: String in messages) {
      this.sendMessage(message)
    }
  }

  /**
   * @since 0.1.0
   */
  override fun sendMessages(
    messages: () -> Array<out String>
  ) {
    if (this._teammates.isEmpty()) {
      return
    }

    this.sendMessages(messages())
  }

  override fun toLocalTeam(): LocalTeam {
    return this
  }

  override fun toString(): String {
    return StylishToStringBuilder()
      .begin("LocalTeam")
      .add("identifier", this.identifier)
      .add("name", this.name)
      .add("teammates", this._teammates)
      .add("health", this.health)
      .build()
  }
}