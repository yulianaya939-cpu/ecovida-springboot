# Eco Vida S.A.S. - Backend

## Descripción

Eco Vida S.A.S. cuenta con un backend desarrollado con Spring Boot para gestionar la información de usuarios, clientes, residuos y recolecciones.

El backend expone servicios REST, administra la persistencia mediante MySQL, implementa autenticación mediante JWT y controla los permisos de acuerdo con el rol del usuario.

También permite el consumo de servicios externos para obtener información de usuarios y datos climáticos.

## Tecnologías utilizadas

- Java
- Spring Boot
- Spring Security
- JWT
- BCrypt
- Spring Data JPA
- MySQL
- Maven

## Requisitos

Para ejecutar el backend se necesita:

- Java
- Maven o Maven Wrapper
- MySQL
- Base de datos ecovidasas_spring

## Configuración

La aplicación utiliza variables de entorno para evitar publicar credenciales directamente en el repositorio.

En el archivo application.properties se utilizan las siguientes propiedades:

spring.datasource.url=jdbc:mysql://localhost:3306/ecovidasas_spring
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}
jwt.secret=${JWT_SECRET}

Antes de ejecutar el proyecto deben estar configuradas las siguientes variables:

DB_USERNAME
DB_PASSWORD
JWT_SECRET

El backend utiliza el puerto 8080.

## Ejecución

En Windows, desde la carpeta raíz del proyecto se puede ejecutar:

.\mvnw.cmd spring-boot:run

También es posible ejecutar el proyecto desde un IDE compatible con Spring Boot.

El backend estará disponible en:

http://localhost:8080

## Arquitectura

El backend utiliza una arquitectura por capas.

La estructura principal se encuentra organizada en:

src/main/java/com/ecovidasas/

├── controller/
├── service/
├── repository/
├── entity/
├── dto/
└── security/

### Controller

Recibe las solicitudes HTTP y expone los servicios REST.

### Service

Contiene la lógica de negocio de los diferentes módulos.

### Repository

Permite realizar las operaciones de acceso a la base de datos mediante Spring Data JPA.

### Entity

Representa las entidades que se almacenan en la base de datos.

### DTO

Permite transportar la información necesaria entre las diferentes operaciones.

### Security

Contiene la configuración de autenticación, autorización y filtros relacionados con JWT.

## Autenticación

El sistema utiliza JWT para autenticar a los usuarios.

El proceso general es:

Usuario
↓
Inicio de sesión
↓
Validación de credenciales
↓
Generación del token JWT
↓
Acceso a endpoints protegidos

Cuando las credenciales son correctas y el usuario está activo, el backend genera el token.

Las contraseñas son procesadas mediante BCrypt.

## Roles

El sistema utiliza dos roles principales:

- Administrador
- Usuario

El Administrador tiene permisos para realizar operaciones administrativas, especialmente la gestión de usuarios.

El Usuario puede acceder a las funciones permitidas para su rol.

Las operaciones protegidas se validan desde el backend mediante Spring Security.

## Endpoints principales

### Autenticación

POST /api/auth/login

Permite validar las credenciales y obtener el token JWT.

### Usuarios

GET    /api/usuarios
GET    /api/usuarios/{id}
POST   /api/usuarios
PUT    /api/usuarios/{id}
DELETE /api/usuarios/{id}

### Clientes

GET    /clientes
GET    /clientes/{id}
POST   /clientes
PUT    /clientes/{id}
DELETE /clientes/{id}

### Residuos

GET    /residuos
GET    /residuos/{id}
POST   /residuos
PUT    /residuos/{id}
DELETE /residuos/{id}

### Recolecciones

GET    /api/recolecciones
GET    /api/recolecciones/{id}
POST   /api/recolecciones
PUT    /api/recolecciones/{id}
DELETE /api/recolecciones/{id}

### Servicios externos

GET /api/publica/usuarios
GET /api/clima

## Modelo de datos

Las principales entidades del sistema son:

Usuario
Cliente
Residuo
Recoleccion

Las relaciones principales son:

Cliente
│
├── Residuo
│
└── Recoleccion
       │
       └── Residuo

Un cliente puede tener varios residuos registrados.

Una recolección se relaciona con un cliente y un residuo previamente registrado.

## Seguridad

Las solicitudes protegidas requieren autenticación mediante JWT.

Las operaciones administrativas se restringen según el rol del usuario.

Durante las pruebas se comprobó que un usuario con rol Usuario no puede ejecutar una operación exclusiva del Administrador.

Por ejemplo:

DELETE /residuos/10

devuelve 403 Forbidden cuando se intenta ejecutar con un usuario que no tiene el permiso correspondiente.

## Validaciones y manejo de errores

El backend realiza validaciones sobre los datos recibidos y maneja diferentes situaciones mediante códigos de respuesta HTTP.

Durante las pruebas se verificaron, entre otros, los siguientes resultados:

- 200 OK
- 201 Created
- 204 No Content
- 400 Bad Request
- 403 Forbidden
- 404 Not Found

## Servicios externos

### API pública de usuarios

Se integró JSONPlaceholder mediante el servicio:

GET /api/publica/usuarios

### Servicio de clima

Se integró Open-Meteo para consultar información climática de Cartagena mediante:

GET /api/clima

La respuesta incluye información como temperatura, precipitación y velocidad del viento.

## Base de datos

La persistencia de la información se realiza mediante MySQL.

Las entidades principales se relacionan mediante claves primarias y foráneas.

La base de datos utilizada por el proyecto es:

ecovidasas_spring

## Pruebas

Durante la integración se realizaron pruebas mediante Postman y desde la aplicación.

Se verificaron:

- autenticación;
- operaciones CRUD;
- validaciones;
- autorización por roles;
- manejo de recursos inexistentes;
- consumo de servicios externos;
- integración entre módulos.

## Usuarios de prueba

El sistema fue probado con usuarios correspondientes a los roles:

- Administrador
- Usuario

Las contraseñas y tokens utilizados durante las pruebas no se incluyen en el repositorio.

## Repositorio

https://github.com/yulianaya939-cpu/ecovida-springboot