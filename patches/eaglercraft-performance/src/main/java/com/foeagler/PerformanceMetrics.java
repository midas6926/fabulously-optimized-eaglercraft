package com.foeagler;

/**
 * Tracks performance metrics to identify bottlenecks.
 * Used for profiling and optimization guidance.
 */
public final class PerformanceMetrics {
    private long frameStartMs;
    private long renderStartMs;
    private long renderEndMs;
    private long updateStartMs;
    private long updateEndMs;

    private int frameCount = 0;
    private long totalFrameTimeMs = 0;
    private long maxFrameTimeMs = 0;
    private long minFrameTimeMs = Long.MAX_VALUE;

    private int entityRenderCount = 0;
    private int blockEntityRenderCount = 0;
    private int entityCullCount = 0;
    private int blockEntityCullCount = 0;

    public void startFrame() {
        frameStartMs = System.currentTimeMillis();
    }

    public void endFrame() {
        long frameTime = System.currentTimeMillis() - frameStartMs;
        totalFrameTimeMs += frameTime;
        maxFrameTimeMs = Math.max(maxFrameTimeMs, frameTime);
        minFrameTimeMs = Math.min(minFrameTimeMs, frameTime);
        frameCount++;
    }

    public void startRender() {
        renderStartMs = System.currentTimeMillis();
    }

    public void endRender() {
        renderEndMs = System.currentTimeMillis();
    }

    public void startUpdate() {
        updateStartMs = System.currentTimeMillis();
    }

    public void endUpdate() {
        updateEndMs = System.currentTimeMillis();
    }

    public void recordEntityRender(boolean culled) {
        if (culled) {
            entityCullCount++;
        } else {
            entityRenderCount++;
        }
    }

    public void recordBlockEntityRender(boolean culled) {
        if (culled) {
            blockEntityCullCount++;
        } else {
            blockEntityRenderCount++;
        }
    }

    public double getAverageFrameTimeMs() {
        return frameCount == 0 ? 0 : (double) totalFrameTimeMs / frameCount;
    }

    public long getMaxFrameTimeMs() {
        return maxFrameTimeMs;
    }

    public long getMinFrameTimeMs() {
        return minFrameTimeMs == Long.MAX_VALUE ? 0 : minFrameTimeMs;
    }

    public long getRenderTimeMs() {
        return renderEndMs - renderStartMs;
    }

    public long getUpdateTimeMs() {
        return updateEndMs - updateStartMs;
    }

    public String getReport() {
        StringBuilder sb = StringBuilderPool.acquire();
        sb.append("=== Performance Report ===\n");
        sb.append("Avg frame time: ").append(String.format("%.2f", getAverageFrameTimeMs())).append(" ms\n");
        sb.append("Max frame time: ").append(getMaxFrameTimeMs()).append(" ms\n");
        sb.append("Min frame time: ").append(getMinFrameTimeMs()).append(" ms\n");
        sb.append("Render time: ").append(getRenderTimeMs()).append(" ms\n");
        sb.append("Update time: ").append(getUpdateTimeMs()).append(" ms\n");
        sb.append("Entities rendered: ").append(entityRenderCount).append(", culled: ").append(entityCullCount).append("\n");
        sb.append("Block entities rendered: ").append(blockEntityRenderCount).append(", culled: ").append(blockEntityCullCount).append("\n");
        String result = sb.toString();
        StringBuilderPool.release(sb);
        return result;
    }
}
