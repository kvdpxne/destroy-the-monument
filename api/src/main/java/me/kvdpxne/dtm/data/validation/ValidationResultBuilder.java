package me.kvdpxne.dtm.data.validation;

import java.util.Map;
import me.kvdpxne.dtm.capabilities.Buildable;
import me.kvdpxne.dtm.capabilities.Resettable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Range;

/**
 * Builder for constructing validation results by collecting validation errors.
 * Supports adding errors either as pre-built instances or through field-specific parameters.
 * Implements {@link Buildable} for result construction and {@link Resettable} for reuse.
 *
 * @author Łukasz Pietrzak (kvdpxne)
 * @version 0.1.0
 * @since 0.1.0
 */
public interface ValidationResultBuilder
  extends
  Buildable<ValidationResult>,
  Resettable {

  /**
   * Adds a fully constructed validation error to the result.
   *
   * @param error The validation error to add (must not be null)
   * @return This builder instance for method chaining (never null)
   * @since 0.1.0
   */
  @NotNull
  ValidationResultBuilder addError(
    @NotNull ValidationError error
  );

  /**
   * Creates and adds a validation error with the specified parameters.
   *
   * @param field The invalid field name (must not be null)
   * @param message Error description (must not be null)
   * @param code Error code (must be negative)
   * @param invalidValue The rejected value (may be null)
   * @param metadata Additional error context (may be null)
   * @return This builder instance (never null)
   * @since 0.1.0
   */
  @NotNull
  ValidationResultBuilder addError(
    @NotNull String field,
    @NotNull String message,
    @Range(from = Integer.MIN_VALUE, to = -1) int code,
    @Nullable Object invalidValue,
    @Nullable Map<@NotNull String, @Nullable Object> metadata
  );

  /**
   * Creates and adds a validation error without metadata.
   * Convenience method equivalent to {@code addError(field, message, code, invalidValue, null)}.
   *
   * @param field The invalid field name (must not be null)
   * @param message Error description (must not be null)
   * @param code Error code (must be negative)
   * @param invalidValue The rejected value (may be null)
   * @return This builder instance (never null)
   * @since 0.1.0
   */
  @NotNull
  default ValidationResultBuilder addError(
    @NotNull String field,
    @NotNull String message,
    @Range(from = Integer.MIN_VALUE, to = -1) int code,
    @Nullable Object invalidValue
  ) {
    return this.addError(field, message, code, invalidValue, null);
  }

  /**
   * Creates and adds a validation error without metadata or invalid value.
   * Convenience method equivalent to {@code addError(field, message, code, null, null)}.
   *
   * @param field The invalid field name (must not be null)
   * @param message Error description (must not be null)
   * @param code Error code (must be negative)
   * @return This builder instance (never null)
   * @since 0.1.0
   */
  @NotNull
  default ValidationResultBuilder addError(
    @NotNull String field,
    @NotNull String message,
    @Range(from = Integer.MIN_VALUE, to = -1) int code
  ) {
    return this.addError(field, message, code, null);
  }

  /**
   * Constructs the final validation result.
   * Returns a success result if no errors were added, or a failure result
   * containing all collected errors.
   *
   * @return The validation result (never null)
   * @since 0.1.0
   */
  @Override
  @NotNull
  ValidationResult build();

  /**
   * Resets the builder by clearing all collected errors.
   * Prepares the builder for reuse in new validation operations.
   *
   * @since 0.1.0
   */
  @Override
  void reset();
}
