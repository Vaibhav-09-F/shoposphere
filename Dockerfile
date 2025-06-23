# Use a lightweight JDK image
FROM eclipse-temurin:17-jdk-alpine AS build

WORKDIR /workspace

# Copy everything and build the jar
COPY . .
# If you’ve added the Maven wrapper (mvnw), use it; otherwise use mvn
RUN chmod +x mvnw
RUN ./mvnw clean package -DskipTests

# --- Runtime image ---
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app
# Copy the built jar from the build stage
COPY --from=build /workspace/target/shoposphere-0.0.1-SNAPSHOT.jar app.jar

# Expose port
EXPOSE 8080

# Run the jar
ENTRYPOINT ["java","-jar","app.jar"]