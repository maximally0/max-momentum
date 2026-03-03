# 🥔 Potato PC Quick Start Guide

Got a low-spec PC? This guide is for you.

## TL;DR

**Linux/Mac:**
```bash
./start-potato-pc.sh
```

**Windows:**
```cmd
start-potato-pc.bat
```

That's it. The script handles everything.

---

## What You Get vs What You Lose

### ✅ What Works
- All game modes (SkyBlock, BedWars, SkyWars)
- All services (Auction House, Bazaar, Party system)
- All features (minions, collections, quests, achievements)
- 20-30 concurrent players
- Basic gameplay is smooth

### ⚠️ What's Slower
- Chunk loading (2-3 seconds vs instant)
- Auction/Bazaar queries (1-2 seconds vs instant)
- Server startup (3-5 minutes vs 2 minutes)
- Teleporting between worlds (2-3 seconds vs instant)

### ❌ What Doesn't Work Well
- 50+ concurrent players (will crash)
- Heavy redstone contraptions (will lag)
- Large entity farms (will lag)
- Multiple simultaneous BedWars/SkyWars games (will lag)
- Running other heavy programs at the same time

---

## System Requirements

### Minimum (Potato Mode)
- **RAM**: 4GB + 2GB swap
- **CPU**: 4 cores @ 2.5GHz
- **Storage**: 10GB SSD (HDD not recommended)
- **OS**: Windows 10+, Ubuntu 20.04+, macOS 12+
- **Network**: 10 Mbps upload

### Recommended (If You Can Upgrade)
- **RAM**: 8GB (no swap needed)
- **CPU**: 6 cores @ 3.0GHz
- **Storage**: 20GB SSD
- **OS**: Same as above
- **Network**: 25 Mbps upload

---

## Quick Commands

### Start Server
```bash
# Linux/Mac
./start-potato-pc.sh

# Windows
start-potato-pc.bat

# Manual (all platforms)
docker-compose -f docker-compose.minimal.yml up
```

### Stop Server
```bash
# Graceful shutdown
docker-compose -f docker-compose.minimal.yml down

# Force stop (if frozen)
docker-compose -f docker-compose.minimal.yml kill
```

### Monitor Performance
```bash
# Real-time memory usage
docker stats

# Check swap usage (Linux)
free -h

# View logs
docker-compose -f docker-compose.minimal.yml logs -f

# Check specific service
docker-compose -f docker-compose.minimal.yml logs [service_name]
```

### Restart Services
```bash
# Restart all
docker-compose -f docker-compose.minimal.yml restart

# Restart specific service
docker-compose -f docker-compose.minimal.yml restart hypixelcore_hub
```

---

## Performance Tips

### 1. Enable Swap (Linux Only - CRITICAL)
```bash
sudo fallocate -l 2G /swapfile
sudo chmod 600 /swapfile
sudo mkswap /swapfile
sudo swapon /swapfile
```

Without swap, services will crash randomly.

### 2. Use SSD Storage
HDD is 10x slower. If you're on HDD:
- Expect 10-20 second chunk loading
- Frequent lag spikes
- Slow database queries

### 3. Reduce Player Limit
Edit `configuration/config.yml`:
```yaml
server:
  max-players: 20  # Safe for 4GB
```

### 4. Disable Unused Features
```yaml
spark: false        # Profiler (not needed)
anticheat: false    # If you trust players
sandbox: false      # Development mode
```

### 5. Close Other Programs
- Close Chrome/Firefox (uses 1-2GB)
- Close Discord (uses 500MB)
- Close Spotify (uses 300MB)
- Close IDE/editors (uses 500MB-2GB)

### 6. Restart Daily
Memory leaks accumulate. Restart once per day:
```bash
docker-compose -f docker-compose.minimal.yml restart
```

### 7. Monitor Swap Usage
```bash
# Check swap
free -h

# If swap > 1.5GB consistently, you need more RAM
```

---

## Troubleshooting

### "Out of Memory" Errors
```bash
# 1. Check swap is enabled (Linux)
swapon --show

# 2. Check Docker memory (Windows)
# Docker Desktop → Settings → Resources → Memory → 4GB

# 3. Reduce player count
# Edit configuration/config.yml → max-players: 15

# 4. Close other programs
```

