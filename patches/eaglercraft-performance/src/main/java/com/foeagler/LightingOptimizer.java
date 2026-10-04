package com.foeagler;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Optimizes light level calculations and caching.
 * Reduces per-block lighting updates by batching and throttling.
 */
public final class LightingOptimizer {
    private final BrowserPerformanceConfig config;
    private final Deque<LightUpdate> pendingLightUpdates;
    private final int MAX_LIGHT_UPDATES_PER_FRAME;
    private static final int LIGHT_CACHE_SIZE = 4096;
    private final byte[] lightCache;
    private int cacheHits = 0;
    private int cacheMisses = 0;

    public static final class LightUpdate {
        public final int x, y, z;
        public final byte level;

        public LightUpdate(int x, int y, int z, byte level) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.level = level;
        }
    }

    public LightingOptimizer(BrowserPerformanceConfig config) {
        this.config = config;
        this.pendingLightUpdates = new ArrayDeque<>(512);
        this.MAX_LIGHT_UPDATES_PER_FRAME = Math.max(4, config.getTargetFps() / 10);
        this.lightCache = new byte[LIGHT_CACHE_SIZE];
        java.util.Arrays.fill(this.lightCache, (byte) -1);
    }

    public void queueLightUpdate(int x, int y, int z, byte level) {
        pendingLightUpdates.addLast(new LightUpdate(x, y, z, level));
    }

    public LightUpdate pollNextLightUpdate() {
        if (pendingLightUpdates.isEmpty()) {
            return null;
        }
        return pendingLightUpdates.removeFirst();
    }

    public byte getCachedLight(int x, int y, int z) {
        int hash = (x * 73856093) ^ (y * 19349663) ^ (z * 83492791);
        int index = Math.abs(hash) % LIGHT_CACHE_SIZE;
        byte cached = lightCache[index];
        if (cached >= 0) {
            cacheHits++;
        } else {
            cacheMisses++;
        }
        return cached;
    }

    public void setCachedLight(int x, int y, int z, byte level) {
        int hash = (x * 73856093) ^ (y * 19349663) ^ (z * 83492791);
        int index = Math.abs(hash) % LIGHT_CACHE_SIZE;
        lightCache[index] = level;
    }

    public int getPendingUpdates() {
        return pendingLightUpdates.size();
    }
}
