package com.foeagler;

public final class ComprehensiveOptimizationManager {
    private final FrameBudgetManager frameBudget;
    private final EntityCullingFast entityCuller;
    private final ChunkUpdateBudget chunkBudget;
    private final RenderStateDeduplicator renderState;
    private final NoAllocRenderQueue renderQueue;
    private final FloatArrayPool floatPool;
    private final IntArrayPool intPool;
    private final ChunkMeshCache meshCache;
    private final WorldTickOptimizer worldTick;
    private final PerformanceMetrics metrics;
    private final MemoryAllocOptimizer memAllocTracker;
    private final LightingOptimizer lightingOptimizer;
    private final ParticleOptimizer particleOptimizer;
    private final TextureAtlasOptimizer textureOptimizer;

    public ComprehensiveOptimizationManager(BrowserPerformanceConfig config) {
        this.frameBudget = new FrameBudgetManager(config.getTargetFps(), config.getInactiveFps(), config.getIdleWindowMs());
        this.entityCuller = new EntityCullingFast(config.getEntityCullingDistance());
        this.chunkBudget = new ChunkUpdateBudget(Math.max(2, config.getTargetFps() / 10));
        this.renderState = new RenderStateDeduplicator();
        this.renderQueue = new NoAllocRenderQueue();
        this.floatPool = new FloatArrayPool();
        this.intPool = new IntArrayPool();
        this.meshCache = new ChunkMeshCache();
        this.worldTick = new WorldTickOptimizer(config);
        this.metrics = new PerformanceMetrics();
        this.memAllocTracker = new MemoryAllocOptimizer(config);
        this.lightingOptimizer = new LightingOptimizer(config);
        this.particleOptimizer = new ParticleOptimizer(config);
        this.textureOptimizer = new TextureAtlasOptimizer(config);
    }

    public void onFrameStart() {
        metrics.startFrame();
        renderState.resetFrame();
        renderQueue.reset();
        memAllocTracker.resetFrameAllocations();
    }

    public void onFrameEnd() {
        metrics.endFrame();
    }

    public void onInputEvent() {
        frameBudget.onInput();
    }

    public int getFrameBudgetMs() {
        return frameBudget.getFrameBudgetMs();
    }

    public boolean isIdle() {
        return frameBudget.isIdle();
    }

    public boolean shouldRenderEntity(double camX, double camY, double camZ, double entX, double entY, double entZ) {
        boolean result = entityCuller.shouldRender(camX, camY, camZ, entX, entY, entZ);
        metrics.recordEntityRender(!result);
        return result;
    }

    public void queueChunkUpdate(int x, int y, int z, boolean priority) {
        chunkBudget.queue(x, y, z, priority);
    }

    public ChunkUpdateBudget.ChunkPos pollChunkUpdate() {
        return chunkBudget.pollNext();
    }

    public int getChunkUpdateBudget() {
        return chunkBudget.getMaxPerFrame();
    }

    public boolean setRenderBlendMode(int mode) {
        return renderState.setBlendMode(mode);
    }

    public boolean setRenderDepthMode(int mode) {
        return renderState.setDepthMode(mode);
    }

    public boolean setRenderTexture(int textureId) {
        return renderState.setTextureBind(textureId);
    }

    public boolean setRenderCullMode(int mode) {
        return renderState.setCullMode(mode);
    }

    public NoAllocRenderQueue getRenderQueue() {
        return renderQueue;
    }

    public float[] acquireFloatArray(int size) {
        memAllocTracker.recordAllocation();
        return floatPool.acquire(size);
    }

    public void releaseFloatArray(float[] arr) {
        floatPool.release(arr);
    }

    public int[] acquireIntArray(int size) {
        memAllocTracker.recordAllocation();
        return intPool.acquire(size);
    }

    public void releaseIntArray(int[] arr) {
        intPool.release(arr);
    }

    public boolean isMeshCached(int x, int y, int z, long hash) {
        return meshCache.isCached(x, y, z, hash);
    }

    public void cacheMesh(int x, int y, int z, long hash) {
        meshCache.cache(x, y, z, hash);
    }

    public boolean shouldTickWorld() {
        return worldTick.shouldTickWorld();
    }

    public PerformanceMetrics getMetrics() {
        return metrics;
    }

    public String getPerformanceReport() {
        return metrics.getReport();
    }
}
