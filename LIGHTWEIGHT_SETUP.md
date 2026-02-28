# Lightweight Setup Guide

This configuration removes Murder Mystery and optimizes memory usage while keeping all SkyBlock content and other mini-games.

## What's Removed
- Murder Mystery (lobby, game, configurator)
- Dark Auction service (can re-enable if needed)

## What's Kept
- **All SkyBlock**: All islands, dungeons, full progression
- **Mini-games**: BedWars, SkyWars (with lobbies and games)
- **All Services**: Auction House, Bazaar, Party, Friend, API, ItemTracker, Punishment
- **Infrastructure**: Proxy, MongoDB, Redis, PicoLimbo, Anticheat

## Resource Requirements (Optimized)
- **RAM**: 8-10GB (down from 16GB)
- **CPU**: 4-6 cores (down from 6+)
- **Storage**: ~3GB

## Setup

### Option 1: Use Minimal Docker Compose
```bash
docker-compose -f docker-compose.minimal.yml up --build
```

### Option 2: Modify Build Configuration
1. Replace `settings.gradle.kts` with `settings.minimal.gradle.kts`:
   ```bash
   cp settings.minimal.gradle.kts settings.gradle.kts
   ```

2. Build only what you need:
   ```bash
   ./gradlew build
   ```

3. Use the minimal docker-compose:
   ```bash
   docker-compose -f docker-compose.minimal.yml up
   ```

## Memory Allocation
Each service has reduced memory limits:
- Island/Hub: 512MB-1GB (down from 2GB+)
- Lobby: 256MB-512MB
- Services: 128MB-512MB each

## Further Optimization

### Remove More Islands
Edit `settings.minimal.gradle.kts` and comment out:
```kotlin
// include(":type.hub")  // If you only want personal islands
```

### Remove Services
Comment out services you don't need:
```kotlin
// include(":service.auctionhouse")  // No auction house
// include(":service.bazaar")        // No bazaar
// include(":service.party")          // No parties
```

### Single Server Mode
For absolute minimum, run just one server:
- Keep only `:type.island` and `:type.lobby`
- Remove all services except `:service.datamutex`
- RAM requirement: ~2GB total

## Performance Tips
1. Use Java 25 with virtual threads (already configured)
2. Reduce MongoDB memory: Add `--wiredTigerCacheSizeGB 0.5` to mongo command
3. Use Redis with maxmemory limit: `redis-server --maxmemory 256mb`
4. Disable Spark profiling if not needed
5. Run on SSD for better I/O performance

## Estimated Resource Usage
```
MongoDB:        ~500MB RAM
Redis:          ~256MB RAM
Proxy:          ~256MB RAM
SkyBlock (3):   ~3GB RAM (Island, Hub, Farming)
Mini-games (4): ~2GB RAM (BedWars x2, SkyWars x2)
Lobbies:        ~512MB RAM
Services (5):   ~1.5GB RAM (combined)
PicoLimbo:      ~128MB RAM
----------------------------
Total:          ~8-10GB RAM
```

Compare to original: ~16GB+ RAM (37% reduction)
Memory saved mainly from: Removing Murder Mystery, optimized JVM flags, reduced service memory
