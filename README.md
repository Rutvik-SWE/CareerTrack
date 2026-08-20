# CareerTrack
A simple job application tracker for students and freshers.

## Overview

CareerTrack is a web application that helps students and freshers manage their job applications in one place. Job applications are often spread across LinkedIn, Naukri, Indeed, company websites, and other sources. CareerTrack keeps those application details organized so users can track their progress more easily.

## Problem

When applying for jobs through different platforms, it is easy to forget:
- where you applied
- which company you applied to
- which position you applied for
- when you applied
- the current application status

## Solution

CareerTrack solves this problem by providing a central place to record and track applications. The workflow is simple:

Find a job anywhere -> Apply on that platform -> Record it in CareerTrack -> Track and manage applications

## Features

### Authentication
- Register new account
- Login securely
- Logout
- Protected pages (must be logged in to view data)

### Applications
- Add new job application
- View all applications
- Edit application details
- Delete application
- Update status
- Search by company or job title
- Filter by application status

### Companies
- Add company
- View companies
- Edit company
- Delete company

### Dashboard
- Application statistics (Total, Applied, Interviews, Offers, Rejected)
- Recent applications summary

### Profile
- Store personal information (Name, Email, Phone, Location)
- Store professional summary and skills
- Store LinkedIn and GitHub links
- Copy information to clipboard for quick pasting into job applications

### Resume
- Upload PDF resume
- View/download resume
- Delete resume
- Replace existing resume

## Application Status

When tracking applications, you can assign one of the following standardized statuses:
- Saved
- Applied
- Assessment
- Interview
- Offer
- Rejected
- Withdrawn

## How It Works

1. Find a job
2. Apply through LinkedIn / Naukri / Company Website
3. Add the application to CareerTrack
4. Track status
5. Update progress as you move through the interview process

## Technology Stack

**Backend**
- Java 17
- Spring Boot 3
- Spring MVC
- Spring Data JPA
- Hibernate
- Spring Security

**Frontend**
- Thymeleaf
- HTML5
- CSS3 (Vanilla CSS)
- Vanilla JavaScript

**Database**
- MySQL

**Build**
- Maven

**Version Control**
- Git / GitHub

## Architecture

```text
Browser
   ↓
Thymeleaf Controllers
        OR
REST Controllers
        ↓
      Service
        ↓
    Repository
        ↓
   JPA / Hibernate
        ↓
      MySQL
```

## Database

The database consists of four main entities, with all data strictly owned by the registered user.

```text
User
 ├── Profile (1-to-1)
 ├── Companies (1-to-Many)
 └── Applications (1-to-Many)

Company
 └── Applications (1-to-Many)
```

## Project Structure

```text
src/
├── main/
│   ├── java/
│   │   └── com/careertrack/
│   │       ├── controller/
│   │       │   └── api/
│   │       ├── service/
│   │       ├── repository/
│   │       ├── entity/
│   │       ├── dto/
│   │       ├── security/
│   │       └── exception/
│   │
│   └── resources/
│       ├── templates/
│       ├── static/
│       │   └── css/
│       └── application.properties
│
└── test/
    └── java/
        └── com/careertrack/
            ├── CareerTrackApplicationTests.java
            └── service/
                ├── ApplicationServiceTest.java
                └── UserServiceTest.java
```

## Setup and Installation

### Requirements
- Java 17
- Maven
- MySQL Server

### Database Setup
1. Open your MySQL client or terminal.
2. Create an empty database:
   ```sql
   CREATE DATABASE careertrack;
   ```
3. Update the credentials in `src/main/resources/application.properties` or set them as environment variables on your system:
   ```properties
   spring.datasource.username=${DB_USERNAME:root}
   spring.datasource.password=${DB_PASSWORD:your_password}
   ```
4. Start the application. Hibernate will automatically generate all necessary database tables.

### Run the Project
Build the project using Maven:
```powershell
mvn clean install
```
Start the Spring Boot application:
```powershell
mvn spring-boot:run
```
Open your browser and navigate to:
```text
http://localhost:8080
```

## REST API

CareerTrack provides a small set of JSON APIs alongside the Thymeleaf application to demonstrate REST API concepts.

| Method | Endpoint                   | Purpose                          |
| ------ | -------------------------- | -------------------------------- |
| GET    | `/api/applications`        | Get all applications for user    |
| GET    | `/api/applications/{id}`   | Get specific application by ID   |
| POST   | `/api/applications`        | Create a new application         |
| PUT    | `/api/applications/{id}`   | Update an existing application   |
| DELETE | `/api/applications/{id}`   | Delete an application            |
| GET    | `/api/profile`             | Get user's profile information   |

*Note: All REST endpoints require standard Spring Security session authentication. They will only return data owned by the logged-in user. Sensitive entity data (like passwords) is stripped out using simple DTOs.*

## Error Handling

The application uses centralized exception handling (`@ControllerAdvice`). Common errors such as missing records and invalid input return clear JSON messages for the REST APIs, or redirect to a clean, user-friendly HTML error page for the main web application instead of showing raw server stack traces.

## Testing

The project includes basic JUnit 5 automated testing using Mockito.
Tests are written to verify application context loading, user registration (password hashing), and application creation business logic.

Run the tests using:
```powershell
mvn test
```

## Security

- **Authentication**: Form-based Spring Security session authentication.
- **Passwords**: Encrypted using BCrypt before storing in the database.
- **Data Ownership**: Service and Controller layers verify that the currently logged-in user owns the Company/Profile/Application before any read/write operations are permitted.
- **Validation**: Backend form validation (`@NotBlank`, `@Email`) is applied to entities and checked using `@Valid`.
- **DTOs**: Data Transfer Objects protect sensitive database fields from being exposed via JSON endpoints.

## Future Improvements

- Add a basic dashboard chart to visualize applications over time
- Add pagination for large application lists
- Implement password reset functionality
- Add deployment configurations (e.g., Dockerfile)

## Project Status

Status: Completed

## Author

Rutvik
