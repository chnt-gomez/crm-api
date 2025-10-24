#!/bin/bash
# local-setup.sh
# ----------------------
# Automates building the CRM API and starting Docker containers
# Usage: ./local-setup.sh

set -e  # Exit immediately if a command exits with a non-zero status

echo "🚀 Starting local setup for CRM API..."

# Step 0: Clean up previous containers (if any)
echo "🧹 Stopping and removing any existing containers..."
docker compose down

# Step 1: Build the JAR
echo "🛠 Building the JAR with Gradle..."
./gradlew clean build

# Step 2: Build the Docker image
echo "🐳 Building the Docker image..."
docker compose build crm-api

# Step 3: Run Docker Compose and attach logs
echo "📦 Starting Docker Compose (press Ctrl+C to stop)..."
docker compose up