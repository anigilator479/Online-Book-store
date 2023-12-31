# <h1 align="center">📚Online Book Store📚</h1>

### 👋 Introduction
The main goal of the Online Book Store application is to offer a platform for purchasing books that is both efficient and user-friendly. This platform benefits both customers and administrators by enabling customers to explore and purchase books securely. At the same time, administrators have the capability to make different operations like add, delete or update books, monitor and confirm orders

### 👩‍💻 Technologies Used
`The following technologies are used to build the Online Book Store Application:`
- ☕ **Java 17**
- 🌱 **Spring Boot**
- 🌱🛢️ **Spring Data JPA**
- 🌱🛡️ **Spring Security**
- 🗎 **Swagger**
- 🐬 **MySQL**
- 🐋  **Docker**
- 🌶️ **Lombok**
- ↔️ **MapStruct**

### ❓ How to use
`Before running Online Book Store, ensure you have the following installed:`
- ☕ Java
- 🐋 Docker

`Follow the steps below to install:`
1. Clone the repository from GitHub and navigate to the project directory.
2. Create a `.env` file with the necessary environment variables. (See `.env-sample` for a sample.)
3. Run the following command to build and start the Docker containers:
   `docker-compose up --build`.
4. The application should now be running at `http://localhost:8081`. 

### 🧐 Basic functionality video guide: [Link](https://www.loom.com/share/dc843b8b611d451295b0f8a1a379d619?sid=8e993d65-6180-4403-9d8b-4a09245b0c47)

### 🛢️ Database structure:
#### <h4 align="center">![img.png](assets/img.png) </h4>

### 👉 API Endpoints
`The Booking app provides the following API endpoints:`

### 🕵️ Auth Endpoints

| **HTTP method** | **Endpoint**                      | **Role**   | **Function**                                           |
|:----------------|:----------------------------------|------------|:-------------------------------------------------------|
| POST            | /register                         | ALL        | Register a new user.                                   |
| POST            | /login                            | ALL        | Get JWT token for authentication.                      |

### 📙 Books Management Endpoints
| **HTTP method** | **Endpoint**                      | **Role**   | **Function**                                           |
|:----------------|:----------------------------------|------------|:-------------------------------------------------------|
| POST            | /api/books                        | ADMIN      | Add a new book.                                        |
| GET             | /api/books                        | ALL        | Get a list of all books.                               |
| GET             | /api/books/{id}                   | ALL        | Get detailed information about a specific book.        |
| PUT             | /api/books/{id}                   | ADMIN      | Update information about specific book.                |
| DELETE          | /api/books/{id}                   | ADMIN      | Delete book.                                           |

### 📚 Categories Management Endpoints
| **HTTP method** | **Endpoint**                      | **Role**   | **Function**                                           |
|:----------------|:----------------------------------|------------|:-------------------------------------------------------|
| POST            | /api/categories                   | ADMIN      | Add a new category.                                    |
| GET             | /api/categories                   | ADMIN/USER | Get a range of all books categories                    |
| GET             | /api/categories/{id}              | ADMIN/USER | Get specific category by ID.                           |
| GET             | /api/categories/{id}/books        | ADMIN/USER | Get a list of books with specific category             |
| DELETE          | /api/categories/{id}              | ADMIN      | Deletes a specific category                            |
| PUT             | /api/categories/{id}              | ADMIN      | Update information about specific category             |

### 📋 Orders Management Endpoints
| **HTTP method** | **Endpoint**                      | **Role**   | **Function**                                           |
|:----------------|:----------------------------------|------------|:-------------------------------------------------------|
| POST            | /api/orders                       | ADMIN/USER | Create order from user's shopping cart content         |
| GET             | /api/orders                       | ADMIN/USER | Get orders history                                     |
| GET             | /api/orders/{orderId}/items       | ADMIN/USER | Get user's orders items list                           |
| GET             | /api/orders/{orderId}/items/{id}  | ADMIN/USER | Responses user's specific item from orders items by id |
| PATCH           | /api/orders/{id}                  | ADMIN      | Updates order's status by id                           |

### 🛒 Shopping Cart Management Endpoints
| **HTTP method** | **Endpoint**                      | **Role**   | **Function**                                           |
|:----------------|:----------------------------------|------------|:-------------------------------------------------------|
| GET             | /api/cart                         | ADMIN/USER | Get all information about shopping cart content        |
| DELETE          | /api/cart/cart-items/{id}         | ADMIN/USER | Delete a cart item from a shopping cart                |
| POST            | /api/cart                         | ADMIN/USER | Add cart item                                          |
| PUT             | /api/cart/{id}                    | ADMIN/USER | Update cart item quantity                              |

### 🤝 Contribution Guidelines
`I welcome contributions to this project:`

For every new feature or bug fix, please establish a separate branch and initiate a pull request to the primary branch. Prior to merging, it is imperative that all pull requests undergo thorough review and receive approval.
