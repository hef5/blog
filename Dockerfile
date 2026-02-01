# =========================================================================================
# Multi-stage Dockerfile for optimized image size and security
# =========================================================================================

# Stage 1: Build the application
# We use a Maven image to build the artifact, so the source code and Maven 
# are not included in the final runtime image.
FROM maven:3.9.6-eclipse-temurin-11-alpine AS builder
WORKDIR /app

# Copy the dependency specifications first to leverage Docker cache
COPY pom.xml .
# Download dependencies (this step will be cached unless pom.xml changes)
RUN mvn dependency:go-offline -B

# Copy the actual source code and build
COPY src ./src
RUN mvn clean package -DskipTests

# =========================================================================================

# Stage 2: create the runtime image
# We use a lightweight JRE image for production
FROM eclipse-temurin:11-jre-alpine
WORKDIR /app

# Best Practice: Run as a non-root user
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

# Copy the built artifact from the builder stage
COPY --from=builder /app/target/*.jar app.jar

# Environment configuration
ENV PORT=8080
ENV SPRING_PROFILES_ACTIVE=pro

# NOTE: For GCP Cloud Run, we rely on the attached Service Account (Workload Identity)
# rather than baking in a sensitive key file like 'blog-key.json'.
# If local testing requires a key, mount it as a volume at runtime.

EXPOSE 8080

# Use exec form of ENTRYPOINT so signals are passed to the JVM
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app/app.jar"]
