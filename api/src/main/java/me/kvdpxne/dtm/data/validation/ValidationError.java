package me.kvdpxne.dtm.data.validation;

import java.util.Map;
import me.kvdpxne.dtm.util.StylishToString;
import me.kvdpxne.dtm.util.StylishToStringProvider;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Range;

/**
 * Represents a validation error containing details about a specific validation failure.
 * Provides structured information about the error including the affected field, error message,
 * error code, invalid value, and optional metadata. Also supports styled string representation.
 *
 * @author Łukasz Pietrzak (kvdpxne)
 * @version 0.1.0
 * @since 0.1.0
 */
public interface ValidationError extends StylishToStringProvider {

  /**
   * Gets the name of the field that failed validation.
   *
   * @return The invalid field name (never null)
   * @since 0.1.0
   */
  @NotNull
  String getField();

  /**
   * Gets the human-readable error message describing the validation failure.
   *
   * @return The error description (never null)
   * @since 0.1.0
   */
  @NotNull
  String getMessage();

  /**
   * Gets the unique error code identifying the type of validation failure.
   * The code must be a negative integer within the full negative integer range.
   *
   * @return The error code (negative integer)
   * @since 0.1.0
   */
  @Range(from = Integer.MIN_VALUE, to = -1)
  int getCode();

  /**
   * Gets the invalid value that caused the validation failure, if available.
   *
   * @return The rejected value or null if not available
   * @since 0.1.0
   */
  @Nullable
  Object getInvalidValue();

  /**
   * Gets additional metadata about the validation failure, if available.
   * The metadata map may contain contextual information to help diagnose the error.
   * Keys must be non-null strings, while values may be null.
   *
   * @return Additional error context or null if not available
   * @since 0.1.0
   */
  @Nullable
  Map<@NotNull String, @Nullable Object> getMetadata();


  /**
   * Converts the error to a formatted string representation with structured styling.
   *
   * @return A styled string representation of the error (never null)
   * @since 0.1.0
   */
  @Override
  @NotNull
  StylishToString toStylishString();
}
