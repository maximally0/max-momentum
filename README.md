# Max Momentum

A 1.21.11 Minestom-based Minecraft server platform with a properly abstracted, scalable microservices architecture.

> **Note**: This implementation is under active development and is not yet production-ready.

## Source Code Availability

This project is licensed under AGPL-3.0. Complete source code is available at:
**https://github.com/maximally0/max-momentum**

## Documentation

Documentation is available in the `website/` directory.

## Features

- **Multi-Server Architecture** - 13 server types (SkyBlock + BedWars)
- **Microservices** - 8 independent services (Auctions, Bazaar, Party, etc.)
- **Redis Communication** - Real-time inter-service messaging
- **MongoDB Storage** - Persistent data storage
- **Velocity Proxy** - Load balancing and player routing
- **Docker Support** - Full Docker Compose deployment
- **Java 25** - Modern Java with virtual threads

## Requirements

- 16GB+ RAM
- 6+ CPU Cores
- Java 25
- MongoDB
- Redis

See the [full requirements](https://opensource.swofty.net/docs/requirements) for details.

## Quick Start

```bash
# Clone the repository
git clone https://github.com/maximally0/max-momentum.git

# Docker deployment
docker-compose up --build
```

For manual setup, see documentation in `website/docs/`.

## Credits

Built with Minestom. Forked from [HypixelSkyBlock](https://github.com/Swofty-Developments/HypixelSkyBlock) (AGPL-3.0).

## License

This project is licensed under AGPL-3.0. See LICENSE file for details.
