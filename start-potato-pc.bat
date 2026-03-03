@echo off
REM Max Momentum - Potato PC Startup Script (Windows)
REM For systems with 4GB RAM

echo.
echo 🥔 Max Momentum - Potato PC Mode
echo ==================================
echo.

REM Check if Docker is running
docker info >nul 2>&1
if errorlevel 1 (
    echo ❌ ERROR: Docker is not running!
    echo    Please start Docker Desktop and try again.
    pause
    exit /b 1
)

echo ✅ Docker is running
echo.

REM Check if config exists
if not exist "configuration\config.yml" (
    if exist "configuration\config.example.yml" (
        echo 📝 Creating config.yml from example...
        copy "configuration\config.example.yml" "configuration\config.yml" >nul
        echo ✅ Config created
    ) else (
        echo ⚠️  No config file found. Using defaults.
    )
)
echo.

echo 🚀 Starting Max Momentum in Potato PC mode...
echo.
echo 📋 What you're getting:
echo    ✅ All game modes (SkyBlock, BedWars, SkyWars)
echo    ✅ All services (Auction, Bazaar, Party, etc.)
echo    ✅ 20-30 concurrent players
echo.
echo ⚠️  Trade-offs:
echo    • Slower chunk loading
echo    • More frequent lag spikes
echo    • Cannot handle 50+ players
echo    • Heavy farms will cause lag
echo.
echo 💡 Tips:
echo    • Use SSD storage (HDD is 10x slower)
echo    • Monitor with: docker stats
echo    • View logs: docker-compose -f docker-compose.minimal.yml logs -f
echo    • Stop with: docker-compose -f docker-compose.minimal.yml down
echo.
echo ⚠️  IMPORTANT: Make sure Docker Desktop has at least 4GB RAM allocated
echo    Settings → Resources → Memory → Set to 4GB or higher
echo.

pause

echo.
echo 🔨 Building and starting services...
echo    This will take 2-5 minutes on first run...
echo.

docker-compose -f docker-compose.minimal.yml up --build

if errorlevel 1 (
    echo.
    echo ❌ Failed to start. Common issues:
    echo    1. Docker Desktop not running
    echo    2. Not enough RAM allocated in Docker Desktop settings
    echo    3. Port 25565 in use by another program
    echo    4. Antivirus blocking Docker
    pause
    exit /b 1
)
