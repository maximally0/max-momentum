# FORENSIC SERVER INTELLIGENCE REPORT
## Hypixel SkyBlock Recreation - Complete Reverse Engineering Audit

**Report Generated**: 2025-02-28  
**Codebase**: HypixelSkyBlock (Minestom-based)  
**Purpose**: Complete forensic analysis for safe rebranding and modification

---

## 0. METADATA

### Server Software
- **Type**: Minestom (Custom Minecraft Server Implementation)
- **NOT Spigot/Paper**: This is a from-scratch server using Minestom framework
- **Proxy**: Velocity 3.5.0-SNAPSHOT

### Minecraft Version
- **Target Version**: 1.21.11
- **Cross-Version Support**: 1.8-1.21 via ViaVersion/ViaBackwards/ViaRewind

### Java Version
- **Required**: Java 25
- **Toolchain**: JavaLanguageVersion.of(25)
- **JVM Args**: `-Xmx2g -Xms512m -XX:+UseG1GC -Dfile.encoding=UTF-8`

### Build Tool
- **Primary**: Gradle 8.x with Kotlin DSL
- **Build Files**: 
  - Root: `build.gradle.kts`
  - Settings: `settings.gradle.kts`
  - Properties: `gradle.properties`
- **Multi-Module**: 40+ Gradle subprojects

### Operating System
- **Primary Target**: Linux (Docker-based deployment)
- **Supported**: Windows, macOS (development)
- **Shell Scripts**: Bash (`.sh` files)

### Module Count
- **Total Modules**: 40+ Gradle subprojects
- **Game Server Types**: 27 (SkyBlock islands + mini-games)
- **Microservices**: 10 independent services
- **Core Libraries**: 3 (commons, loader, packer)


### Detected Counts

**Java Classes**: Estimated 2000+ classes across all modules

**Configuration Files**:
- YAML: 50+ files (achievements, quests, levels, kits, perks, collections, skills)
- JSON: 20+ files (maps, configs, pack metadata)
- Properties: 30+ files (i18n translations)
- TOML: 2 files (velocity.toml, server.toml)
- CSV: 3 files (crystals, fairy souls, regions)

**Custom Plugins**: 0 (This is NOT a plugin-based server)
- This is a monolithic Minestom application
- No Bukkit/Spigot plugin system
- All functionality is compiled into JARs

**String Literals**: Estimated 10,000+ across codebase
- Heavy use of Minecraft color codes (§)
- Extensive i18n system with .properties files
- Hardcoded strings in Java for GUI titles, messages, commands

---

## 1. FULL FILESYSTEM TREE

### Root Structure
```
HypixelSkyBlock/
├── .github/                    # GitHub workflows, issue templates
├── anticheat/                  # Standalone anti-cheat module (MIT licensed)
├── commons/                    # Shared code library (CRITICAL - used by all modules)
├── configuration/              # All YAML/JSON/properties configs (HIGH RISK)
├── DockerFiles/                # Docker build files
├── dungeons/                   # Dungeons system module
├── gradle/                     # Gradle wrapper
├── loader/                     # Server bootstrap/entry point (CRITICAL)
├── packer/                     # Resource pack server
├── proxy.api/                  # Proxy communication API
├── pvp/                        # PvP mechanics library
├── server/                     # Velocity proxy JAR location
├── service.*/                  # 10 microservices (see below)
├── setup/                      # Installation scripts
├── spark/                      # Performance profiling integration
├── type.*/                     # 27 game server types (see below)
├── velocity.extension/         # Velocity proxy plugin (CRITICAL)
├── website/                    # Documentation site (VitePress)
├── build.gradle.kts            # Root build configuration
├── settings.gradle.kts         # Module registry (CRITICAL)
├── docker-compose.yml          # Full deployment orchestration
└── README.md                   # Project documentation
```


### Directory Risk Assessment

