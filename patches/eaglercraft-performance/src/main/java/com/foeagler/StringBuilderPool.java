package com.foeagler;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Object pool specifically for StringBuilder to reduce string allocation overhead.
 * Strings are heavily used in rendering and chat, so this is a critical optimization.
 */
public final class StringBuilderPool {
    private static final Deque<StringBuilder> POOL = new ArrayDeque<>(64);
    private static final int MAX_POOL_SIZE = 128;
    private static final int MAX_BUILDER_CAPACITY = 4096;

    public static StringBuilder acquire() {
        StringBuilder sb = POOL.pollFirst();
        if (sb != null) {
            sb.setLength(0);
            return sb;
        }
        return new StringBuilder();
    }

    public static void release(StringBuilder sb) {
        if (sb == null) return;
        if (POOL.size() >= MAX_POOL_SIZE) return;
        if (sb.capacity() > MAX_BUILDER_CAPACITY) return;
        sb.setLength(0);
        POOL.addLast(sb);
    }
}
