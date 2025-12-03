# E-Commerce Backend System
##  Project Overview
This project is a Spring Boot–based **E-commerce Backend System** that provides REST APIs to manage users, products, shopping cart, orders, payments, and inventory.
The system follows industry-standard layered architecture:
### **Key Features**
- User registration, login, and role-based access (ADMIN & CUSTOMER)
- Product management (CRUD operations)
- Shopping cart management per customer
- Checkout and order creation
- Payment simulation (success/failure)
- Automatic inventory updates after each order
- Pagination & filtering for product listing
## How to Run Locally
Right-click → Run As → Spring Boot App

### **Prerequisites**
- Java 17+
- Maven
- MySQL Server
- Eclipse or IntelliJ
### **Steps**
1. **Clone the repository**
   ```bash
   git clone <your-repo-url>
CREATE DATABASE ecommerce;
spring.datasource.url=jdbc:mysql://localhost:3306/ecommerce
spring.datasource.username=root
spring.datasource.password=yourpassword
spring.jpa.hibernate.ddl-auto=update
Right-click → Run As → Spring Boot App
mvn spring-boot:run
http://localhost:8080

USER APIs
---------
POST    /api/users/register
POST    /api/users/login
GET     /api/users/{id}
PUT     /api/users/{id}
DELETE  /api/users/{id}          (Admin only)

PRODUCT APIs
------------
POST    /api/products            (Admin)
GET     /api/products
GET     /api/products/{id}
PUT     /api/products/{id}       (Admin)
DELETE  /api/products/{id}       (Admin)

CART APIs
---------
POST    /api/cart/add/{productId}
PUT     /api/cart/update/{productId}
DELETE  /api/cart/remove/{productId}
GET     /api/cart

ORDER APIs
----------
POST    /api/orders/checkout
GET     /api/orders
GET     /api/orders/{id}
PUT     /api/orders/{id}/status   (Admin)


**DATABASE SCHEMA**
users
---------------------------------------------------
user_id        BIGINT (PK, AUTO_INCREMENT)
name           VARCHAR
email          VARCHAR
password       VARCHAR
role           VARCHAR

carts
---------------------------------------------------
cart_id        BIGINT (PK, AUTO_INCREMENT)
total_price    DECIMAL(19,2)
user_id        BIGINT (FK → users.user_id)


cart_items
---------------------------------------------------
id             BIGINT (PK, AUTO_INCREMENT)
quantity       INT
subtotal       DECIMAL(19,2)
cart_id        BIGINT (FK → carts.cart_id)
product_id     BIGINT (FK → products.product_id)


products
---------------------------------------------------
product_id         BIGINT (PK, AUTO_INCREMENT)
product_name       VARCHAR
product_description VARCHAR
price              DECIMAL(19,2)
stock_quantity     INT
category           VARCHAR
image_url          VARCHAR
rating             DOUBLE


orders
---------------------------------------------------
id                BIGINT (PK, AUTO_INCREMENT)
total_amount      DECIMAL(19,2)
order_date        DATETIME
payment_status    VARCHAR
order_status      VARCHAR
user_id           BIGINT (FK → users.user_id)


order_items
---------------------------------------------------
id              BIGINT (PK, AUTO_INCREMENT)
quantity        INT
price           DECIMAL(19,2)
order_id        BIGINT (FK → orders.id)
product_id      BIGINT (FK → products.product_id)







