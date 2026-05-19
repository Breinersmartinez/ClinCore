# Sistema de Gestión de Citas Médicas
## Spring Boot · Arquitectura Hexagonal · Vertical Slicing

---

## Stack Tecnológico

| Capa | Tecnología |
|------|-----------|
| Backend | Java 21 + Spring Boot 3.2.5 |
| Seguridad | Spring Security + JWT (jjwt 0.11.5) |
| Persistencia | Spring Data JPA + Hibernate |
| Base de datos | H2 (demo) / Oracle XE 18c (producción) |
| Build | Maven 3.8+ |
| Lombok | 1.18.x |

---

## Arquitectura

### Hexagonal + Vertical Slicing

```
src/main/java/co/edu/citas/
│
├── CitasMedicasApplication.java
│
├── shared/                          ← Código transversal
│   ├── config/
│   │   ├── SecurityConfig.java      ← Spring Security + CORS
│   │   └── DataInitializer.java     ← Datos semilla
│   ├── exception/
│   │   ├── GlobalExceptionHandler.java
│   │   ├── ResourceNotFoundException.java
│   │   └── BusinessException.java
│   └── security/
│       ├── JwtUtil.java
│       └── JwtAuthenticationFilter.java
│
└── modules/                         ← Vertical Slicing: 1 carpeta = 1 módulo
    │
    ├── auth/                        ← Login / Autenticación
    │   ├── application/dto/AuthDto.java
    │   ├── application/usecase/AuthUseCase.java
    │   └── infrastructure/web/AuthController.java
    │
    ├── rol/                         ← CRUD de Roles + Gestión de Permisos
    │   ├── domain/
    │   │   ├── model/Rol.java                          ← Entidad de dominio
    │   │   └── port/RolRepositoryPort.java             ← Puerto (interfaz)
    │   ├── application/
    │   │   ├── dto/RolDto.java
    │   │   └── usecase/RolUseCase.java                 ← Lógica de negocio
    │   └── infrastructure/
    │       ├── persistence/
    │       │   ├── entity/RolEntity.java               ← Entidad JPA
    │       │   └── repository/
    │       │       ├── RolJpaRepository.java           ← Spring Data JPA
    │       │       └── RolRepositoryAdapter.java       ← Adaptador (implementa puerto)
    │       └── web/RolController.java                  ← REST Controller
    │
    ├── usuario/                     ← CRUD de Usuarios + Seguridad
    │   └── ... (misma estructura)
    │
    ├── consultorio/                 ← CRUD de Consultorios  ✓
    │   └── ... (misma estructura)
    │
    └── especialidad/                ← CRUD de Especialidades  ✓
        └── ... (misma estructura)
```

### Flujo Hexagonal por módulo

```
HTTP Request
    ↓
[Controller]           ← Adaptador primario (driving)
    ↓
[UseCase]              ← Lógica de negocio (núcleo)
    ↓
[RepositoryPort]       ← Puerto de salida (interfaz)
    ↓
[RepositoryAdapter]    ← Adaptador secundario (driven)
    ↓
[JpaRepository + DB]   ← Infraestructura
```

---

## Instalación y Ejecución

### Prerequisitos
- Java 21+
- Maven 3.8+

### Pasos

```bash
# 1. Clonar / descomprimir el proyecto
cd citas-medicas

# 2. Compilar
mvn clean package -DskipTests

# 3. Ejecutar
java -jar target/citas-medicas-1.0.0.jar

# O con Maven directamente
mvn spring-boot:run
```

### Acceso a H2 Console (modo desarrollo)
```
URL:      http://localhost:8080/h2-console
JDBC URL: jdbc:h2:mem:citasdb
User:     sa
Password: (vacío)
```

---

## Usuarios por Defecto

