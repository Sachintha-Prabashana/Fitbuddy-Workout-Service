# ECA Gym Workout Service

## 👤 Student Information
- **Name**: R.K. Sachintha Prabashana
- **Student ID**: 241722032
- **GCP Project ID**: `fitbuddy-505618`

## ## Project Description
The Workout Service manages workout routines, daily plans, schedules, and exercises for members in the FitBuddy platform.

## ## Technology Stack
- **Language**: Java 25
- **Framework**: Spring Boot 4.0.1 / 4.1.0
- **Database**: MongoDB (via Spring Data MongoDB)
- **Build Tool**: Maven
- **Other libraries**: Lombok, MapStruct (for mapping)

## ## Project Structure
```
workout-service/
├── src/
│   ├── main/
│   │   ├── java/lk/ijse/eca/workoutservice/
│   │   │   ├── WorkoutServiceApplication.java # Entry point
│   │   │   ├── config/      # Mongo and Security Configs
│   │   │   ├── controller/  # REST Endpoints for workouts
│   │   │   ├── document/    # MongoDB Entities (WorkoutPlan, MemberWorkout, etc.)
│   │   │   ├── dto/         # Request & Response models
│   │   │   ├── exception/   # Custom domain exceptions
│   │   │   ├── handler/     # Exception interceptors
│   │   │   ├── mapper/      # MapStruct DTO mappings
│   │   │   ├── repository/  # Spring Data Mongo Repositories
│   │   │   ├── security/    # JWT Validation & Security
│   │   │   └── service/     # Core Business Logics & Client Clients
│   │   └── resources/
│   │       ├── application.yaml
│   │       └── application-dev.yaml
│   └── test/
├── pom.xml
└── README.md
```

## ## Setup / Getting Started Instructions
1. Navigate to the `workout-service` directory.
2. Build the Maven package:
   ```bash
   ./mvnw clean install
   ```
3. Run the Spring Boot application:
   ```bash
   ./mvnw spring-boot:run
   ```
4. By default, the service starts on a dynamic port and registers itself automatically with Eureka Service Discovery.
