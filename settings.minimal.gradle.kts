rootProject.name = "HypixelSkyBlock"

// Core infrastructure
include(":packer")
include(":commons")
include(":dungeons")
include(":loader")
include(":spark")
include(":pvp")

// Proxy
include(":velocity.extension")
include(":proxy.api")

// Base types
include(":service.generic")
include(":type.generic")
include(":type.skyblockgeneric")
include(":type.lobby")

// All SkyBlock servers
include(":type.prototypelobby")
include(":type.thefarmingislands")
include(":type.spidersden")
include(":type.theend")
include(":type.crimsonisle")
include(":type.goldmine")
include(":type.deepcaverns")
include(":type.dwarvenmines")
include(":type.thepark")
include(":type.galatea")
include(":type.backwaterbayou")
include(":type.jerrysworkshop")
include(":type.island")
include(":type.hub")
include(":type.dungeonhub")

// Mini-games: BedWars
include(":type.bedwarslobby")
include(":type.bedwarsgame")
include(":type.bedwarsconfigurator")

// Mini-games: SkyWars
include(":type.skywarslobby")
include(":type.skywarsgame")
include(":type.skywarsconfigurator")

// Ravenguard (if needed)
include(":type.ravengardgeneric")
include(":type.ravengardlobby")

// All services
include(":service.auctionhouse")
include(":service.bazaar")
include(":service.itemtracker")
include(":service.api")
include(":service.datamutex")
include(":service.party")
include(":service.orchestrator")
include(":service.darkauction")
include(":service.friend")
include(":service.punishment")

// Anticheat
include(":anticheat")
