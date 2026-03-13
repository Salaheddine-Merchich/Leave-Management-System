# Leave Management System

A full-stack web application designed to manage employee leave requests. The project is structured as a monorepo, separating the backend API, the frontend application, and performance testing scripts.

## Project Structure

*   **`backend/`**: Java Spring Boot REST API
*   **`frontend/`**: Angular Single Page Application
*   **`testing/`**: Apache JMeter load testing scripts
*   **`.github/workflows/`**: Continuous Integration (CI) configuration

## Technologies Used

### Backend
*   **Java 17**
*   **Spring Boot 3** (Spring Web, Spring Data JPA)
*   **H2 Database** (In-memory, for development and testing)
*   **Maven** (Build Tool)
*   **Testing**: JUnit 5, Mockito, MockMvc, Selenium WebDriver

### Frontend
*   **Angular 17+**
*   **TypeScript**
*   **Testing**: Jasmine, Karma

### Infrastructure & QA
*   **GitHub Actions** (CI/CD Pipeline)
*   **Apache JMeter** (Load & Performance Testing)

## Key Features & Implementations

*   **RESTful API**: Standardized backend endpoints for managing Users and Leave Requests.
*   **Performance Optimizations**: 
    *   Resolved N+1 query issues using `FetchType.LAZY` and optimized JPA queries.
    *   Implemented Angular Lazy Loading for routes to reduce initial bundle size.
*   **Comprehensive Testing Strategy**:
    *   Unit and Integration tests for backend services manually mocking dependencies.
    *   End-to-End (E2E) UI testing using Selenium and the Page Object Model (POM) pattern.
    *   Load testing scripts verified up to 2,000 concurrent users.

## How to Run Locally

### Prerequisites
*   Java 17
*   Node.js (v18+)
*   Git

### 1. Start the Backend API
Navigate to the backend directory and run the Spring Boot application using the Maven wrapper:

```bash
cd backend
./mvnw spring-boot:run
```
*The API will start on `http://localhost:8080`. By default, it uses an in-memory H2 database.*

### 2. Start the Frontend Application
In a new terminal, navigate to the frontend directory, install dependencies, and start the development server:

```bash
cd frontend
npm install
ng serve
```
*The application UI will be accessible at `http://localhost:4200`.*

## Running Tests

### Backend Tests (Unit & Integration)
```bash
cd backend
./mvnw test -Dtest="!*E2ETest,!*TestSuite"
```

### End-to-End Tests (Selenium)
Make sure both the frontend and backend servers are running, then execute:
```bash
cd backend
./mvnw test -Dtest="*E2ETest"
```

### Frontend Tests (Angular)
```bash
cd frontend
npm test -- --watch=false --browsers=ChromeHeadless
```
