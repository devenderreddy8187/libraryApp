# Library Management System with Spring Boot

A modern library management system built with Spring Boot, featuring Spring Security with role-based access control.

## Features

- **Book Management**: Add, view, search, borrow, and return books
- **User Authentication**: Secure login system with Spring Security
- **Role-Based Access Control**: Different permissions for users and administrators
- **Modern UI**: Responsive design with dark theme
- **H2 Database**: In-memory database for development

## User Roles

### User Role
- View all books
- Search books
- Borrow and return books
- Cannot add or delete books

### Admin Role
- All user permissions
- Add new books
- Delete books
- Full system access

## Demo Credentials

- **User**: `user` / `password`
- **Admin**: `admin` / `admin`

## Security Features

- Spring Security integration
- Password encryption with BCrypt
- Form-based authentication
- Role-based authorization
- CSRF protection (disabled for API endpoints)
- Secure session management

## API Endpoints

### Public Endpoints
- `GET /` - Main page
- `GET /login.html` - Login page
- `GET /api/books` - List all books
- `GET /api/books/search` - Search books
- `GET /api/books/status/{status}` - Get books by status
- `GET /api/user` - Get current user info

### Authenticated Endpoints
- `POST /api/books/{id}/borrow` - Borrow a book (USER, ADMIN)
- `POST /api/books/{id}/return` - Return a book (USER, ADMIN)

### Admin Only Endpoints
- `POST /api/books` - Add new book
- `DELETE /api/books/{id}` - Delete book

## Getting Started

1. **Prerequisites**
   - Java 21+
   - Maven 3.6+

2. **Clone and Run**
   ```bash
   git clone <repository-url>
   cd library-springboot
   mvn spring-boot:run
   ```

3. **Access the Application**
   - Main application: http://localhost:8080
   - Login page: http://localhost:8080/login.html
   - H2 Console: http://localhost:8080/h2-console

4. **Database Configuration**
   - JDBC URL: `jdbc:h2:mem:testdb`
   - Username: `sa`
   - Password: `password`

## Technology Stack

- **Backend**: Spring Boot 3.3.0, Spring Security, Spring Data JPA
- **Database**: H2 (in-memory)
- **Frontend**: HTML5, CSS3, JavaScript (Vanilla)
- **Build Tool**: Maven
- **Java Version**: 21

## Project Structure

```
src/
├── main/
│   ├── java/com/example/library/
│   │   ├── config/          # Security and configuration
│   │   ├── controller/      # REST controllers
│   │   ├── entity/          # JPA entities
│   │   ├── repository/      # Data access layer
│   │   └── service/         # Business logic
│   └── resources/
│       ├── static/          # Frontend files
│       └── application.properties
```

## Security Configuration

The application uses Spring Security with the following configuration:

- **Authentication**: Form-based login
- **Authorization**: Role-based access control
- **Password Storage**: BCrypt encryption
- **Session Management**: Spring Security default
- **CSRF**: Disabled for API endpoints

## Development

To run in development mode:

```bash
mvn spring-boot:run
```

The application will:
1. Create the database schema
2. Load sample books
3. Create demo users (user/password, admin/admin)
4. Start the web server on port 8080

## Testing

Access the application with different user roles to test the security features:

1. **Anonymous User**: Can view books and search
2. **Regular User**: Can borrow/return books
3. **Admin User**: Full access to all features

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Test thoroughly
5. Submit a pull request

## License

This project is licensed under the MIT License. 