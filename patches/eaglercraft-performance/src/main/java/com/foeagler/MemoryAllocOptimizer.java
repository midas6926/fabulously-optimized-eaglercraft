package com.foeagler;

/**
 * Tracks and reduces memory allocations in hot paths.
 * Browser targets have tight memory budgets.
 */
public final class MemoryAllocOptimizer {
    private final BrowserPerformanceConfig config;
    private long lastGcTime;
    private int allocationCount;
    private int skipFrameCount;
    private static final int GC_WINDOW_MS = 5000;
    private static final int MAX_ALLOCS_PER_FRAME = 100;

    public MemoryAllocOptimizer(BrowserPerformanceConfig config) {
        this.config = config;
        this.lastGcTime = System.currentTimeMillis();
        this.allocationCount = 0;
        this.skipFrameCount = 0;
    }

    public void recordAllocation() {
        allocationCount++;
    }

    public void recordAllocations(int count) {
        allocationCount += count;
    }

    public boolean isAllocationBudgetExceeded() {
        return allocationCount > MAX_ALLOCS_PER_FRAME;
    }

    public void resetFrameAllocations() {
        allocationCount = 0;
    }

    public boolean shouldSuggestGC() {
        long now = System.currentTimeMillis();
        long elapsedMs = now - lastGcTime;
        if (elapsedMs >= GC_WINDOW_MS && allocationCount > MAX_ALLOCS_PER_FRAME * 30) {
            lastGcTime = now;
            return true;
        }
        return false;
    }

    public void reduceRenderPressure() {
        skipFrameCount = 2;
    }

    public boolean shouldSkipExpensiveOperations() {
        if (skipFrameCount > 0) {
            skipFrameCount--;
            return true;
        }
        return allocationCount > MAX_ALLOCS_PER_FRAME * 2;
    }
}
