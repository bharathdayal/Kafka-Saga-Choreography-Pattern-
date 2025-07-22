# Spring Boot + Kafka Saga Example (Choreography Pattern)

This project demonstrates a distributed Saga pattern using Spring Boot and Apache Kafka, with services communicating via Kafka topics.

## 🧱 Microservices Included

- **order-service** – Starts a new order
- **payment-service** – Handles payment after order is created
- **inventory-service** – Reserves stock after payment is successful
- **notification-service** – Sends confirmation after inventory is reserved
- **common-models** – Shared DTOs
- **kafka-core** – Shared KafkaProducerService

---

## 🛠️ Prerequisites

- Java 17+
- Gradle 7+
- Apache Kafka & Zookeeper running on:
  - `localhost:2181` (Zookeeper)
  - `localhost:9092` (Kafka)

---

## 🔄 Kafka Setup

Start Zookeeper and Kafka from your Kafka installation directory:

```bash
# Terminal 1 - Zookeeper
bin/zookeeper-server-start.sh config/zookeeper.properties

# Terminal 2 - Kafka Broker
bin/kafka-server-start.sh config/server.properties
```

---

## 🚀 How to Run

### Step 1: Build the Project

From the root directory:

```bash
./gradlew clean build
```

> On Windows:
> ```cmd
> gradlew.bat clean build
> ```

---

### Step 2: Start Services

In 4 separate terminals, run:

```bash
./gradlew :order-service:bootRun
./gradlew :payment-service:bootRun
./gradlew :inventory-service:bootRun
./gradlew :notification-service:bootRun
```

---

## 📦 Kafka Topics Used

- `order-created`
- `payment-processed`
- `inventory-reserved`

---

## ✅ Test the Saga

Send a POST request to `order-service`:

```http
POST http://localhost:8081/orders
Content-Type: application/json

{
  "orderId": "order-101",
  "productId": "P123",
  "quantity": 2
}
```

### 🔄 Expected Flow

1. `order-service` publishes `order-created`
2. `payment-service` listens and publishes `payment-processed`
3. `inventory-service` listens and publishes `inventory-reserved`
4. `notification-service` listens and prints a confirmation

---

## 📚 Tech Stack

- Spring Boot 3.x
- Apache Kafka
- Gradle Multi-Module
