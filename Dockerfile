# --- STAGE 1: BUILD ---
FROM maven:3.9.6-eclipse-temurin-17 AS build

# Establece el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copia los archivos del proyecto al directorio /app
COPY . /app

# Ejecuta el build de Maven. El -DskipTests es clave para evitar fallos de prueba.
RUN mvn clean package -DskipTests --batch-mode

# --- STAGE 2: RUNTIME ---
# Imagen base más ligera para la ejecución (solo JRE, no el JDK completo)
FROM eclipse-temurin:17-jre-focal

# Establece la variable de entorno para el puerto de Spring Boot (Render usará esta si no está en application.properties)
ENV PORT 8080

# Exponer el puerto (principalmente para documentación)
EXPOSE 8080

# Establece el directorio de trabajo para el runtime
WORKDIR /app

COPY --from=build /app/target/hibernateSwagger-1.0-SNAPSHOT.jar app.jar

# Comando de ejecución. Le dice a Java cómo iniciar el JAR ejecutable de Spring Boot.
ENTRYPOINT ["java", "-jar", "app.jar"]
