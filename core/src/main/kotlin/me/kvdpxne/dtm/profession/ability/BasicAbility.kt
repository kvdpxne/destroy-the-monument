package me.kvdpxne.dtm.profession.ability

open class BasicAbility(
  // @formatter:off
  private val name       : String,
  private var displayName: String?     = null,
  private val cooldown   : Short,
  private val type       : AbilityType = AbilityType.UNKNOWN,
  // @formatter:on
) : Ability {

  /**
   * @since 0.1.0
   */
  private var paused: Boolean = false

  /**
   * @since 0.1.0
   */
  private var ready: Boolean = false

  /**
   * @since 0.1.0
   */
  private var active: Boolean = false

  /**
   * @since 0.1.0
   */
  private var used: Boolean = false

  /**
   * @since 0.1.0
   */
  @JvmField
  protected var remainingCooldown: Short = this.cooldown

  /**
   * @since 0.1.0
   */
  @JvmField
  protected var taskIdentifier: Int = -1

  override fun getName(): String {
    return this.name
  }

  override fun getDisplayName(): String? {
    return this.displayName
  }

  override fun getCooldown(): Short {
    return this.cooldown
  }

  override fun getRemainingCooldown(): Short {
    return this.remainingCooldown
  }

  override fun reduceCooldown(
    amount: Short
  ) {
    require(0 < amount) {
      ""
    }

    synchronized(this) {
      if (this.active || this.active) {
        return
      }

      if (0 >= this.remainingCooldown) {
        this.ready = true
        return
      }

      --this.remainingCooldown
    }
  }

  override fun reduceCooldown() {
    this.reduceCooldown(1)
  }

  override fun isPaused(): Boolean {
    return this.paused
  }

  override fun isReady(): Boolean {
    return this.ready
  }

  override fun isActive(): Boolean {
    return this.active
  }

  override fun wasUsed(): Boolean {
    return this.used
  }

  override fun getType(): AbilityType {
    return this.type
  }

  override fun pause() {
   this.paused = true
  }

  override fun renew() {
    throw NotImplementedError()
  }

  override fun cancel() {
    throw NotImplementedError()
  }

  override fun copy(): Ability {
    return BasicAbility(
      this.name,
      this.displayName,
      this.cooldown,
      this.type
    )
  }
}