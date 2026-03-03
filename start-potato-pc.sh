#!/bin/bash

# Max Momentum - Potato PC Startup Script
# For systems with 4GB RAM

echo "🥔 Max Momentum - Potato PC Mode"
echo "=================================="
echo ""

# Check if running as root for swap setup
if [ "$EUID" -ne 0 ]; then 
    echo "⚠️  This script needs sudo access to set up swap space."
    echo "    You'll be prompted for your password."
    echo ""
fi

# Check available RAM
TOTAL_RAM=$(free -m | awk '/^Mem:/{print $2}')
echo "📊 Detected RAM: ${TOTAL_RAM}MB"

if [ "$TOTAL_RAM" -lt 3500 ]; then
    echo "❌ ERROR: You have less than 4GB RAM. This setup requires at least 4GB."
    exit 1
fi

if [ "$TOTAL_RAM" -gt 7000 ]; then
    echo "✅ You have ${TOTAL_RAM}MB RAM. You can use the standard setup instead!"
    echo "   Run: docker-compose up --build"
    echo ""
    read -p "Continue with potato mode anyway? (y/n) " -n 1 -r
    echo
    if [[ ! $REPLY =~ ^[Yy]$ ]]; then
        exit 0
    fi
fi

echo ""
echo "🔧 Setting up swap space..."

# Check if swap already exists
if [ -f /swapfile ]; then
    echo "✅ Swap file already exists"
else
    echo "   Creating 2GB swap file..."
    sudo fallocate -l 2G /swapfile
    sudo chmod 600 /swapfile
    sudo mkswap /swapfile
    sudo swapon /swapfile
    echo "✅ Swap enabled"
    
    # Ask to make permanent
    if ! grep -q '/swapfile' /etc/fstab; then
        read -p "Make swap permanent across reboots? (y/n) " -n 1 -r
        echo
        if [[ $REPLY =~ ^[Yy]$ ]]; then
            echo '/swapfile none swap sw 0 0' | sudo tee -a /etc/fstab
            echo "✅ Swap will persist after reboot"
        fi
    fi
fi

echo ""
echo "📝 Checking configuration..."

# Check if config exists
if [ ! -f configuration/config.yml ]; then
    if [ -f configuration/config.example.yml ]; then
        echo "   Creating config.yml from example..."
        cp configuration/config.example.yml configuration/config.yml
        echo "✅ Config created"
    else
        echo "⚠️  No config file found. Using defaults."
    fi
fi

# Suggest player limit reduction
if [ -f configuration/config.yml ]; then
    MAX_PLAYERS=$(grep -oP 'max-players:\s*\K\d+' configuration/config.yml 2>/dev/null || echo "unknown")
    if [ "$MAX_PLAYERS" != "unknown" ] && [ "$MAX_PLAYERS" -gt 50 ]; then
        echo "⚠️  Your max-players is set to $MAX_PLAYERS"
        echo "   For 4GB RAM, we recommend max-players: 30"
        read -p "   Update to 30? (y/n) " -n 1 -r
        echo
        if [[ $REPLY =~ ^[Yy]$ ]]; then
            sed -i 's/max-players:.*/max-players: 30/' configuration/config.yml
            echo "✅ Updated max-players to 30"
        fi
    fi
fi

echo ""
echo "🚀 Starting Max Momentum in Potato PC mode..."
echo ""
echo "📋 What you're getting:"
echo "   ✅ All game modes (SkyBlock, BedWars, SkyWars)"
echo "   ✅ All services (Auction, Bazaar, Party, etc.)"
echo "   ✅ 20-30 concurrent players"
echo ""
echo "⚠️  Trade-offs:"
echo "   • Slower chunk loading"
echo "   • More frequent lag spikes"
echo "   • Cannot handle 50+ players"
echo "   • Heavy farms will cause lag"
echo ""
echo "💡 Tips:"
echo "   • Use SSD storage (HDD is 10x slower)"
echo "   • Monitor with: docker stats"
echo "   • View logs: docker-compose -f docker-compose.minimal.yml logs -f"
echo "   • Stop with: docker-compose -f docker-compose.minimal.yml down"
echo ""

read -p "Press Enter to start, or Ctrl+C to cancel..."

echo ""
echo "🔨 Building and starting services..."
echo "   This will take 2-5 minutes on first run..."
echo ""

docker-compose -f docker-compose.minimal.yml up --build

# If docker-compose fails
if [ $? -ne 0 ]; then
    echo ""
    echo "❌ Failed to start. Common issues:"
    echo "   1. Docker not installed: sudo apt install docker.io docker-compose"
    echo "   2. Docker not running: sudo systemctl start docker"
    echo "   3. Permission denied: sudo usermod -aG docker $USER (then logout/login)"
    echo "   4. Port 25565 in use: sudo lsof -i :25565"
    exit 1
fi
