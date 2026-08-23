# ECA Gym Workout Service

## Student Information
- **Name**: R.K. Sachintha Prabashana
- **Student ID**: 241722032
- **GCP Project ID**: [Your GCP Project ID]

## Project Description
The Workout Service manages workout-related data and operations for the FitBuddy application. It handles workout plans, schedules, and related functionalities.

## Technology Stack
- **Programming Language**: Java
- **Framework**: Spring Boot
- **Build Tool**: Maven
- **Database**: H2 (for development), MySQL (for production)
- **Other Tools**: Docker, Lombok

## Project Structure
```
workout-service/
├── src/
│   ├── main/
│   │   ├── java/  # Application source code
│   │   ├── resources/  # Configuration files
│   └── test/  # Unit and integration tests
├── pom.xml  # Maven configuration
├── README.md  # Documentation
└── target/  # Compiled output
```

## Setup / Getting Started Instructions
1. Clone the repository.
2. Navigate to the `workout-service` directory.
3. Run `mvn clean install` to build the project.
4. Start the application using `mvn spring-boot:run`.
5. Access the service at `http://localhost:8083`.

## Dependencies
- Spring Boot Starter Web
- Spring Boot Starter Data JPA
- H2 Database
- Lombok
- Maven Surefire Plugin

## Purpose
This service is a core component of the FitBuddy platform, ensuring seamless management of workout-related data and operations.
