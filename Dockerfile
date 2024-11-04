# Importar JDK y copiar archivos necesarios
FROM openjdk:19-jdk AS build
WORKDIR /app
COPY pom.xml .
COPY src src

# Copiar el wrapper de Maven
COPY mvnw .
COPY .mvn .mvn

# Dar permisos de ejecución al wrapper de Maven
RUN chmod +x ./mvnw
RUN ./mvnw clean package -DskipTests

# Etapa 2: Crear la imagen Docker final usando OpenJDK 19
FROM openjdk:19-jdk
VOLUME /tmp

# Copiar el JAR desde la etapa de construcción
COPY --from=build /app/target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
EXPOSE 8080