| Username | Password | Rol | Descripción |
|----------|----------|-----|-------------|
| `admin` | `Admin2024#` | ROLE_ADMIN | Acceso total al sistema |
| `medico1` | `Medico2024#` | ROLE_MEDICO | Solo lectura de catálogos |
| `auxiliar1` | `Auxiliar2024#` | ROLE_AUXILIAR | Crear y editar, sin eliminar |

---

## API REST — Endpoints

### 🔐 Autenticación

```
POST /api/auth/login
Content-Type: application/json

{
  "username": "admin",
  "password": "Admin2024#"
}

→ Respuesta:
{
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "username": "admin",
  "nombreCompleto": "Administrador del Sistema",
  "roles": ["ROLE_ADMIN"],
  "expiresIn": 86400000
}
```

### Usar el token en las siguientes peticiones:
```
Authorization: Bearer <token>
```

---

### 🏥 Consultorios — `/api/consultorios`

| Método | URL | Rol requerido | Descripción |
|--------|-----|--------------|-------------|
| GET | `/api/consultorios` | ADMIN, MEDICO, AUXILIAR | Listar todos |
| GET | `/api/consultorios/{id}` | ADMIN, MEDICO, AUXILIAR | Buscar por ID |
| POST | `/api/consultorios` | ADMIN, AUXILIAR | Crear consultorio |
| PUT | `/api/consultorios/{id}` | ADMIN, AUXILIAR | Actualizar |
| DELETE | `/api/consultorios/{id}` | ADMIN | Eliminar |

**Body para POST/PUT:**
```json
{
  "codigoConsultorio": "C-401",
  "nombreConsultorio": "Consultorio de Neurología",
  "numeroPiso": 4,
  "capacidad": 1,
  "idSede": 1
}
```

---

### 🩺 Especialidades — `/api/especialidades`

| Método | URL | Rol requerido | Descripción |
|--------|-----|--------------|-------------|
| GET | `/api/especialidades` | ADMIN, MEDICO, AUXILIAR | Listar todas |
| GET | `/api/especialidades/{id}` | ADMIN, MEDICO, AUXILIAR | Buscar por ID |
| POST | `/api/especialidades` | ADMIN | Crear especialidad |
| PUT | `/api/especialidades/{id}` | ADMIN | Actualizar |
| DELETE | `/api/especialidades/{id}` | ADMIN | Eliminar |

**Body para POST/PUT:**
```json
{
  "codigoEspecialidad": "REUM",
  "nombreEspecialidad": "Reumatología"
}
```

---

### 👤 Usuarios — `/api/usuarios` (solo ADMIN)

| Método | URL | Descripción |
|--------|-----|-------------|
| GET | `/api/usuarios` | Listar todos |
| GET | `/api/usuarios/{id}` | Buscar por ID |
| POST | `/api/usuarios` | Crear usuario |
| PUT | `/api/usuarios/{id}` | Actualizar |
| DELETE | `/api/usuarios/{id}` | Eliminar |

**Body para POST:**
```json
{
  "username": "medico2",
  "email": "medico2@hospital.co",
  "nombreCompleto": "Dra. Ana Martínez",
  "password": "Medico2024#",
  "roles": ["ROLE_MEDICO"]
}
```

---

### 🔑 Roles — `/api/roles` (solo ADMIN)

| Método | URL | Descripción |
|--------|-----|-------------|
| GET | `/api/roles` | Listar todos los roles |
| GET | `/api/roles/{id}` | Buscar por ID |
| POST | `/api/roles` | Crear rol |
| PUT | `/api/roles/{id}` | Actualizar rol y permisos |
| DELETE | `/api/roles/{id}` | Eliminar rol |
| GET | `/api/roles/permisos-disponibles` | Ver permisos del sistema |

**Body para POST/PUT (gestión de permisos):**
```json
{
  "nombre": "ROLE_ENFERMERO",
  "descripcion": "Personal de enfermería",
  "permisos": [
    "CONSULTORIO:VER",
    "ESPECIALIDAD:VER",
    "USUARIO:VER"
  ]
}
```

---

### Permisos Disponibles en el Sistema

