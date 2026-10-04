package com.foeagler;

import java.util.ArrayDeque;
import java.util.Deque;

public final class ChunkUpdateBudget {
    private final Deque<ChunkPos> pendingUpdates;
    private final int maxUpdatesPerFrame;
    private static final int MAX_QUEUE_SIZE = 1024;

    public static final class ChunkPos {
        public final int x, y, z;
        public final boolean priority;

        public ChunkPos(int x, int y, int z, boolean priority) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.priority = priority;
        }
    }

    public ChunkUpdateBudget(int maxUpdatesPerFrame) {
        this.maxUpdatesPerFrame = Math.max(1, maxUpdatesPerFrame);
        this.pendingUpdates = new ArrayDeque<>(256);
    }

    public void queue(int x, int y, int z, boolean priority) {
        if (pendingUpdates.size() >= MAX_QUEUE_SIZE) {
            return; // Drop oldest update
        }
        if (priority) {
            pendingUpdates.addFirst(new ChunkPos(x, y, z, priority));
        } else {
            pendingUpdates.addLast(new ChunkPos(x, y, z, priority));
        }
    }

    public ChunkPos pollNext() {
        return pendingUpdates.pollFirst();
    }

    public int getMaxPerFrame() {
        return maxUpdatesPerFrame;
    }

    public int getPendingCount() {
        return pendingUpdates.size();
    }
}
