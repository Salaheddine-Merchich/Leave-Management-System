# 🌴 Leave Management System

[![LMS CI/CD Pipeline](https://github.com/Salaheddine-Merchich/Leave-Management-System/actions/workflows/ci.yml/badge.svg)](https://github.com/Salaheddine-Merchich/Leave-Management-System/actions/workflows/ci.yml)

A clean, modern web application for managing employee leave requests. Built as a monorepo with a Java Spring Boot backend and an Angular frontend.

## 💻 Tech Stack

- **Backend:** Java 17, Spring Boot 3, Spring Data JPA, H2 Database.
- **Frontend:** Angular 17+, TypeScript, Tailwind/CSS.
- **Testing:** JUnit 5, Mockito, Selenium (E2E), Jasmine & Karma.
- **CI/CD:** GitHub Actions (Automated testing & linting).

## 🚀 Quick Start

To run this project locally, you'll need **Java 17** and **Node.js (v18+)** installed on your machine.

### 1. Start the API (Backend)
The backend uses an in-memory H2 database by default, so no external database setup is required.

```bash
cd backend
./mvnw spring-boot:run
```
*The API will be available at `http://localhost:8080`.*

### 2. Start the UI (Frontend)
Open a new terminal window:

```bash
cd frontend
npm install
npm start
```
*The frontend will run at `http://localhost:4200`.*

---

## 🧪 Running Tests

This project enforces a strict testing pyramid (Unit, Integration, and End-to-End).

### Backend Tests
Run the standard unit and integration tests:
```bash
cd backend
./mvnw test -Dtest="!*E2ETest,!*TestSuite"
```

### End-to-End Tests (Selenium)
To run the automated browser tests, make sure **both the frontend and backend servers are running first**, then execute:
```bash
cd backend
./mvnw test -Dtest="*E2ETest" -DbaseUrl="http://localhost:4200"
```

### Frontend Tests
```bash
cd frontend
npm test -- --watch=false --browsers=ChromeHeadless
```

