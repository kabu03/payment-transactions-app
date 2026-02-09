# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the application's JAR file into the container at /app
COPY target/my-app-1.0-SNAPSHOT.jar /app/paymentTransactionsApp.jar

# Expose the port your application runs on (modify if different)
EXPOSE 8080

# Define environment variables for database connection (modify as needed)
ENV DB_HOST=postgres
ENV DB_PORT=5432
ENV DB_NAME=postgres
ENV DB_USER=postgres
ENV DB_PASSWORD=123

# Run the application
ENTRYPOINT ["java","-jar","paymentTransactionsApp.jar"]
