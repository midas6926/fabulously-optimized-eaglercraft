package com.foeagler;

public final class DynamicFpsLimiter {
    private final BrowserPerformanceConfig config;
    private long lastInputTimestamp;
    private int currentFrameBudgetMs;

    public DynamicFpsLimiter(BrowserPerformanceConfig config) {
        this.config = config;
        this.lastInputTimestamp = System.currentTimeMillis();
        this.currentFrameBudgetMs = 1000 / config.getTargetFps();
    }

    public void markInput() {
        lastInputTimestamp = System.currentTimeMillis();
    }

    public void markNoInput() {
        // no-op; used by callers that want to explicitly note inactivity
    }

    public int getFrameBudgetMs() {
        long now = System.currentTimeMillis();
        long idleMs = now - lastInputTimestamp;

        if (!config.isDynamicFpsEnabled()) {
            return 1000 / config.getTargetFps();
        }

        if (idleMs >= config.getIdleWindowMs()) {
            currentFrameBudgetMs = 1000 / config.getInactiveFps();
        } else {
            currentFrameBudgetMs = 1000 / config.getTargetFps();
        }

        return currentFrameBudgetMs;
    }

    public boolean shouldSkipFrame() {
        int frameBudgetMs = getFrameBudgetMs();
        return frameBudgetMs <= 0;
    }
}
