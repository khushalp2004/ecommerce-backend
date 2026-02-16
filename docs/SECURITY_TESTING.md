# Security Testing Checklist

- Verify password hashing uses BCrypt
- Verify JWT expiration and signature
- Verify role-based access on ADMIN endpoints
- Verify CSRF token requirements for browser-based requests
- Verify CORS origin restrictions
- Validate input on all endpoints
- Verify rate limiting returns HTTP 429
