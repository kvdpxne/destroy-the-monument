package me.kvdpxne.dtm.shared.ancillary

/**
 * Provides a structure for creating builder objects, promoting code
 * readability and configuration flexibility.
 *
 * A builder class implementing this interface defines a step-by-step proces
 * for constructing an object of type `T`. The `build` method finalizes the
 * object and returns it.
 *
 * @param <T> The type of object being built.
 */
interface Buildable<T> {

  /**
   * Constructs and returns the final object.
   *
   * This method combines the configuration steps performed by the builder and
   * returns the fully constructed object of type `T`.
   *
   * Builders typically perform validation checks on the configuration
   * parameters before building the final object.
   *
   * @return The built object of type `T`.
   */
  fun build(): T
}