# CRITICAL REBRAND FINDINGS - EXECUTIVE SUMMARY

## ⚠️ CATASTROPHIC RISK ITEMS (DO NOT RENAME)

### 1. Package Names
**Package**: `net.swofty.*`
- Used in: ALL 40+ modules (2000+ Java files)
- Referenced by: Reflection, package scanning, serialization
- **Impact if renamed**: Complete system failure, all imports break
- **Recommendation**: NEVER rename base package

### 2. Core Class Names
**Classes**: `HypixelPlayer`, `SkyBlockPlayer`, `HypixelCommand`, `HypixelEventClass`, `HypixelNPC`
- Used in: 500+ files each
- Referenced by: Inheritance, type casting, reflection
- **Impact if renamed**: Compilation failure, runtime crashes
- **Recommendation**: Keep internal names, rebrand display strings only

### 3. Gradle Project Name
**File**: `settings.gradle.kts`
**Line 1**: `rootProject.name = "HypixelSkyBlock"`
- **Impact if renamed**: Changes JAR output names
  - `HypixelCore.jar` → `[NewName]Core.jar`
  - Docker compose expects `HypixelCore.jar`
  - Service JARs: `ServiceAuctionHouse.jar`, etc.
- **Recommendation**: Update Docker files if changing

### 4. Velocity Plugin ID
**File**: `velocity.extension/src/main/java/net/swofty/velocity/SkyBlockVelocity.java`
**Line 88**: `@Plugin(id = "skyblock", name = "SkyBlock")`
- **Impact if renamed**: Proxy won't load plugin
- **Recommendation**: Can change, but requires Velocity restart

### 5. MongoDB Database Name
**Hardcoded**: `Minestom`
- **Impact if renamed**: All data access fails
- **Recommendation**: Keep or migrate data

### 6. ServerType Enum Values
**File**: `commons/src/main/java/net/swofty/commons/ServerType.java`
**Values**: `SKYBLOCK_ISLAND`, `SKYBLOCK_HUB`, etc.
- Used in: Switch statements, string comparisons, database keys
- **Impact if renamed**: Server routing breaks, data corruption
- **Recommendation**: Keep enum names, change display strings


## ✅ SAFE TO REBRAND (Player-Facing Only)

### 1. Server MOTD
**File**: `velocity.extension/src/main/java/net/swofty/velocity/SkyBlockVelocity.java`
**Line 395**: `Component.text("                §aHypixel Recreation §c[1.8-1.21]")`
- **Change Safety**: SAFE - cosmetic only
- **Impact**: Server list display name

### 2. GUI Titles
**Files**: All `GUI*.java` files in `type.*/gui/` directories
**Examples**: `"SkyBlock Menu"`, `"Collections"`, `"Auction House"`
- **Change Safety**: SAFE - display strings only
- **Impact**: In-game menu titles

### 3. Scoreboard Titles
**File**: `configuration/i18n/en_US/scoreboard.properties`
**Key**: `scoreboard.skyblock.title_base=SKYBLOCK`
- **Change Safety**: SAFE - i18n file
- **Impact**: Sidebar title

### 4. Tab List Headers/Footers
**File**: `configuration/i18n/en_US/tablist.properties`
**Keys**: `tablist.header`, `tablist.footer`
- **Change Safety**: SAFE - i18n file
- **Impact**: Tab list display

### 5. NPC Names and Dialogues
**Files**: `configuration/i18n/en_US/npcs/*.properties`
- **Change Safety**: SAFE - i18n files
- **Impact**: NPC display names and chat messages

### 6. Item Names and Lore
**Files**: `configuration/skyblock/items/*.yml` (1000+ files)
- **Change Safety**: SAFE - configuration files
- **Impact**: Item display names and descriptions

### 7. Documentation
**Files**: `README.md`, `website/**/*`, `*.md`
- **Change Safety**: SAFE - documentation only
- **Impact**: None on server functionality

### 8. Discord/GitHub Links
**Files**: `README.md`, NPC click events
- **Change Safety**: SAFE - external links
- **Impact**: None on server functionality


