package me.kvdpxne.dtm.capabilities;

/**
 * Represents the capability of an object to revert its internal state to an initial or default
 * configuration. This interface defines a standardized mechanism for state reset operations,
 * enabling object reuse and consistent initialization patterns.
 * <p>
 * <b>Implementation Contract:</b>
 * <ul>
 *   <li>The {@code reset()} method must restore all mutable state to initial conditions</li>
 *   <li>Reset operations should clear any accumulated data or intermediate state</li>
 *   <li>Post-reset, the object should be functionally equivalent to a newly created instance</li>
 *   <li>Implementations should maintain consistency with constructor initialization logic</li>
 * </ul>
 *
 * @author Łukasz Pietrzak (kvdpxne)
 * @version 0.1.0
 * @since 0.1.0
 */
public interface Resettable {

  /**
   * Resets the object's internal state to its initial configuration. This operation must ensure
   * that subsequent uses of the object behave identically to a freshly instantiated instance.
   * <p>
   * <b>Preconditions:</b>
   * <ul>
   *   <li>Object must be in a valid state for reset operations</li>
   * </ul>
   *
   * <b>Postconditions:</b>
   * <ul>
   *   <li>All mutable state restored to initial values</li>
   *   <li>References to external resources released (if applicable)</li>
   *   <li>Object ready for immediate reuse</li>
   * </ul>
   *
   * <b>Error Handling:</b>
   * <ul>
   *   <li>Should not throw checked exceptions</li>
   *   <li>May throw runtime exceptions only for unrecoverable state</li>
   * </ul>
   *
   * @since 0.1.0
   */
  void reset();
}