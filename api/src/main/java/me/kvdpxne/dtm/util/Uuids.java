package me.kvdpxne.dtm.util;

import java.util.UUID;
import me.kvdpxne.dtm.UnsubstantiatedInitializationError;

/**
 * Provides utility methods and constants for working with UUIDs, including special
 * UUID values and validation operations. This class defines standard UUID constants
 * and offers safety checks for UUID generation and validation.
 * <p>
 * <b>Key Features:</b>
 * <ul>
 *   <li>Special UUID constants: {@code NIL} (all zeros) and {@code ALL} (all ones)</li>
 *   <li>Validation methods for special UUID values</li>
 *   <li>Safe random UUID generation with collision avoidance</li>
 * </ul>
 * <p>
 * This class cannot be instantiated as it serves as a static utility container.
 *
 * @since 0.1.0
 */
public final class Uuids {

  /**
   * The nil UUID (00000000-0000-0000-0000-000000000000) representing a universally
   * unique identifier with all 128 bits set to zero. This UUID has special meaning
   * in certain protocols and should not be used as a regular unique identifier.
   *
   * @see <a href="https://tools.ietf.org/html/rfc4122#section-4.1.7">RFC 4122: Nil UUID</a>
   * @since 0.1.0
   */
  public static final UUID NIL = NilUuidHolder.NIL;

  /**
   * The all-bits-set UUID (ffffffff-ffff-ffff-ffff-ffffffffffff) representing a
   * universally unique identifier with all 128 bits set to one. This UUID has
   * special meaning in broadcast contexts and should not be used as a regular
   * unique identifier.
   *
   * @since 0.1.0
   */
  public static final UUID ALL = AllUuidHolder.ALL;

  /**
   * Private constructor to prevent instantiation.
   * Throws {@link UnsubstantiatedInitializationError} if invoked.
   *
   * @since 0.1.0
   */
  private Uuids() {
    throw new UnsubstantiatedInitializationError(this.getClass());
  }

  /**
   * Determines if the specified UUID is the nil UUID (all zeros).
   *
   * @param uuid The UUID to check (may be null)
   * @return {@code true} if the UUID is non-null and equals {@link #NIL},
   *         {@code false} otherwise
   * @since 0.1.0
   */
  public static boolean isNil(final UUID uuid) {
    return null != uuid
      && 0L == uuid.getMostSignificantBits()
      && 0L == uuid.getLeastSignificantBits();
  }

  /**
   * Determines if the specified UUID is the all-bits-set UUID (all ones).
   *
   * @param uuid The UUID to check (may be null)
   * @return {@code true} if the UUID is non-null and equals {@link #ALL},
   *         {@code false} otherwise
   * @since 0.1.0
   */
  public static boolean isAll(final UUID uuid) {
    return null != uuid
      && -1L == uuid.getMostSignificantBits()
      && -1L == uuid.getLeastSignificantBits();
  }

  /**
   * Generates a random UUID while guaranteeing it is neither {@link #NIL} nor {@link #ALL}.
   * Performs up to 3 generation attempts to avoid these special values. Throws an
   * {@link Error} if unable to generate a valid UUID after maximum attempts (extremely unlikely).
   *
   * @return A randomly generated UUID that is not a special value
   * @throws Error If unable to generate a non-special UUID after 3 attempts
   * @since 0.1.0
   */
  public static UUID randomUuid() {
    int attempts = 3;
    UUID next;
    do {
      if (0 >= attempts) {
        throw new Error("Attempt to randomly generated UUID");
      }
      next = UUID.randomUUID();
      --attempts;
    } while (!isNil(next) && !isAll(next));
    return next;
  }

  /**
   * Holder class for the nil UUID constant to ensure lazy initialization.
   *
   * @since 0.1.0
   */
  private static final class NilUuidHolder {
    private static final UUID NIL = new UUID(0L, 0L);
  }

  /**
   * Holder class for the all-bits-set UUID constant to ensure lazy initialization.
   *
   * @since 0.1.0
   */
  private static final class AllUuidHolder {
    private static final UUID ALL = new UUID(-1L, -1L);
  }
}
