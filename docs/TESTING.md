# Testing

- Unit tests: service-layer tests using Mockito
- Integration tests: Testcontainers with PostgreSQL
- API tests: Postman collection in docs/postman_collection.json
- Coverage: JaCoCo report generated on verify
- Security: OWASP dependency-check report on verify

Run tests:
- mvn test

Generate reports:
- mvn verify

Outputs:
- target/site/jacoco/index.html
- target/dependency-check-report.html
