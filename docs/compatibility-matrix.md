# Compatibility matrix

This file documents the practical mapping from Fabulously Optimized (desktop Fabric modpack) to browser Eaglercraft.

## Summary

| FO category | Typical mod(s) | Browser Eaglercraft status | Notes |
| --- | --- | --- | --- |
| Dynamic FPS | Dynamic FPS | Ported as prototype | Implemented in `DynamicFpsLimiter` |
| Entity culling | Entity Culling, MoreCulling | Ported as prototype | Implemented in `EntityCullingManager` |
| Block entity rendering | Better Block Entities, Enhanced Block Entities | Ported as prototype | Implemented in `BlockEntityOptimizer` |
| Chest optimization | FastChest | Ported as prototype | Implemented in `FastChestRenderer` |
| Loading/profile optimizations | Smooth Boot, LazyDFU | Partially feasible | Requires browser-safe startup sequencing |
| Memory optimization | FerriteCore, Lithium | Partially feasible | Requires careful integration in Eaglercraft's world logic |
| Shader / visuals | Iris, Sodium, Continuity | Not ported | Desktop/OpenGL-specific |
| Fabric ecosystem | Fabric API, Mod Menu, etc. | Not ported | Not possible in Eaglercraft |
| Functional utility mods | Zoomify, Controlify, etc. | Not ported by default | Usually platform-specific |

## Ported subset

The following ideas from FO were selected for browser Eaglercraft adaptation:

- Frame budget management for inactive or background sessions
- Camera-frustum entity visibility checks
- Simplified block entity render passes for static or low-impact blocks
- Reduced render path for chests and similar high-volume block entities
- Tracking of frame time and render budget to keep the browser client stable

## Not viable to port directly

These are rejected as direct port targets because they require the Fabric mod ecosystem or non-browser runtime assumptions:

- Sodium
- Iris
- Indium
- Fabric API
- Mod Menu
- most functional mods that only exist in the Fabric Java ecosystem

## Engineering guidance

When porting FO to browser Eaglercraft, treat the browser client as a single, custom runtime. Recreate the optimization behavior in the Eaglercraft client source rather than injecting mod jars or trying to load the Fabric modpack directly.

This project is deliberately limited to the subset that can be honestly implemented by a directly patched browser client.
