# Transport Marketplace Backend

Professional microservices backend for a transport marketplace.

Structure

transport-marketplace-backend/
│
├── api-gateway/
├── auth-service/
├── delivery-service/
├── payment-service/
├── notification-service/
├── reporting-service/
│
├── docker-compose.yml
├── README.md
└── diagrams/

Getting started
1. Install Java 21, Maven, Docker & Docker Compose.
2. Build modules:
   mvn -T 1C -DskipTests package
3. Run via docker-compose .

Conventions
- English for code and docs.
- Conventional Commits.
- Each microservice is autonomous (own DB and port).

Roadmap
- Setup (done)
- Gateway + Auth (JWT)
- Delivery, Payment, Notification, Reporting
- Tests & Docker Compose
- Diagrams & API docs