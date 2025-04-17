#!/bin/bash

# Stop and remove existing containers
docker compose down

# Pull latest images
docker pull dhiashayeb017/chaiebdhia_4twin4_innovativesquad_foyer:latest
docker pull prom/prometheus:latest
docker pull grafana/grafana:latest
docker pull portainer/portainer-ce:latest


# Start containers in detached mode
docker compose up -d --build

# Verify services
echo "Waiting for services to start..."
sleep 30  # Adjust based on your system speed

# Check Spring Boot
curl -s http://localhost:8089/foyer/actuator/health | grep UP || echo "Spring Boot failed to start"

# Check Prometheus
curl -s http://localhost:9090/-/healthy | grep "Prometheus is Healthy" || echo "Prometheus failed to start"

# Check Grafana
curl -s http://localhost:3000/api/health | grep "Database OK" || echo "Grafana failed to start"