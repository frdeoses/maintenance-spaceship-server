# Usa una imagen base con OpenJDK 16 ya que no hay una reciente con java 22
FROM adoptopenjdk:16-jdk

# Directorio de trabajo dentro del contenedor
WORKDIR /app

# Copia el archivo JAR generado por Maven (ubicado en la carpeta target) al directorio de trabajo del contenedor
COPY target/maintenance_spaceships_films-0.0.1-SNAPSHOT.jar /app/spaceships.jar

# Comando a ejecutar cuando se inicie el contenedor
CMD ["java", "-jar", "spaceships.jar"]