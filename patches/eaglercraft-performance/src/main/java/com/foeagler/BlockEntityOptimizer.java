package com.foeagler;

public final class BlockEntityOptimizer {
    private final BrowserPerformanceConfig config;

    public BlockEntityOptimizer(BrowserPerformanceConfig config) {
        this.config = config;
    }

    public boolean shouldSkipRender(
            double cameraX,
            double cameraY,
            double cameraZ,
            double blockMinX,
            double blockMinY,
            double blockMinZ,
            double blockMaxX,
            double blockMaxY,
            double blockMaxZ,
            boolean isStatic,
            boolean isVisible
    ) {
        if (!config.isBlockEntityOptimizationEnabled()) {
            return false;
        }

        double dx = (blockMinX + blockMaxX) * 0.5d - cameraX;
        double dy = (blockMinY + blockMaxY) * 0.5d - cameraY;
        double dz = (blockMinZ + blockMaxZ) * 0.5d - cameraZ;

        double distanceSq = dx * dx + dy * dy + dz * dz;
        double maxDistanceSq = config.getBlockEntityRenderDistance() * config.getBlockEntityRenderDistance();

        if (distanceSq > maxDistanceSq) {
            return true;
        }

        if (isStatic && !isVisible) {
            return true;
        }

        return false;
    }
}
