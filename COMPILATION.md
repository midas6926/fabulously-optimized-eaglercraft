# Compilation Guide for Fabulously Optimized Eaglercraft

This guide explains how to compile the browser Eaglercraft client with the optimization layer integrated.

## Prerequisites

- Java 17+ (for Eaglercraft 26.2)
- Maven 3.8+
- Git
- Node.js 16+ (for web build)

## Setup in GitHub Codespaces

### 1. Clone the main Eaglercraft client repo

```bash
git clone https://github.com/LAX1DUDE/eaglercraft.git
cd eaglercraft
```

### 2. Copy optimization patches

```bash
cp -r /path/to/fabulously-optimized-eaglercraft/patches/eaglercraft-performance/src/main/java/com/foeagler/* src/main/java/com/foeagler/
```

### 3. Integrate into the client main loop

Edit `src/main/java/net/minecraft/client/Minecraft.java` and add:

```java
import com.foeagler.ComprehensiveOptimizationManager;
import com.foeagler.BrowserPerformanceConfig;

public class Minecraft {
    private static ComprehensiveOptimizationManager optimizationManager;

    static {
        optimizationManager = new ComprehensiveOptimizationManager(
            BrowserPerformanceConfig.defaultConfig()
        );
    }

    public void tick() {
        optimizationManager.onFrameStart();

        // ... existing code ...

        // Track input
        if (this.mouse.isMouseDown() || this.keyboard.isKeyDown(0)) {
            optimizationManager.onInputEvent();
        }

        // Skip world tick if idle
        if (!optimizationManager.isIdle()) {
            // ... existing world tick code ...
        }

        optimizationManager.onFrameEnd();
    }

    public void render() {
        optimizationManager.onFrameStart();
        // ... existing render code ...
        optimizationManager.onFrameEnd();
    }
}
```

### 4. Compile with Maven

```bash
mvn clean package -DskipTests -Pbuild-web
```

Or for faster compilation without tests:

```bash
mvn clean compile -Pbuild-web
```

### 5. Build the TeaVM WASM binary

```bash
mvn teavm:build -Pbuild-web
```

### 6. Package the standalone HTML

```bash
mvn package -Pbuild-web
```

Output will be in `target/teavm-output/` or similar.

## Complete Codespaces command

```bash
# Install dependencies
sudo apt-get update && sudo apt-get install -y openjdk-17-jdk maven nodejs

# Clone Eaglercraft
git clone https://github.com/LAX1DUDE/eaglercraft.git
cd eaglercraft

# Copy the optimization layer
mkdir -p src/main/java/com/foeagler
cp ../fabulously-optimized-eaglercraft/patches/eaglercraft-performance/src/main/java/com/foeagler/*.java src/main/java/com/foeagler/

# Build the client
mvn clean package -DskipTests -Pbuild-web

# Output is in target/teavm-output/
echo "Build complete! Check target/teavm-output/"
```

## Direct compilation in Codespaces

```bash
#!/bin/bash
set -e

echo "Installing Java 17..."
sudo apt-get update
sudo apt-get install -y openjdk-17-jdk maven git nodejs npm

echo "Cloning Eaglercraft..."
git clone --depth 1 https://github.com/LAX1DUDE/eaglercraft.git eaglercraft-build
cd eaglercraft-build

echo "Setting up optimization layer..."
mkdir -p src/main/java/com/foeagler
cat > src/main/java/com/foeagler/BrowserPerformanceConfig.java << 'EOF'
# Paste BrowserPerformanceConfig.java content here
EOF

echo "Building TeaVM WASM client..."
mvn clean package -DskipTests -Pbuild-web -T 1C

echo "Build complete!"
ls -lh target/teavm-output/
```

## Quick test after build

```bash
# Start a local web server
cd target/teavm-output/
python3 -m http.server 8000

# Open http://localhost:8000 in your browser
```

## Build troubleshooting

### Out of memory

Increase Maven heap:

```bash
export MAVEN_OPTS="-Xmx4g"
mvn clean package -Pbuild-web
```

### Cannot find TeaVM

Make sure the correct Maven profile is active:

```bash
mvn help:active-profiles
mvn clean package -Pbuild-web
```

### Compilation takes too long

Use parallel compilation:

```bash
mvn clean package -DskipTests -Pbuild-web -T 2C
```

### JAR file issues

Clear Maven cache:

```bash
rm -rf ~/.m2/repository
mvn clean package -Pbuild-web
```

## Integration checklist

- [ ] Copied all `.java` files from `patches/eaglercraft-performance/src/main/java/com/foeagler/` to your Eaglercraft `src/main/java/com/foeagler/`
- [ ] Added `ComprehensiveOptimizationManager` singleton to `Minecraft.java`
- [ ] Called `optimizationManager.onFrameStart()` and `optimizationManager.onFrameEnd()` in main tick loop
- [ ] Called `optimizationManager.onInputEvent()` in input handlers
- [ ] Integrated entity culling checks before rendering entities
- [ ] Integrated chunk update budgeting
- [ ] Integrated render state deduplication
- [ ] Built with Maven and TeaVM

## Output structure

After successful build:

```
target/teavm-output/
├── index.html          # Main HTML file
├── game.wasm           # WebAssembly binary
├── game.js             # JavaScript runtime
└── assets/             # Textures, sounds, etc.
```

You can serve this entire directory with any web server.
