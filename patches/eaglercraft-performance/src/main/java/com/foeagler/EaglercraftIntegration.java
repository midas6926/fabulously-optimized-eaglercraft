package com.foeagler;

public final class EaglercraftIntegration {
    private static final BrowserPerformanceConfig CONFIG = BrowserPerformanceConfig.defaultConfig();
    private static final DynamicFpsLimiter FPS_LIMITER = new DynamicFpsLimiter(CONFIG);
    private static final EntityCullingManager ENTITY_CULLER = new EntityCullingManager(CONFIG);
    private static final BlockEntityOptimizer BLOCK_ENTITY_OPTIMIZER = new BlockEntityOptimizer(CONFIG);
    private static final FastChestRenderer CHEST_RENDERER = new FastChestRenderer(CONFIG);

    private EaglercraftIntegration() {
    }

    public static void onInputActivity() {
        FPS_LIMITER.markInput();
    }

    public static int getFrameBudgetMs() {
        return FPS_LIMITER.getFrameBudgetMs();
    }

    public static boolean shouldSkipEntity(
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
        return !ENTITY_CULLER.shouldRenderEntity(
                cameraX,
                cameraY,
                cameraZ,
                entityMinX,
                entityMinY,
                entityMinZ,
                entityMaxX,
                entityMaxY,
                entityMaxZ
        );
    }

    public static boolean shouldSkipBlockEntity(
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
        return BLOCK_ENTITY_OPTIMIZER.shouldSkipRender(
                cameraX,
                cameraY,
                cameraZ,
                blockMinX,
                blockMinY,
                blockMinZ,
                blockMaxX,
                blockMaxY,
                blockMaxZ,
                isStatic,
                isVisible
        );
    }

    public static boolean shouldUseFastChestRender(boolean isChestOpen, boolean isAnimating, boolean isStatic, float brightness) {
        return CHEST_RENDERER.shouldUseFastRender(isChestOpen, isAnimating, isStatic, brightness);
    }
}