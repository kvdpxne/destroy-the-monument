package me.kvdpxne.dtm.capabilities;

import java.util.Locale;
import org.jetbrains.annotations.NotNull;

/**
 * Represents entities that possess a human-readable identifier distinct from their
 * unique persistence identifier. Defines standardized access to the display name
 * and provides normalization capabilities for consistent case-insensitive operations.
 * <p>
 * <b>Implementation Contract:</b>
 * <ul>
 *   <li>Names must be non-null and non-blank</li>
 *   <li>Names should be human-readable and meaningful</li>
 *   <li>Normalized names must be stable across system locales</li>
 * </ul>
 *
 * @author Łukasz Pietrzak (kvdpxne)
 * @version 0.1.0
 * @since 0.1.0
 */
public interface Nameable {

  /**
   * Retrieves the primary display name of this entity. The name serves as a
   * human-friendly identifier and should be suitable for user interfaces.
   * <p>
   * <b>Requirements:</b>
   * <ul>
   *   <li>Must be non-null</li>
   *   <li>Should contain meaningful content (non-empty)</li>
   *   <li>May contain case-sensitive characters</li>
   * </ul>
   *
   * @return The display name (never null)
   * @since 0.1.0
   */
  @NotNull
  String getName();

  /**
   * Generates a normalized version of the display name suitable for case-insensitive
   * operations. Uses fixed {@link Locale#ENGLISH} for consistent uppercase conversion
   * regardless of system locale, preventing unexpected behavior in internationalized
   * environments.
   * <p>
   * <b>Normalization Guarantees:</b>
   * <ul>
   *   <li>Result is always uppercase</li>
   *   <li>Original name's linguistic content is preserved</li>
   *   <li>Locale-sensitive characters are consistently transformed</li>
   *   <li>Output is stable across different system configurations</li>
   * </ul>
   *
   * <b>Typical Use Cases:</b>
   * <ul>
   *   <li>Case-insensitive comparisons</li>
   *   <li>Consistent hashing algorithms</li>
   *   <li>Standardized storage keys</li>
   * </ul>
   *
   * @return English-uppercase normalized name (never null)
   * @since 0.1.0
   */
  @NotNull
  default String getNormalizedName() {
    return this.getName().toUpperCase(Locale.ENGLISH);
  }
}