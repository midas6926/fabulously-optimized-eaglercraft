package com.foeagler;

public final class EntityCullingFast {
    private final double renderDistanceSq;
    private final double[] tempVec = new double[3];

    public EntityCullingFast(double renderDistance) {
        this.renderDistanceSq = renderDistance * renderDistance;
    }

    public boolean shouldRender(
            double camX, double camY, double camZ,
            double entityX, double entityY, double entityZ
    ) {
        double dx = entityX - camX;
        double dy = entityY - camY;
        double dz = entityZ - camZ;
        double distSq = dx * dx + dy * dy + dz * dz;
        return distSq <= renderDistanceSq;
    }

    public boolean shouldRenderBounds(
            double camX, double camY, double camZ,
            double minX, double minY, double minZ,
            double maxX, double maxY, double maxZ
    ) {
        double cx = (minX + maxX) * 0.5d - camX;
        double cy = (minY + maxY) * 0.5d - camY;
        double cz = (minZ + maxZ) * 0.5d - camZ;
        double distSq = cx * cx + cy * cy + cz * cz;
        return distSq <= renderDistanceSq;
    }
}
