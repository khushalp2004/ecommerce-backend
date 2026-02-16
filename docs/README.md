# E-commerce Backend

This project provides a Spring Boot REST API for products, categories, users, carts, and orders.

## Running locally

- Set database in `src/main/resources/application-dev.properties`.
- Run the app with the `dev` profile (enabled by default).

## API Endpoints

- GET /api/products
- GET /api/products/{id}
- POST /api/products (ADMIN)
- PUT /api/products/{id} (ADMIN)
- DELETE /api/products/{id} (ADMIN)
- GET /api/products/search?keyword=...
- GET /api/categories
- POST /api/auth/register
- POST /api/auth/login
- GET /api/users/profile
- POST /api/cart/add
- GET /api/cart
- POST /api/orders
- GET /api/orders/{id}
- GET /api/stats (ADMIN)

Swagger UI: /swagger-ui.html

## Metrics

The `/api/stats` endpoint returns runtime metrics (uptime, counts, request totals, response times, error rate, and DB query stats).
