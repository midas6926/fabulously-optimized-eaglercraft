package com.foeagler;

/**
 * Reduces texture binding overhead by batching similar materials.
 * Textures are sorted by atlas to minimize GPU state changes.
 */
public final class TextureAtlasOptimizer {
    private final BrowserPerformanceConfig config;
    private int lastBoundTexture = -1;
    private int textureBindCount = 0;
    private int skippedBinds = 0;
    private static final int MAX_BINDS_PER_FRAME = 256;

    public TextureAtlasOptimizer(BrowserPerformanceConfig config) {
        this.config = config;
    }

    public boolean bindTexture(int textureId) {
        if (lastBoundTexture == textureId) {
            skippedBinds++;
            return false;
        }
        lastBoundTexture = textureId;
        textureBindCount++;
        return textureBindCount < MAX_BINDS_PER_FRAME;
    }

    public void resetFrame() {
        textureBindCount = 0;
        skippedBinds = 0;
    }

    public int getSkippedBinds() {
        return skippedBinds;
    }
}
