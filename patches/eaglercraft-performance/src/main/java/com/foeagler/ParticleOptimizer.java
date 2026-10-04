package com.foeagler;

import java.util.ArrayList;
import java.util.List;

/**
 * Reduces particle system overhead by culling distant particles
 * and batching updates.
 */
public final class ParticleOptimizer {
    private final BrowserPerformanceConfig config;
    private final List<ParticleData> particles;
    private static final float PARTICLE_CULL_DISTANCE = 64.0f;
    private static final int MAX_PARTICLES = 4096;

    public static final class ParticleData {
        public double x, y, z;
        public double vx, vy, vz;
        public float age;
        public float maxAge;
        public float scale;

        public ParticleData(double x, double y, double z, float scale) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.scale = scale;
            this.age = 0;
            this.maxAge = 1;
        }
    }

    public ParticleOptimizer(BrowserPerformanceConfig config) {
        this.config = config;
        this.particles = new ArrayList<>(1024);
    }

    public void addParticle(double x, double y, double z, float scale) {
        if (particles.size() < MAX_PARTICLES) {
            particles.add(new ParticleData(x, y, z, scale));
        }
    }

    public void updateParticles(double cameraX, double cameraY, double cameraZ, float dt) {
        int removeIndex = 0;
        for (int i = 0; i < particles.size(); i++) {
            ParticleData p = particles.get(i);
            p.age += dt;

            if (p.age >= p.maxAge) {
                continue; // Mark for removal
            }

            double dx = p.x - cameraX;
            double dy = p.y - cameraY;
            double dz = p.z - cameraZ;
            double distSq = dx * dx + dy * dy + dz * dz;

            if (distSq <= PARTICLE_CULL_DISTANCE * PARTICLE_CULL_DISTANCE) {
                if (removeIndex < i) {
                    particles.set(removeIndex, p);
                }
                removeIndex++;
            }
        }

        while (particles.size() > removeIndex) {
            particles.remove(particles.size() - 1);
        }
    }

    public int getParticleCount() {
        return particles.size();
    }
}
