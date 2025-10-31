# Etapa 1 — Build
FROM maven:3.9.9-eclipse-temurin-17 AS build
WORKDIR /app

# Copia o pom.xml e baixa dependências
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copia o código-fonte e faz o build
COPY src ./src
RUN mvn clean package -DskipTests

# Etapa 2 — Runtime
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app

# Copia o jar gerado da etapa anterior
COPY --from=build /app/target/*.jar app.jar

# Exponha a porta padrão do Spring Boot (ajuste se necessário)
EXPOSE 8080

# Comando de execução
ENTRYPOINT ["java", "-jar", "app.jar"]
