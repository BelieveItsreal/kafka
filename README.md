# Kafka Practice — Spring Boot Producer & Consumer

A small practice project for learning Apache Kafka integration with Spring Boot 4 / Spring Kafka 4. It consists of two independent Spring Boot apps that exchange JSON messages over Kafka.

## Project Structure

```
kafka/
├── demo/         # Producer app (port 8080)
├── consumer/     # Consumer app (port 8081)
└── docker-compose.yml   # Local single-node Kafka broker (KRaft mode)
```

## Prerequisites

- Java 21
- Maven (or use the included `mvnw` / `mvnw.cmd` wrappers)
- Docker (for running Kafka locally)

## Running locally

1. **Start Kafka**

   ```bash
   docker compose up -d
   ```

   This brings up a single-node Kafka broker (KRaft mode, `apache/kafka:4.1.1`) on `localhost:9092`.

2. **Start the producer (`demo`)**

   ```bash
   cd demo
   ./mvnw spring-boot:run
   ```

   Runs on [http://localhost:8080](http://localhost:8080).

3. **Start the consumer (`consumer`)**

   ```bash
   cd consumer
   ./mvnw spring-boot:run
   ```

   Runs on [http://localhost:8081](http://localhost:8081).

## Usage

Send a message via the producer's REST endpoint:

```bash
curl -X POST "http://localhost:8080/api/send?message=hello"
```

This publishes a `RiderLocation` object (as JSON) to the `my-topic` Kafka topic. The consumer app's `@KafkaListener` methods will pick it up and print it to the console.

## Kafka Topics

- `my-topic` — carries `RiderLocation` JSON messages from the producer.
- `my-topic-1` — declared via `KafkaTopicConfig` (3 partitions, replication factor 1), not currently used by any listener/producer.

## JSON Serialization Notes

The producer and consumer each have their **own copy** of the `RiderLocation` class, in different packages (`com.demo.Entity.RiderLocation` vs `com.consumer.Entity.RiderLocation`), to demonstrate decoupled serialization:

- **Producer** (`demo`) uses `JacksonJsonSerializer` and sets `spring.kafka.producer.properties.spring.json.add.type.headers=false` so it doesn't embed its own class name in message headers.
- **Consumer** uses `ErrorHandlingDeserializer` wrapping `JacksonJsonDeserializer`, with:
  - `spring.json.value.default.type` set to its own local `RiderLocation` class
  - `spring.json.use.type.headers=false` so it ignores any type headers and always deserializes into its own type
  - `spring.json.trusted.packages=*`

## Known Issues / TODO

- `KafkaConsumer.listen1` / `listen2` currently declare a `String` parameter, but every message on `my-topic` is actually a `RiderLocation` object — these need to be updated to accept `RiderLocation` to avoid `MessageConversionException`.
