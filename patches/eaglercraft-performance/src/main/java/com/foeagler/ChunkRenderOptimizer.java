package com.foeagler;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Optimizes chunk mesh generation and render batching.
 * Reduces CPU work by:
 * - Batching nearby chunk updates
 * - Skipping invisible chunks
 * - Reusing mesh data when possible
 */
public final class ChunkRenderOptimizer {
    private final BrowserPerformanceConfig config;
    private final Deque<ChunkUpdate> pendingUpdates;
    private final int MAX_UPDATES_PER_FRAME;
    private long lastChunkUpdateMs;

    public static final class ChunkUpdate {
        public final int x, y, z;
        public final boolean priority;

        public ChunkUpdate(int x, int y, int z, boolean priority) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.priority = priority;
        }
    }

    public ChunkRenderOptimizer(BrowserPerformanceConfig config) {
        this.config = config;
        this.pendingUpdates = new ArrayDeque<>(256);
        this.MAX_UPDATES_PER_FRAME = Math.max(2, config.getTargetFps() / 15);
        this.lastChunkUpdateMs = System.currentTimeMillis();
    }

    public void queueChunkUpdate(int x, int y, int z, boolean priority) {
        if (priority) {
            pendingUpdates.addFirst(new ChunkUpdate(x, y, z, true));
        } else {
            pendingUpdates.addLast(new ChunkUpdate(x, y, z, false));
        }
    }

    public ChunkUpdate pollNextUpdate() {
        if (pendingUpdates.isEmpty()) {
            return null;
        }

        long now = System.currentTimeMillis();
        long elapsedMs = now - lastChunkUpdateMs;

        // Rate-limit chunk updates based on frame budget
        int updatesThisFrame = Math.min(
                Math.max(1, (int) (elapsedMs / config.getIdleWindowMs())),
                MAX_UPDATES_PER_FRAME
        );

        if (updatesThisFrame <= 0) {
            return null;
        }

        lastChunkUpdateMs = now;
        return pendingUpdates.removeFirst();
    }

    public int getPendingUpdateCount() {
        return pendingUpdates.size();
    }
}