## ⚠️ MEDIUM RISK (Requires Testing)

### 1. World Folder Names
**File**: `commons/src/main/java/net/swofty/commons/CustomWorlds.java`
**Values**: `"hypixel_skyblock_hub"`, `"hypixel_skyblock_island_template"`, etc.
- **Change Safety**: MEDIUM - requires world file migration
- **Impact**: World loading fails if folders don't match
- **Recommendation**: Rename folders in `./configuration/skyblock/islands/` to match

### 2. Module Names in settings.gradle.kts
**File**: `settings.gradle.kts`
**Lines**: `include(":type.island")`, `include(":service.auctionhouse")`, etc.
- **Change Safety**: MEDIUM - affects build system
- **Impact**: Gradle build fails if module names don't match folder names
- **Recommendation**: Keep module names matching folder structure

### 3. Docker Compose Service Names
**File**: `docker-compose.yml`
**Services**: `hypixelcore_island`, `hypixelcore_hub`, etc.
- **Change Safety**: MEDIUM - affects container orchestration
- **Impact**: Docker networking breaks if service names change
- **Recommendation**: Update all service references consistently

### 4. JAR File Names
**Expected by Docker**: `HypixelCore.jar`, `ServiceAuctionHouse.jar`, etc.
- **Change Safety**: MEDIUM - requires Docker file updates
- **Impact**: Docker containers fail to start if JARs not found
- **Recommendation**: Update Dockerfile COPY commands

## 🔧 TECHNICAL DEBT ASSESSMENT

### Tightly Coupled Modules
1. **commons** → ALL modules (40+ dependencies)
2. **type.generic** → All game servers (27 dependencies)
3. **type.skyblockgeneric** → All SkyBlock islands (14 dependencies)

### Magic Strings (Anti-Pattern)
- Hardcoded collection names: `"profiles"`, `"data"`, `"coop"`
- Hardcoded database name: `"Minestom"`
- Hardcoded world folder prefixes: `"hypixel_"`
- Hardcoded plugin ID: `"skyblock"`

### Reflection Usage (Fragile)
```java
// Package scanning for events, commands, NPCs
HypixelGenericLoader.loopThroughPackage(
    "net.swofty.type.island.events",
    HypixelEventClass.class
)
```
**Risk**: Package renames break reflection

### Serialization Coupling
- MongoDB stores Java class names in documents
- Changing class names breaks deserialization
- **Recommendation**: Use DTOs for persistence


## 📋 SAFE PHASED REBRAND STRATEGY

### Phase 1: Visual Rebrand Only (LOW RISK)
**Duration**: 1-2 days  
**Rollback**: Easy - revert config files

**Steps**:
1. Update `configuration/i18n/en_US/*.properties`
   - Change all display strings
   - Update scoreboard titles
   - Update tab list headers
   - Update GUI titles

2. Update `README.md` and documentation
   - Change project description
   - Update Discord/GitHub links
   - Update branding text

3. Update server MOTD
   - `velocity.extension/src/main/java/net/swofty/velocity/SkyBlockVelocity.java:395`
   - Change ping response text

4. Update NPC dialogues
   - `configuration/i18n/en_US/npcs/*.properties`

5. Update item names (optional)
   - `configuration/skyblock/items/*.yml`

**Testing**: Join server, check all visible text

### Phase 2: Configuration Rebrand (MEDIUM RISK)
**Duration**: 1 week  
**Rollback**: Medium - requires backup restoration

**Steps**:
1. Backup MongoDB database
   ```bash
   mongodump --db Minestom --out backup/
   ```

2. Update world folder names
   - Rename folders in `./configuration/skyblock/islands/`
   - Update `CustomWorlds.java` enum values
   - Test world loading

3. Update Docker service names
   - Modify `docker-compose.yml`
   - Update service references
   - Test container orchestration

4. Update JAR names (if needed)
   - Modify `settings.gradle.kts` rootProject.name
   - Update Dockerfile COPY commands
   - Rebuild all JARs

