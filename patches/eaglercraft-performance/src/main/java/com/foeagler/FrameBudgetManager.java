package com.foeagler;

public final class FrameBudgetManager {
    private final int targetFps;
    private final int idleFps;
    private final long idleThresholdMs;
    private long lastInputMs;
    private int currentBudgetMs;

    public FrameBudgetManager(int targetFps, int idleFps, long idleThresholdMs) {
        this.targetFps = Math.max(1, targetFps);
        this.idleFps = Math.max(1, idleFps);
        this.idleThresholdMs = idleThresholdMs;
        this.lastInputMs = System.currentTimeMillis();
        this.currentBudgetMs = 1000 / this.targetFps;
    }

    public void onInput() {
        lastInputMs = System.currentTimeMillis();
    }

    public int getFrameBudgetMs() {
        long now = System.currentTimeMillis();
        long idleMs = now - lastInputMs;

        if (idleMs > idleThresholdMs) {
            currentBudgetMs = 1000 / idleFps;
        } else {
            currentBudgetMs = 1000 / targetFps;
        }

        return currentBudgetMs;
    }

    public boolean isIdle() {
        return (System.currentTimeMillis() - lastInputMs) > idleThresholdMs;
    }
}
