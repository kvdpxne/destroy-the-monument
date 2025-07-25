package me.kvdpxne.dtm.capabilities;

import org.jetbrains.annotations.NotNull;

/**
 * Represents objects capable of producing independent state replicas of themselves.
 * Defines a standardized mechanism for creating deep copies that maintain value
 * equivalence while ensuring referential independence between the original and copy.
 * <p>
 * <b>Implementation Contract:</b>
 * <ul>
 *   <li>The copy must be a distinct object instance from the original</li>
 *   <li>Primitive and immutable fields may be shared</li>
 *   <li>Mutable object references must be deeply copied or made immutable</li>
 *   <li>Modifications to the copy must not affect the original</li>
 *   <li>The copy should satisfy {@code original.equals(copy) == true}</li>
 * </ul>
 *
 * @param <T> The concrete type of the implementing object
 * @author Łukasz Pietrzak (kvdpxne)
 * @version 0.1.0
 * @since 0.1.0
 */
public interface Copyable<T> {

  /**
   * Creates a state-replicated instance of the current object. The returned copy
   * must maintain value equivalence with the original while being referentially
   * independent. Implementations should perform deep copying for all mutable state
   * to ensure modification isolation.
   * <p>
   * <b>Key Requirements:</b>
   * <ol>
   *   <li><b>Referential Independence:</b> {@code original != copy}</li>
   *   <li><b>State Equivalence:</b> {@code original.equals(copy)}</li>
   *   <li><b>Immutability Safety:</b> Shared references must be immutable or defensively copied</li>
   *   <li><b>No Side Effects:</b> Original object state remains unchanged</li>
   * </ol>
   *
   * @return A non-null, fully initialized replica with identical state
   * @throws UnsupportedOperationException If deep copying is not feasible for the object type
   * @since 0.1.0
   */
  @NotNull
  T copy();
}