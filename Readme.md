# 🚀 Old World Roster Builder

A web application for creating and managing army rosters for Warhammer: The Old World.

## About the Project

Old World Roster Builder allows players to create army rosters, select units and upgrades, calculate army points, and validate army composition against predefined limits.

The application also supports user accounts, army management, and PDF export.

> **Current limitation:** Only the Skaven faction is supported.

## Features

### Army Building
- Create and manage army rosters.
- Select a faction and army point limit.
- Add and remove units.
- Increase and decrease unit quantities.
- Select unit upgrades.
- Calculate unit and army point costs.
- Track Lords, Heroes, Core, Special, and Rare composition limits.
- Validate army composition.
- View unit statistics.

### User Management
- User registration and authentication.
- Account management.
- User-specific army management.

### Administration
- Administration panel.
- Unit catalog management.

### Export
- Export army rosters to PDF.

## Architecture

The application consists of a Spring Boot backend and a React frontend.

### Backend
- Exposes REST endpoints.
- Handles business logic and army composition validation.
- Manages persistence through Spring Data JPA and Hibernate.
- Uses PostgreSQL for data storage.
- Handles authentication and authorization.

### Frontend
- Provides the user interface for army building and account management.
- Communicates with the backend through HTTP requests.

## Technologies

### Backend
- Java 21
- Spring Boot
- Spring Data JPA
- Hibernate
- Spring Security
- JWT
- PostgreSQL
- Gradle
- iText

### Frontend
- React
- React Router
- Axios
- Bootstrap
- Bootstrap Icons
- Vite

## Getting Started

### Prerequisites

Make sure the following tools are installed:
- Java 21
- PostgreSQL
- Node.js and npm

### Backend Setup

1. Clone the repository.
2. Configure the PostgreSQL connection in `application.properties`.
3. Start the Spring Boot application using the Gradle wrapper.

On Windows:

```powershell
.\gradlew bootRun
```

On Linux or macOS:

```bash
./gradlew bootRun
```

To run the backend tests:

Windows:

```powershell
.\gradlew test
```

Linux or macOS:

```bash
./gradlew test
```

### Frontend Setup

Navigate to the frontend directory:

```bash
cd frontend
npm install
npm run dev
```

Vite will start the development server and display the local address in the terminal.

> **Note:** Adjust the frontend directory path if necessary to match the repository structure.

## Security

The application uses Spring Security and JWT-based authentication.

- Passwords are stored using password hashing.
- Authenticated users can manage their own armies.
- Administrative operations are restricted by user roles.
- Access to individual armies is checked against their ownership.

## Screenshots

Screenshots of the application will be added here.

Suggested screenshots:
- Army list
- Army builder
- Unit statistics and upgrades
- Army composition and points
- PDF roster export
- Administration panel

## Limitations

- Only the Skaven faction is currently supported.
- The application is under active development.
- Automated test coverage is still being expanded.

## Roadmap

- Expand support for additional factions.
- Increase automated test coverage.
- Improve application configuration and deployment.
- Continue improving the user interface and army validation.

## License

No license has been specified yet.