package com.foeagler;

public final class ChunkMeshCache {
    private static final int CACHE_SIZE = 4096;
    private final long[] chunkKeys;
    private final long[] meshHashes;
    private final int position;

    public ChunkMeshCache() {
        this.chunkKeys = new long[CACHE_SIZE];
        this.meshHashes = new long[CACHE_SIZE];
        this.position = 0;
        java.util.Arrays.fill(chunkKeys, -1L);
    }

    public long getChunkKey(int x, int y, int z) {
        return ((long) x << 40) | ((long) y << 20) | z;
    }

    public boolean isCached(int x, int y, int z, long meshHash) {
        long key = getChunkKey(x, y, z);
        for (int i = 0; i < CACHE_SIZE; i++) {
            if (chunkKeys[i] == key && meshHashes[i] == meshHash) {
                return true;
            }
        }
        return false;
    }

    public void cache(int x, int y, int z, long meshHash) {
        long key = getChunkKey(x, y, z);
        int idx = Math.abs((int) ((key ^ meshHash) % CACHE_SIZE));
        chunkKeys[idx] = key;
        meshHashes[idx] = meshHash;
    }

    public void clear() {
        java.util.Arrays.fill(chunkKeys, -1L);
        java.util.Arrays.fill(meshHashes, 0L);
    }
}
