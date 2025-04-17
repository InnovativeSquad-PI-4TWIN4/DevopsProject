#!/bin/bash

set -e  # Exit on any error

# Log file for deployment
LOG_FILE="deploy.log"
echo "Starting deployment at $(date)" | tee -a $LOG_FILE

# Debugging: Check Docker and Docker Compose availability
echo "Checking Docker and Docker Compose..." | tee -a $LOG_FILE
docker --version >> $LOG_FILE 2>&1 || { echo "Docker not found or inaccessible" | tee -a $LOG_FILE; exit 1; }
docker-compose --version >> $LOG_FILE 2>&1 || { echo "Docker Compose not found or inaccessible" | tee -a $LOG_FILE; exit 1; }

# Stop and remove existing containers (handle case where no containers exist)
echo "Stopping and removing existing containers..." | tee -a $LOG_FILE
docker-compose down >> $LOG_FILE 2>&1 || echo "No containers to stop or docker-compose down failed (continuing anyway)" | tee -a $LOG_FILE

# Pull latest images
echo "Pulling latest images..." | tee -a $LOG_FILE
docker pull dhiashayeb017/chaiebdhia_4twin4_innovativesquad_foyer:latest >> $LOG_FILE 2>&1 || { echo "Failed to pull Spring app image" | tee -a $LOG_FILE; exit 1; }
docker pull prom/prometheus:latest >> $LOG_FILE 2>&1 || { echo "Failed to pull Prometheus image" | tee -a $LOG_FILE; exit 1; }
docker pull grafana/grafana:latest >> $LOG_FILE 2>&1 || { echo "Failed to pull Grafana image" | tee -a $LOG_FILE; exit 1; }
docker pull mysql:8.0 >> $LOG_FILE 2>&1 || { echo "Failed to pull MySQL image" | tee -a $LOG_FILE; exit 1; }

# Start containers in detached mode
echo "Starting containers with docker-compose..." | tee -a $LOG_FILE
docker-compose up -d --build >> $LOG_FILE 2>&1 || { echo "Failed to start containers" | tee -a $LOG_FILE; exit 1; }

# Wait for MySQL to be healthy
echo "Waiting for MySQL to be healthy..." | tee -a $LOG_FILE
until docker-compose ps | grep mysql | grep healthy > /dev/null; do
    echo "MySQL not healthy yet, waiting..." | tee -a $LOG_FILE
    sleep 5
done
echo "MySQL is healthy!" | tee -a $LOG_FILE

# Wait for Spring Boot to be healthy
echo "Waiting for Spring Boot to be healthy..." | tee -a $LOG_FILE
until docker-compose ps | grep spring-app | grep healthy > /dev/null; do
    echo "Spring Boot not healthy yet, waiting..." | tee -a $LOG_FILE
    sleep 5
done
echo "Spring Boot is healthy!" | tee -a $LOG_FILE

# Verify services
echo "Verifying services..." | tee -a $LOG_FILE

# Check Spring Boot
curl -s http://localhost:8089/foyer/actuator/health | grep UP || { echo "Spring Boot failed to start" | tee -a $LOG_FILE; exit 1; }
echo "Spring Boot is UP" | tee -a $LOG_FILE

# Check Prometheus
curl -s http://localhost:9090/-/healthy | grep "Prometheus is Healthy" || { echo "Prometheus failed to start" | tee -a $LOG_FILE; exit 1; }
echo "Prometheus is healthy" | tee -a $LOG_FILE

# Check Grafana
curl -s http://localhost:3000/api/health | grep "Database OK" || { echo "Grafana failed to start" | tee -a $LOG_FILE; exit 1; }
echo "Grafana is healthy" | tee -a $LOG_FILE

echo "Deployment completed successfully at $(date)" | tee -a $LOG_FILE