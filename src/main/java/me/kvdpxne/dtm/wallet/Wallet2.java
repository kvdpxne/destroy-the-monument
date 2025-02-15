package me.kvdpxne.dtm.wallet;

import java.io.Serializable;
import me.kvdpxne.dtm.annotations.UnsignedFloat32;
import me.kvdpxne.dtm.annotations.UnsignedInteger64;

/**
 * @since 0.1.0
 */
public interface Wallet2 extends Comparable<Wallet2>, Serializable {

  /**
   * @since 0.1.0
   */
  @UnsignedInteger64
  long getCoins_uint64();

  /**
   * @since 0.1.0
   */
  void setCoins_uint64(
    final long coins_uint64
  );

  /**
   * @since 0.1.0
   */
  @UnsignedFloat32
  float getMultiplier_ufloat32();

  /**
   * @since 0.1.0
   */
  void setMultiplier_ufloat32(
    final float multiplier_ufloat32
  );

  /**
   * @since 0.1.0
   */
  void addCoins_uint64(
    final long coins_uint64
  );

  /**
   * @since 0.1.0
   */
  void subtractCoins_uint64(
    final long coins_uint64
  );

  /**
   * @since 0.1.0
   */
  boolean isInfinity();

  /**
   * @since 0.1.0
   */
  boolean isLocked();

  /**
   * @since 0.1.0
   */
  boolean isEmpty();
}
