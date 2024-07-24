# Usa una imagen de OpenJDK para Java 17 como base
FROM amazoncorretto:17
# Establece el directorio de trabajo en /app
WORKDIR /app

# Copia el JAR construido desde el sistema de archivos local al contenedor
COPY target/internship-backend-0.0.1-SNAPSHOT.jar .

# Expone el puerto en el que la aplicación se ejecutará
EXPOSE 8080

RUN echo "Variables de entorno dentro del contenedor:"

RUN env

# Comando para ejecutar la aplicación al iniciar el contenedor
CMD ["java", "-jar", "internship-backend-0.0.1-SNAPSHOT.jar"]
