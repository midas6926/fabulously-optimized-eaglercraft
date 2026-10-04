package com.foeagler;

public final class WorldTickOptimizer {
    private final BrowserPerformanceConfig config;
    private long lastWorldTickMs;
    private static final long MIN_TICK_INTERVAL_MS = 50; // 20 TPS max

    public WorldTickOptimizer(BrowserPerformanceConfig config) {
        this.config = config;
        this.lastWorldTickMs = System.currentTimeMillis();
    }

    public boolean shouldTickWorld() {
        long now = System.currentTimeMillis();
        long elapsedMs = now - lastWorldTickMs;

        if (elapsedMs >= MIN_TICK_INTERVAL_MS) {
            lastWorldTickMs = now;
            return true;
        }
        return false;
    }

    public boolean shouldTickLighting() {
        return shouldTickWorld();
    }

    public boolean shouldTickEntities() {
        return shouldTickWorld();
    }

    public boolean shouldTickRedstone() {
        return shouldTickWorld();
    }
}
