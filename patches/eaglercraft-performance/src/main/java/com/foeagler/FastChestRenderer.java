package com.foeagler;

public final class FastChestRenderer {
    private final BrowserPerformanceConfig config;

    public FastChestRenderer(BrowserPerformanceConfig config) {
        this.config = config;
    }

    public boolean shouldUseFastRender(boolean isChestOpen, boolean isAnimating, boolean isStatic, float brightness) {
        if (!config.isFastChestRenderingEnabled()) {
            return false;
        }

        if (isChestOpen || isAnimating) {
            return false;
        }

        return isStatic || brightness <= 1.0f;
    }
}
