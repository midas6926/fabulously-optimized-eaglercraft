package com.foeagler;

public final class EntityCullingManager {
    private final BrowserPerformanceConfig config;

    public EntityCullingManager(BrowserPerformanceConfig config) {
        this.config = config;
    }

    public boolean shouldRenderEntity(
            double cameraX,
            double cameraY,
            double cameraZ,
            double entityMinX,
            double entityMinY,
            double entityMinZ,
            double entityMaxX,
            double entityMaxY,
            double entityMaxZ
    ) {
        if (!config.isEntityCullingEnabled()) {
            return true;
        }

        double dxMin = entityMinX - cameraX;
        double dyMin = entityMinY - cameraY;
        double dzMin = entityMinZ - cameraZ;

        double dxMax = entityMaxX - cameraX;
        double dyMax = entityMaxY - cameraY;
        double dzMax = entityMaxZ - cameraZ;

        double distanceSqMin = dxMin * dxMin + dyMin * dyMin + dzMin * dzMin;
        double distanceSqMax = dxMax * dxMax + dyMax * dyMax + dzMax * dzMax;

        double renderDistanceSq = config.getEntityCullingDistance() * config.getEntityCullingDistance();

        if (distanceSqMin > renderDistanceSq && distanceSqMax > renderDistanceSq) {
            return false;
        }

        return true;
    }
}
