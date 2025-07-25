package me.kvdpxne.dtm.data.validation;

import java.util.Collection;
import java.util.function.Consumer;
import java.util.function.Supplier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Range;
import org.jetbrains.annotations.UnmodifiableView;

/**
 * Represents the outcome of a validation operation, which can be either successful
 * or contain a collection of validation errors. Provides methods to combine results,
 * and to execute callbacks based on the validation outcome.
 *
 * @author Łukasz Pietrzak (kvdpxne)
 * @version 0.1.0
 * @since 0.1.0
 */
public interface ValidationResult {

  /**
   * Determines if the validation was successful (no errors).
   *
   * @return True if validation succeeded with no errors, false otherwise
   * @since 0.1.0
   */
  boolean isValid();

  /**
   * Combines this result with other validation results.
   * Returns a new result representing the aggregate outcome. The combined result
   * will be successful only if all input results are successful.
   *
   * @param others Other validation results to combine (must not be null)
   * @return Combined validation result (never null)
   * @since 0.1.0
   */
  @NotNull
  ValidationResult combine(
    @Nullable ValidationResult... others
  );

  /**
   * Executes the specified action if the validation failed.
   * The action receives the collection of validation errors. Returns this result
   * for method chaining.
   *
   * @param action Callback to execute with error collection (must not be null)
   * @return This validation result (never null)
   * @since 0.1.0
   */
  @NotNull
  ValidationResult onFailure(
    @NotNull Consumer<@NotNull Collection<@NotNull ValidationError>> action
  );

  /**
   * Executes the specified action if the validation succeeded.
   * Returns this result for method chaining.
   *
   * @param action Callback to execute on success (must not be null)
   * @return This validation result (never null)
   * @since 0.1.0
   */
  @NotNull
  ValidationResult onSuccess(
    @NotNull Supplier<@NotNull Void> action
  );

  /**
   * Represents a failed validation result containing one or more errors.
   *
   * @since 0.1.0
   */
  interface Failure extends ValidationResult {

    /**
     * Gets an unmodifiable view of the validation errors.
     *
     * @return Read-only collection of validation errors (never null)
     * @since 0.1.0
     */
    @UnmodifiableView
    @NotNull
    Collection<@NotNull ValidationError> getErrors();

    /**
     * @since 0.1.0
     */
    boolean hasErrorByCode(
      @Range(from = Integer.MIN_VALUE, to = -1) int code
    );
  }

  /**
   * Represents a successful validation result with no errors.
   *
   * @since 0.1.0
   */
  interface Success extends ValidationResult {
  }
}
