package me.kvdpxne.dtm.capabilities;

import java.util.function.Function;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnknownNullability;

/**
 * Defines the capability for objects to generate their configuration builders and support
 * customized reconstruction workflows. This interface enables advanced mutation patterns where
 * objects can be modified through their builders and rebuilt using custom transformation logic.
 * <p>
 * <b>Core Concepts:</b>
 * <ul>
 *   <li><b>Rebuilder Access:</b> Direct access to a new builder instance</li>
 *   <li><b>Custom Reconstruction:</b> Transformation pipelines for modified rebuilds</li>
 *   <li><b>Type-Safe Mutation:</b> Builder-mediated state changes</li>
 * </ul>
 *
 * @param <T> The target object type produced by the builder
 * @param <U> The builder type implementing {@link Buildable} for {@code T}
 * @author Łukasz Pietrzak (kvdpxne)
 * @version 0.1.0
 * @since 0.1.0
 */
public interface Rebuildable<T, U extends Buildable<T>> {

  /**
   * Generates a new builder instance initialized with the current object's state. The returned
   * builder can be modified and used to create a new instance of {@code T}.
   * <p>
   * <b>Implementation Requirements:</b>
   * <ul>
   *   <li>The builder must reflect the current object's state</li>
   *   <li>Modifying the builder must not affect the original object</li>
   *   <li>The builder must be fully functional and ready for configuration</li>
   * </ul>
   *
   * @return A new builder instance (never null)
   * @since 0.1.0
   */
  @NotNull
  U rebuild();

  /**
   * Performs customized reconstruction using a transformation pipeline. Supplies a new builder to
   * the mapping function and returns the function's result.
   * <p>
   * <b>Typical Usage Patterns:</b>
   * <pre>
   * // Modify and rebuild
   * T modified = original.rebuild(builder -> builder.setValue(42).build());
   *
   * // Extract builder state
   * String info = original.rebuild(Builder::toString);
   * </pre>
   * <p>
   * <b>Nullability Note:</b> The mapping function's return value may have unknown
   * nullability as indicated by {@code @UnknownNullability}.
   *
   * @param mapper Transformation function that accepts a builder and returns a result
   * @return Result of the transformation function
   * @throws NullPointerException If {@code mapper} is null
   * @since 0.1.0
   */
  @NotNull
  T rebuild(
    @NotNull Function<@NotNull U, ? extends @UnknownNullability T> mapper
  );
}