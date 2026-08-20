# CareerTrack

<p align="center">
  <b>Job Application Tracker for Students & Freshers</b><br>
  Keep applications from different job platforms in one place.
</p>

<p align="center">
  <img src="https://img.shields.io/badge/Java-17-orange?logo=openjdk" alt="Java 17">
  <img src="https://img.shields.io/badge/Spring%20Boot-3.3.0-6DB33F?logo=springboot" alt="Spring Boot">
  <img src="https://img.shields.io/badge/MySQL-8.0.45-4479A1?logo=mysql" alt="MySQL">
  <img src="https://img.shields.io/badge/Maven-C71A36?logo=apachemaven" alt="Maven">
</p>

---

## About

CareerTrack is a simple web application for students and freshers who apply for jobs through **LinkedIn, Naukri, Indeed, company websites, and other sources**.

It helps you save and track all those applications in **one place**.

> Find a job anywhere → Apply there → Track it in CareerTrack

CareerTrack does not replace job platforms or automatically apply for jobs.

## Features

- **Authentication** — Register, Login, Logout
- **Applications** — Add, edit, delete, search, filter, update status
- **Companies** — Simple company CRUD
- **Dashboard** — Application summary and recent applications
- **My Profile** — Save skills, summary, links, and personal details
- **Quick Copy** — Copy profile information for job forms
- **Resume** — Upload and manage PDF resume
- **REST APIs** — Selected JSON APIs with DTOs
- **Security** — Spring Security, BCrypt, and user-data protection

## Status

```text
Saved → Applied → Assessment → Interview → Offer
                         ↘ Rejected / Withdrawn
```

## Tech Stack

**Java 17 · Spring Boot 3.3 · Spring MVC · Spring Data JPA · Hibernate · Spring Security · Thymeleaf · HTML · CSS · JavaScript · MySQL · Maven**

## Architecture

```text
Browser
   ↓
Controller / REST API
   ↓
Service
   ↓
Repository
   ↓
JPA / Hibernate
   ↓
MySQL
```

## Run Locally

### Requirements

- Java 17+
- Maven
- MySQL
- Git

### 1. Clone

```powershell
git clone https://github.com/Rutvik-SWE/CareerTrack.git
cd CareerTrack
```

### 2. Create Database

```sql
CREATE DATABASE careertrack;
```

### 3. Set Database Credentials

```text
DB_USERNAME=root
DB_PASSWORD=your_mysql_password
```

Keep your real password out of GitHub.

### 4. Run

```powershell
mvn clean install
mvn spring-boot:run
```

Open:

```text
http://localhost:8080
```

## Testing

```powershell
mvn test
```

## Project Structure

```text
src/main/java/com/careertrack/
├── controller
├── service
├── repository
├── entity
├── dto
├── security
└── exception
```

## Why I Built It

Job applications are often spread across multiple platforms. CareerTrack was built to make that process easier by keeping application details, company information, and reusable profile data together.

## Future Improvements

- Better analytics
- Pagination
- Password reset
- Deployment

## Author

**Rutvik Katariya**

[GitHub](https://github.com/Rutvik-SWE)
