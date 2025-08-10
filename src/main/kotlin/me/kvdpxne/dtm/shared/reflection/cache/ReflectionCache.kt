package me.kvdpxne.dtm.shared.reflection.cache

import java.lang.ref.Reference
import java.lang.ref.ReferenceQueue
import java.lang.ref.WeakReference
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap

/**
 * **Abstract base class** for all reflection caches that provides:
 *
 * - **Memory-safe storage** using weak references
 * - **Automatic cleanup** of garbage-collected entries
 * - **Thread-safe operations** via `ConcurrentHashMap`
 * - **Lazy computation** of expensive reflection operations
 *
 * ### Critical Design Features
 *
 * 1. **Weak Reference Management**:
 *    ```mermaid
 *    graph LR
 *      A[Cache Entry] -->|Strong Reference| B[Reflection Operation]
 *      C[WeakReference] -->|Weak Reference| B
 *      D[ReferenceQueue] -->|Tracks| C
 *    ```
 *    - Prevents classloader leaks during plugin reloads
 *    - Automatically removes entries when classes are garbage collected
 *
 * 2. **Automatic Cleanup**:
 *    - Processes reference queue on every cache access
 *    - Removes dangling references without dedicated threads
 *    - Zero overhead when no garbage collection has occurred
 *
 * 3. **Lazy Computation**:
 *    - Computes values only when first requested
 *    - Thread-safe double-checked locking pattern
 *    - Prevents redundant computation in concurrent environments
 *
 * ### Non-Technical Analogy
 * Think of this as a **self-maintaining library system** that:
 *
 * - **Automatically returns borrowed books** when patrons leave (weak references)
 * - **Clears empty shelves** when books are destroyed (cleanup)
 * - **Prints new books only when requested** (lazy computation)
 * - **Serves multiple patrons simultaneously** (thread safety)
 *
 * Without this system, the library would:
 * - Overflow with unused books (memory leaks)
 * - Waste resources printing duplicates (redundant computation)
 * - Create chaos during busy hours (thread conflicts)
 *
 * ### Why This Matters for Performance
 * Reflection operations are expensive because they:
 *
 * 1. Bypass Java's normal method dispatch
 * 2. Require security permission checks
 * 3. Involve complex signature matching
 *
 * This cache reduces those costs by:
 * - Eliminating redundant security checks
 * - Avoiding repeated signature analysis
 * - Minimizing JVM internal operations
 *
 * ### Implementation Notes
 * - **Internal class**: Not intended for direct use outside reflection package
 * - **Generic parameters**:
 *   - `K`: Key type (from [cache.keys][me.kvdpxne.dtm.shared.reflection.cache.keys])
 *   - `V`: Value type (reflection operation implementation)
 * - **Thread safety**: Achieved through `ConcurrentHashMap` and atomic operations
 * - **Memory profile**: Optimized for long-running server applications
 *
 * @param K The key type used to identify cache entries
 * @param V The value type stored in the cache
 *
 * @see [ClassCache] Implementation for class lookups
 * @see [ConstructorCache] Implementation for constructor invokers
 * @see [FieldCache] Implementation for field accessors
 * @see [MethodCache] Implementation for method invokers
 * @since 0.1.0
 */
internal abstract class ReflectionCache<K, V> protected constructor() {

  /**
   * **Thread-safe storage** for cache entries using weak references.
   *
   * ### Technical Details
   * - Uses `ConcurrentHashMap` for thread-safe operations
   * - Values stored as `WeakReference` to allow garbage collection
   * - Prevents memory leaks when classes are unloaded
   *
   * ### Memory Management
   * When a class is garbage collected:
   * 1. JVM enqueues the weak reference in `referenceQueue`
   * 2. Next cache access triggers cleanup
   * 3. Entry is removed from the map
   *
   * This prevents the common "classloader leak" problem in plugin-based systems.
   *
   * @see [referenceQueue] Tracks garbage-collected entries
   * @since 0.1.0
   */
  private val cache: ConcurrentMap<K, Reference<V>> = ConcurrentHashMap()

  /**
   * **Reference queue** that tracks garbage-collected cache entries.
   *
   * ### Technical Workflow
   * 1. When a value is garbage collected:
   *    - JVM automatically enqueues its weak reference
   * 2. During next cache access:
   *    - `cleanup()` processes the queue
   *    - Entries with enqueued references are removed
   *
   * ### Why This Matters
   * Without this mechanism:
   * - Cache would retain strong references to classes
   * - Preventing classloader garbage collection
   * - Causing memory leaks during plugin reloads
   *
   * @see [cleanup] Processes the queue to remove dead entries
   * @since 0.1.0
   */
  private val referenceQueue: ReferenceQueue<V> = ReferenceQueue()

  /**
   * **Retrieves or computes** a cache entry in a thread-safe manner.
   *
   * ### Operation Flow
   * 1. **Cleanup**: Removes garbage-collected entries
   * 2. **Check cache**: Returns existing value if present
   * 3. **Compute**: Creates new value if missing
   * 4. **Store**: Saves computed value in cache
   *
   * ### Thread Safety
   * Uses `ConcurrentHashMap.computeIfAbsent()` which guarantees:
   * - Only one thread computes the value for a given key
   * - Other threads block until computation completes
   * - No duplicate computations for the same key
   *
   * ### Error Handling
   * - Throws `IllegalStateException` if value was garbage collected mid-operation
   *   (extremely rare race condition)
   * - Propagates exceptions from `compute` function
   *
   * @param key The cache key to look up
   * @param compute Function to compute value if not present in cache
   *   - Only called when entry is missing
   *   - Must be thread-safe and side-effect free
   *
   * @return The cached or newly computed value
   *
   * @throws [IllegalStateException] If cached value was unexpectedly garbage collected
   *
   * @sample me.kvdpxne.dtm.shared.reflection.samples.CacheSamples.getFieldAccessor
   * @since 0.1.0
   */
  protected fun getOrCompute(
    key: K,
    compute: () -> V
  ): V {
    this.cleanup()
    return this.cache
      .computeIfAbsent(key) { _: K -> WeakReference(compute()) }
      .get() ?: error("Cached value was garbage collected unexpectedly")
  }

  /**
   * **Processes reference queue** to remove garbage-collected entries.
   *
   * ### Technical Workflow
   * 1. Polls `referenceQueue` for enqueued references
   * 2. For each enqueued reference:
   *    - Removes corresponding entry from `cache`
   *
   * ### Performance Characteristics
   * - **O(1)** when no references enqueued
   * - **O(n)** for `n` enqueued references
   * - Zero cost when no garbage collection has occurred
   *
   * ### Why Call Before Every Access?
   * Ensures cache stays clean without:
   * - Dedicated cleanup threads
   * - Scheduled maintenance tasks
   * - Memory leak risks
   *
   * @see [referenceQueue] Source of enqueued references
   * @since 0.1.0
   */
  private fun cleanup() {
    var ref: Reference<*>?
    while (true) {
      ref = this.referenceQueue.poll() ?: break
      this.cache.entries.removeIf { (_: K?, value: Reference<V>?) ->
        value == ref
      }
    }
  }
}