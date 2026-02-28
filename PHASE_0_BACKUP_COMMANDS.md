# PHASE 0 — BACKUP COMMANDS
## Complete Backup Strategy Before Repository Initialization

**Target Repository**: `C:\Users\rishh\OneDrive\Desktop\Projects\HypixelSkyBlock-master`  
**Backup Date**: 2025-02-28  
**Purpose**: Create complete backups before git initialization and rebrand

---

## ⚠️ CRITICAL WARNINGS

1. **DO NOT SKIP BACKUPS** - Once git is initialized, you cannot recover the original state without backups
2. **VERIFY EACH BACKUP** - Test that archives can be extracted before proceeding
3. **STORE BACKUPS SAFELY** - Keep backups on separate drive or cloud storage
4. **CHECK DISK SPACE** - Ensure you have at least 5GB free space for backups

---

## BACKUP STRATEGY OVERVIEW

Since this is NOT a git repository (no `.git` folder exists), we only need to backup:
- ✅ Working directory (all source files)
- ✅ MongoDB database (if running)
- ✅ Docker images (if built)
- ❌ Git history (doesn't exist)
- ❌ Git remotes (doesn't exist)

---

## 1. WORKING DIRECTORY BACKUP (CRITICAL)

### Option A: Full Directory Archive (Recommended)
```bash
# Navigate to parent directory
cd C:\Users\rishh\OneDrive\Desktop\Projects

# Create timestamped backup
tar -czvf HypixelSkyBlock-backup-20250228.tar.gz HypixelSkyBlock-master/

# Verify backup was created
ls -lh HypixelSkyBlock-backup-*.tar.gz
```

**Expected Output**:
```
-rw-r--r-- 1 user user 1.2G Feb 28 12:00 HypixelSkyBlock-backup-20250228.tar.gz
```

### Option B: ZIP Archive (Windows-Friendly)
```powershell
# Using PowerShell
cd C:\Users\rishh\OneDrive\Desktop\Projects
Compress-Archive -Path .\HypixelSkyBlock-master\* -DestinationPath .\HypixelSkyBlock-backup-20250228.zip -CompressionLevel Optimal

# Verify backup
Get-Item .\HypixelSkyBlock-backup-*.zip | Select-Object Name, Length, LastWriteTime
```

### Option C: Copy to External Drive
```bash
# Copy entire directory to external drive
cp -r HypixelSkyBlock-master /mnt/external-drive/backups/HypixelSkyBlock-backup-20250228/

# Verify copy
diff -r HypixelSkyBlock-master /mnt/external-drive/backups/HypixelSkyBlock-backup-20250228/
```

---

## 2. VERIFY BACKUP INTEGRITY (MANDATORY)

### Test Archive Extraction
```bash
# Create test directory
mkdir backup-test

# Extract to test directory
tar -xzvf HypixelSkyBlock-backup-20250228.tar.gz -C backup-test/

# Verify critical files exist
ls backup-test/HypixelSkyBlock-master/build.gradle.kts
ls backup-test/HypixelSkyBlock-master/settings.gradle.kts
ls backup-test/HypixelSkyBlock-master/LICENSE

# Count Java files (should be 2000+)
find backup-test/HypixelSkyBlock-master -name "*.java" | wc -l

# Clean up test
rm -rf backup-test/
```

**Expected Java File Count**: ~2000+ files

### Verify Archive Contents
```bash
# List first 50 files in archive
tar -tzf HypixelSkyBlock-backup-20250228.tar.gz | head -50

# Check archive size
du -h HypixelSkyBlock-backup-20250228.tar.gz
```

---

## 3. MONGODB DATABASE BACKUP (If Running)

### Check if MongoDB is Running
```bash
# Check MongoDB status
docker ps | grep mongo
# OR
mongosh --eval "db.version()"
```

### Backup MongoDB Database
```bash
# Create backup directory
mkdir -p mongo-backups/backup-20250228

# Dump entire Minestom database
mongodump --db Minestom --out mongo-backups/backup-20250228/

# Verify backup
ls -lh mongo-backups/backup-20250228/Minestom/
```

**Expected Collections**:
- `profiles` (player profiles)
- `data` (player data)
- `coop` (coop data)
- `auctions` (auction house)
- `bazaar` (bazaar data)
- Additional collections...

### Backup All MongoDB Databases
```bash
# Backup all databases (safer option)
mongodump --out mongo-backups/backup-20250228-all/

# Verify backup
du -sh mongo-backups/backup-20250228-all/
```

### Compress MongoDB Backup
```bash
# Compress backup for storage
tar -czvf mongo-backup-20250228.tar.gz mongo-backups/backup-20250228/

# Verify compressed backup
ls -lh mongo-backup-20250228.tar.gz
```

---

## 4. DOCKER IMAGE BACKUP (If Built)

### List Docker Images
```bash
# List all images related to project
docker images | grep -i hypixel
docker images | grep -i skyblock
docker images | grep -i swofty
```

### Save Docker Images
```bash
# Save each image to tar file
docker save hypixelcore:latest > docker-images/hypixelcore-latest.tar
docker save serviceauctionhouse:latest > docker-images/serviceauctionhouse-latest.tar
# ... repeat for each image

# OR save all images at once
docker images --format "{{.Repository}}:{{.Tag}}" | grep -E "hypixel|skyblock|swofty" | xargs -I {} docker save {} -o docker-images/{}.tar
```

### Verify Docker Image Backups
```bash
# List saved images
ls -lh docker-images/*.tar

# Verify image can be loaded (test with one image)
docker rmi hypixelcore:latest
docker load < docker-images/hypixelcore-latest.tar
docker images | grep hypixelcore
```

---

## 5. CONFIGURATION FILES BACKUP (Extra Safety)

### Backup Critical Configs Separately
```bash
# Create config backup directory
mkdir -p config-backups/backup-20250228

# Copy critical configuration files
cp docker-compose.yml config-backups/backup-20250228/
cp docker-compose.minimal.yml config-backups/backup-20250228/
cp build.gradle.kts config-backups/backup-20250228/
cp settings.gradle.kts config-backups/backup-20250228/
cp LICENSE config-backups/backup-20250228/
cp -r configuration/ config-backups/backup-20250228/

# Compress config backup
tar -czvf config-backup-20250228.tar.gz config-backups/backup-20250228/
```

---

## 6. BACKUP CHECKLIST

Before proceeding to git initialization, verify:

- [ ] **Working directory backup created** (`HypixelSkyBlock-backup-20250228.tar.gz`)
- [ ] **Backup integrity verified** (extracted and tested)
- [ ] **Backup size is reasonable** (~1-2GB compressed)
- [ ] **Critical files present in backup** (build.gradle.kts, LICENSE, etc.)
- [ ] **Java file count correct** (~2000+ files)
- [ ] **MongoDB backup created** (if database is running)
- [ ] **MongoDB backup verified** (collections present)
- [ ] **Docker images saved** (if images are built)
- [ ] **Backup stored safely** (external drive or cloud storage)
- [ ] **Disk space available** (at least 2GB free after backups)

---

## 7. BACKUP STORAGE RECOMMENDATIONS

### Local Storage
```bash
# Move backups to safe location
mkdir -p ~/backups/hypixel-skyblock/
mv HypixelSkyBlock-backup-*.tar.gz ~/backups/hypixel-skyblock/
mv mongo-backup-*.tar.gz ~/backups/hypixel-skyblock/
mv docker-images/*.tar ~/backups/hypixel-skyblock/docker-images/
```

### Cloud Storage (Recommended)
```bash
# Upload to cloud storage (example with rclone)
rclone copy HypixelSkyBlock-backup-20250228.tar.gz remote:backups/hypixel-skyblock/
rclone copy mongo-backup-20250228.tar.gz remote:backups/hypixel-skyblock/

# OR use cloud provider CLI
# AWS S3
aws s3 cp HypixelSkyBlock-backup-20250228.tar.gz s3://your-bucket/backups/

# Google Drive (using gdrive)
gdrive upload HypixelSkyBlock-backup-20250228.tar.gz
```

### External Drive
```bash
# Copy to external drive
cp HypixelSkyBlock-backup-20250228.tar.gz /mnt/external-drive/backups/
cp mongo-backup-20250228.tar.gz /mnt/external-drive/backups/
```

---

## 8. BACKUP RESTORATION PROCEDURE

### Restore Working Directory
```bash
# Extract backup to new location
mkdir -p restored/
tar -xzvf HypixelSkyBlock-backup-20250228.tar.gz -C restored/

# Verify restoration
ls restored/HypixelSkyBlock-master/
```

### Restore MongoDB Database
```bash
# Restore entire database
mongorestore --db Minestom mongo-backups/backup-20250228/Minestom/

# Verify restoration
mongosh Minestom --eval "db.getCollectionNames()"
```

### Restore Docker Images
```bash
# Load saved images
docker load < docker-images/hypixelcore-latest.tar
docker load < docker-images/serviceauctionhouse-latest.tar

# Verify images loaded
docker images
```

---

## 9. BACKUP RETENTION POLICY

### Recommended Retention
- **Pre-rebrand backup**: Keep permanently (baseline)
- **Phase 0 backup**: Keep for 6 months
- **Phase 1 backup**: Keep for 3 months
- **Phase 2 backup**: Keep for 1 month
- **Weekly backups**: Keep for 4 weeks
- **Daily backups**: Keep for 7 days

### Cleanup Old Backups
```bash
# List backups by date
ls -lht ~/backups/hypixel-skyblock/

# Remove backups older than 6 months
find ~/backups/hypixel-skyblock/ -name "*.tar.gz" -mtime +180 -delete
```

---

## 10. EMERGENCY RECOVERY PLAN

### If Something Goes Wrong During Rebrand

1. **Stop all operations immediately**
2. **Do NOT commit or push changes**
3. **Restore from backup**:
   ```bash
   # Navigate to parent directory
   cd C:\Users\rishh\OneDrive\Desktop\Projects
   
   # Remove corrupted directory
   rm -rf HypixelSkyBlock-master/
   
   # Extract backup
   tar -xzvf HypixelSkyBlock-backup-20250228.tar.gz
   
   # Verify restoration
   cd HypixelSkyBlock-master
   ./gradlew clean build
   ```

4. **Restore MongoDB** (if needed):
   ```bash
   mongorestore --drop --db Minestom mongo-backups/backup-20250228/Minestom/
   ```

5. **Analyze what went wrong**
6. **Plan corrective action**
7. **Try again with lessons learned**

---

## 11. BACKUP VERIFICATION SCRIPT

### Automated Verification Script
```bash
#!/bin/bash
# backup-verify.sh

echo "=== Backup Verification Script ==="
echo ""

# Check working directory backup
if [ -f "HypixelSkyBlock-backup-20250228.tar.gz" ]; then
    echo "✅ Working directory backup found"
    echo "   Size: $(du -h HypixelSkyBlock-backup-20250228.tar.gz | cut -f1)"
else
    echo "❌ Working directory backup NOT found"
    exit 1
fi

# Check MongoDB backup
if [ -d "mongo-backups/backup-20250228" ]; then
    echo "✅ MongoDB backup found"
    echo "   Collections: $(ls mongo-backups/backup-20250228/Minestom/ | wc -l)"
else
    echo "⚠️  MongoDB backup NOT found (skip if not using MongoDB)"
fi

# Check Docker images
if [ -d "docker-images" ] && [ "$(ls -A docker-images/*.tar 2>/dev/null)" ]; then
    echo "✅ Docker images backed up"
    echo "   Images: $(ls docker-images/*.tar | wc -l)"
else
    echo "⚠️  Docker images NOT backed up (skip if not using Docker)"
fi

echo ""
echo "=== Verification Complete ==="
```

### Run Verification
```bash
chmod +x backup-verify.sh
./backup-verify.sh
```

---

## 12. NEXT STEPS

After all backups are complete and verified:

1. ✅ Review `PHASE_0_HISTORY_RESET_PROCEDURE.md`
2. ✅ Understand git initialization steps
3. ✅ Prepare new GitHub repository
4. ⚠️ **WAIT FOR EXPLICIT APPROVAL** before executing any commands
5. ✅ Proceed to Phase 0 execution only after approval

---

## SUMMARY

**Backup Status**: ⏳ **PENDING EXECUTION**

**Required Backups**:
1. Working directory archive (CRITICAL)
2. MongoDB database dump (if running)
3. Docker images (if built)

**Estimated Backup Size**: 1-3GB total  
**Estimated Time**: 10-30 minutes  
**Risk Level**: ✅ **VERY LOW** (read-only operations)

**DO NOT PROCEED** to `PHASE_0_HISTORY_RESET_PROCEDURE.md` until all backups are complete and verified.
