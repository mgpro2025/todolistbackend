# --- Etapa 1: Construcción (Build) ---
# Usamos una imagen de Java (JDK) para compilar nuestro código
FROM eclipse-temurin:21-jdk-jammy as builder

# Establecemos el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copiamos los archivos necesarios para descargar dependencias
COPY .mvn/ .mvn/
COPY mvnw pom.xml ./

# Descargamos las dependencias (esto se cachea para builds más rápidas)
RUN ./mvnw dependency:go-offline

# Copiamos el resto del código fuente
COPY src ./src

# Compilamos la aplicación y creamos el archivo .jar
# Saltamos los tests porque asumimos que ya pasaron en desarrollo
RUN ./mvnw clean install -DskipTests


# --- Etapa 2: Ejecución (Runtime) ---
# Usamos una imagen más ligera solo con el entorno de ejecución de Java (JRE)
FROM eclipse-temurin:21-jre-jammy

# Establecemos el directorio de trabajo
WORKDIR /app

# Copiamos solo el archivo .jar compilado desde la etapa anterior
COPY --from=builder /app/target/*.jar app.jar

# Exponemos el puerto en el que corre Spring Boot
EXPOSE 8080

# El comando para iniciar la aplicación cuando el contenedor se inicie
ENTRYPOINT ["java", "-jar", "app.jar"]