#### CRITICAL RISK (Breaking changes will crash server)
- **commons/**: Shared by ALL modules. Package rename = catastrophic failure
- **loader/**: Main entry point. Contains Hypixel.java bootstrap
- **velocity.extension/**: Proxy plugin. Hardcoded plugin ID "skyblock"
- **settings.gradle.kts**: Module registry. Removing modules breaks build
- **configuration/**: Runtime configs. Missing files = startup failure

#### HIGH RISK (Breaking changes affect major features)
- **type.generic/**: Base class for all game servers
- **type.skyblockgeneric/**: Base for all SkyBlock islands
- **service.generic/**: Base for all microservices
- **proxy.api/**: Communication protocol between proxy and servers

#### MODERATE RISK (Isolated features, can be disabled)
- **type.*/**: Individual game server types (can be excluded from build)
- **service.*/**: Individual microservices (can be disabled)
- **anticheat/**: Standalone module with own license

#### LOW RISK (Optional/development)
- **website/**: Documentation only
- **setup/**: Installation scripts
- **DockerFiles/**: Deployment configs
- **.github/**: CI/CD workflows

### Key Directories Explained

**configuration/** (HIGH RISK - Runtime Dependencies)
```
configuration/
├── achievements/          # Achievement definitions by game mode
│   ├── bedwars/          # BedWars achievements (challenge, seasonal, tiered)
│   ├── skyblock/         # SkyBlock achievements
│   └── skywars/          # SkyWars achievements
├── bedwars/              # BedWars map definitions (maps.json)
├── i18n/                 # Internationalization (BRANDING HEAVY)
│   ├── en_US/           # English translations
│   │   ├── gui/         # GUI text (17 files)
│   │   ├── npcs/        # NPC dialogues (7 files)
│   │   ├── bedwars.properties
│   │   ├── commands.properties
│   │   ├── items.properties
│   │   ├── npcs.properties
│   │   ├── scoreboard.properties
│   │   └── tablist.properties
│   └── fi_FI/           # Finnish translations (partial)
├── leveling/             # Level rewards (rewards.yml)
├── quests/               # Quest definitions by game mode
│   ├── bedwars/         # Daily, weekly, challenge quests
│   └── skywars/         # Quest definitions
├── resourcepacks/        # Custom resource packs
├── skyblock/             # SkyBlock game data (MASSIVE)
│   ├── collections/     # Collection tiers (boss, combat, farming, fishing, foraging, mining)
│   ├── furniture/       # Furniture definitions
│   ├── items/           # Item definitions (1000+ YAML files)
│   ├── levels/          # Skill level definitions
│   ├── pack_textures/   # Custom textures
│   ├── reforges/        # Reforge stats
│   ├── skills/          # Skill definitions
│   ├── SkyBlockPack/    # Resource pack
│   └── songs/           # Note block songs
├── skywars/              # SkyWars configs (kits, levels, perks, soul well)
├── config.example.yml    # Main server config template
├── config.docker.yml     # Docker-specific config
├── velocity.toml         # Velocity proxy config
├── server.toml           # Minestom server config
├── mongo-init.sh         # MongoDB initialization
└── entrypoint.sh         # Docker entrypoint
```


**commons/** (CATASTROPHIC RISK - Core Library)
```
commons/
├── src/
│   ├── codegen/          # Code generation (ItemType enum from YAML)
│   ├── generated/        # Auto-generated code (DO NOT EDIT)
│   └── main/java/net/swofty/commons/
│       ├── bedwars/      # BedWars shared code
│       ├── config/       # Configuration provider
│       ├── friend/       # Friend system
│       ├── game/         # Game mechanics
│       ├── impl/         # Service implementations
│       ├── party/        # Party system
│       ├── presence/     # Player presence
│       ├── protocol/     # Network protocol
│       ├── proxy/        # Proxy communication
│       ├── punishment/   # Ban/mute system
│       ├── service/      # Service framework
│       ├── skyblock/     # SkyBlock shared code
│       ├── skywars/      # SkyWars shared code
│       ├── Acronym.java
│       ├── ChatColor.java
│       ├── CustomWorlds.java  # World folder names (HARDCODED)
│       ├── ServerType.java    # Server type enum (CRITICAL)
│       ├── ServiceType.java   # Service type enum
│       └── StringUtility.java
└── build.gradle.kts
```

**Package**: `net.swofty.commons`  
**Used By**: ALL 40+ modules  
**Rename Risk**: CATASTROPHIC - breaks all imports

**type.generic/** (HIGH RISK - Base Game Server)
```
type.generic/
└── src/main/java/net/swofty/type/generic/
    ├── achievement/      # Achievement system
    ├── block/            # Block handlers
    ├── command/          # Command framework (HypixelCommand base)
    ├── data/             # Data handlers, MongoDB
    ├── entity/           # NPCs, holograms, custom entities
    ├── event/            # Event system (HypixelEventClass)
    ├── gui/              # Inventory GUI framework
    ├── i18n/             # Internationalization system
    ├── item/             # Item system
    ├── leaderboard/      # Leaderboard service
    ├── levels/           # Leveling system
    ├── minion/           # Minion system (SkyBlock)
    ├── mission/          # Mission/quest system
    ├── packet/           # Custom packets
    ├── protocol/         # Network protocol
    ├── quest/            # Quest system
    ├── region/           # Region system
    ├── scoreboard/       # Scoreboard system (HypixelScoreboard)
    ├── skill/            # Skill system
    ├── tab/              # Tab list system
    ├── user/             # Player class (HypixelPlayer base)
    ├── utility/          # Utilities
    ├── HypixelConst.java      # Constants (BRANDING)
    ├── HypixelGenericLoader.java  # Loader base class
    └── SkyBlockTypeLoader.java    # SkyBlock loader interface
```

**Package**: `net.swofty.type.generic`  
**Used By**: All 27 game server types  
**Rename Risk**: HIGH - breaks all game servers


**velocity.extension/** (CRITICAL RISK - Proxy Plugin)
```
velocity.extension/
└── src/main/java/net/swofty/velocity/
    ├── command/          # Proxy commands (/limbo, /protocolversion, /serverstatus)
    ├── data/             # MongoDB databases (UserDatabase, ProfilesDatabase, CoopDatabase)
    ├── gamemanager/      # Server load balancing
    ├── packet/           # Packet handlers
    ├── presence/         # Player presence publisher
    ├── redis/            # Redis listeners (15+ channels)
    ├── testflow/         # Test flow system
    ├── viaversion/       # Cross-version support
    └── SkyBlockVelocity.java  # Main plugin class (@Plugin annotation)
```

**Plugin ID**: `skyblock` (HARDCODED in @Plugin annotation)  
**Plugin Name**: `SkyBlock` (HARDCODED)  
**Package**: `net.swofty.velocity`  
**Rename Risk**: CRITICAL - proxy won't load if ID changes

---

## 2. DEPENDENCY GRAPH

### Module Dependencies

**Core Dependencies** (Everything depends on these):
```
commons
  ├── Used by: ALL 40+ modules
  ├── Contains: ServerType, ServiceType, CustomWorlds, protocol objects
  └── Package: net.swofty.commons
```

**Loader Chain**:
```
loader
  ├── Depends on: commons
  ├── Contains: Hypixel.java (main entry point)
  ├── Bootstraps: All game server types
  └── Package: net.swofty.loader
```

**Game Server Hierarchy**:
```
type.generic (base for all game servers)
  ├── Depends on: commons, loader
  ├── Provides: HypixelPlayer, HypixelCommand, HypixelEventClass, HypixelNPC
  └── Extended by: type.lobby, type.skyblockgeneric

type.lobby (base for lobby servers)
  ├── Depends on: type.generic
  ├── Provides: LobbyTypeLoader, lobby events, lobby items
  └── Extended by: type.prototypelobby, type.bedwarslobby, type.skywarslobby

type.skyblockgeneric (base for SkyBlock servers)
  ├── Depends on: type.generic
  ├── Provides: SkyBlockPlayer, SkyBlockTypeLoader, SkyBlock-specific systems
  └── Extended by: All 14 SkyBlock island types

type.island, type.hub, type.thefarmingislands, etc.
  ├── Depends on: type.skyblockgeneric
  ├── Implements: Specific island logic, NPCs, events
  └── Package: net.swofty.type.[islandname]
```


**Service Hierarchy**:
```
service.generic (base for all services)
  ├── Depends on: commons
  ├── Provides: SkyBlockService interface, Redis integration
  └── Extended by: All 10 microservices

service.auctionhouse, service.bazaar, service.party, etc.
  ├── Depends on: service.generic, commons
  ├── Standalone JARs with main() methods
  └── Communicate via Redis pub/sub
```

**Proxy**:
```
velocity.extension
  ├── Depends on: commons, proxy.api
  ├── Velocity plugin (loads into Velocity proxy)
  ├── Manages: Player routing, load balancing, authentication
  └── Package: net.swofty.velocity
```

### External Dependencies (from build.gradle.kts)

**Core Libraries**:
- Minestom (server framework) - via mavenLocal()
- Velocity API (proxy) - via maven("https://repo.viaversion.com")
- MongoDB Driver - via mavenCentral()
- Redis (Jedis/Lettuce) - via mavenCentral()

**Cross-Version Support**:
- ViaVersion - via maven("https://repo.viaversion.com")
- ViaBackwards - via maven("https://repo.viaversion.com")
- ViaRewind - via maven("https://repo.viaversion.com")

**Utilities**:
- Lombok 1.18.42 - code generation
- Jackson 3.1.0 - JSON serialization
- Reflections 0.10.2 - package scanning
- org.json 20251224 - JSON parsing
- Sentry 8.30.0 - error tracking

**Testing**:
- JUnit Jupiter 6.0.3

### Event Listener Dependencies

**Minestom Event System** (NOT Bukkit):
- Uses `EventNode<EntityInstanceEvent>`
- Uses `EventListener.builder(EventClass.class).handler(...)`
- Custom event system: `HypixelEventClass`

**Custom Events** (type.generic):
```java
// Base class for all custom events
public abstract class HypixelEventClass {
    // Loaded via reflection from package scanning
}
```

**Event Registration Pattern**:
```java
// In each server type loader:
List<HypixelEventClass> getTraditionalEvents() {
    return HypixelGenericLoader.loopThroughPackage(
        "net.swofty.type.[typename].events",
        HypixelEventClass.class
    ).collect(Collectors.toList());
}
```

**Event Packages by Server Type**:
- `net.swofty.type.island.events`
- `net.swofty.type.hub.events`
- `net.swofty.type.thefarmingislands.events`
- etc. (one per server type)


### Shared Utilities

**StringUtility** (commons):
- `toNormalCase()` - converts ENUM_NAME to Normal Case
- `commaify()` - adds commas to numbers
- `shortenNumber()` - 1000 → 1k
- Used by: ALL modules for display formatting

**ConfigProvider** (commons):
- Loads `config.yml` from `./configuration_files/`
- Provides: MongoDB URL, Redis URL, Velocity secret
- Used by: ALL services and game servers

**HypixelConst** (type.generic):
- `getInstanceContainer()` - gets main world instance
- `getServerName()` - returns server display name
- Used by: All game servers for world access

---

## 3. COMPLETE STRING EXTRACTION INDEX

### Branding Strings (CRITICAL - HIGH VISIBILITY)

#### "Hypixel" References

**File**: `velocity.extension/src/main/java/net/swofty/velocity/SkyBlockVelocity.java`
- Line 395: `Component.text("                §aHypixel Recreation §c[1.8-1.21]")`
  - Context: Server list MOTD
  - Change Safety: SAFE - cosmetic only
  - Location: Ping response handler

- Line 90: `Component.text("§cThere are no Hypixel (type=" + type.name() + ") servers available at the moment.")`
  - Context: Error message when no servers available
  - Change Safety: SAFE - error message
  - Location: TransferHandler.java

**File**: `README.md`
- Line 1: `# Hypixel SkyBlock`
  - Context: Project title
  - Change Safety: SAFE - documentation only

- Line 5: `A 1.21.11 Minestom-based recreation of Hypixel SkyBlock`
  - Context: Project description
  - Change Safety: SAFE - documentation only

**File**: `settings.gradle.kts`
- Line 1: `rootProject.name = "HypixelSkyBlock"`
  - Context: Gradle project name
  - Change Safety: RISKY - affects JAR names, may break Docker builds
  - Impact: Changes output JAR names (HypixelCore.jar → [NewName]Core.jar)


#### "SkyBlock" References (1000+ occurrences)

**High-Impact Locations**:

**Class Names** (CATASTROPHIC RISK):
- `SkyBlockPlayer` (type.skyblockgeneric) - Base player class for all SkyBlock servers
- `SkyBlockTypeLoader` (type.generic) - Interface for SkyBlock server types
- `SkyBlockGenericLoader` (type.skyblockgeneric) - Loader for SkyBlock servers
- `SkyBlockVelocity` (velocity.extension) - Main proxy plugin class
- `SkyBlockService` (service.generic) - Base service interface
- `SkyBlockDataHandler` (type.skyblockgeneric) - Player data handler
- `SkyBlockScoreboard` (type.generic) - Scoreboard system
- `SkyBlockActionBar` (type.skyblockgeneric) - Action bar system
- `SkyBlockRecipe` (type.skyblockgeneric) - Recipe system
- `SkyBlockCalendar` (type.skyblockgeneric) - In-game calendar
- `SkyBlockCommand` (type.skyblockgeneric) - Command base class

**Rename Risk**: CATASTROPHIC - These are referenced in 100+ files each

**Package Names** (CATASTROPHIC RISK):
- `net.swofty.type.skyblockgeneric` - Base package for SkyBlock
- `net.swofty.type.skyblockgeneric.*` - 50+ subpackages

**Rename Risk**: CATASTROPHIC - Breaks all imports, reflection, package scanning

**Enum Values** (HIGH RISK):
```java
// commons/src/main/java/net/swofty/commons/ServerType.java
public enum ServerType {
    SKYBLOCK_ISLAND(true),
    SKYBLOCK_HUB(true),
    SKYBLOCK_SPIDERS_DEN(true),
    SKYBLOCK_THE_END(true),
    SKYBLOCK_CRIMSON_ISLE(true),
    SKYBLOCK_DUNGEON_HUB(true),
    SKYBLOCK_THE_FARMING_ISLANDS(true),
    SKYBLOCK_GOLD_MINE(true),
    SKYBLOCK_DEEP_CAVERNS(true),
    SKYBLOCK_DWARVEN_MINES(true),
    SKYBLOCK_THE_PARK(true),
    SKYBLOCK_GALATEA(true),
    SKYBLOCK_BACKWATER_BAYOU(true),
    SKYBLOCK_JERRYS_WORKSHOP(true),
    // ... other types
}
```
**Rename Risk**: HIGH - Used in switch statements, string comparisons, database keys

**World Folder Names** (HIGH RISK):
```java
// commons/src/main/java/net/swofty/commons/CustomWorlds.java
public enum CustomWorlds {
    SKYBLOCK_ISLAND_TEMPLATE("hypixel_skyblock_island_template"),
    SKYBLOCK_HUB("hypixel_skyblock_hub"),
    SKYBLOCK_SPIDERS_DEN("hypixel_skyblock_spiders_den"),
    // ... etc
}
```
**Rename Risk**: HIGH - Changing these breaks world loading
**File Path**: `./configuration/skyblock/islands/[foldername]`


#### GUI Titles (SAFE - Player-Facing Only)

**File**: `type.generic/src/main/java/net/swofty/type/generic/gui/inventory/HypixelInventoryGUI.java`
- Base class for all GUIs
- Title passed to constructor
- Change Safety: SAFE - cosmetic only

**Examples** (from type.skyblockgeneric):
- `"SkyBlock Menu"` - Main menu
- `"Your Skills"` - Skills GUI
- `"Collections"` - Collections menu
- `"Recipe Book"` - Crafting recipes
- `"Auction House"` - Auction browser
- `"Bazaar"` - Bazaar trading
- `"Your Profile"` - Player profile
- `"Wardrobe"` - Cosmetics
- `"Pets"` - Pet management

**Change Safety**: SAFE - These are display strings only

#### Scoreboard Titles (SAFE - Player-Facing Only)

**File**: `type.skyblockgeneric/src/main/java/net/swofty/type/skyblockgeneric/SkyBlockScoreboard.java`
```java
private static String getSidebarName(int counter) {
    String baseText = "SKYBLOCK";
    // Animated title with color cycling
}
```
**Change Safety**: SAFE - cosmetic only

**File**: `configuration/i18n/en_US/scoreboard.properties`
```properties
scoreboard.skyblock.title_base=SKYBLOCK
scoreboard.skyblock.title_color_1=§e
scoreboard.skyblock.title_color_2=§6
```
**Change Safety**: SAFE - i18n file, easily modified

#### Tab List Headers (SAFE - Player-Facing Only)

**File**: `type.generic/src/main/java/net/swofty/type/generic/tab/TablistManager.java`
- Manages tab list display
- Headers/footers set per server type

**File**: `configuration/i18n/en_US/tablist.properties`
```properties
tablist.header=§aHypixel Network §7[1.8-1.21]
tablist.footer=§7Playing on §b{server}
```
**Change Safety**: SAFE - i18n file


#### NPC Names (SAFE - Player-Facing Only)

**File**: `configuration/i18n/en_US/npcs.properties`
```properties
npc.banker.name=Banker
npc.auction_master.name=Auction Master
npc.bazaar_agent.name=Bazaar Agent
# ... 100+ NPC names
```
**Change Safety**: SAFE - i18n file

**Hardcoded NPC Names** (in Java):
```java
// type.hub/src/main/java/net/swofty/type/hub/npcs/NPCBanker.java
public String[] holograms(HypixelPlayer player) {
    return new String[]{"Banker", "§e§lCLICK"};
}
```
**Change Safety**: SAFE - cosmetic only
**Count**: 100+ NPC classes across all server types

#### Item Display Names (SAFE - Player-Facing Only)

**File**: `configuration/skyblock/items/*.yml` (1000+ files)
```yaml
type: DIAMOND_SWORD
displayName: "§6Aspect of the Dragons"
lore:
  - "§7Damage: §c+225"
  - "§7Strength: §c+100"
```
**Change Safety**: SAFE - configuration files

**File**: `configuration/i18n/en_US/items.properties`
```properties
item.aspect_of_the_dragons.name=§6Aspect of the Dragons
item.aspect_of_the_dragons.lore.1=§7Damage: §c+225
```
**Change Safety**: SAFE - i18n file

#### Join/Leave Messages (SAFE - Player-Facing Only)

**File**: `type.generic/src/main/java/net/swofty/type/generic/event/actions/player/ActionPlayerJoin.java`
```java
player.sendMessage("§eWelcome to the server!");
```
**Change Safety**: SAFE - cosmetic only

**File**: `configuration/i18n/en_US/commands.properties`
```properties
join.welcome=§eWelcome to §aHypixel SkyBlock§e!
join.first_time=§7This is your first time joining!
```
**Change Safety**: SAFE - i18n file


### URLs and External Links

#### Discord Links

**File**: `README.md`
- Line 3: `https://discord.swofty.net`
  - Context: Discord invite link
  - Change Safety: SAFE - documentation only

**File**: `anticheat/README.md`
- Line 3: `https://discord.gg/atlasmc`
  - Context: Discord invite for anti-cheat support
  - Change Safety: SAFE - documentation only

#### GitHub Links

**File**: `README.md`
- Line 21: `https://github.com/Swofty-Developments/HypixelSkyBlock/releases`
- Line 22: `https://swofty-developments.github.io/HypixelSkyBlock/`
- Line 23: `https://github.com/Swofty-Developments/HypixelSkyBlock`
  - Change Safety: SAFE - documentation only

**File**: Multiple NPC files (type.thefarmingislands/npcs/*.java)
```java
.clickEvent(ClickEvent.openUrl("https://github.com/Swofty-Developments/HypixelSkyBlock"))
```
- Context: "Feature not implemented" message with GitHub link
- Change Safety: SAFE - can be removed or changed

#### Documentation Links

**File**: `README.md`
- `https://opensource.swofty.net` - Documentation site
  - Change Safety: SAFE - external documentation

#### Email Addresses
- **None found** in codebase

### Database Keys and Collection Names

#### MongoDB Collections (HIGH RISK)

**Database Name**: `Minestom` (HARDCODED)

**Collections**:
```java
// velocity.extension/src/main/java/net/swofty/velocity/data/UserDatabase.java
collection = database.getCollection("profiles");

// velocity.extension/src/main/java/net/swofty/velocity/data/ProfilesDatabase.java
collection = database.getCollection("data");

// velocity.extension/src/main/java/net/swofty/velocity/data/CoopDatabase.java
collection = database.getCollection("coop");
```

**Rename Risk**: HIGH - Changing collection names breaks data access
**Migration Required**: Yes - data must be migrated to new collection names

