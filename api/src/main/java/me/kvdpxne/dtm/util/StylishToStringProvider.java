package me.kvdpxne.dtm.util;

import java.util.function.Supplier;
import org.jetbrains.annotations.NotNull;

/**
 * Supplies a {@link StylishToString} representation of an object.
 * Acts as a bridge between objects and their stylized string formats,
 * and interoperates with {@link Supplier} for functional compatibility.
 *
 * @since 0.1.0
 */
public interface StylishToStringProvider
  extends
  Supplier<StylishToString> {

  /**
   * Provides a {@link StylishToString} instance representing the object's stylized formats.
   *
   * @return Non-null {@link StylishToString} instance for the current object.
   * @since 0.1.0
   */
  @NotNull
  StylishToString toStylishString();

  /**
   * Functional interface compatibility method. Delegates to {@link #toStylishString()}.
   *
   * @return Non-null {@link StylishToString} instance (identical to {@link #toStylishString()}).
   * @since 0.1.0
   */
  @Override
  @NotNull
  default StylishToString get() {
    return this.toStylishString();
  }
}
