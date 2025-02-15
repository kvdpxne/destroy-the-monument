package me.kvdpxne.dtm.wallet

class Wallet2Impl : Wallet2 {

  private var coins_uint64: Long = 0
  private var multiplier_ufloat32: Float = 0.0F

  override fun compareTo(other: Wallet2?): Int {
    TODO("Not yet implemented")
  }

  override fun getCoins_uint64(): Long {
    return this.coins_uint64
  }

  @Suppress("LocalVariableName")
  override fun setCoins_uint64(coins_uint64: Long) {
    TODO("Not yet implemented")
  }

  override fun getMultiplier_ufloat32(): Float {
    TODO("Not yet implemented")
  }

  override fun setMultiplier_ufloat32(multiplier_ufloat32: Float) {
    TODO("Not yet implemented")
  }

  override fun addCoins_uint64(coins_uint64: Long) {
    TODO("Not yet implemented")
  }

  override fun subtractCoins_uint64(coins_uint64: Long) {
    TODO("Not yet implemented")
  }

  override fun isInfinity(): Boolean {
    TODO("Not yet implemented")
  }

  override fun isLocked(): Boolean {
    TODO("Not yet implemented")
  }

  override fun isEmpty(): Boolean {
    TODO("Not yet implemented")
  }
}