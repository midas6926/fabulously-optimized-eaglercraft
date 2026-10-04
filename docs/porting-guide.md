# Porting guide

This guide explains how to turn the Fabulously Optimized philosophy into a browser Eaglercraft optimization pass.

## 1. Start from the real problem

FO is not just a set of visual tweaks. It is a collection of optimization patterns for the desktop Minecraft client:

- reduce draw calls
- cull hidden objects
- avoid busy background work
- reduce alloc churn
- keep the game responsive when focus changes

The browser client should receive the same treatment, but without Fabric or modloader compatibility.

## 2. Implement optimization primitives

The most effective porting targets are what we call optimization primitives:

- `DynamicFpsLimiter` for background idle frames
- `EntityCullingManager` for camera/frustum visibility checks
- `BlockEntityOptimizer` for render throttling and batching
- `FastChestRenderer` for simplified static chest rendering
- `BrowserPerformanceConfig` for the settings profile

These primitives should be wired into the client tick and render pipeline.

## 3. Wire the patch into the client lifecycle

Recommended integration points:

- input handler: update last input timestamp
- main game loop: compute effective FPS budget
- render pass: skip off-screen entities and block entities
- chunk/block entity pass: simplify rendering for static objects
- render scheduler: skip expensive updates when frame budget is low

## 4. Keep the browser constraints in mind

Browser Eaglercraft pipelines have hard constraints:

- no standard JVM file access for mods
- no Fabric launcher lifecycle
- no arbitrary native library loading
- tighter memory and animation budgets than desktop Java

So the optimization patch must be conservative, deterministic, and browser-safe.

## 5. What to avoid

Avoid trying to transplant the modpack itself. That will fail because the project is not a modloader-compatible runtime.

Do not attempt to include:

- Fabric API dependencies
- desktop-only shader graphs
- desktop-specific GL extensions
- modloader file injection logic
- packaging scripts that only work for CurseForge / Modrinth

## 6. Next milestone

A realistic browser Eaglercraft version of FO should achieve:

- less per-frame work when idle
- lower draw count in crowded scenes
- reduced entity render cost while not visible
- stable performance on low-end browsers
- no direct reliance on Fabric or desktop Java features
