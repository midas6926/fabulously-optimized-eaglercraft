# Fabulously Optimized for Eaglercraft

This repository is the browser-port subset of Fabulously Optimized, adapted for Eaglercraft 26.2 instead of a direct Fabric modpack port.

Important: this is not a blind port of the modpack as-is. Fabulously Optimized is a Fabric modpack and depends on the Fabric mod ecosystem, desktop Java runtime behavior, and JVM-specific assumptions that do not exist in a browser Eaglercraft target.

This project focuses on the practical subset of FO that can be recreated as Eaglercraft client optimizations:

- Dynamic FPS limiting
- Entity culling
- Faster block entity rendering
- Chest rendering simplification
- Reduced render churn and garbage pressure
- Browser-safe GPU and update scheduling patterns

## Scope of this project

The goal is to recreate the performance philosophy of Fabulously Optimized in a browser client without trying to load Fabric mods or port the entire modpack.

This project intentionally ignores:

- Fabric-only mods
- shader ecosystems like Iris
- desktop-only rendering features
- launcher-level pack composition
- any feature that depends on Java modloader compatibility

## Included optimizations

The repository currently includes prototype Java patches for:

- `DynamicFpsLimiter`
- `EntityCullingManager`
- `BlockEntityOptimizer`
- `FastChestRenderer`
- `BrowserPerformanceConfig`

## Repo layout

```text
README.md

docs/
  compatibility-matrix.md
  porting-guide.md

patches/
  eaglercraft-performance/
    src/main/java/com/foeagler/
      BrowserPerformanceConfig.java
      DynamicFpsLimiter.java
      EntityCullingManager.java
      BlockEntityOptimizer.java
      FastChestRenderer.java
```

## Why this exists

The Fabulously Optimized modpack is an optimization-focused set of Fabric mods for the desktop Minecraft stack. Browser Eaglercraft is not a Fabric runtime and cannot load those mods without rebuilding the client itself.

The correct Eaglercraft adaptation is therefore:

1. Reimplement the useful optimization ideas directly in Eaglercraft's Java source.
2. Keep only the features that map cleanly to the browser environment.
3. Exclude the parts that require the Fabric ecosystem.

## Current implementation status

This repository is a patch-ready prototype set for browser Eaglercraft optimization work.

Status:

- Dynamic FPS limiting: implemented as a prototype helper
- Entity culling: implemented as a prototype helper
- Block entity batching: implemented as a prototype helper
- Fast chest rendering: implemented as a prototype helper
- Config/defaults: implemented as configuration object
- Integration into a full Eaglercraft build: still project-level work

## Recommended next step

Integrate these classes into the Eaglercraft client source path where the frame loop and world rendering occur, especially:

- the main client tick loop
- the render/update scheduler
- the entity and block entity render paths
- the world/chunk draw call path

## License

This repository is a compatibility project for browser Eaglercraft optimization experiments. It is intended for project-specific porting work and not a direct re-distribution of the Fabulously Optimized pack.

See `docs/compatibility-matrix.md` for the mapping between FO mods and what was actually ported to the browser client model.
