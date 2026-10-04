package com.foeagler;

public final class BrowserPerformanceConfig {
    public static final class Defaults {
        public static final int TARGET_FPS = 60;
        public static final int INACTIVE_FPS = 20;
        public static final int IDLE_WINDOW_MS = 3000;
        public static final float ENTITY_CULLING_DISTANCE = 24.0f;
        public static final float BLOCK_ENTITY_RENDER_DISTANCE = 18.0f;
        public static final boolean DYNAMIC_FPS_ENABLED = true;
        public static final boolean ENTITY_CULLING_ENABLED = true;
        public static final boolean BLOCK_ENTITY_OPTIMIZATION_ENABLED = true;
        public static final boolean FAST_CHEST_RENDERING_ENABLED = true;
    }

    private final int targetFps;
    private final int inactiveFps;
    private final int idleWindowMs;
    private final float entityCullingDistance;
    private final float blockEntityRenderDistance;
    private final boolean dynamicFpsEnabled;
    private final boolean entityCullingEnabled;
    private final boolean blockEntityOptimizationEnabled;
    private final boolean fastChestRenderingEnabled;

    public BrowserPerformanceConfig(
            int targetFps,
            int inactiveFps,
            int idleWindowMs,
            float entityCullingDistance,
            float blockEntityRenderDistance,
            boolean dynamicFpsEnabled,
            boolean entityCullingEnabled,
            boolean blockEntityOptimizationEnabled,
            boolean fastChestRenderingEnabled
    ) {
        this.targetFps = targetFps;
        this.inactiveFps = inactiveFps;
        this.idleWindowMs = idleWindowMs;
        this.entityCullingDistance = entityCullingDistance;
        this.blockEntityRenderDistance = blockEntityRenderDistance;
        this.dynamicFpsEnabled = dynamicFpsEnabled;
        this.entityCullingEnabled = entityCullingEnabled;
        this.blockEntityOptimizationEnabled = blockEntityOptimizationEnabled;
        this.fastChestRenderingEnabled = fastChestRenderingEnabled;
    }

    public static BrowserPerformanceConfig defaultConfig() {
        return new BrowserPerformanceConfig(
                Defaults.TARGET_FPS,
                Defaults.INACTIVE_FPS,
                Defaults.IDLE_WINDOW_MS,
                Defaults.ENTITY_CULLING_DISTANCE,
                Defaults.BLOCK_ENTITY_RENDER_DISTANCE,
                Defaults.DYNAMIC_FPS_ENABLED,
                Defaults.ENTITY_CULLING_ENABLED,
                Defaults.BLOCK_ENTITY_OPTIMIZATION_ENABLED,
                Defaults.FAST_CHEST_RENDERING_ENABLED
        );
    }

    public int getTargetFps() {
        return targetFps;
    }

    public int getInactiveFps() {
        return inactiveFps;
    }

    public int getIdleWindowMs() {
        return idleWindowMs;
    }

    public float getEntityCullingDistance() {
        return entityCullingDistance;
    }

    public float getBlockEntityRenderDistance() {
        return blockEntityRenderDistance;
    }

    public boolean isDynamicFpsEnabled() {
        return dynamicFpsEnabled;
    }

    public boolean isEntityCullingEnabled() {
        return entityCullingEnabled;
    }

    public boolean isBlockEntityOptimizationEnabled() {
        return blockEntityOptimizationEnabled;
    }

    public boolean isFastChestRenderingEnabled() {
        return fastChestRenderingEnabled;
    }
}
