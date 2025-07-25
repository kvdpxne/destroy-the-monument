package me.kvdpxne.dtm.wallet

import java.util.UUID
import me.kvdpxne.dtm.StylishToStringBuilder
import me.kvdpxne.dtm.state.BasicMutableIdentifiable
import me.kvdpxne.dtm.shared.language.toSingleLines
import me.kvdpxne.dtm.util.StylishToString

/**
 * @param initialCoins
 * @param initialMultiplier
 * @param initialModifiedState
 * @param identifier
 *
 * @since 0.1.0
 */
class BasicWallet(
  // @formatter:off
  initialCoins        : Long    = 0,
  initialMultiplier   : Float   = 1.0F,
  initialModifiedState: Boolean = true,
  identifier          : UUID    = UUID.randomUUID()
  // @formatter:on
) :
  BasicMutableIdentifiable<UUID>(
    initialModifiedState,
    identifier
  ),
  Wallet {

  init {
    require(0 <= initialCoins) {
      "The initial number of coins must be greater than or equal to 0."
    }

    require(0.1F <= initialMultiplier) {
      "The initial multiplier must be greater than or equal to 0.1F."
    }
  }

  companion object {

    /**
     * @since 0.1.0
     */
    @Suppress("ConstPropertyName")
    private const val serialVersionUID: Long = -5069802736468333941L
  }

  /**
   * @since 0.1.0
   */
  private var coins: Long = initialCoins

  /**
   * @since 0.1.0
   */
  private var multiplier: Float = initialMultiplier

  /**
   * @since 0.1.0
   */
  private var isInfinity: Boolean = false

  /**
   * @since 0.1.0
   */
  private var isBlocked: Boolean = false

  @Synchronized
  override fun getCoins(): Long {
    return this.coins;
  }

  override fun setCoins(
    coins: Long
  ) {
    require(0 <= coins) {
      """
        The passed number of coins "$coins" to be set in the wallet
        represented by the identifier "${this.getIdentifier()}" must be greater
        than or equal to 0.
      """.toSingleLines()
    }

    synchronized(this) {
      // Jeżeli przekazana liczba monet jest taka sama jak aktualna liczba monet
      // przechowywana przez obiekt, to dalsze instrukcje powinny zostać
      // natychmiast przerwane, ponieważ przechowywana ilość monet przez obiekt
      // nie ulegnie zmianie.
      if (coins == this.coins) {
        return
      }

      this.coins = coins
      this.markAsModified()
    }
  }

  @Synchronized
  override fun getMultiplier(): Float {
    return this.multiplier
  }

  override fun setMultiplier(
    multiplier: Float
  ) {
    require(0.0F <= multiplier) {
      """
        The passed multiplier "$multiplier" to be set in the wallet
        represented by the identifier "${this.getIdentifier()}" must be greater
        than 0.0F.
      """.toSingleLines()
    }

    synchronized(this) {
      // Jeżeli przekazany liczba mnożnika jest taka sama jak aktualna liczba
      // mnożnika przechowywana przez obiekt, to dalsze instrukcje powinny
      // zostać natychmiast przerwane, ponieważ przechowywana liczba mnożnika
      // przez obiekt nie ulegnie zmianie.
      if (this.multiplier != multiplier) {
        return
      }

      this.multiplier = multiplier
      this.markAsModified()
    }
  }

  @Synchronized
  override fun isInfinity(): Boolean {
    return this.isInfinity
  }

  @Synchronized
  override fun setInfinity(
    infinity: Boolean
  ) {
    if (infinity == this.isInfinity) {
      return
    }

    this.isInfinity = infinity
    this.markAsModified()
  }

  @Synchronized
  override fun isBlocked(): Boolean {
    return this.isBlocked
  }

  @Synchronized
  override fun setBlocked(
    blocked: Boolean
  ) {
    if (blocked == this.isBlocked) {
      return
    }

    this.isBlocked = blocked
    this.markAsModified()
  }

  override fun addCoins(
    coins: Long
  ) {
    require(0 < coins) {
      """
        The passed number of coins "$coins" to be added to the wallet
        represented by the identifier "${this.getIdentifier()}" must be greater
        than 0.
      """.toSingleLines()
    }

    //
    if (this.isBlocked()) {
      return
    }

    // The current number of coins.
    val curCoins: Double

    // The current multiplier.
    val curMultiplier: Double

    //
    //
    synchronized(this) {
      curCoins = this.coins.toDouble()
      curMultiplier = this.multiplier.toDouble()
    }

    // The product of the multiplication of 2 components stored in a
    // primitive type capable of caching more than the largest product of
    // these components.
    val product: Double = curMultiplier * curCoins

    // If the product of multiplying the number of coins to be added by the
    // wallet multiplier is 0, then adding this product to the current wallet
    // coins will not change anything, so do not follow further instructions.
    if (0.0 == product) {
      return
    }

    if (product !in 0.toDouble()..Long.MAX_VALUE.toDouble()) {
      throw ArithmeticException(
        """
          The multiplication product of the passed number of coins to add
          "$curCoins" by the wallet multiplier "${this.multiplier}" represented
          by the identifier "${this.getIdentifier()}" overflows the primitive long
          type in unsigned numbers.
        """.toSingleLines()
      )
    }

    val sum: Double = curCoins + product
    if (sum !in 0.toDouble()..Long.MAX_VALUE.toDouble()) {
      throw ArithmeticException(
        "" // TODO ArithmeticException missing message
      )
    }

    synchronized(this) {
      this.coins = sum.toLong()
      this.markAsModified()
    }
  }

  override fun subtractCoins(
    coins: Long
  ) {
    require(0 < coins) {
      """
        The passed number of coins "$coins" to subtract from the wallet
        represented by the identifier "${this.getIdentifier()}" must be greater
        than 0.
      """.toSingleLines()
    }

    //
    if (this.isInfinity()) {
      return
    }

    val currentCoins: Long
    synchronized(this) {
      currentCoins = this.coins
    }

    val difference: Long = currentCoins - coins
    if (difference !in 0L..Long.MAX_VALUE) {
      throw ArithmeticException(
        ""
      )
    }

    synchronized(this) {
      this.coins = difference
      this.markAsModified()
    }
  }

  override fun compareTo(other: Wallet): Int {
    return this.coins.compareTo(other.coins) +
      this.multiplier.compareTo(other.multiplier)
  }

  override fun copy(): Wallet {
    return BasicWallet(
      this.coins,
      this.multiplier,
      this.wasModified(),
      this.getIdentifier()
    )
  }

  override fun toStylishString(): StylishToString {
    return StylishToStringBuilder().begin(Wallet::class.java.name)
      .add("identifier", this.identifier)
      .add("coins", this.coins)
      .add("multiplier", this.multiplier)
      .add("infinite", this.isInfinity)
      .add("blocked", this.isBlocked)
      .toStylishString()
  }

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (other !is BasicWallet) return false
    if (!super.equals(other)) return false

    if (coins != other.coins) return false
    if (multiplier != other.multiplier) return false
    if (isInfinity != other.isInfinity) return false
    if (isBlocked != other.isBlocked) return false

    return true
  }

  override fun hashCode(): Int {
    var result = super.hashCode()
    result = 31 * result + coins.hashCode()
    result = 31 * result + multiplier.hashCode()
    result = 31 * result + isInfinity.hashCode()
    result = 31 * result + isBlocked.hashCode()
    return result
  }

  override fun toString(): String {
    return this.toStylishString().packed()
  }
}