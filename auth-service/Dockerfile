FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

COPY auth-service/build/libs/auth-service.jar app.jar

EXPOSE 8082

ENV SPRING_PROFILES_ACTIVE=prod
ENV DB_HOST=postgres
ENV DB_USER=reciclai_user
ENV DB_PASSWORD=reciclai_pass_2024

ENTRYPOINT ["java", "-jar", "app.jar"]
