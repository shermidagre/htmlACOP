FROM maven:3.9.6-eclipse-temurin-17 AS build

WORKDIR /app

# **CRUCIAL:** Copia todos los archivos necesarios para la compilación de Maven:
# pom.xml, la carpeta src/ con el código fuente, etc.
COPY . /app

# Ejecuta el build, generando el JAR en target/
# Usamos -DskipTests para asegurar que no fallen por falta de un entorno de pruebas completo
RUN mvn clean package -DskipTests --batch-mode

# =========================================================
# ETAPA 2: FINAL (Imagen de Ejecución)
FROM eclipse-temurin:17-jre-focal

WORKDIR /app

# Copia el JAR ejecutable de la Etapa 1
COPY --from=build /app/target/hibernateSwagger-1.0-SNAPSHOT.jar app.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "app.jar"]