**Testing**: Full deployment test, verify all services start


### Phase 3: Deep Structural Rename (HIGH RISK - NOT RECOMMENDED)
**Duration**: 1-2 months  
**Rollback**: DIFFICULT - requires full codebase revert

**⚠️ WARNING**: Only attempt if absolutely necessary

**Steps**:
1. Create feature branch
2. Rename package `net.swofty` → `net.yourname`
   - Use IDE refactoring tools
   - Update ALL imports (2000+ files)
   - Update reflection package strings
   - Update serialization class names

3. Rename core classes
   - `HypixelPlayer` → `YourPlayer`
   - `SkyBlockPlayer` → `YourGamePlayer`
   - Update ALL references (500+ files each)

4. Update MongoDB schema
   - Migrate data to new collection names
   - Update all database queries
   - Test data integrity

5. Update Velocity plugin
   - Change plugin ID in @Plugin annotation
   - Update plugin.yml (if exists)
   - Test proxy loading

**Testing**: 
- Full regression testing
- Load testing
- Data migration verification
- Cross-version compatibility testing

**Recommendation**: DON'T DO THIS unless rebranding internal code is legally required

### Phase 4: Cleanup (LOW RISK)
**Duration**: 1 week

**Steps**:
1. Remove old comments referencing "Hypixel"
2. Update code documentation
3. Update JavaDocs
4. Update build scripts
5. Update CI/CD pipelines

## 🚨 BREAK SIMULATIONS

### Scenario 1: Rename Package `net.swofty` → `net.newname`
**Consequences**:
- ❌ ALL 2000+ Java files fail to compile
- ❌ Reflection-based package scanning breaks
- ❌ Serialized data in MongoDB becomes unreadable
- ❌ Redis message routing fails
- ❌ Service discovery breaks
- ❌ Event system stops working
- ❌ Command registration fails
- ❌ NPC loading breaks

**Recovery**: Revert all changes, restore from backup


### Scenario 2: Rename `HypixelPlayer` → `CustomPlayer`
**Consequences**:
- ❌ 500+ files fail to compile
- ❌ Type casting breaks: `(HypixelPlayer) player`
- ❌ Inheritance chain breaks in all game servers
- ❌ Event handlers fail to recognize player type
- ❌ Data handlers can't deserialize player data
- ❌ Command executors fail type checks

**Recovery**: Revert all changes, rebuild all modules

### Scenario 3: Rename MongoDB Database `Minestom` → `NewName`
**Consequences**:
- ❌ All data access fails immediately
- ❌ Player profiles not found
- ❌ Coop data inaccessible
- ❌ Authentication fails
- ❌ Server won't start (connection errors)

**Recovery**: 
1. Rename database back: `use admin; db.adminCommand({renameDatabase: "NewName", to: "Minestom"})`
2. OR update all database connection strings

### Scenario 4: Rename World Folders
**Consequences**:
- ❌ Worlds fail to load
- ❌ Players spawn in void
- ❌ Server crashes on world access
- ❌ NPCs don't spawn
- ❌ Regions don't load

**Recovery**: Rename folders back to original names

### Scenario 5: Change Velocity Plugin ID
**Consequences**:
- ❌ Proxy fails to load plugin
- ❌ No player routing
- ❌ No load balancing
- ❌ Players can't connect to game servers

**Recovery**: Change plugin ID back, restart proxy

### Scenario 6: Rename JAR Files
**Consequences**:
- ❌ Docker containers fail to start
- ❌ Services don't launch
- ❌ Orchestration breaks

**Recovery**: Update Dockerfile COPY commands, rebuild containers


## 📊 REBRAND RISK MATRIX

