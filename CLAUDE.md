# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

This is a Spring Boot todo application built with Java 24, using PostgreSQL as the database and Thymeleaf for web templating. The application provides both REST API endpoints and web interface for managing todo items.

## Development Commands

### Build and Run
- `./gradlew build` - Build the application
- `./gradlew bootRun` - Run the application (starts on port 8080)
- `./gradlew test` - Run all tests
- `./gradlew clean` - Clean build artifacts

### Database
- `docker compose up` - Start PostgreSQL database (runs on port 5435)
- Database connection: `jdbc:postgresql://localhost:5435/mydatabase`
- Credentials: username=`myuser`, password=`secret`

### Development
- Application runs on http://localhost:8080
- Web interface available at root path
- REST API endpoints under `/api/todos`
- Hot reload available with Spring Boot DevTools

## Architecture

### Package Structure
- `kz.todo.app` - Root package
  - `config/` - Configuration classes including data initialization
  - `controller/` - REST controllers (`TodoController`) and web controllers (`WebController`)
  - `dto/` - Data transfer objects for API requests/responses
  - `entity/` - JPA entities (`Todo`)
  - `exception/` - Global exception handling
  - `repository/` - Spring Data JPA repositories
  - `service/` - Business logic layer (`TodoService`)

### Key Components

**Todo Entity** (`src/main/java/kz/todo/app/entity/Todo.java:17`):
- Uses Lombok annotations for boilerplate reduction
- JPA entity with PostgreSQL dialect
- Automatic timestamp management with `@PreUpdate`

**REST API** (`src/main/java/kz/todo/app/controller/TodoController.java:22`):
- CRUD operations for todos
- Filtering (all/active/completed) and search functionality
- Statistics endpoint at `/api/todos/stats`
- Cross-origin enabled for frontend integration

**Service Layer** (`src/main/java/kz/todo/app/service/TodoService.java:20`):
- Transactional business logic
- Comprehensive filtering and search operations
- DTO mapping between entity and API layers

### Database Configuration
- PostgreSQL with JPA/Hibernate
- DDL auto-update enabled for development
- SQL logging enabled in debug mode
- Connection pooling via Spring Boot defaults

### Development Notes
- Uses Lombok for reducing boilerplate code
- Validation with Jakarta Validation API
- Logging configured for Spring Web and application packages
- Docker Compose integration for database dependency