| Clave | Módulo | Acción |
|-------|--------|--------|
| `CONSULTORIO:VER` | Consultorio | Ver/listar |
| `CONSULTORIO:CREAR` | Consultorio | Crear |
| `CONSULTORIO:EDITAR` | Consultorio | Editar |
| `CONSULTORIO:ELIMINAR` | Consultorio | Eliminar |
| `ESPECIALIDAD:VER` | Especialidad | Ver/listar |
| `ESPECIALIDAD:CREAR` | Especialidad | Crear |
| `ESPECIALIDAD:EDITAR` | Especialidad | Editar |
| `ESPECIALIDAD:ELIMINAR` | Especialidad | Eliminar |
| `USUARIO:VER` | Usuario | Ver/listar |
| `USUARIO:CREAR` | Usuario | Crear |
| `USUARIO:EDITAR` | Usuario | Editar |
| `USUARIO:ELIMINAR` | Usuario | Eliminar |

---

## Configuración para Oracle XE 18c

En `application.properties`, reemplazar las líneas H2 por:

```properties
# Oracle XE 18c
spring.datasource.url=jdbc:oracle:thin:@localhost:1521/XEPDB1
spring.datasource.driver-class-name=oracle.jdbc.OracleDriver
spring.datasource.username=app_citas
spring.datasource.password=TU_PASSWORD_SEGURA

spring.jpa.database-platform=org.hibernate.dialect.OracleDialect
spring.jpa.hibernate.ddl-auto=update
spring.h2.console.enabled=false
```

Y agregar en `pom.xml`:
```xml
<dependency>
    <groupId>com.oracle.database.jdbc</groupId>
    <artifactId>ojdbc11</artifactId>
    <scope>runtime</scope>
</dependency>
```

---

## Flujo de Login con curl

```bash
# 1. Login
TOKEN=$(curl -s -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"Admin2024#"}' | \
  python3 -c "import sys,json; print(json.load(sys.stdin)['token'])")

# 2. Listar consultorios
curl -H "Authorization: Bearer $TOKEN" http://localhost:8080/api/consultorios

# 3. Crear consultorio
curl -X POST http://localhost:8080/api/consultorios \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{"codigoConsultorio":"C-501","nombreConsultorio":"Neurología","numeroPiso":5,"capacidad":1,"idSede":1}'

# 4. Actualizar
curl -X PUT http://localhost:8080/api/consultorios/1 \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{"codigoConsultorio":"C-101","nombreConsultorio":"Medicina General (actualizado)","numeroPiso":1,"capacidad":2,"idSede":1}'

# 5. Eliminar (solo admin)
curl -X DELETE http://localhost:8080/api/consultorios/6 \
  -H "Authorization: Bearer $TOKEN"

# 6. Gestionar rol: crear con permisos personalizados
curl -X POST http://localhost:8080/api/roles \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "nombre": "ROLE_ENFERMERO",
    "descripcion": "Personal de enfermería",
    "permisos": ["CONSULTORIO:VER", "ESPECIALIDAD:VER"]
  }'
```

---

## Matriz de Roles y Accesos

| Endpoint | ADMIN | MEDICO | AUXILIAR |
|----------|:-----:|:------:|:--------:|
| GET /consultorios | ✅ | ✅ | ✅ |
| POST /consultorios | ✅ | ❌ | ✅ |
| PUT /consultorios | ✅ | ❌ | ✅ |
| DELETE /consultorios | ✅ | ❌ | ❌ |
| GET /especialidades | ✅ | ✅ | ✅ |
| POST/PUT/DELETE /especialidades | ✅ | ❌ | ❌ |
| /usuarios (todo) | ✅ | ❌ | ❌ |
| /roles (todo) | ✅ | ❌ | ❌ |

---

*Proyecto educativo — Electivas I, II, III, IV*
*Spring Boot 3.2.5 · Java 21 · Arquitectura Hexagonal con Vertical Slicing*