### Services Keep Crashing
```bash
# Check which service
docker-compose -f docker-compose.minimal.yml ps

# View crash logs
docker-compose -f docker-compose.minimal.yml logs [service_name]

# Common fix: Restart
docker-compose -f docker-compose.minimal.yml restart [service_name]
```

### Extreme Lag
```bash
# 1. Check memory usage
docker stats

# 2. Check swap usage (Linux)
free -h

# 3. Reduce player count to 15
# 4. Disable heavy features (farms, minions)
# 5. Use SSD instead of HDD
```

### Can't Connect to Server
```bash
# 1. Check if services are running
docker-compose -f docker-compose.minimal.yml ps

# 2. Check proxy is healthy
docker-compose -f docker-compose.minimal.yml logs proxy

# 3. Check port is open
netstat -an | grep 25565  # Linux
netstat -an | findstr 25565  # Windows

# 4. Restart proxy
docker-compose -f docker-compose.minimal.yml restart proxy
```

### Windows Docker Issues
```
1. Open Docker Desktop
2. Settings → Resources → Memory → 4GB minimum
3. Settings → Resources → Disk → 20GB minimum
4. Apply & Restart
5. Try again
```

---

## When to Upgrade

You need more RAM if:
- ✗ Swap usage consistently > 1.5GB
- ✗ Services crash multiple times per day
- ✗ Can't support more than 15 players
- ✗ Lag is constant even with optimizations
- ✗ Startup takes > 10 minutes

**Upgrade path:**
- 4GB → 8GB: Huge improvement, no swap needed
- 8GB → 16GB: Can handle 100+ players
- 16GB+: Production-ready

---

## Memory Usage Breakdown

| Component | Memory | What It Does |
|-----------|--------|--------------|
| MongoDB | 384MB | Database (player data, items) |
| Redis | 192MB | Cache & messaging |
| Proxy | 384MB | Player routing |
| Hub | 320MB | Main spawn area |
| Island | 320MB | Player islands |
| Farming | 320MB | Farming zone |
| Lobbies | 192MB each | Game lobbies (3x) |
| Games | 320MB each | BedWars/SkyWars (2x) |
| Services | 128-192MB | Backend services (5x) |
| **Total** | **~3.5GB** | Leaves 500MB for OS |

---

## Comparison Table

| Spec | Full Setup | Minimal Setup | Potato Setup |
|------|-----------|---------------|--------------|
| RAM | 16GB | 8GB | 4GB + 2GB swap |
| Players | 100+ | 50+ | 20-30 |
| Chunk Load | Instant | 1s | 2-3s |
| Lag Spikes | Rare | Occasional | Frequent |
| Startup Time | 2 min | 3 min | 5 min |
| Multiple Games | Yes | Yes | No |
| Heavy Farms | Yes | Yes | No |
| Performance | Excellent | Good | Acceptable |

---

## FAQ

**Q: Can I run this on 2GB RAM?**
A: No. Minimum is 4GB + 2GB swap. With 2GB, services will crash constantly.

**Q: Do I need swap on Windows?**
A: No, Windows manages virtual memory automatically. Just allocate 4GB to Docker Desktop.

**Q: Can I run this on a Raspberry Pi?**
A: No. ARM architecture not supported, and Pi doesn't have enough RAM.

**Q: Why is it so slow on HDD?**
A: Database queries hit disk constantly. SSD is 10-100x faster for random reads.

**Q: Can I upgrade RAM later?**
A: Yes! Just use the standard setup command: `docker-compose up`

**Q: How do I reduce memory usage further?**
A: See `4GB-OPTIMIZED-GUIDE.md` for advanced tuning. You can cut services to ~3GB.

**Q: Will this work on a laptop?**
A: Yes, if it has 4GB RAM and decent CPU. Battery life will be poor though.

**Q: Can I host this for friends?**
A: Yes, but limit to 10-15 friends max. More will cause lag.

---

## Support

- Full documentation: `README.md`
- Advanced tuning: `4GB-OPTIMIZED-GUIDE.md`
- Issues: https://github.com/maximally0/max-momentum/issues

---

**Remember:** This is a potato setup. It works, but it's not optimal. If you can upgrade to 8GB RAM, do it. The difference is huge.

