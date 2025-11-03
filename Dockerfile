# Multi-stage build para optimizar el tamaño de la imagen final

# Etapa 1: Build de la aplicación
FROM eclipse-temurin:17-jdk-alpine AS build

# Instalar Maven
RUN apk add --no-cache maven

# Establecer directorio de trabajo
WORKDIR /app

# Copiar archivos de configuración de Maven
COPY pom.xml .
COPY mvnw .
COPY mvnw.cmd .
COPY .mvn .mvn

# Descargar dependencias (cache layer)
RUN mvn dependency:go-offline -B

# Copiar código fuente
COPY src ./src

# Compilar la aplicación
RUN mvn clean package -DskipTests

# Etapa 2: Imagen de ejecución
FROM eclipse-temurin:17-jre-alpine

# Crear usuario no-root para seguridad
RUN addgroup -g 1001 -S appgroup && \
    adduser -u 1001 -S appuser -G appgroup

# Instalar dependencias de runtime
RUN apk add --no-cache tzdata curl

# Configurar timezone
ENV TZ=America/La_Paz

# Establecer directorio de trabajo
WORKDIR /app

# Copiar el JAR de la etapa de build
COPY --from=build /app/target/*.jar app.jar

# Cambiar permisos
RUN chown -R appuser:appgroup /app

# Cambiar a usuario no-root
USER appuser

# Configurar variables de entorno para JVM
ENV JAVA_OPTS="-Xms512m -Xmx1024m -XX:+UseG1GC -XX:G1HeapRegionSize=16m -XX:+UseStringDeduplication"

# Exponer puerto
EXPOSE 8081

# Health check
HEALTHCHECK --interval=30s --timeout=10s --start-period=60s --retries=3 \
  CMD curl -f http://localhost:8081/actuator/health || exit 1

# Comando de ejecución
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]