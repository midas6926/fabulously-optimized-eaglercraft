package com.foeagler;

/**
 * Base class for objects that can be cached and reused to reduce allocations.
 */
public interface CacheableObject {
    void reset();

    void release();
}
