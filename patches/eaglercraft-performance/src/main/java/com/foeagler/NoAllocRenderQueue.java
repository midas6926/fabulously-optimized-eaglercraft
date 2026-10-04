package com.foeagler;

import java.util.ArrayDeque;
import java.util.Deque;

public final class NoAllocRenderQueue {
    private final int[] commandBuffer;
    private final float[] dataBuffer;
    private int cmdIndex = 0;
    private int dataIndex = 0;
    private static final int MAX_COMMANDS = 65536;
    private static final int MAX_DATA = 262144;

    // Command codes
    public static final int CMD_DRAW_VERTEX_ARRAY = 1;
    public static final int CMD_DRAW_INDEXED = 2;
    public static final int CMD_SET_BLEND = 3;
    public static final int CMD_SET_DEPTH = 4;
    public static final int CMD_BIND_TEXTURE = 5;
    public static final int CMD_SET_MATRIX = 6;

    public NoAllocRenderQueue() {
        this.commandBuffer = new int[MAX_COMMANDS];
        this.dataBuffer = new float[MAX_DATA];
    }

    public void drawVertexArray(int vertexCount, int firstVertex) {
        if (cmdIndex + 3 > MAX_COMMANDS) {
            return; // Drop command
        }
        commandBuffer[cmdIndex++] = CMD_DRAW_VERTEX_ARRAY;
        commandBuffer[cmdIndex++] = vertexCount;
        commandBuffer[cmdIndex++] = firstVertex;
    }

    public void drawIndexed(int indexCount, int firstIndex) {
        if (cmdIndex + 3 > MAX_COMMANDS) {
            return;
        }
        commandBuffer[cmdIndex++] = CMD_DRAW_INDEXED;
        commandBuffer[cmdIndex++] = indexCount;
        commandBuffer[cmdIndex++] = firstIndex;
    }

    public void setBlendMode(int mode) {
        if (cmdIndex + 2 > MAX_COMMANDS) {
            return;
        }
        commandBuffer[cmdIndex++] = CMD_SET_BLEND;
        commandBuffer[cmdIndex++] = mode;
    }

    public void setDepthMode(int mode) {
        if (cmdIndex + 2 > MAX_COMMANDS) {
            return;
        }
        commandBuffer[cmdIndex++] = CMD_SET_DEPTH;
        commandBuffer[cmdIndex++] = mode;
    }

    public void bindTexture(int textureId) {
        if (cmdIndex + 2 > MAX_COMMANDS) {
            return;
        }
        commandBuffer[cmdIndex++] = CMD_BIND_TEXTURE;
        commandBuffer[cmdIndex++] = textureId;
    }

    public void reset() {
        cmdIndex = 0;
        dataIndex = 0;
    }

    public int getCommandCount() {
        return cmdIndex;
    }

    public int[] getCommandBuffer() {
        return commandBuffer;
    }

    public float[] getDataBuffer() {
        return dataBuffer;
    }
}
