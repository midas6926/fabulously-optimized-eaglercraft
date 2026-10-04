package com.foeagler;

public final class RenderStateDeduplicator {
    private int lastBlendMode = -1;
    private int lastDepthMode = -1;
    private int lastTextureBind = -1;
    private int lastCullMode = -1;
    private int lastAlphaMode = -1;
    private boolean lastLighting = false;
    private boolean lastFog = false;
    private int stateChanges = 0;
    private int skippedChanges = 0;

    public boolean setBlendMode(int mode) {
        if (lastBlendMode == mode) {
            skippedChanges++;
            return false;
        }
        lastBlendMode = mode;
        stateChanges++;
        return true;
    }

    public boolean setDepthMode(int mode) {
        if (lastDepthMode == mode) {
            skippedChanges++;
            return false;
        }
        lastDepthMode = mode;
        stateChanges++;
        return true;
    }

    public boolean setTextureBind(int textureId) {
        if (lastTextureBind == textureId) {
            skippedChanges++;
            return false;
        }
        lastTextureBind = textureId;
        stateChanges++;
        return true;
    }

    public boolean setCullMode(int mode) {
        if (lastCullMode == mode) {
            skippedChanges++;
            return false;
        }
        lastCullMode = mode;
        stateChanges++;
        return true;
    }

    public boolean setAlphaMode(int mode) {
        if (lastAlphaMode == mode) {
            skippedChanges++;
            return false;
        }
        lastAlphaMode = mode;
        stateChanges++;
        return true;
    }

    public boolean setLighting(boolean enabled) {
        if (lastLighting == enabled) {
            skippedChanges++;
            return false;
        }
        lastLighting = enabled;
        stateChanges++;
        return true;
    }

    public boolean setFog(boolean enabled) {
        if (lastFog == enabled) {
            skippedChanges++;
            return false;
        }
        lastFog = enabled;
        stateChanges++;
        return true;
    }

    public void resetFrame() {
        stateChanges = 0;
        skippedChanges = 0;
    }

    public int getStateChanges() {
        return stateChanges;
    }

    public int getSkippedChanges() {
        return skippedChanges;
    }
}
