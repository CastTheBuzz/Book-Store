# 📚 Bookstore Management System

A full-stack web application built with **Spring Boot** and **PostgreSQL** that allows users to manage books with authentication and role-based access control.

This project was originally developed as a university assignment and later improved into a portfolio-ready production deployment.

---

## 🚀 Live Demo

🌐 **Live Application:**  
https://your-render-url.onrender.com  

*(Replace with your actual Render link)*

---

## 🛠 Tech Stack

### Backend
- Java 21
- Spring Boot
- Spring Security
- Spring Data JPA
- Hibernate

### Frontend
- Thymeleaf
- HTML5
- CSS3

### Database
- PostgreSQL

### Deployment
- Render (Web Service)
- Render PostgreSQL (Managed Database)

---

## ✨ Features

- 🔐 User Registration & Login
- 👤 Role-Based Access Control (ADMIN / USER)
- 📚 Add, Edit, Delete Books (Admin)
- 🔎 Search & Pagination
- 🖼 Image Upload for Books
- 📖 Book Description Support
- 🎨 Responsive UI Styling
- 🌍 Deployed to Cloud (Render)

---

## 🏗 Architecture

The application follows a standard layered architecture:

Controller → Service → Repository → Database

- Controllers handle HTTP requests
- Services contain business logic
- Repositories interact with PostgreSQL
- Thymeleaf renders dynamic views

---

## 🗄 Database Structure

Main Tables:

- users
- roles
- books

Hibernate auto-generates schema using:

```
spring.jpa.hibernate.ddl-auto=update
```

---

## ⚙ Environment Variables (Production)

The application uses environment variables for secure deployment:

```
spring.datasource.url=${DB_URL}
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}
```

These are configured inside Render.

---

## 🖥 Running Locally

### 1️⃣ Clone Repository

```
git clone https://github.com/yourusername/bookstore.git
cd bookstore
```

### 2️⃣ Configure application.properties

For local development:

```
spring.datasource.url=jdbc:postgresql://localhost:5432/bookstore
spring.datasource.username=postgres
spring.datasource.password=yourpassword
```

### 3️⃣ Run Application

Windows:

```
mvnw.cmd spring-boot:run
```

Mac/Linux:

```
./mvnw spring-boot:run
```

Or build jar:

```
mvnw.cmd clean package
java -jar target/bookstore-0.0.1-SNAPSHOT.jar
```

---

## ☁ Deployment Process (Render)

1. Created PostgreSQL database on Render
2. Configured environment variables
3. Connected GitHub repository
4. Used Maven wrapper:

Build command:
```
./mvnw clean package
```

Start command:
```
java -jar target/bookstore-0.0.1-SNAPSHOT.jar
```

---

## 📁 Project Structure

```
src/
 ├── main/
 │    ├── java/
 │    ├── resources/
 │    │     ├── templates/
 │    │     ├── static/
 │    │     └── application.properties
 └── test/
```

---

## 🔒 Security

- Password encryption using BCrypt
- Authentication with Spring Security
- Role-based authorization

---

## 📌 Future Improvements

- Cloud image storage (AWS S3 or Cloudinary)
- REST API version
- Docker containerization
- Unit & Integration tests
- CI/CD pipeline

---

## 👨‍💻 Author

Kristian Lala  
Software Engineering Student  
Java & Spring Boot Developer  

---

## 📄 License

This project is for educational and portfolio purposes.
