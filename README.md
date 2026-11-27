# Gemini Planning & Review Workflow

Java 21 Spring Boot workflow demonstration with a two-stage Gemini prompt pipeline.

## Implemented
- Gemini 2.5 Flash planning call followed by a review call
- H2/JPA persistent workflow history
- REST `POST /api/v1/workflows`, `GET /api/v1/workflows`, `GET /api/v1/workflows/{id}`
- Browser interface at `http://localhost:8080/`
- Basic context test, Dockerfile and CI

## Run
```bash
export GEMINI_API_KEY=your_key_here
mvn spring-boot:run
```

## Limitations
This is a **two-stage LLM pipeline**, not a full autonomous multi-agent framework. No external tools, autonomous execution, authentication, distributed queue, retries, structured JSON validation or comprehensive integration tests. Do not send confidential data. Build has not been independently verified.
