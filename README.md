# Spaceship API

## Descripción
La Spaceship API permite a los usuarios administrar una base de datos de naves espaciales para su uso en películas. Esta API está construida con Spring Boot, utiliza una base de datos H2, maneja las migraciones de la base de datos a través de Liquibase, y utiliza Kafka para el manejo de mensajes.

## Generar y Acceder a la Documentación de la API
La documentación de la API se genera automáticamente a través de Springdoc y está disponible en Swagger UI. Sigue estos pasos para acceder:
- Ejecutar: 
```
mvn spring-boot:run
```

- Inicia la aplicación y navega a http://localhost:8080/swagger-ui.html para ver la documentación de la API.

#### En caso de haber problemas con la genereacion de documentacion he añadido la coleccion postman
- **resources/`Spaceships.postman_collection`**


## Requisitos
- Java 22
- Maven para la gestión de dependencias

## Dependencias Principales
- **Spring Boot Web**: para el manejo de solicitudes web.
- **Spring Security**: para la seguridad y la autenticación.
- **Spring Data JPA**: para la persistencia de datos.
- **Liquibase**: para la migración de la base de datos.
- **Spring Kafka**: para integrar capacidades de Kafka.
- **JJWt**: para la generación y validación de tokens JWT.
- **Lombok**: para reducir el código boilerplate.
- **Caffeine**: para implementar caching.

## Configuración de la Base de Datos
La API utiliza una base de datos H2 en memoria con las siguientes credenciales:
- **URL**: `jdbc:h2:mem:spaceships_db;DB_CLOSE_DELAY=-1;MODE=MYSQL`
- **Driver**: `org.h2.Driver`
- **Username**: `root`
- **Password**: `12345`

### Liquibase
Liquibase se utiliza para gestionar las migraciones de la base de datos. Los archivos XML especifican los cambiosets que crean estructuras de tablas e insertan datos iniciales.

#### Archivos de Changelog
- `001-create-tables.xml`: Define la estructura inicial de las tablas.
- `002-insert-roles.xml`: Inserta roles predeterminados en la base de datos.

## Kafka Integration
La API integra Kafka para el procesamiento de mensajes de naves espaciales.

### Consumer de Kafka
El `SpaceshipConsumer` escucha en el topic `spaceship` y procesa los mensajes recibidos:
- **Grupo ID**: `mi-grupo`
- **Bootstrap Servers**: `localhost:9092`

### Producer de Kafka
El `SpaceshipProducer` envía mensajes al topic `spaceship` usando un `KafkaTemplate`.

## Logging Aspect
Se ha implementado un aspecto de logging para mejorar la trazabilidad de las solicitudes. Este aspecto registra un mensaje antes de que se ejecute el método `view` del `SpaceshipController`:
- **Método Afectado**: `view(Long id)`
- **Condición Especial**: Si se solicita una nave con ID negativo, se registra un error y se lanza una excepción.

## Caching
La API utiliza caching para mejorar el rendimiento y reducir la carga en la base de datos. Se emplean diferentes caches para diferentes tipos de consultas:
- **spaceshipPages**: Cachea páginas de listados de naves espaciales.
- **spaceship**: Cachea los detalles de una nave espacial específica por su ID.
- **spaceshipByName**: Cachea resultados de búsquedas de naves por nombre.

Los métodos de actualización y eliminación de naves espaciales gestionan adecuadamente la invalidación del cache para asegurar la coherencia de los datos.

## Dependencias Principales
- Spring Boot Web para el manejo de solicitudes web.
- Spring Security para la seguridad y la autenticación.
- Spring Data JPA para la persistencia de datos.
- Liquibase para la migración de la base de datos.
- Spring Kafka para integrar capacidades de Kafka.
- JJWt para la generación y validación de tokens JWT.
- Lombok para reducir el código boilerplate.
- Caffeine para implementar caching.

## Instalación y Ejecución
Clonar el repositorio y ejecutar el siguiente comando en la raíz del proyecto para iniciar la aplicación:
```bash

mvn spring-boot:run

````

## Autenticación
Para interactuar con los endpoints de la API, es necesario autenticarse. Los detalles de usuario se gestionan a través del endpoint `/api/users`.

### Endpoints de Usuario
- `POST /api/users/admin`: Registra un usuario como administrador. Este endpoint permite crear un usuario con privilegios de administrador, necesario para realizar operaciones de creación, actualización y eliminación de naves espaciales.
- `POST /api/users/register`: Registra un usuario sin privilegios administrativos. Este endpoint es para usuarios que necesitan acceso de consulta sin capacidades de modificación.

## Seguridad
Esta API utiliza Spring Security para gestionar la autenticación y autorización de los usuarios. La seguridad está configurada para requerir que todos los usuarios estén autenticados para acceder a los endpoints principales, y los roles de usuario (como administrador) son necesarios para acceder a funciones específicas.

### JWT para Seguridad
La API utiliza tokens JWT (JSON Web Tokens) para manejar la autenticación de los usuarios. Los tokens aseguran que las solicitudes a la API estén autorizadas. Se generan utilizando JJWT y se validan en cada solicitud para asegurar que sean válidos y estén activos.

### Roles y Privilegios
- **ADMIN**: Usuarios con este rol pueden crear, actualizar y eliminar naves espaciales.
- **USER**: Los usuarios con este rol básicamente tienen permisos de lectura para consultar información sobre naves espaciales.

## Dockerización
La aplicación está dockerizada utilizando la siguiente configuración en un archivo `Dockerfile`:

#### Usa una imagen base con OpenJDK 16 ya que no hay una reciente con Java 22
FROM adoptopenjdk:16-jdk

#### Directorio de trabajo dentro del contenedor
WORKDIR /app

##### Copia el archivo JAR generado por Maven (ubicado en la carpeta target) al directorio de trabajo del contenedor
COPY target/maintenance_spaceships_films-0.0.1-SNAPSHOT.jar /app/spaceships.jar

##### Comando a ejecutar cuando se inicie el contenedor
CMD ["java", "-jar", "spaceships.jar"]


## Tests
La API incluye tests unitarios y de integración para asegurar la calidad y funcionalidad del código.

### Tests Unitarios
Tests unitarios en `SpaceshipControllerTest` verifican las operaciones CRUD en los endpoints y aseguran que las respuestas y estados HTTP son los esperados.

### Tests de Integración
En `SpaceshipServiceImplTest`, se prueban las interacciones con la base de datos y la lógica de negocio, asegurando que las operaciones de base de datos funcionan como se espera y que los servicios responden correctamente a las entradas dadas.