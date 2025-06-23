# Shoposphere

Shoposphere is a robust, fully-featured e-commerce backend developed using Java Spring Boot, PostgreSQL (Supabase), JWT authentication, and deployed on Render. It provides a secure, scalable, and comprehensive backend solution for consumer-facing online stores.

---

## Key Features

### User Authentication (JWT)
- Secure token-based authentication
- Clearly defined roles: **Admin** and **Customer**

### Role-Based Access Control
- **Admin**: Full CRUD operations on products, categories, and user management.
- **Customer**: Browse products, manage shopping carts, and place orders.

### Product and Category Management
- Complete CRUD functionality
- Advanced filtering and searching capabilities

### Shopping Cart
- Easily add, remove, and manage items

### Order Processing
- Seamless checkout experience
- Order tracking and status updates

### Address Management
- Store and manage multiple addresses conveniently

---

## Tech Stack

- **Java 17+**
- **Spring Boot 3.x**
- **Spring Security (JWT)**
- **PostgreSQL (hosted via Supabase)**
- **JPA & Hibernate**
- **Maven**
- **Render (cloud hosting)**

---

## Database

- PostgreSQL database hosted on [Supabase](https://supabase.com/)
- Reliable and scalable data persistence

---

## API Documentation

- Full Postman collection for testing: [Postman Collection](https://.postman.co/workspace/My-Workspace~dbb58aef-b80d-4a8e-82f4-d091279927f8/collection/34578416-c5ce6c58-d0ec-47eb-9089-03f9e4c418a7?action=share&creator=34578416)

---

## Live Deployment

Shoposphere is deployed and accessible via Render's cloud hosting:

https://shoposphere.onrender.com

---

## Local Development Setup

### Prerequisites

- Java 17+
- Maven
- PostgreSQL (or Supabase)

### Installation Steps

1. **Clone the repository:**
   
```bash
   git clone https://github.com/Vaibhav-09-F/shoposphere.git
```

2. **Set environment variables (replace placeholder values with your details):**
   
export JDBC_DATABASE_URL=jdbc:postgresql://your-db-host:5432/your-db-name
export JDBC_DATABASE_USERNAME=your-username
export JDBC_DATABASE_PASSWORD=your-password
export jwt_secret=your_jwt_secret
export jwt_expiration=86400000

4. **Build and run the application:**
   
./mvnw spring-boot:run

Your server will start locally at:

http://localhost:8080

---

### Project Structure

shoposphere

├── src

│   └── main

│       ├── java

│       │   └── com.ecom

│       │       ├── controller

│       │       ├── dto

│       │       ├── model

│       │       ├── repository

│       │       ├── service

│       │       └── config

│       └── resources

│           └── application.properties

├── pom.xml

└── README.md

---

### Authentication Endpoints

- Register a new user (default role: USER):
  
  ```bash
  curl -X POST https://shoposphere.onrender.com/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{"name":"John Doe","email":"john@example.com","password":"password"}'
  
- User login (receive JWT token):
  
  ```bash
  curl -X POST https://shoposphere.onrender.com/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"john@example.com","password":"password"}'

---

### Planned Future Improvements

- Integration with payment gateways (Stripe, PayPal, etc.)
- Enhanced order tracking and notifications

