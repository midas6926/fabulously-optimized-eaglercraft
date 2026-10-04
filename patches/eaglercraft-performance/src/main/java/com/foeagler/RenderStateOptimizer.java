package com.foeagler;

/**
 * Reduces redundant GL state changes and draw calls.
 * Batches rendering operations to minimize state thrashing.
 */
public final class RenderStateOptimizer {
    private final BrowserPerformanceConfig config;
    private int lastBlendMode = -1;
    private int lastAlphaTest = -1;
    private int lastDepthTest = -1;
    private int lastCullingMode = -1;
    private boolean lastLightingState = false;
    private int stateChangeCount = 0;
    private int skippedStateChanges = 0;
    private static final int MAX_STATE_CHANGES_PER_FRAME = 200;

    public RenderStateOptimizer(BrowserPerformanceConfig config) {
        this.config = config;
    }

    public boolean setBlendMode(int mode) {
        if (lastBlendMode == mode) {
            skippedStateChanges++;
            return false;
        }
        lastBlendMode = mode;
        stateChangeCount++;
        return stateChangeCount < MAX_STATE_CHANGES_PER_FRAME;
    }

    public boolean setAlphaTest(int mode) {
        if (lastAlphaTest == mode) {
            skippedStateChanges++;
            return false;
        }
        lastAlphaTest = mode;
        stateChangeCount++;
        return stateChangeCount < MAX_STATE_CHANGES_PER_FRAME;
    }

    public boolean setDepthTest(int mode) {
        if (lastDepthTest == mode) {
            skippedStateChanges++;
            return false;
        }
        lastDepthTest = mode;
        stateChangeCount++;
        return stateChangeCount < MAX_STATE_CHANGES_PER_FRAME;
    }

    public boolean setCullingMode(int mode) {
        if (lastCullingMode == mode) {
            skippedStateChanges++;
            return false;
        }
        lastCullingMode = mode;
        stateChangeCount++;
        return stateChangeCount < MAX_STATE_CHANGES_PER_FRAME;
    }

    public boolean setLighting(boolean enabled) {
        if (lastLightingState == enabled) {
            skippedStateChanges++;
            return false;
        }
        lastLightingState = enabled;
        stateChangeCount++;
        return stateChangeCount < MAX_STATE_CHANGES_PER_FRAME;
    }

    public void resetFrame() {
        stateChangeCount = 0;
        skippedStateChanges = 0;
    }

    public int getSkippedChanges() {
        return skippedStateChanges;
    }
}
