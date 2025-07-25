package me.kvdpxne.dtm.util;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

/**
 * Provides stylized string representations of an object in different formats.
 * Implementations should offer both compact (single-line) and structured (multi-line) formats.
 *
 * @since 0.1.0
 */
public interface StylishToString {

  /**
   * Returns a compact, single-line string representation of the object.
   * Suitable for concise outputs like logs or condensed displays where minimal space is used.
   *
   * @return Non-null compact string representation of the object.
   * @since 0.1.0
   */
  @NotNull
  String packed();

  /**
   * Returns a structured, multi-line string representation of the object with customizable indentation.
   * Useful for human-readable outputs (e.g., debugging, configuration dumps) where hierarchical clarity is needed.
   *
   * @param indent Number of spaces to indent nested elements. Must be a non-negative integer.
   * @return Non-null formatted multi-line string representation of the object.
   * @throws IllegalArgumentException If {@code indent} is negative (enforced via {@code @Range}).
   * @since 0.1.0
   */
  @NotNull
  String listed(
    @Range(from = 0, to = Integer.MAX_VALUE) final int indent
  );
}
