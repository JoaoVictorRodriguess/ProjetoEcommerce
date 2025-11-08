# ===========================
# 1️⃣ Etapa de Build
# ===========================
FROM maven:3.9.9-eclipse-temurin-21 AS builder

# Define o diretório de trabalho
WORKDIR /app

# Copia o arquivo pom.xml e baixa as dependências
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copia o restante do código-fonte
COPY src ./src

# Compila o projeto e gera o .jar
RUN mvn clean package -DskipTests

# ===========================
# 2️⃣ Etapa de Execução
# ===========================
FROM eclipse-temurin:21-jdk

# Define o diretório de trabalho
WORKDIR /app

# Copia o jar gerado da etapa anterior
COPY --from=builder /app/target/project-Ecommerce-0.0.1-SNAPSHOT.jar app.jar

# Define a porta exposta
EXPOSE 8080

# Define a variável de ambiente do profile (opcional)
ENV SPRING_PROFILES_ACTIVE=dev

# Comando de execução do aplicativo
ENTRYPOINT ["java", "-jar", "app.jar"]
