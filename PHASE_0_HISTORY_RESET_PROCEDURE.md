# PHASE 0 — HISTORY RESET PROCEDURE
## Git Repository Initialization (Simplified - No History to Wipe)

**Target Repository**: `C:\Users\rishh\OneDrive\Desktop\Projects\HypixelSkyBlock-master`  
**Execution Date**: TBD (awaiting approval)  
**Status**: ⚠️ **DO NOT EXECUTE WITHOUT EXPLICIT APPROVAL**

---

## ⚠️ CRITICAL PRE-EXECUTION CHECKLIST

Before executing ANY commands in this document:

- [ ] **ALL BACKUPS COMPLETE** (see `PHASE_0_BACKUP_COMMANDS.md`)
- [ ] **BACKUPS VERIFIED** (extracted and tested)
- [ ] **BACKUPS STORED SAFELY** (external drive or cloud)
- [ ] **NEW GITHUB REPOSITORY CREATED** (https://github.com/yourusername/max-momentum)
- [ ] **EXPLICIT HUMAN APPROVAL RECEIVED** (do not proceed without this)
- [ ] **UNDERSTOOD THAT THIS IS IRREVERSIBLE** (cannot undo without backup)

**IF ANY ITEM IS UNCHECKED, STOP AND DO NOT PROCEED**

---

## SIMPLIFIED WORKFLOW

Since this directory is **NOT a git repository** (no `.git` folder exists), the workflow is dramatically simplified:

### Standard Workflow (with git history) ❌
1. ~~Analyze git history~~
2. ~~Create mirror backup~~
3. ~~Remove .git directory~~
4. ~~Wipe commit history~~
5. ~~Break fork relationship~~

### Actual Workflow (no git history) ✅
1. Initialize new git repository
2. Create initial commit
3. Rename branch to main
4. Connect to new remote
5. Push to GitHub

**Time Required**: ~5 minutes  
**Complexity**: ✅ **VERY LOW**  
**Risk**: ✅ **MINIMAL** (only creates new files, doesn't delete anything)

---

## STEP 1: INITIALIZE GIT REPOSITORY

### Navigate to Repository
```bash
cd C:\Users\rishh\OneDrive\Desktop\Projects\HypixelSkyBlock-master
```

### Verify Current State
```bash
# Confirm no .git directory exists
ls -la | grep .git

# Expected output: (nothing)
```

### Initialize Git Repository
```bash
# Initialize new git repository
git init

# Expected output:
# Initialized empty Git repository in C:/Users/rishh/OneDrive/Desktop/Projects/HypixelSkyBlock-master/.git/
```

### Verify Initialization
```bash
# Check git status
git status

# Expected output:
# On branch master
# No commits yet
# Untracked files: (list of all files)
```

**Result**: `.git` directory created, repository initialized

---

## STEP 2: CONFIGURE GIT USER (If Not Already Set)

### Check Current Git Config
```bash
# Check global git config
git config --global user.name
git config --global user.email
```

### Set Git User (If Needed)
```bash
# Set your name
git config --global user.name "Your Name"

# Set your email
git config --global user.email "your.email@example.com"
```

**Note**: Use the email associated with your GitHub account

---

## STEP 3: CREATE INITIAL COMMIT

### Stage All Files
```bash
# Add all files to staging area
git add .

# Verify files staged
git status
```

**Expected Output**:
```
On branch master
No commits yet
Changes to be committed:
  new file: .gitignore
  new file: build.gradle.kts
  new file: settings.gradle.kts
  new file: LICENSE
  ... (2000+ files)
```

### Create Initial Commit
```bash
# Create initial commit with descriptive message
git commit -m "Initial commit - Max Momentum base

- Forked from HypixelSkyBlock (AGPL-3.0)
- Removed Murder Mystery modules
- Kept SkyBlock, BedWars, SkyWars
- Pre-rebrand baseline commit
- All original licenses preserved"

# Expected output:
# [master (root-commit) abc1234] Initial commit - Max Momentum base
# 2000+ files changed, 500000+ insertions(+)
```

### Verify Commit
```bash
# Check commit history
git log

# Expected output:
# commit abc1234... (HEAD -> master)
# Author: Your Name <your.email@example.com>
# Date: Sat Feb 28 12:00:00 2025
#
# Initial commit - Max Momentum base
# ...
```

**Result**: Single commit created with all files

---

## STEP 4: RENAME BRANCH TO MAIN

### Check Current Branch
```bash
# Show current branch
git branch

# Expected output:
# * master
```

### Rename Branch
```bash
# Rename master to main
git branch -M main

# Verify rename
git branch

# Expected output:
# * main
```

**Result**: Default branch is now `main` (modern convention)

---

## STEP 5: CREATE NEW GITHUB REPOSITORY

### Manual Steps (GitHub Web Interface)

1. **Go to GitHub**: https://github.com/new
2. **Repository Name**: `max-momentum`
3. **Description**: "Max Momentum - Minecraft Server Platform (forked from HypixelSkyBlock)"
4. **Visibility**: 
   - ⚠️ **MUST BE PUBLIC** (AGPL-3.0 requirement)
   - Private repositories violate AGPL-3.0 Section 13
5. **Initialize**: 
   - ❌ **DO NOT** add README
   - ❌ **DO NOT** add .gitignore
   - ❌ **DO NOT** add license
   - (We already have these files)
6. **Click**: "Create repository"

### Expected Repository URL
```
https://github.com/yourusername/max-momentum
```

**Replace `yourusername` with your actual GitHub username**

---

## STEP 6: CONNECT TO NEW REMOTE

### Add Remote Origin
```bash
# Add new GitHub repository as remote
git remote add origin https://github.com/yourusername/max-momentum.git

# Verify remote added
git remote -v

# Expected output:
# origin  https://github.com/yourusername/max-momentum.git (fetch)
# origin  https://github.com/yourusername/max-momentum.git (push)
```

**⚠️ IMPORTANT**: Replace `yourusername` with your actual GitHub username

### Verify Remote Connection
```bash
# Test connection to remote
git remote show origin

# Expected output:
# * remote origin
#   Fetch URL: https://github.com/yourusername/max-momentum.git
#   Push  URL: https://github.com/yourusername/max-momentum.git
#   HEAD branch: (unknown)
```

---

## STEP 7: PUSH TO GITHUB

### Push Initial Commit
```bash
# Push main branch to origin
git push -u origin main

# Expected output:
# Enumerating objects: 2500, done.
# Counting objects: 100% (2500/2500), done.
# Delta compression using up to 8 threads
# Compressing objects: 100% (2000/2000), done.
# Writing objects: 100% (2500/2500), 50.00 MiB | 5.00 MiB/s, done.
# Total 2500 (delta 500), reused 0 (delta 0)
# To https://github.com/yourusername/max-momentum.git
#  * [new branch]      main -> main
# Branch 'main' set up to track remote branch 'main' from 'origin'.
```

### Verify Push Success
```bash
# Check remote branches
git branch -r

# Expected output:
# origin/main

# Check tracking
git branch -vv

# Expected output:
# * main abc1234 [origin/main] Initial commit - Max Momentum base
```

### Verify on GitHub
1. Go to: `https://github.com/yourusername/max-momentum`
2. Verify files are present
3. Verify commit history shows 1 commit
4. Verify LICENSE file is visible

---

## STEP 8: VERIFY REPOSITORY STATE

### Check Commit Count
```bash
# Count commits
git rev-list --count HEAD

# Expected output:
# 1
```

### Check Remote Configuration
```bash
# List remotes
git remote -v

# Expected output:
# origin  https://github.com/yourusername/max-momentum.git (fetch)
# origin  https://github.com/yourusername/max-momentum.git (push)

# Verify no upstream remote exists
git remote show

# Expected output:
# origin
```

### Check Branch Configuration
```bash
# List all branches
git branch -a

# Expected output:
# * main
#   remotes/origin/main
```

### Verify No Fork Relationship
1. Go to GitHub repository: `https://github.com/yourusername/max-momentum`
2. Check if "forked from" badge appears at top
3. **Expected**: No fork badge (this is a new repository)

**Result**: Clean repository with no fork relationship

---

## STEP 9: AGPL-3.0 COMPLIANCE SETUP

### Add Source Code Disclosure Notice

Since AGPL-3.0 Section 13 requires providing source code to network users, add a prominent notice:

#### Option A: Add to README.md
```bash
# Edit README.md
nano README.md
```

Add at the top:
```markdown
# Max Momentum

> **Source Code Availability**: This project is licensed under AGPL-3.0. 
> The complete source code is available at: https://github.com/yourusername/max-momentum

> **Original Project**: Forked from [HypixelSkyBlock](https://github.com/Swofty-Developments/HypixelSkyBlock)
> Copyright notices and licenses preserved as required.
```

#### Option B: Add In-Game Command (Recommended)
Create a `/source` command that displays:
```
§7This server runs §bMax Momentum§7, licensed under AGPL-3.0
§7Source code: §bhttps://github.com/yourusername/max-momentum
```

#### Option C: Add to Server MOTD
Update `velocity.extension/src/main/java/net/swofty/velocity/SkyBlockVelocity.java`:
```java
Component.text("§aMax Momentum §7| §bSource: github.com/yourusername/max-momentum")
```

**⚠️ CRITICAL**: You MUST implement at least one of these options to comply with AGPL-3.0

### Commit Compliance Changes
```bash
# Stage changes
git add README.md

# Commit
git commit -m "Add AGPL-3.0 source code disclosure notice"

# Push
git push origin main
```

---

## STEP 10: REPOSITORY PROTECTION (Recommended)

### Enable Branch Protection
1. Go to: `https://github.com/yourusername/max-momentum/settings/branches`
2. Click "Add rule"
3. Branch name pattern: `main`
4. Enable:
   - ✅ Require pull request reviews before merging
   - ✅ Require status checks to pass before merging
   - ✅ Require branches to be up to date before merging
   - ✅ Include administrators
5. Click "Create"

### Add Repository Topics
1. Go to: `https://github.com/yourusername/max-momentum`
2. Click "⚙️" next to "About"
3. Add topics:
   - `minecraft`
   - `minecraft-server`
   - `minestom`
   - `skyblock`
   - `bedwars`
   - `skywars`
   - `agpl-3`
4. Click "Save changes"

### Update Repository Description
```
Max Momentum - Minecraft Server Platform | SkyBlock, BedWars, SkyWars | AGPL-3.0
```

---

## STEP 11: POST-INITIALIZATION CLEANUP

### Remove Backup Files (Optional)
```bash
# If you created any backup files in the working directory
rm -f *.tar.gz
rm -f *.zip
rm -rf backup-test/
```

### Update .gitignore (If Needed)
```bash
# Verify .gitignore is correct
cat .gitignore

# Add any missing patterns
echo "*.tar.gz" >> .gitignore
echo "*.zip" >> .gitignore
echo "backup-*/" >> .gitignore

# Commit changes
git add .gitignore
git commit -m "Update .gitignore for backup files"
git push origin main
```

---

## TROUBLESHOOTING

### Issue: "fatal: not a git repository"
**Solution**: You haven't run `git init` yet. Go back to Step 1.

### Issue: "fatal: remote origin already exists"
**Solution**: Remove existing remote and re-add:
```bash
git remote remove origin
git remote add origin https://github.com/yourusername/max-momentum.git
```

### Issue: "error: failed to push some refs"
**Solution**: Pull first (if remote has commits):
```bash
git pull origin main --allow-unrelated-histories
git push origin main
```

### Issue: "Permission denied (publickey)"
**Solution**: Use HTTPS instead of SSH, or set up SSH keys:
```bash
# Use HTTPS
git remote set-url origin https://github.com/yourusername/max-momentum.git

# OR set up SSH keys
ssh-keygen -t ed25519 -C "your.email@example.com"
# Add key to GitHub: https://github.com/settings/keys
```

### Issue: "Repository not found"
**Solution**: Verify repository exists and URL is correct:
```bash
# Check remote URL
git remote -v

# Update if wrong
git remote set-url origin https://github.com/yourusername/max-momentum.git
```

---

## ROLLBACK PROCEDURE

### If Something Goes Wrong

1. **Stop immediately**
2. **Do NOT push to GitHub** (if you haven't already)
3. **Remove .git directory**:
   ```bash
   rm -rf .git
   ```
4. **Restore from backup**:
   ```bash
   cd C:\Users\rishh\OneDrive\Desktop\Projects
   rm -rf HypixelSkyBlock-master/
   tar -xzvf HypixelSkyBlock-backup-20250228.tar.gz
   ```
5. **Analyze what went wrong**
6. **Try again**

---

## VERIFICATION CHECKLIST

After completing all steps, verify:

- [ ] **Git repository initialized** (`.git` directory exists)
- [ ] **Initial commit created** (1 commit in history)
- [ ] **Branch renamed to main** (`git branch` shows `main`)
- [ ] **Remote origin configured** (`git remote -v` shows GitHub URL)
- [ ] **Code pushed to GitHub** (repository visible on GitHub)
- [ ] **Commit count is 1** (`git rev-list --count HEAD` returns 1)
- [ ] **No upstream remote** (`git remote show` only shows `origin`)
- [ ] **No fork relationship** (no "forked from" badge on GitHub)
- [ ] **LICENSE file present** (AGPL-3.0 visible on GitHub)
- [ ] **anticheat/LICENSE.md present** (MIT license visible)
- [ ] **AGPL-3.0 compliance notice added** (README or in-game)
- [ ] **Repository is public** (AGPL-3.0 requirement)
- [ ] **All files present** (2000+ Java files visible)
- [ ] **Build files intact** (build.gradle.kts, settings.gradle.kts)
- [ ] **Configuration files intact** (configuration/ directory)

**If all items are checked, Phase 0 is complete** ✅

---

## NEXT STEPS

After Phase 0 completion:

1. ✅ Review `PHASE_0_VERIFICATION.md`
2. ✅ Run verification tests
3. ✅ Confirm build still works
4. ✅ Document Phase 0 completion
5. ⏭️ Proceed to Phase 1 (Visual Rebrand) when ready

**DO NOT** proceed to Phase 1 until all verification checks pass.

---

## SUMMARY

**Phase 0 Workflow**: ✅ **SIMPLIFIED** (no history to wipe)

**Steps**:
1. Initialize git repository (`git init`)
2. Create initial commit (`git commit`)
3. Rename branch to main (`git branch -M main`)
4. Connect to GitHub (`git remote add origin`)
5. Push to GitHub (`git push -u origin main`)

**Time Required**: ~5 minutes  
**Complexity**: ✅ **VERY LOW**  
**Risk**: ✅ **MINIMAL**  
**Reversibility**: ✅ **EASY** (just delete `.git` directory)

**Status**: ⏳ **AWAITING APPROVAL**

**⚠️ DO NOT EXECUTE WITHOUT EXPLICIT HUMAN APPROVAL ⚠️**
