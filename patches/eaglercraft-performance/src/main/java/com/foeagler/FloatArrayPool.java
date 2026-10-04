package com.foeagler;

import java.util.ArrayDeque;
import java.util.Deque;

public final class FloatArrayPool {
    private final Deque<float[]> arrays64 = new ArrayDeque<>();
    private final Deque<float[]> arrays256 = new ArrayDeque<>();
    private final Deque<float[]> arrays1024 = new ArrayDeque<>();
    private static final int POOL_SIZE = 64;

    public float[] acquire(int size) {
        if (size <= 64) {
            float[] arr = arrays64.pollFirst();
            return arr != null ? arr : new float[64];
        } else if (size <= 256) {
            float[] arr = arrays256.pollFirst();
            return arr != null ? arr : new float[256];
        } else if (size <= 1024) {
            float[] arr = arrays1024.pollFirst();
            return arr != null ? arr : new float[1024];
        }
        return new float[size];
    }

    public void release(float[] arr) {
        if (arr == null) return;
        if (arr.length == 64 && arrays64.size() < POOL_SIZE) {
            java.util.Arrays.fill(arr, 0);
            arrays64.addLast(arr);
        } else if (arr.length == 256 && arrays256.size() < POOL_SIZE) {
            java.util.Arrays.fill(arr, 0);
            arrays256.addLast(arr);
        } else if (arr.length == 1024 && arrays1024.size() < POOL_SIZE) {
            java.util.Arrays.fill(arr, 0);
            arrays1024.addLast(arr);
        }
    }
}
