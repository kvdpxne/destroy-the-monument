package me.kvdpxne.dtm.statistics

/**
 * Interface representing player statistics that can be measured and compared.
 *
 * This interface inherits from `Measurable`, indicating that statistics
 * objects can provide a measurable value.
 * The specific value returned by `measure` likely represents some overall
 * summary of the player's performance.
 *
 * @since 0.1.0
 */
interface Statistics : Measurable