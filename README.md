# 📚 Bookstore Application

## 🌐 Overview

The Bookstore Application is a full-stack web system built with Spring Boot that allows users to browse, manage, and organize books by category.  

It provides both:

- A web interface (Thymeleaf templates)
- JSON-based endpoints for data interaction

The system supports book management, category organization, user registration, authentication, and role-based access control.

This project demonstrates backend architecture, REST principles, database integration, and secure user management in a production-ready Spring Boot environment.

---

# 🎯 Main Features

## 📖 Book Management
- View all books
- Add new books (Admin only)
- Edit existing books (Admin only)
- Delete books (Admin only)
- Assign books to categories

Each book includes:
- Title
- Author
- Year
- ISBN
- Price
- Category reference

---

## 🗂️ Category Management
- Predefined categories created on startup
- Books are linked to categories
- Categories help organize inventory logically

Example categories:
- Programming
- Science
- Finance
- Fiction

---

## 👤 User System
- User registration
- Secure login
- Role-based permissions
- Password encryption using BCrypt

Roles:
- `ROLE_USER` – Can browse books
- `ROLE_ADMIN` – Can manage books (create, edit, delete)

---

## 🔄 JSON Support (REST Capabilities)

In addition to the web interface, the application supports JSON-based interaction.

This allows:

- Retrieving book data as JSON
- Sending POST requests with JSON payloads
- API-style interaction using tools like Postman or curl

Example JSON structure for creating a book:

```json
{
  "title": "Clean Code",
  "author": "Robert C. Martin",
  "year": 2008,
  "isbn": "9780132350884",
  "price": 39.99,
  "category": {
    "id": 1
  }
}
```

This makes the system flexible for both frontend rendering and API consumption.

---

# 🏗️ Technical Architecture

The project follows a layered Spring Boot MVC architecture:

Controller → Service → Repository → Database

### Layers Explained

**Controller Layer**
- Handles HTTP requests
- Returns either HTML views or JSON responses

**Service Layer**
- Business logic
- Security authentication handling

**Repository Layer**
- JPA repositories for database operations
- Uses Hibernate for ORM

**Database**
- PostgreSQL
- Cloud-hosted in production

---

# 🛠️ Technology Stack

### Backend
- Java 21
- Spring Boot
- Spring Security
- Spring Data JPA
- Hibernate

### Frontend
- Thymeleaf
- HTML
- CSS

### Database
- PostgreSQL

### Deployment
- Docker
- Render (Cloud Hosting)
- Git & GitHub

---

# 🗄️ Database Design

## Main Entities

### Book
- id
- title
- author
- year
- isbn
- price
- category (Many-to-One relationship)

### Category
- id
- name
- books (One-to-Many relationship)

### User
- id
- username
- password (encrypted)
- role

---

# 🔐 Security Overview

- Form-based login
- BCrypt password hashing
- Custom UserDetailsService
- Role-based access restrictions
- Admin-only endpoints protected via Spring Security

Passwords are never stored in plain text.

---

# 🚀 Running the Application

## Run Locally

1. Clone repository

```
git clone https://github.com/yourusername/bookstore.git
cd bookstore
```

2. Configure PostgreSQL database

Update `application.properties`:

```
spring.datasource.url=jdbc:postgresql://localhost:5432/bookstore
spring.datasource.username=your_username
spring.datasource.password=your_password
```

3. Run application

```
mvnw spring-boot:run
```

Open:

```
http://localhost:8080
```

---

# 🐳 Production Deployment

The application is containerized using Docker and deployed to Render.

- Docker builds the application image
- Environment variables configure the database connection
- PostgreSQL is hosted in the cloud
- Application runs fully in production environment

---

# 📂 Project Structure

```
bookstore
│
├── config
├── controller
├── model
├── repository
├── service
├── templates
├── static
├── Dockerfile
├── pom.xml
```

---

# 🧪 API Testing

You can test JSON endpoints using:

- Postman
- curl
- Browser (for GET requests)

Example:

GET all books:
```
GET /api/books
```

POST new book:
```
POST /api/books
Content-Type: application/json
```

---

# 🎓 Learning Objectives Demonstrated

This project showcases:

- Full CRUD operations
- REST + MVC hybrid design
- Entity relationships with JPA
- Role-based authorization
- Secure authentication flow
- Production database integration
- Docker containerization
- Cloud deployment

---

# 📈 Possible Future Enhancements

- Pagination for large book lists
- Search & filtering
- Book cover image upload
- REST API documentation (Swagger)
- Unit & integration tests
- Admin dashboard statistics
- Shopping cart functionality

---

# 👨‍💻 Author

Developed as a backend-focused Spring Boot bookstore system demonstrating full-stack fundamentals, API design, and production deployment.

---
