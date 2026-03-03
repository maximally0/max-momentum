# 4GB RAM Setup Guide

This configuration runs Max Momentum on systems with only 4GB RAM available.

## What's Included

### Infrastructure (1.5GB)
- MongoDB (768MB) - Reduced cache size
- Redis (256MB) - Memory-limited with LRU eviction
- Velocity Proxy (512MB)

### Game Servers (2GB)
- SkyBlock Hub (640MB) - Main spawn area
- SkyBlock Island (640MB) - Player islands
- Prototype Lobby (384MB) - Entry point

### Services (384MB)
- API Service (192MB) - Core API
- Party Service (192MB) - Party system

## What's Removed

To fit in 4GB, these were cut:
- ❌ BedWars (lobby + game = 1.5GB)
- ❌ SkyWars (lobby + game = 1.5GB)
- ❌ Farming Islands server (1GB)
- ❌ Auction House service (512MB)
- ❌ Bazaar service (512MB)
- ❌ Item Tracker service (256MB)
- ❌ PicoLimbo (128MB)

## Usage

```bash
# Start ultra-minimal setup
docker-compose -f docker-compose.ultra-minimal.yml up -d

# View logs
docker-compose -f docker-compose.ultra-minimal.yml logs -f

# Stop
docker-compose -f docker-compose.ultra-minimal.yml down
```

## Memory Breakdown

| Service | Memory | Purpose |
|---------|--------|---------|
| MongoDB | 768MB | Database with reduced cache |
| Redis | 256MB | Pub/sub + caching |
| Proxy | 512MB | Player routing |
| Hub | 640MB | Main spawn |
| Island | 640MB | Player islands |
| Lobby | 384MB | Entry lobby |
| API | 192MB | Core services |
| Party | 192MB | Party system |
| **Total** | **~3.4GB** | Leaves 600MB for OS |

## Performance Tips

### 1. Reduce Player Count
Edit `configuration/config.yml`:
```yaml
server:
  max-players: 50  # Down from 1000
```

### 2. Disable Unused Features
```yaml
spark: false
anticheat: false
sandbox: false
```

### 3. Optimize MongoDB
The config already includes:
- `--wiredTigerCacheSizeGB 0.5` (reduced from default 1GB)
- Quiet mode to reduce logging overhead

### 4. Optimize Redis
The config already includes:
- `--maxmemory 256mb` (hard limit)
- `--maxmemory-policy allkeys-lru` (evict old data)
- `--save ""` (disable persistence)

### 5. Use Serial GC
All Java services use `-XX:+UseSerialGC` which is more memory-efficient than G1GC for small heaps.

## Adding More Features

If you need specific features back, add them one at a time:

### Add Farming Islands (+640MB)
```yaml
hypixelcore_farming:
  image: game_server_prepared
  container_name: hypixelcore_farming
  restart: "unless-stopped"
  environment:
    <<: *forwarding_env
    SERVICE_CMD: java -Xms256M -Xmx512M -XX:+UseSerialGC -jar HypixelCore.jar SKYBLOCK_THE_FARMING_ISLANDS
  depends_on:
    proxy:
      condition: service_healthy
    game_server_builder:
      condition: service_started
  volumes:
    - ./configuration:/app/configuration_files
  networks:
    - hypixel_network
  deploy:
    resources:
      limits:
        memory: 640M
```

### Add Auction House (+192MB)
```yaml
service_auctionhouse:
  image: game_server_prepared
  container_name: service_auctionhouse
  restart: "unless-stopped"
  environment:
    <<: *forwarding_env
    SERVICE_CMD: java -Xms128M -Xmx256M -XX:+UseSerialGC -jar ServiceAuctionHouse.jar
  depends_on:
    proxy:
      condition: service_healthy
    game_server_builder:
      condition: service_started
  volumes:
    - ./configuration:/app/configuration_files
  networks:
    - hypixel_network
  deploy:
    resources:
      limits:
        memory: 192M
```

## Troubleshooting

### Out of Memory Errors
```bash
# Check actual memory usage
docker stats

# If still OOM, reduce heap sizes further:
# Change -Xmx512M to -Xmx384M
# Change -Xmx256M to -Xmx192M
```

### Slow Performance
```bash
# This is expected with 4GB RAM
# Consider:
# 1. Reducing max players to 20-30
# 2. Disabling resource-intensive features
# 3. Using SSD storage
# 4. Upgrading to 8GB RAM if possible
```

### Services Crashing
```bash
# Check logs
docker-compose -f docker-compose.ultra-minimal.yml logs [service_name]

# Common causes:
# - Not enough swap space (add 2GB swap)
# - Too many players online
# - Memory leak (restart service)
```

## Recommended System Requirements

For this 4GB setup:
- **RAM**: 4GB (3.4GB used, 600MB for OS)
- **Swap**: 2GB recommended
- **CPU**: 4+ cores
- **Storage**: 10GB SSD
- **Players**: 20-50 max

## Upgrading Path

When you get more RAM:
- **6GB**: Add Farming Islands + Auction House
- **8GB**: Add Bazaar + BedWars
- **12GB**: Add SkyWars + all services
- **16GB**: Full deployment (docker-compose.minimal.yml)

