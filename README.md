# Picnic Grocery Fulfillment Orchestrator (Demo)

This repository contains a simplified, **Picnic-inspired** event-driven microservices system
implemented with **Java 21**, **Spring Boot 3**, **WebFlux**, **Kafka**, **PostgreSQL**, and **MongoDB**.

It demonstrates:

- Order intake via reactive REST API
- Inventory reservation using PostgreSQL
- Route planning using MongoDB
- Event-driven saga-style orchestration with Kafka

## Architecture

1. **order-service**
   - Exposes `POST /api/orders`
   - Persists orders in PostgreSQL
   - Publishes `order.created` events to Kafka

2. **inventory-service**
   - Listens to `order.created`
   - Checks stock in PostgreSQL
   - Reserves inventory and publishes `inventory.reserved`

3. **routing-service**
   - Listens to `inventory.reserved`
   - Generates a fake route with ETA and stores it in MongoDB
   - Publishes `route.created`

4. **orchestrator-service**
   - Listens to `route.created`
   - Logs final "order ready for delivery" signal (placeholder for real orchestration)

## Getting Started (Local)

### 1. Start infrastructure

```bash
cd infra
docker-compose up -d
```

This will start:

- Kafka (with Zookeeper)
- PostgreSQL for orders and inventory
- MongoDB for routing

### 2. Build all services

```bash
mvn clean package
```

### 3. Run the services

In separate terminals:

```bash
cd order-service
mvn spring-boot:run

cd inventory-service
mvn spring-boot:run

cd routing-service
mvn spring-boot:run

cd orchestrator-service
mvn spring-boot:run
```

### 4. Create an order

```bash
curl -X POST http://localhost:8081/api/orders \
  -H "Content-Type: application/json" \
  -d '{
    "customerId": "cust-123",
    "items": [
      {"sku": "ITEM-APPLE", "quantity": 2},
      {"sku": "ITEM-MILK", "quantity": 1}
    ]
  }'
```

You should see:

- Order stored in `orders` DB
- Inventory checked/updated in `inventory` DB
- Route created in `routingdb.routes`
- Logs in **orchestrator-service** indicating order is ready for delivery

## Notes

- This is intentionally minimal but **fully functional** and can be extended with:
  - Resilience4j
  - Prometheus / Grafana
  - Kubernetes & Helm
  - Authentication (Keycloak)
