
# Vehicle Servicing at Home (VSAH) Management System

## Overview

The **Vehicle Servicing at Home (VSAH) Management System** is a web application designed to manage and streamline various administrative and service-related operations within a vehicle service center. The backend is built using the Java Spring Boot framework, providing a scalable and secure solution. Data is securely managed and stored in a MySQL database.

## Features

- **Admin Module**:
  - Manage vehicle service appointments and history.
  - Secure dashboard for overall service center management.
  - Manage technician details and track their status.
  - Comprehensive feedback management system.

- **Technician Module**:
  - Manage and update service appointments.
  - Access and update service histories.
  - Provide and view feedback on services rendered.

- **User Module**:
  - Schedule and view service appointments.
  - View vehicle service history.
  - Provide feedback on services.

- **Security**:
  - User authentication and authorization with JWT (JSON Web Token).
  - Role-based access control for admins, technicians, and users.
  - Secure API endpoints with Spring Security.

- **Dynamic Data Handling**:
  - Responsive display of nested objects in tables with modal views.
  - Dynamic API response handling for various data types.

- **Testing**:
  - API validation with Postman.
  - Unit and integration tests using Mockito and JUnit.

## Tech Stack

### Backend

- **Framework**: Java Spring Boot
- **Database**: MySQL
- **Security**: Spring Security with JWT
- **Testing**: Postman, Mockito, JUnit

## Getting Started

### Prerequisites

- Java 17 or higher
- Maven
- MySQL Server
- Postman (for testing)

### Installation

1. Clone the repository:

   ```bash
   git clone https://github.com/SouravPoojary/Backend.git
   cd Backend
   ```

2. Configure MySQL:

   - Install MySQL Server if not already installed.
   - Create a database named \`vsah_db\`.
   - Create a user with appropriate permissions and set the password.

3. Set up environment variables:

   Create a \`.env\` file in the root directory and add the following:

   ```plaintext
   SPRING_DATASOURCE_URL=jdbc:mysql://localhost:3306/vsah_db
   SPRING_DATASOURCE_USERNAME=<username>
   SPRING_DATASOURCE_PASSWORD=<password>
   SPRING_JPA_HIBERNATE_DDL_AUTO=update
  ```

4. Build the project:

   ```bash
   mvn clean install
  ```

5. Run the application:

  ```bash
   mvn spring-boot:run
  ```

### API Endpoints

The API is documented using Swagger. Once the application is running, you can access the API documentation at:

- [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

### Testing

#### Postman

A Postman collection is included in the repository. Import \`VSAH.postman_collection.json\` into Postman to test the API endpoints.

#### Unit Tests

The project includes unit and integration tests using Mockito and JUnit. To run the tests, use the following command:

```bash
mvn test
```

## Security

The application uses Spring Security with JWT to secure API endpoints. Ensure that you configure proper security measures and handle sensitive data responsibly.

## Contributing

Contributions are welcome! Please open an issue or submit a pull request.

## Contact

If you have any questions or feedback, please reach out to us at \`souravmca000@gmail.com\`.
