package me.kvdpxne.dtm.statistics

/**
 * Interface representing objects that can be measured and provide a
 * quantitative value.
 *
 * @since 0.1.0
 */
interface Measurable {

  /**
   * Calculates and returns a measurable value as a float.
   *
   * The specific meaning and unit of the measurement depend on the
   * implementing class.
   *
   * @return The measured value as a float.
   * @since 0.1.0
   */
  fun measure(): Float
}