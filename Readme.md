# ⚔ Old World Roster Builder

## 🚀 How to run

### Backend

1. Clone the repository.
2. Configure the database connection in `application.properties`.
3. Start the Spring Boot application.

### Frontend

```bash
cd frontend
npm install
npm run dev
```
The frontend source code is included in the Spring Boot project under:

src/main/resources/templates/frontend/src##  About the project

Old World Roster Builder is a web application for creating and managing
Warhammer: The Old World 8th ed army rosters.

The application allows users to build armies by selecting units,
purchasing upgrades and monitoring army points and composition limits.
Created armies can also be exported to PDF.

> **AD:** Only the Skaven faction is currently supported.

##  Features

- Create and manage army rosters
- Select faction and army point limit
- Add and remove units
- Add and remove unit upgrades
- Calculate total army points
- Track Lords, Heroes, Core, Special and Rare limits
- Validate army composition
- View unit statistics
- Export army rosters to PDF
- User authentication
- Account management
- Administration panel

## 🛠 Technologies

### Backend

- Java
- Spring Boot
- Spring Data JPA
- Hibernate
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