# 4GB RAM Optimized Setup

This keeps ALL features but aggressively optimizes memory usage to fit in 4GB.

## What Changed

### Memory Reductions
- MongoDB: 1GB → 384MB (cache reduced to 256MB)
- Redis: 512MB → 192MB (128MB max memory)
- Proxy: 512MB → 384MB
- Game servers: 1GB → 256MB each
- Lobbies: 512MB → 128MB each
- Services: 256-512MB → 64-128MB each

### JVM Optimizations
All Java services now use:
- `-XX:+UseSerialGC` - More memory-efficient than G1GC for small heaps
- `-XX:MaxGCPauseMillis=50` - Faster GC cycles
- `-XX:+UseStringDeduplication` - Reduces string memory usage
- Smaller heap sizes with tighter limits

## Memory Breakdown

| Component | Memory | Count | Total |
|-----------|--------|-------|-------|
| MongoDB | 384MB | 1 | 384MB |
| Redis | 192MB | 1 | 192MB |
| Proxy | 384MB | 1 | 384MB |
| Game Servers | 320MB | 3 | 960MB |
| Lobbies | 192MB | 3 | 576MB |
| PicoLimbo | 128MB | 1 | 128MB |
| Services | 128-192MB | 5 | 832MB |
| **TOTAL** | | | **~3.5GB** |

Leaves ~500MB for Docker overhead and OS.

## What's Included

### SkyBlock (3 servers)
✅ Hub (320MB)
✅ Island (320MB)
✅ Farming Islands (320MB)

### BedWars (2 servers)
✅ Lobby (192MB)
✅ Game (320MB)

### SkyWars (2 servers)
✅ Lobby (192MB)
✅ Game (320MB)

### Services (5 services)
✅ API (192MB)
✅ Auction House (192MB)
✅ Bazaar (192MB)
✅ Item Tracker (128MB)
✅ Party (128MB)

### Infrastructure
✅ MongoDB (384MB)
✅ Redis (192MB)
✅ Velocity Proxy (384MB)
✅ PicoLimbo (128MB)

## Usage

```bash
# Start optimized setup
docker-compose -f docker-compose.minimal.yml up -d

# Monitor memory usage
docker stats

# View logs
docker-compose -f docker-compose.minimal.yml logs -f

# Stop
docker-compose -f docker-compose.minimal.yml down
```

## Performance Expectations

### What Works
- ✅ All game modes functional
- ✅ All services operational
- ✅ 10-30 concurrent players
- ✅ Basic gameplay smooth

### What's Slower
- ⚠️ Chunk loading may lag with many players
- ⚠️ Auction house queries slower with large datasets
- ⚠️ GC pauses more frequent (but shorter)
- ⚠️ Server startup takes longer

### What Might Break
- ❌ 50+ concurrent players will cause OOM
- ❌ Large auction house operations may timeout
- ❌ Heavy redstone/entity farms will lag
- ❌ Multiple simultaneous BedWars/SkyWars games

## Critical Optimizations

### 1. Enable Swap (REQUIRED)
```bash
# Add 2GB swap to prevent OOM crashes
sudo fallocate -l 2G /swapfile
sudo chmod 600 /swapfile
sudo mkswap /swapfile
sudo swapon /swapfile

# Make permanent
echo '/swapfile none swap sw 0 0' | sudo tee -a /etc/fstab
```

### 2. Reduce Player Limits
Edit `configuration/config.yml`:
```yaml
server:
  max-players: 30  # Down from 1000
```

### 3. Disable Heavy Features
```yaml
spark: false        # Profiler not needed
anticheat: false    # Use if you trust players
sandbox: false      # Development feature
```

### 4. Optimize MongoDB Queries
```javascript
// Add indexes to speed up queries
db.profiles.createIndex({ "uuid": 1 })
db.data.createIndex({ "uuid": 1, "type": 1 })
db.auctions.createIndex({ "endTime": 1 })
db.bazaar.createIndex({ "item": 1 })
```

### 5. Clean Up Old Data
```javascript
// Remove expired auctions
db.auctions.deleteMany({ endTime: { $lt: new Date() } })

// Remove old logs
db.logs.deleteMany({ timestamp: { $lt: new Date(Date.now() - 7*24*60*60*1000) } })
```

## Monitoring

### Check Memory Usage
```bash
# Real-time stats
docker stats

# Check if services are OOM
docker-compose logs | grep -i "out of memory"

# Check swap usage
free -h
```

### Warning Signs
- Swap usage > 1GB = Need more RAM
- Services restarting frequently = OOM crashes
- High CPU with low memory = Excessive GC

## Troubleshooting

### Services Keep Crashing
```bash
# Check which service
docker-compose ps

# View crash logs
docker-compose logs [service_name]

# Common fix: Reduce heap size further
# Edit docker-compose.minimal.yml:
# Change -Xmx256M to -Xmx192M
```

### Extreme Lag
```bash
# Check GC activity
docker-compose logs | grep "GC"

# Reduce player count
# Disable heavy features (minions, farms)
# Add more swap space
```

### MongoDB Slow
```bash
# Check cache hit rate
docker exec hypixel_mongo mongosh --eval "db.serverStatus().wiredTiger.cache"

# If cache misses high, increase cache:
# Edit docker-compose.minimal.yml:
# Change --wiredTigerCacheSizeGB 0.25 to 0.5
# (Requires reducing memory elsewhere)
```

### Redis Evicting Data
```bash
# Check eviction stats
docker exec hypixel_redis redis-cli info stats | grep evicted

# If high evictions, increase maxmemory:
# Edit docker-compose.minimal.yml:
# Change --maxmemory 128mb to 192mb
# (Requires reducing memory elsewhere)
```

## Advanced Tuning

### If You Have 5GB RAM
Increase these:
```yaml
mongodb: 384M → 512M
redis: 192M → 256M
game servers: 256M → 384M
```

### If You Have 6GB RAM
Increase these:
```yaml
mongodb: 384M → 768M
redis: 192M → 384M
game servers: 256M → 512M
services: 128M → 256M
```

### If You Only Have 3.5GB RAM
Disable one game mode:
```bash
# Comment out BedWars or SkyWars in docker-compose.minimal.yml
# Saves ~512MB
```

## Performance Tips

### 1. Use SSD Storage
HDD will make everything 10x slower with limited RAM.

### 2. Limit Entities
```yaml
# In server configs
max-entities-per-chunk: 50
entity-activation-range: 32
```

### 3. Reduce View Distance
```yaml
view-distance: 6  # Down from 10
simulation-distance: 4  # Down from 8
```

### 4. Disable Unused Worlds
Only load worlds that players are actively using.

### 5. Schedule Restarts
```bash
# Restart daily to clear memory leaks
0 4 * * * docker-compose -f docker-compose.minimal.yml restart
```

## When to Upgrade

You need more RAM if:
- Swap usage consistently > 1.5GB
- Services crash multiple times per day
- Player count limited to < 20
- Lag is constant even with optimizations

Recommended: 8GB RAM for comfortable operation.

## Comparison

| Setup | RAM | Players | Features | Performance |
|-------|-----|---------|----------|-------------|
| Full | 16GB | 100+ | All | Excellent |
| Minimal | 8GB | 50+ | All | Good |
| Optimized | 4GB | 20-30 | All | Acceptable |
| Ultra-Minimal | 4GB | 30-50 | Limited | Good |

This optimized setup prioritizes keeping all features over performance.
If you want better performance, use the ultra-minimal setup instead.

