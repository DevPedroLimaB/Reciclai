FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Copia o JAR já compilado
COPY backend/build/libs/*.jar app.jar

# Exponhe porta
EXPOSE 8080

# Variáveis de ambiente
ENV SPRING_PROFILES_ACTIVE=prod
ENV DB_HOST=postgres
ENV DB_USER=reciclai_user
ENV DB_PASSWORD=reciclai_pass_2024

# Comando de inicialização
ENTRYPOINT ["java", "-jar", "app.jar"]