| Item | Risk Level | Impact | Effort | Recommendation |
|------|-----------|--------|--------|----------------|
| Package names | CATASTROPHIC | Complete failure | Months | NEVER |
| Core class names | CATASTROPHIC | Compilation failure | Months | NEVER |
| MongoDB database name | HIGH | Data loss | Hours | Keep or migrate |
| ServerType enum values | HIGH | Routing failure | Weeks | Keep internal |
| World folder names | MEDIUM | World loading fails | Days | Can rename with care |
| GUI titles | LOW | Cosmetic only | Hours | SAFE |
| Scoreboard titles | LOW | Cosmetic only | Minutes | SAFE |
| Tab list text | LOW | Cosmetic only | Minutes | SAFE |
| NPC names | LOW | Cosmetic only | Hours | SAFE |
| Item names | LOW | Cosmetic only | Days | SAFE |
| Documentation | LOW | None | Hours | SAFE |
| Server MOTD | LOW | Cosmetic only | Minutes | SAFE |
| Discord links | LOW | None | Minutes | SAFE |
| JAR names | MEDIUM | Docker breaks | Hours | Update Docker |
| Docker service names | MEDIUM | Orchestration breaks | Hours | Update compose |
| Velocity plugin ID | MEDIUM | Proxy breaks | Minutes | Can change |

## 🎯 RECOMMENDED APPROACH

### For Public Server (Player-Facing Rebrand)
**Use Phase 1 Only**:
- Change all i18n files
- Update server MOTD
- Update documentation
- Keep all internal code unchanged

**Result**: Players see your brand, code remains stable

### For Private Fork (Full Rebrand)
**Use Phases 1-2**:
- Phase 1: Visual rebrand
- Phase 2: Configuration rebrand
- Skip Phase 3 (code rename)

**Result**: Your brand everywhere except internal code

### For Legal Compliance (Must Remove "Hypixel")
**Use All Phases** (with extreme caution):
- Hire experienced Java developers
- Budget 2-3 months
- Extensive testing required
- Expect bugs and issues

**Result**: Fully rebranded, but high risk of breakage


## 🔍 KEY TECHNICAL FINDINGS

### Architecture Type
- **Monolithic Minestom Application** (NOT plugin-based)
- **Microservices** for backend (10 services)
- **Velocity Proxy** for load balancing
- **NOT Spigot/Paper** - completely custom server

### Communication Patterns
- **Redis Pub/Sub** for inter-service messaging
- **MongoDB** for persistent storage
- **Direct TCP** for proxy-to-server communication

### Critical Dependencies
1. **Minestom** - Core server framework
2. **Velocity** - Proxy server
3. **MongoDB** - Database
4. **Redis** - Message broker
5. **ViaVersion** - Cross-version support

### Code Generation
- **ItemType enum** auto-generated from YAML configs
- **Location**: `commons/src/generated/`
- **Source**: `configuration/skyblock/items/*.yml`
- **Risk**: Regeneration required if item configs change

### Reflection Usage
- **Package scanning** for events, commands, NPCs
- **Pattern**: `HypixelGenericLoader.loopThroughPackage()`
- **Risk**: Package renames break discovery

### Serialization
- **Jackson** for JSON
- **MongoDB BSON** for database
- **Risk**: Class renames break deserialization

## 📝 FINAL RECOMMENDATIONS

### DO:
✅ Change all player-facing text (i18n files)  
✅ Update server MOTD and branding  
✅ Modify GUI titles and messages  
✅ Update documentation and links  
✅ Test thoroughly after each change  
✅ Keep backups of everything  

### DON'T:
❌ Rename package `net.swofty`  
❌ Rename core classes (`HypixelPlayer`, `SkyBlockPlayer`)  
❌ Change MongoDB database name without migration  
❌ Modify ServerType enum values  
❌ Rename modules without updating build files  
❌ Change anything without testing first  

### MAYBE (With Extreme Caution):
⚠️ Rename world folders (requires file migration)  
⚠️ Change JAR names (requires Docker updates)  
⚠️ Modify Docker service names (requires compose updates)  
⚠️ Update Velocity plugin ID (requires proxy restart)  

---

**Report Conclusion**: This is a complex, tightly-coupled codebase. Safe rebranding is possible for player-facing elements, but deep structural changes carry catastrophic risk. Recommend Phase 1 (visual rebrand) only for most use cases.

