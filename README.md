# Max Momentum

[![License: AGPL-3.0](https://img.shields.io/badge/License-AGPL%203.0-blue.svg)](LICENSE)
[![Java 25](https://img.shields.io/badge/Java-25-orange.svg)](https://openjdk.org/)
[![Minestom](https://img.shields.io/badge/Minestom-1.21.11-green.svg)](https://minestom.net/)

A high-performance, scalable Minecraft server platform built on Minestom with a microservices architecture. Features SkyBlock, BedWars, and SkyWars game modes with distributed services for auctions, bazaar, parties, and more.

> **⚠️ Development Status**: This project is under active development and not yet production-ready. Expect breaking changes and incomplete features.

---

## 📋 Table of Contents

- [Features](#-features)
- [Architecture](#-architecture)
- [Requirements](#-requirements)
  - [Potato PC Mode (4GB RAM)](#minimum-specifications-potato-pc-mode)
- [Quick Start](#-quick-start)
- [Installation](#-installation)
  - [Docker Deployment](#docker-deployment-recommended)
  - [Manual Setup](#manual-setup)
- [Configuration](#-configuration)
- [Game Modes](#-game-modes)
- [Services](#-services)
- [Development](#-development)
- [Project Structure](#-project-structure)
- [Performance Tuning](#-performance-tuning)
- [Troubleshooting](#-troubleshooting)
- [Contributing](#-contributing)
- [License](#-license)

---

## ✨ Features

### Core Platform
- **🚀 Modern Java 25** - Leverages virtual threads and latest JVM optimizations
- **⚡ Minestom-Based** - Lightweight, high-performance server framework (not Spigot/Paper)
- **🔄 Microservices Architecture** - Distributed, scalable service design
- **🌐 Multi-Server Support** - 40+ server types across multiple game modes
- **📦 Docker Ready** - Full containerization with Docker Compose
- **🔌 Hot-Reload** - Configuration changes without server restart

### Infrastructure
- **Velocity Proxy** - Modern proxy with player routing and load balancing
- **Redis Pub/Sub** - Real-time inter-service communication
- **MongoDB** - Persistent player data, profiles, and game state
- **ViaVersion** - Cross-version support (1.8 - 1.21)
- **Sentry Integration** - Error tracking and performance monitoring

### Game Modes
- **SkyBlock** - Full-featured skyblock with 14+ islands, skills, collections, minions
- **BedWars** - Team-based bed defense game with custom maps
- **SkyWars** - Solo/team skywars with lucky blocks and custom kits

### Developer Features
- **Code Generation** - Auto-generated item types from YAML configs
- **Reflection-Based Loading** - Dynamic event, command, and NPC registration
- **Extensible Plugin System** - Easy to add new game modes and features
- **Comprehensive Logging** - Detailed debug information with Sentry integration

---

## 🏗️ Architecture

Max Momentum uses a distributed microservices architecture for scalability and fault tolerance:

```
┌─────────────────────────────────────────────────────────────┐
│                      Velocity Proxy                         │
│              (Load Balancing & Routing)                     │
└─────────────────────────────────────────────────────────────┘
                            │
        ┌───────────────────┼───────────────────┐
        │                   │                   │
┌───────▼────────┐  ┌──────▼──────┐  ┌────────▼────────┐
│  Game Servers  │  │  Services   │  │  Infrastructure │
├────────────────┤  ├─────────────┤  ├─────────────────┤
│ • Hub          │  │ • Auctions  │  │ • MongoDB       │
│ • Islands      │  │ • Bazaar    │  │ • Redis         │
│ • Farming      │  │ • Party     │  │ • PicoLimbo     │
│ • Mining       │  │ • Friends   │  └─────────────────┘
│ • BedWars      │  │ • API       │
│ • SkyWars      │  │ • Tracker   │
│ • Lobbies      │  │ • Mutex     │
└────────────────┘  └─────────────┘
```

### Communication Flow
1. **Player Connection** → Velocity Proxy authenticates and routes
2. **Game Server** → Handles gameplay, sends events via Redis
3. **Services** → Process events, update MongoDB, respond via Redis
4. **Cross-Server** → Redis pub/sub for real-time synchronization

---

## 💻 Requirements

### Recommended Specifications
- **RAM**: 16GB (8GB for services, 8GB for game servers)
- **CPU**: 6+ cores (12+ recommended for production)
- **Storage**: 20GB SSD
- **OS**: Linux (Ubuntu 22.04+), Windows 10+, macOS 12+

### Minimum Specifications (Potato PC Mode)
- **RAM**: 4GB + 2GB swap
- **CPU**: 4 cores
- **Storage**: 10GB SSD (HDD will be very slow)
- **OS**: Linux (Ubuntu 22.04+), Windows 10+, macOS 12+
- **Players**: 20-30 max concurrent
- **Performance**: Acceptable with trade-offs (see below)

### Software Dependencies
- **Java 25** - [Download OpenJDK 25](https://jdk.java.net/25/)
- **MongoDB 8.0+** - [Installation Guide](https://www.mongodb.com/docs/manual/installation/)
- **Redis 7.0+** - [Installation Guide](https://redis.io/docs/getting-started/installation/)
- **Docker & Docker Compose** (for containerized deployment)

### Network Requirements
- **Port 25565** - Minecraft server (Velocity proxy)
- **Port 27017** - MongoDB (internal)
- **Port 6379** - Redis (internal)

---

## 🚀 Quick Start

### Got a Good PC? (16GB RAM)
```bash
git clone https://github.com/maximally0/max-momentum.git
cd max-momentum
docker-compose up --build
```

### Got a Potato PC? (4GB RAM)

**Linux/Mac (Easy Mode):**
```bash
git clone https://github.com/maximally0/max-momentum.git
cd max-momentum
chmod +x start-potato-pc.sh
./start-potato-pc.sh
```

**Windows (Easy Mode):**
```cmd
git clone https://github.com/maximally0/max-momentum.git
cd max-momentum
start-potato-pc.bat
```

**Manual Setup:**
```bash
# Enable swap first (Linux only - prevents crashes)
sudo fallocate -l 2G /swapfile
sudo chmod 600 /swapfile
sudo mkswap /swapfile
sudo swapon /swapfile

# Start optimized setup
docker-compose -f docker-compose.minimal.yml up --build
```

**Potato PC Trade-offs:** 20-30 players max, slower performance, more lag spikes. See [POTATO-PC-QUICK-START.md](POTATO-PC-QUICK-START.md) for full guide.

Server available at `localhost:25565` after ~2-3 minutes.

---

## 📦 Installation

### Prerequisites

**For Standard Setup (16GB RAM):**
- Java 25 - [Download OpenJDK 25](https://jdk.java.net/25/)
- Docker & Docker Compose
- 16GB RAM, 6+ CPU cores, 20GB SSD

**For Potato PC Setup (4GB RAM):**
- Java 25 - [Download OpenJDK 25](https://jdk.java.net/25/)
- Docker & Docker Compose
- 4GB RAM + 2GB swap, 4+ CPU cores, 10GB SSD
- ⚠️ SSD highly recommended (HDD will be very slow)

### Prerequisites Check
```bash
# Verify Java 25
java -version  # Should show "openjdk version 25"

# Verify Docker
docker --version
docker-compose --version

# Check available RAM
free -h  # Linux
wmic memorychip get capacity  # Windows
```

### Clone Repository
```bash
git clone https://github.com/maximally0/max-momentum.git
cd max-momentum
```

### Docker Deployment (Recommended)

#### 1. Configure Environment
```bash
# Copy example configuration
cp configuration/config.example.yml configuration/config.yml

# Edit configuration (optional)
nano configuration/config.yml
```

#### 2. Set Forwarding Secret
```bash
# Generate secure secret
export FORWARDING_SECRET=$(openssl rand -base64 32)

# Or set in docker-compose.yml
```

#### 3. Start Services
```bash
# Full deployment (all game modes) - Requires 16GB RAM
docker-compose up -d

# Minimal deployment (optimized) - Requires 8GB RAM
docker-compose -f docker-compose.minimal.yml up -d

# View logs
docker-compose logs -f

# Stop services
docker-compose down
```

#### 3b. Potato PC Setup (4GB RAM)

If you have limited RAM, follow these steps:

```bash
# 1. Enable swap (REQUIRED to prevent crashes)
sudo fallocate -l 2G /swapfile
sudo chmod 600 /swapfile
sudo mkswap /swapfile
sudo swapon /swapfile

# Make swap permanent (optional)
echo '/swapfile none swap sw 0 0' | sudo tee -a /etc/fstab

# 2. Reduce player limit
nano configuration/config.yml
# Change max-players to 30

# 3. Start optimized setup
docker-compose -f docker-compose.minimal.yml up -d

# 4. Monitor memory usage
docker stats
```

**Potato PC Trade-offs:**
- Player limit: 20-30 (vs 100+)
- Chunk loading: Slower
- Lag spikes: More frequent
- Heavy farms: Will cause lag
- Multiple simultaneous games: Not recommended

**When to upgrade:** If swap usage > 1.5GB or services crash daily, you need more RAM.

#### 4. Verify Deployment
```bash
# Check service health
docker-compose ps

# Test connection
telnet localhost 25565
```

### Manual Setup

#### 1. Install Dependencies
```bash
# Ubuntu/Debian
sudo apt update
sudo apt install openjdk-25-jdk mongodb redis-server

# macOS (Homebrew)
brew install openjdk@25 mongodb-community redis

# Windows
# Download and install manually from official websites
```

#### 2. Build Project
```bash
# Build all modules
./gradlew clean build

# Build specific module
./gradlew :type.hub:build
```

#### 3. Configure Services

**MongoDB Setup**:
```bash
# Start MongoDB
mongod --dbpath ./data/db

# Initialize database
mongosh < configuration/mongo-init.sh
```

**Redis Setup**:
```bash
# Start Redis
redis-server
```

#### 4. Start Velocity Proxy
```bash
cd velocity.extension/build/libs
java -jar velocity-extension.jar
```

#### 5. Start Game Servers
```bash
# Hub server
java -jar HypixelCore.jar SKYBLOCK_HUB

# Island server
java -jar HypixelCore.jar SKYBLOCK_ISLAND

# Additional servers as needed
```

#### 6. Start Services
```bash
# Auction house
java -jar ServiceAuctionHouse.jar

# Bazaar
java -jar ServiceBazaar.jar

# Party system
java -jar ServiceParty.jar

# Additional services as needed
```

---

## ⚙️ Configuration

### Main Configuration
Edit `configuration/config.yml`:

```yaml
# Server settings
server:
  name: "Max Momentum"
  motd: "§aMax Momentum §7| §bSkyBlock, BedWars, SkyWars"
  max-players: 1000

# Database
mongodb:
  host: "localhost"
  port: 27017
  database: "Minestom"
  
redis:
  host: "localhost"
  port: 6379

# Proxy
velocity:
  forwarding-secret: "your-secret-here"
```

### Docker Configuration
Edit `docker-compose.yml` for service-specific settings:

```yaml
environment:
  FORWARDING_SECRET: "change-me"  # Change this!
  JAVA_OPTS: "-Xmx4G -Xms2G"      # Memory allocation
```

### Game Mode Configuration
- **Items**: `configuration/skyblock/items/*.yml`
- **Collections**: `configuration/skyblock/collections/*.yml`
- **Quests**: `configuration/quests/*/`
- **Achievements**: `configuration/achievements/*/`
- **Maps**: `configuration/bedwars/maps.json`

### Internationalization
Add translations in `configuration/i18n/`:
- `en_US/` - English (default)
- `fi_FI/` - Finnish (example)

---

## 🎮 Game Modes

### SkyBlock
**14 Server Types** | **1000+ Items** | **Skills & Collections**

#### Islands
- **Hub** - Main spawn area with NPCs and shops
- **Private Island** - Player-owned customizable island
- **Farming Islands** - Crop farming and animal husbandry
- **Gold Mine** - Mining and ore collection
- **Deep Caverns** - Advanced mining with rare ores
- **Dwarven Mines** - Dwarven-themed mining area
- **Spider's Den** - Combat zone with spiders
- **The End** - End-game content
- **The Park** - Foraging and wood collection
- **Crimson Isle** - Nether-themed combat zone
- **Jerry's Workshop** - Seasonal event area
- **Dungeon Hub** - Dungeon entrance (WIP)

#### Features
- **Skills**: Combat, Mining, Foraging, Farming, Fishing, Enchanting, Alchemy, Runecrafting
- **Collections**: 100+ collectible items with tier rewards
- **Minions**: Automated resource gathering (20+ types)
- **Auction House**: Player-to-player trading
- **Bazaar**: Instant buy/sell marketplace
- **Museum**: Item collection and display
- **Quests**: 50+ missions with rewards
- **Achievements**: 100+ achievements across categories

### BedWars
**3 Server Types** | **Custom Maps** | **Team-Based**

- **Lobby** - Waiting area with kit selection
- **Game** - 4v4v4v4 bed defense gameplay
- **Configurator** - Map creation and testing

### SkyWars
**3 Server Types** | **Lucky Blocks** | **Solo/Teams**

- **Lobby** - Kit selection and matchmaking
- **Game** - Solo/team skywars with custom items
- **Configurator** - Map creation and testing

#### Lucky Block Items
- 100+ unique items from lucky blocks
- Custom weapons, armor, and consumables
- Environmental effects and mob spawns

---

## 🔧 Services

### Core Services

#### Auction House (`service.auctionhouse`)
- Player-to-player item auctions
- Bid tracking and notifications
- Auction expiration handling
- MongoDB storage for listings

#### Bazaar (`service.bazaar`)
- Instant buy/sell marketplace
- Order matching system
- Price history tracking
- Real-time price updates via Redis

#### Party System (`service.party`)
- Cross-server party management
- Party chat and invitations
- Leader controls and permissions
- Redis-based synchronization

#### Item Tracker (`service.itemtracker`)
- Tracks item ownership and transfers
- Duplication detection
- Item history logging
- Analytics and reporting

#### API Service (`service.api`)
- REST API for external integrations
- Player statistics endpoints
- Server status monitoring
- Webhook support

#### Data Mutex (`service.datamutex`)
- Distributed locking for player data
- Prevents data corruption
- Cross-server synchronization
- Redis-based lock management

#### Dark Auction (`service.darkauction`)
- Special auction events
- Rare item sales
- Scheduled auctions
- Broadcast notifications

#### Friend System (`service.friend`)
- Friend list management
- Online status tracking
- Friend requests and notifications
- Cross-server messaging

#### Punishment System (`service.punishment`)
- Ban and mute management
- Temporary and permanent punishments
- Appeal system
- Punishment history

### Service Communication
All services communicate via Redis pub/sub:
```
Game Server → Redis → Service → MongoDB → Redis → Game Server
```

---

## 🛠️ Development

### Building from Source

```bash
# Clone repository
git clone https://github.com/maximally0/max-momentum.git
cd max-momentum

# Build all modules
./gradlew clean build

# Build specific module
./gradlew :type.hub:build

# Run tests
./gradlew test

# Generate Javadocs
./gradlew javadoc
```

### Project Structure
```
max-momentum/
├── anticheat/              # Anti-cheat system (MIT license)
├── commons/                # Shared utilities and data structures
│   ├── src/main/          # Core commons code
│   ├── src/codegen/       # Code generation utilities
│   └── src/generated/     # Auto-generated item types
├── configuration/          # Server configuration files
│   ├── i18n/              # Translations
│   ├── skyblock/          # SkyBlock configs (items, collections)
│   ├── bedwars/           # BedWars configs (maps)
│   ├── quests/            # Quest definitions
│   └── achievements/      # Achievement definitions
├── type.*/                 # Game server implementations
│   ├── type.hub/          # Hub server
│   ├── type.island/       # Private island server
│   ├── type.bedwarsgame/  # BedWars game server
│   └── ...                # Other server types
├── service.*/              # Microservices
│   ├── service.auctionhouse/
│   ├── service.bazaar/
│   └── ...                # Other services
├── velocity.extension/     # Velocity proxy plugin
├── DockerFiles/           # Docker build configurations
├── website/               # Documentation website
└── build.gradle.kts       # Root build configuration
```

### Adding a New Game Mode

1. **Create Module**:
```bash
mkdir type.newgame
cd type.newgame
```

2. **Add build.gradle.kts**:
```kotlin
dependencies {
    implementation(project(":commons"))
    implementation(project(":type.generic"))
}
```

3. **Create Loader Class**:
```java
public class TypeNewGameLoader extends HypixelGenericLoader {
    @Override
    public void onLoad() {
        // Initialize game mode
    }
}
```

4. **Register in settings.gradle.kts**:
```kotlin
include(":type.newgame")
```

5. **Add to ServerType enum**:
```java
NEW_GAME("newgame", TypeNewGameLoader.class)
```

### Code Generation
Items are auto-generated from YAML configs:
```bash
# Generate item types
./gradlew :commons:generateItemTypes

# Generated files appear in commons/src/generated/
```

---

## 📊 Performance Tuning

### Standard Setup (16GB RAM)

#### JVM Optimization
```bash
# Recommended JVM flags
java -Xmx8G -Xms4G \
     -XX:+UseG1GC \
     -XX:+ParallelRefProcEnabled \
     -XX:MaxGCPauseMillis=200 \
     -XX:+UnlockExperimentalVMOptions \
     -XX:+DisableExplicitGC \
     -XX:G1NewSizePercent=30 \
     -XX:G1MaxNewSizePercent=40 \
     -XX:G1HeapRegionSize=8M \
     -XX:G1ReservePercent=20 \
     -XX:G1HeapWastePercent=5 \
     -XX:G1MixedGCCountTarget=4 \
     -XX:InitiatingHeapOccupancyPercent=15 \
     -XX:G1MixedGCLiveThresholdPercent=90 \
     -XX:G1RSetUpdatingPauseTimePercent=5 \
     -XX:SurvivorRatio=32 \
     -XX:+PerfDisableSharedMem \
     -XX:MaxTenuringThreshold=1 \
     -jar HypixelCore.jar
```

### Potato PC Setup (4GB RAM)

The `docker-compose.minimal.yml` is pre-configured for 4GB systems with:
- Serial GC (more memory-efficient)
- Reduced heap sizes (256MB per game server)
- String deduplication enabled
- MongoDB cache limited to 256MB
- Redis limited to 128MB

#### Additional Optimizations

**1. Enable Swap (REQUIRED)**
```bash
sudo fallocate -l 2G /swapfile
sudo chmod 600 /swapfile
sudo mkswap /swapfile
sudo swapon /swapfile
```

**2. Reduce Player Limits**
Edit `configuration/config.yml`:
```yaml
server:
  max-players: 30  # Down from 1000
```

**3. Disable Heavy Features**
```yaml
spark: false        # Profiler not needed
anticheat: false    # Use if you trust players
sandbox: false      # Development feature
```

**4. Monitor Memory**
```bash
# Real-time stats
docker stats

# Check swap usage
free -h

# View service logs
docker-compose -f docker-compose.minimal.yml logs -f
```

**5. Performance Comparison**

| Setup | RAM | Players | Features | Performance | Startup Command |
|-------|-----|---------|----------|-------------|-----------------|
| Full | 16GB | 100+ | All | Excellent | `docker-compose up` |
| Minimal | 8GB | 50+ | All | Good | `docker-compose -f docker-compose.minimal.yml up` |
| Potato | 4GB+2GB swap | 20-30 | All | Acceptable | `./start-potato-pc.sh` or `start-potato-pc.bat` |

**Which setup should I use?**
- **16GB+ RAM**: Use full setup (`docker-compose up`)
- **8GB RAM**: Use minimal setup (`docker-compose -f docker-compose.minimal.yml up`)
- **4GB RAM**: Use potato setup (`./start-potato-pc.sh` or manual with swap)
- **Less than 4GB**: Not recommended, will crash frequently

See `4GB-OPTIMIZED-GUIDE.md` for detailed potato PC tuning instructions.

### MongoDB Optimization
```javascript
// Create indexes for better performance
db.profiles.createIndex({ "uuid": 1 })
db.data.createIndex({ "uuid": 1, "type": 1 })
db.auctions.createIndex({ "endTime": 1 })
```

### Redis Optimization
```conf
# redis.conf
maxmemory 2gb
maxmemory-policy allkeys-lru
save ""  # Disable persistence for cache-only usage
```

### Docker Resource Limits
```yaml
services:
  hypixelcore_hub:
    deploy:
      resources:
        limits:
          cpus: '2'
          memory: 4G
        reservations:
          cpus: '1'
          memory: 2G
```

---

## 🐛 Troubleshooting

### Common Issues

#### Server Won't Start
```bash
# Check Java version
java -version  # Must be 25+

# Check port availability
netstat -an | grep 25565

# Check logs
docker-compose logs -f
```

#### Connection Issues
```bash
# Verify forwarding secret matches
grep FORWARDING_SECRET docker-compose.yml

# Check proxy status
docker-compose ps proxy

# Test connectivity
telnet localhost 25565
```

#### Database Connection Failed
```bash
# Check MongoDB status
docker-compose ps mongodb

# Test connection
mongosh --host localhost --port 27017

# Check credentials
grep mongodb configuration/config.yml
```

#### Redis Connection Failed
```bash
# Check Redis status
docker-compose ps redis

# Test connection
redis-cli ping

# Check configuration
grep redis configuration/config.yml
```

#### Out of Memory
```bash
# Increase Docker memory limit
# Edit docker-compose.yml:
environment:
  JAVA_OPTS: "-Xmx8G -Xms4G"

# Or use minimal configuration
docker-compose -f docker-compose.minimal.yml up
```

#### Potato PC Specific Issues

**Services Keep Crashing:**
```bash
# Check swap usage
free -h  # Linux
# If swap > 1.5GB, you need more RAM

# Reduce heap sizes further in docker-compose.minimal.yml
# Change -Xmx256M to -Xmx192M
```

**Extreme Lag:**
```bash
# Reduce player count in configuration/config.yml
max-players: 20  # Down from 30

# Disable heavy features
spark: false
anticheat: false

# Check if using HDD (switch to SSD)
df -Th  # Linux
```

**Docker Desktop (Windows) Issues:**
```
1. Open Docker Desktop Settings
2. Resources → Memory → Set to 4GB minimum
3. Resources → Disk Image Size → Set to 20GB minimum
4. Apply & Restart
```

**Swap Not Working (Linux):**
```bash
# Verify swap is enabled
swapon --show

# If empty, enable it
sudo swapon /swapfile

# Check swap usage
free -h
```

### Debug Mode
Enable detailed logging:
```yaml
# config.yml
logging:
  level: DEBUG
  sentry:
    enabled: true
    dsn: "your-sentry-dsn"
```

### Getting Help
- Check `website/docs/troubleshooting.md`
- Review Docker logs: `docker-compose logs -f`
- Check MongoDB logs: `docker-compose logs mongodb`
- Enable Sentry for error tracking

---

## 🤝 Contributing

Contributions are welcome! This project is open source under AGPL-3.0.

### Development Workflow
1. Fork the repository
2. Create a feature branch: `git checkout -b feature/amazing-feature`
3. Make your changes
4. Test thoroughly: `./gradlew test`
5. Commit: `git commit -m 'Add amazing feature'`
6. Push: `git push origin feature/amazing-feature`
7. Open a Pull Request

### Code Style
- Follow Java naming conventions
- Use Lombok for boilerplate reduction
- Add Javadoc comments for public APIs
- Write unit tests for new features

### Testing
```bash
# Run all tests
./gradlew test

# Run specific module tests
./gradlew :commons:test

# Generate coverage report
./gradlew jacocoTestReport
```

---

## 📄 License

This project is licensed under the **GNU Affero General Public License v3.0 (AGPL-3.0)**.

### What This Means
- ✅ You can use, modify, and distribute this software
- ✅ You can run this server commercially
- ⚠️ **You MUST provide source code to all users** (AGPL-3.0 Section 13)
- ⚠️ **You MUST keep this license** (no relicensing)
- ⚠️ **You MUST disclose modifications** (copyleft)

### Source Code Availability
As required by AGPL-3.0, complete source code is available at:
**https://github.com/maximally0/max-momentum**

### Anticheat Module
The `anticheat/` directory is licensed under MIT License (more permissive).

### Attribution
This project is forked from [HypixelSkyBlock](https://github.com/Swofty-Developments/HypixelSkyBlock) by Swofty Developments (AGPL-3.0).

Built with [Minestom](https://minestom.net/) - A lightweight Minecraft server framework.

---

## 🔗 Links

- **Source Code**: https://github.com/maximally0/max-momentum
- **Documentation**: `website/docs/`
- **Issue Tracker**: https://github.com/maximally0/max-momentum/issues
- **Minestom**: https://minestom.net/
- **Velocity**: https://papermc.io/software/velocity

---

## 📈 Project Stats

- **40+ Modules** - Gradle multi-project build
- **2000+ Java Classes** - Comprehensive game implementation
- **1000+ Items** - SkyBlock items with full functionality
- **100+ Quests** - Across all game modes
- **14 SkyBlock Islands** - Fully implemented zones
- **8 Microservices** - Distributed architecture
- **3 Game Modes** - SkyBlock, BedWars, SkyWars

---

**Made with ❤️ using Minestom and Java 25**
