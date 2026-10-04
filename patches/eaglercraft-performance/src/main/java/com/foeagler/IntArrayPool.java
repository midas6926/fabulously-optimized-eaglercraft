package com.foeagler;

import java.util.ArrayDeque;
import java.util.Deque;

public final class IntArrayPool {
    private final Deque<int[]> arrays64 = new ArrayDeque<>();
    private final Deque<int[]> arrays256 = new ArrayDeque<>();
    private final Deque<int[]> arrays1024 = new ArrayDeque<>();
    private static final int POOL_SIZE = 64;

    public int[] acquire(int size) {
        if (size <= 64) {
            int[] arr = arrays64.pollFirst();
            return arr != null ? arr : new int[64];
        } else if (size <= 256) {
            int[] arr = arrays256.pollFirst();
            return arr != null ? arr : new int[256];
        } else if (size <= 1024) {
            int[] arr = arrays1024.pollFirst();
            return arr != null ? arr : new int[1024];
        }
        return new int[size];
    }

    public void release(int[] arr) {
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
