package com.foeagler;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.function.Supplier;

/**
 * Object pooling to reduce garbage collection pressure.
 * Commonly used for temporary vectors, matrices, and render state objects.
 */
public final class ObjectPool<T extends CacheableObject> {
    private final Deque<T> available;
    private final Deque<T> inUse;
    private final Supplier<T> factory;
    private final int maxPoolSize;
    private int allocationCount = 0;

    public ObjectPool(Supplier<T> factory, int initialSize, int maxPoolSize) {
        this.factory = factory;
        this.maxPoolSize = maxPoolSize;
        this.available = new ArrayDeque<>(initialSize);
        this.inUse = new ArrayDeque<>(initialSize);

        for (int i = 0; i < initialSize; i++) {
            available.add(factory.get());
        }
    }

    public T acquire() {
        T obj;
        if (available.isEmpty()) {
            if (allocationCount < maxPoolSize) {
                obj = factory.get();
                allocationCount++;
            } else {
                // Pool exhausted; return null to signal fallback
                return null;
            }
        } else {
            obj = available.removeFirst();
        }
        inUse.add(obj);
        return obj;
    }

    public void release(T obj) {
        if (obj == null) return;
        if (!inUse.remove(obj)) return; // Not from this pool
        obj.reset();
        available.add(obj);
    }

    public void releaseAll() {
        while (!inUse.isEmpty()) {
            T obj = inUse.removeFirst();
            obj.reset();
            available.add(obj);
        }
    }

    public int getPoolSize() {
        return available.size();
    }

    public int getInUseCount() {
        return inUse.size();
    }
}
