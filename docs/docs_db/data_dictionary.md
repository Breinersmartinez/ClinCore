# Diccionario de Datos
## Sistema de Gestión de Citas Médicas
**Base de Datos:** Oracle XE 18c | **Esquema:** APP_CITAS | **Versión:** 1.0

---

##  Índice de Contenidos
1. [Información General](#información-general)
2. [Tablas del Sistema](#tablas-del-sistema)
3. [Relaciones y Claves Foráneas](#relaciones-y-claves-foráneas)
4. [Índices](#índices)
5. [Matriz de Privilegios por Rol](#matriz-de-privilegios-por-rol)

---

## Información General

### Configuración de la Base de Datos
| Propiedad | Valor |
|-----------|-------|
| **SGBD** | Oracle XE 18c |
| **Esquema** | APP_CITAS |
| **Usuario Propietario** | app_citas |
| **Tablespace Principal** | tbs_citas_medicas |
| **Tamaño Inicial** | 100 MB (Autoextensión hasta 500 MB) |

### Usuarios y Roles del Sistema
| Usuario | Rol | Descripción |
|---------|-----|-------------|
| app_citas | DBA App | Propietario del esquema y tablas |
| usr_medico | rol_medico | Médicos del sistema |
| usr_paciente | rol_paciente | Pacientes registrados |
| usr_administrativo | rol_administrativo | Administradores del sistema |
| usr_auxiliar | rol_auxiliar_medico | Personal auxiliar médico |

---

## Tablas del Sistema

### 1. DEPARTAMENTOS
**Descripción:** Tabla maestra de departamentos del país (Colombia)

| Campo | Tipo | Restricciones | Descripción |
|-------|------|---------------|-------------|
| ID_DEPARTAMENTO | NUMBER | PK, Auto-Identity | Identificador único del departamento |
| CODIGO_DANE | VARCHAR2(2) | NOT NULL, UNIQUE | Código DANE del departamento (2 dígitos) |
| NOMBRE_DEPARTAMENTO | VARCHAR2(100) | NOT NULL, UNIQUE | Nombre oficial del departamento |
| ACTIVO | CHAR(1) | DEFAULT 'S', CHECK (S/N) | Estado: S=Activo, N=Inactivo |
| FECHA_CREACION | DATE | DEFAULT SYSDATE, NOT NULL | Fecha de creación del registro |
| FECHA_MODIFICACION | DATE | NULL | Fecha de última modificación |
| USUARIO_CREACION | VARCHAR2(50) | DEFAULT USER, NOT NULL | Usuario que creó el registro |

**Tablespace:** tbs_citas_medicas

---

### 2. MUNICIPIOS
**Descripción:** Tabla maestra de municipios asociados a cada departamento

| Campo | Tipo | Restricciones | Descripción |
|-------|------|---------------|-------------|
| ID_MUNICIPIO | NUMBER | PK, Auto-Identity | Identificador único del municipio |
| CODIGO_DANE | VARCHAR2(5) | NOT NULL, UNIQUE | Código DANE del municipio (5 dígitos) |
| NOMBRE_MUNICIPIO | VARCHAR2(150) | NOT NULL | Nombre oficial del municipio |
| ID_DEPARTAMENTO | NUMBER | NOT NULL, FK | Referencia al departamento |
| ACTIVO | CHAR(1) | DEFAULT 'S', CHECK (S/N) | Estado: S=Activo, N=Inactivo |
| FECHA_CREACION | DATE | DEFAULT SYSDATE, NOT NULL | Fecha de creación |
| FECHA_MODIFICACION | DATE | NULL | Fecha de última modificación |
| USUARIO_CREACION | VARCHAR2(50) | DEFAULT USER, NOT NULL | Usuario creador |

**Tablespace:** tbs_citas_medicas  
**Relación:** FK_MUN_DEPARTAMENTO → DEPARTAMENTOS(ID_DEPARTAMENTO)

---

### 3. SEDES
**Descripción:** Sedes o instalaciones de la institución médica

| Campo | Tipo | Restricciones | Descripción |
|-------|------|---------------|-------------|
| ID_SEDE | NUMBER | PK, Auto-Identity | Identificador único de la sede |
| CODIGO_SEDE | VARCHAR2(10) | NOT NULL, UNIQUE | Código identificador de la sede |
| NOMBRE_SEDE | VARCHAR2(200) | NOT NULL | Nombre de la sede médica |
| DIRECCION | VARCHAR2(300) | NOT NULL | Dirección completa de la sede |
| TELEFONO | VARCHAR2(20) | NULL | Teléfono de contacto |
| EMAIL | VARCHAR2(100) | NULL | Email de contacto |
| ID_MUNICIPIO | NUMBER | NOT NULL, FK | Referencia al municipio |
| ACTIVO | CHAR(1) | DEFAULT 'S', CHECK (S/N) | Estado: S=Activo, N=Inactivo |
| FECHA_CREACION | DATE | DEFAULT SYSDATE, NOT NULL | Fecha de creación |
| FECHA_MODIFICACION | DATE | NULL | Fecha de última modificación |
| USUARIO_CREACION | VARCHAR2(50) | DEFAULT USER, NOT NULL | Usuario creador |

**Tablespace:** tbs_citas_medicas  
**Relación:** FK_SED_MUNICIPIO → MUNICIPIOS(ID_MUNICIPIO)

---

### 4. CONSULTORIOS
**Descripción:** Consultorios disponibles en cada sede

| Campo | Tipo | Restricciones | Descripción |
|-------|------|---------------|-------------|
| ID_CONSULTORIO | NUMBER | PK, Auto-Identity | Identificador único del consultorio |
| CODIGO_CONSULTORIO | VARCHAR2(10) | NOT NULL | Código identificador |
| NOMBRE_CONSULTORIO | VARCHAR2(200) | NOT NULL | Nombre o descripción del consultorio |
| NUMERO_PISO | NUMBER(2) | NULL | Número de piso donde se ubica |
| CAPACIDAD | NUMBER(3) | DEFAULT 1 | Capacidad máxima de pacientes simultáneos |
| ID_SEDE | NUMBER | NOT NULL, FK | Referencia a la sede |
| ACTIVO | CHAR(1) | DEFAULT 'S', CHECK (S/N) | Estado: S=Activo, N=Inactivo |
| FECHA_CREACION | DATE | DEFAULT SYSDATE, NOT NULL | Fecha de creación |
| FECHA_MODIFICACION | DATE | NULL | Fecha de última modificación |
| USUARIO_CREACION | VARCHAR2(50) | DEFAULT USER, NOT NULL | Usuario creador |

**Tablespace:** tbs_citas_medicas  
**Relación:** FK_CON_SEDE → SEDES(ID_SEDE)  
**Constraint Único:** CODIGO_CONSULTORIO + ID_SEDE (consultorios únicos por sede)

---

### 5. ESPECIALIDADES
**Descripción:** Tabla auxiliar de especialidades médicas disponibles

| Campo | Tipo | Restricciones | Descripción |
|-------|------|---------------|-------------|
| ID_ESPECIALIDAD | NUMBER | PK, Auto-Identity | Identificador único de especialidad |
| CODIGO_ESPECIALIDAD | VARCHAR2(10) | NOT NULL, UNIQUE | Código identificador |
| NOMBRE_ESPECIALIDAD | VARCHAR2(150) | NOT NULL | Nombre de la especialidad médica |
| ACTIVO | CHAR(1) | DEFAULT 'S', CHECK (S/N) | Estado: S=Activo, N=Inactivo |
| FECHA_CREACION | DATE | DEFAULT SYSDATE, NOT NULL | Fecha de creación |

**Tablespace:** tbs_citas_medicas  
**Ejemplos:** Cardiología, Pediatría, Dermatología, Oftalmología, etc.

---

### 6. PERSONAS
**Descripción:** Tabla base para médicos y pacientes (Entidad centralizada)

| Campo | Tipo | Restricciones | Descripción |
|-------|------|---------------|-------------|
| ID_PERSONA | NUMBER | PK, Auto-Identity | Identificador único de persona |
| TIPO_DOCUMENTO | VARCHAR2(3) | NOT NULL, CHECK | Tipo: CC, TI, CE, PAS, RC |
| NUMERO_DOCUMENTO | VARCHAR2(20) | NOT NULL | Número de documento |
| PRIMER_NOMBRE | VARCHAR2(80) | NOT NULL | Primer nombre |
| SEGUNDO_NOMBRE | VARCHAR2(80) | NULL | Segundo nombre |
| PRIMER_APELLIDO | VARCHAR2(80) | NOT NULL | Primer apellido |
| SEGUNDO_APELLIDO | VARCHAR2(80) | NULL | Segundo apellido |
| FECHA_NACIMIENTO | DATE | NULL | Fecha de nacimiento |
| SEXO | CHAR(1) | CHECK (M/F/O) | Sexo: M=Masculino, F=Femenino, O=Otro |
| EMAIL | VARCHAR2(100) | NULL | Email de contacto |
| TELEFONO | VARCHAR2(20) | NULL | Teléfono de contacto |
| ID_MUNICIPIO | NUMBER | NULL, FK | Municipio de residencia |
| ACTIVO | CHAR(1) | DEFAULT 'S', CHECK (S/N) | Estado: S=Activo, N=Inactivo |
| FECHA_CREACION | DATE | DEFAULT SYSDATE, NOT NULL | Fecha de creación |
| FECHA_MODIFICACION | DATE | NULL | Fecha de última modificación |
| USUARIO_CREACION | VARCHAR2(50) | DEFAULT USER, NOT NULL | Usuario creador |

**Tablespace:** tbs_citas_medicas  
**Relación:** FK_PER_MUNICIPIO → MUNICIPIOS(ID_MUNICIPIO)  
**Constraint Único:** TIPO_DOCUMENTO + NUMERO_DOCUMENTO

**Valores Válidos:**
- TIPO_DOCUMENTO: CC (Cédula), TI (Tarjeta Identidad), CE (Cédula Extranjería), PAS (Pasaporte), RC (Registro Civil)
- SEXO: M, F, O

---

### 7. MEDICOS
**Descripción:** Registro de médicos del sistema

| Campo | Tipo | Restricciones | Descripción |
|-------|------|---------------|-------------|
| ID_MEDICO | NUMBER | PK, Auto-Identity | Identificador único del médico |
| ID_PERSONA | NUMBER | NOT NULL, UNIQUE, FK | Referencia a la persona |
| ID_ESPECIALIDAD | NUMBER | NOT NULL, FK | Especialidad médica |
| NUMERO_REGISTRO | VARCHAR2(20) | NOT NULL, UNIQUE | Número de registro profesional |
| TARIFA_CONSULTA | NUMBER(12,2) | NULL | Tarifa de consulta en COP |
| ACTIVO | CHAR(1) | DEFAULT 'S', CHECK (S/N) | Estado: S=Activo, N=Inactivo |
| FECHA_CREACION | DATE | DEFAULT SYSDATE, NOT NULL | Fecha de creación |

**Tablespace:** tbs_citas_medicas  
**Relaciones:**
- FK_MED_PERSONA → PERSONAS(ID_PERSONA)
- FK_MED_ESPECIALIDAD → ESPECIALIDADES(ID_ESPECIALIDAD)

**Notas:** Un médico solo puede estar asociado a una persona y una especialidad. El número de registro es único en el sistema.

---

### 8. PACIENTES
**Descripción:** Registro de pacientes del sistema

| Campo | Tipo | Restricciones | Descripción |
|-------|------|---------------|-------------|
| ID_PACIENTE | NUMBER | PK, Auto-Identity | Identificador único del paciente |
| ID_PERSONA | NUMBER | NOT NULL, UNIQUE, FK | Referencia a la persona |
| NUMERO_HISTORIA | VARCHAR2(20) | NOT NULL, UNIQUE | Número único de historia clínica |
| TIPO_AFILIACION | VARCHAR2(20) | DEFAULT 'CONTRIBUTIVO' | Tipo de afiliación sanitaria |
| EPS | VARCHAR2(100) | NULL | Entidad Promotora de Salud |
| ACTIVO | CHAR(1) | DEFAULT 'S', CHECK (S/N) | Estado: S=Activo, N=Inactivo |
| FECHA_CREACION | DATE | DEFAULT SYSDATE, NOT NULL | Fecha de creación |

**Tablespace:** tbs_citas_medicas  
**Relación:** FK_PAC_PERSONA → PERSONAS(ID_PERSONA)

**Valores Válidos - TIPO_AFILIACION:**
- CONTRIBUTIVO
- SUBSIDIADO
- VINCULADO
- PARTICULAR

**Ejemplos EPS:** Sanitas, Axa Colmedica, Coomeva, Famisanar, etc.

---

### 9. CITAS_MEDICAS
**Descripción:** Registro de citas médicas del sistema

| Campo | Tipo | Restricciones | Descripción |
|-------|------|---------------|-------------|
| ID_CITA | NUMBER | PK, Auto-Identity | Identificador único de la cita |
| NUMERO_CITA | VARCHAR2(20) | NOT NULL, UNIQUE | Número de cita para referencia |
| ID_PACIENTE | NUMBER | NOT NULL, FK | Referencia al paciente |
| ID_MEDICO | NUMBER | NOT NULL, FK | Referencia al médico |
| ID_CONSULTORIO | NUMBER | NOT NULL, FK | Referencia al consultorio |
| FECHA_CITA | DATE | NOT NULL | Fecha programada de la cita |
| HORA_INICIO | VARCHAR2(5) | NOT NULL | Hora de inicio (formato HH:MM) |
| HORA_FIN | VARCHAR2(5) | NOT NULL | Hora de finalización (formato HH:MM) |
| ESTADO | VARCHAR2(20) | DEFAULT 'PROGRAMADA', CHECK | Estado actual de la cita |
| MOTIVO_CONSULTA | VARCHAR2(500) | NULL | Motivo o síntomas reportados |
| OBSERVACIONES | VARCHAR2(1000) | NULL | Observaciones adicionales |
| ACTIVO | CHAR(1) | DEFAULT 'S', CHECK (S/N) | Estado: S=Activo, N=Inactivo |
| FECHA_CREACION | DATE | DEFAULT SYSDATE, NOT NULL | Fecha de creación |
| FECHA_MODIFICACION | DATE | NULL | Fecha de última modificación |
| USUARIO_CREACION | VARCHAR2(50) | DEFAULT USER, NOT NULL | Usuario creador |

**Tablespace:** tbs_citas_medicas

**Relaciones:**
- FK_CIT_PACIENTE → PACIENTES(ID_PACIENTE)
- FK_CIT_MEDICO → MEDICOS(ID_MEDICO)
- FK_CIT_CONSULTORIO → CONSULTORIOS(ID_CONSULTORIO)

**Valores Válidos - ESTADO:**
- PROGRAMADA (Cita creada pero no confirmada)
- CONFIRMADA (Cita confirmada por el paciente)
- ATENDIDA (Cita completada)
- CANCELADA (Cita cancelada por usuario)
- NO_ASISTIO (Paciente no asistió)

---

### 10. AUDITORIA_ACCIONES
**Descripción:** Registro de auditoría de acciones realizadas por rol de usuario

| Campo | Tipo | Restricciones | Descripción |
|-------|------|---------------|-------------|
| ID_AUDITORIA | NUMBER | PK, Auto-Identity | Identificador único del registro |
| TABLA_AFECTADA | VARCHAR2(50) | NOT NULL | Nombre de la tabla modificada |
| ACCION | VARCHAR2(10) | NOT NULL, CHECK | Tipo de acción: INSERT, UPDATE, DELETE, SELECT |
| ID_REGISTRO | NUMBER | NULL | ID del registro afectado |
| USUARIO_BD | VARCHAR2(50) | NOT NULL | Usuario de base de datos que ejecutó |
| ROL_ACTIVO | VARCHAR2(50) | NULL | Rol activo del usuario en ese momento |
| DATOS_ANTERIORES | VARCHAR2(4000) | NULL | Valores anteriores (en UPDATE/DELETE) |
| DATOS_NUEVOS | VARCHAR2(4000) | NULL | Valores nuevos (en INSERT/UPDATE) |
| FECHA_ACCION | DATE | DEFAULT SYSDATE, NOT NULL | Fecha y hora de la acción |
| IP_CLIENTE | VARCHAR2(50) | NULL | IP del cliente que ejecutó la acción |

**Tablespace:** tbs_citas_medicas

**Propósito:** Mantener un registro completo de todas las modificaciones realizadas en el sistema para fines de auditoría, compliance y trazabilidad de cambios.

---

## Relaciones y Claves Foráneas

### Diagrama de Relaciones
```
DEPARTAMENTOS
    ├── 1:N → MUNICIPIOS
    │         ├── 1:N → SEDES
    │         │         ├── 1:N → CONSULTORIOS
    │         │         └── ← CITAS_MEDICAS (consultorio)
    │         └── ← PERSONAS (municipio)
    │             ├── 1:1 → MEDICOS
    │             │         ├── 1:N → CITAS_MEDICAS (médico)
    │             │         └── → ESPECIALIDADES
    │             └── 1:1 → PACIENTES
    │                       └── 1:N → CITAS_MEDICAS (paciente)
```

### Tabla de Relaciones Detallada

| Relación | Tabla Padre | Tabla Hijo | Campos | Tipo | Acción |
|----------|-------------|-----------|--------|------|--------|
| FK_MUN_DEPARTAMENTO | DEPARTAMENTOS | MUNICIPIOS | ID_DEPARTAMENTO | 1:N | RESTRICT |
| FK_SED_MUNICIPIO | MUNICIPIOS | SEDES | ID_MUNICIPIO | 1:N | RESTRICT |
| FK_CON_SEDE | SEDES | CONSULTORIOS | ID_SEDE | 1:N | RESTRICT |
| FK_PER_MUNICIPIO | MUNICIPIOS | PERSONAS | ID_MUNICIPIO | N:1 | RESTRICT |
| FK_MED_PERSONA | PERSONAS | MEDICOS | ID_PERSONA | 1:1 | RESTRICT |
| FK_MED_ESPECIALIDAD | ESPECIALIDADES | MEDICOS | ID_ESPECIALIDAD | 1:N | RESTRICT |
| FK_PAC_PERSONA | PERSONAS | PACIENTES | ID_PERSONA | 1:1 | RESTRICT |
| FK_CIT_PACIENTE | PACIENTES | CITAS_MEDICAS | ID_PACIENTE | 1:N | RESTRICT |
| FK_CIT_MEDICO | MEDICOS | CITAS_MEDICAS | ID_MEDICO | 1:N | RESTRICT |
| FK_CIT_CONSULTORIO | CONSULTORIOS | CITAS_MEDICAS | ID_CONSULTORIO | 1:N | RESTRICT |

---

## Índices

### Índices Creados para Optimización

| Nombre Índice | Tabla | Columnas | Tipo | Propósito |
|---------------|-------|----------|------|-----------|
| idx_mun_depto | municipios | id_departamento | SIMPLE | Búsquedas por departamento |
| idx_sed_mun | sedes | id_municipio | SIMPLE | Búsquedas por municipio |
| idx_con_sede | consultorios | id_sede | SIMPLE | Búsquedas por sede |
| idx_cit_paciente | citas_medicas | id_paciente | SIMPLE | Citas de un paciente |
| idx_cit_medico | citas_medicas | id_medico | SIMPLE | Citas de un médico |
| idx_cit_fecha | citas_medicas | fecha_cita | SIMPLE | Búsquedas por fecha |
| idx_cit_estado | citas_medicas | estado | SIMPLE | Filtros por estado |
| idx_aud_tabla | auditoria_acciones | tabla_afectada, fecha_accion | COMPUESTO | Auditoría por tabla y fecha |

---

## Matriz de Privilegios por Rol

### Resumen de Permisos

| Tabla | rol_medico | rol_paciente | rol_administrativo | rol_auxiliar_medico |
|-------|:-:|:-:|:-:|:-:|
| DEPARTAMENTOS | SELECT | SELECT | ALL* | SELECT |
| MUNICIPIOS | SELECT | SELECT | ALL* | SELECT |
| SEDES | SELECT | SELECT | ALL* | SELECT |
| CONSULTORIOS | SELECT | SELECT | ALL* | SELECT |
| ESPECIALIDADES | SELECT | — | ALL* | SELECT |
| PERSONAS | SELECT | SELECT¹ | ALL* | SELECT, INSERT |
| MEDICOS | SELECT | SELECT | ALL* | SELECT |
| PACIENTES | SELECT | SELECT¹ | ALL* | SELECT, INSERT |
| CITAS_MEDICAS | SELECT, UPDATE | SELECT¹ | ALL* | SELECT, INSERT, UPDATE |
| AUDITORIA_ACCIONES | — | — | SELECT | — |

**Leyenda:**
- ALL* = SELECT, INSERT, UPDATE, DELETE
- SELECT¹ = Solo puede ver su propia información (controlado a nivel aplicación)
- — = Sin acceso

### Detalle de Permisos por Rol

#### ROL_MEDICO
```
✓ Consultar catálogos maestros (lectura)
✓ Consultar información de personas, médicos y pacientes
✓ Consultar citas médicas
✓ Actualizar estado de citas (completadas, observaciones)
✗ No puede crear ni eliminar registros
✗ Sin acceso a auditoría
```

#### ROL_PACIENTE
```
✓ Consultar catálogos maestros básicos
✓ Consultar sus propios datos (filtrado en aplicación)
✓ Consultar sus propias citas
✗ No puede modificar datos
✗ Sin acceso a auditoría
```

#### ROL_ADMINISTRATIVO
```
✓ Acceso completo a catálogos (CRUD)
✓ Acceso completo a personas, médicos y pacientes
✓ Acceso completo a citas médicas
✓ Lectura de auditoría
✓ Mayor nivel de privelegio del sistema
```

#### ROL_AUXILIAR_MEDICO
```
✓ Consultar catálogos maestros
✓ Registrar nuevas personas y pacientes
✓ Crear nuevas citas
✓ Actualizar información de citas
✓ Consultar medicos y especialidades
✗ No puede eliminar registros
```

---

## Especificaciones Técnicas

### Convenciones de Nombres

| Elemento | Convención | Ejemplo |
|----------|-----------|---------|
| Tablas | Minúsculas con guiones bajos | citas_medicas, consultorios |
| Columnas | Minúsculas con guiones bajos | id_paciente, numero_documento |
| Claves Primarias | PK_nombre_tabla | pk_cita |
| Claves Foráneas | FK_tabla_referencia | fk_cit_paciente |
| Restricciones UNIQUE | UQ_descripción | uq_per_documento |
| Restricciones CHECK | CK_descripción | ck_per_sexo |
| Índices | idx_tabla_columna | idx_cit_fecha |

### Tipos de Datos Estándar

| Tipo | Uso | Ejemplos |
|------|-----|----------|
| NUMBER | Identificadores, cantidades, tarifas | id_*, capacidad, tarifa_consulta |
| NUMBER(12,2) | Moneda | tarifa_consulta |
| VARCHAR2(n) | Textos variables | nombres, direcciones, emails |
| CHAR(1) | Flags booleanos | activo (S/N), sexo (M/F/O) |
| DATE | Fechas y marcas de tiempo | fecha_cita, fecha_creacion |

### Valores por Defecto

- **SYSDATE:** Fecha y hora actual del sistema
- **USER:** Usuario conectado a la base de datos
- **'S':** Estado activo predeterminado
- **'PROGRAMADA':** Estado inicial de citas

---

## Consideraciones de Integridad

### Reglas de Negocio Implementadas

1. **Identidad Única de Personas:** No pueden existir dos registros con el mismo tipo y número de documento
2. **Especialidad Única:** Cada médico tiene una única especialidad
3. **Código Único por Sede:** Los consultorios tienen códigos únicos dentro de cada sede
4. **Historia Clínica Única:** Cada paciente tiene una única historia clínica
5. **Número de Cita Único:** Cada cita tiene un número de referencia único
6. **Estados Válidos:** Las citas solo pueden estar en estados predefinidos

### Consideraciones de Seguridad

- Las columnas de auditoría registran usuario y fecha de todas las modificaciones
- La tabla AUDITORIA_ACCIONES permite trazabilidad completa de cambios
- Los roles limitan el acceso según el perfil de usuario
- Los usuarios finales se conectan con privilegios específicos según su rol
- Los sinónimos públicos evitan que los usuarios necesiten usar el prefijo del esquema

---

## Notas Importantes

1. **Encriptación de Contraseñas:** Las contraseñas mostradas (Citas2024#, etc.) son solo para propósitos educativos. En producción, usar contraseñas robustas y gestión segura de credenciales.

2. **Autoextensión:** El tablespace está configurado para crecer automáticamente de 10M en 10M hasta 500M máximo.

3. **Auditoría:** Se recomienda crear triggers en las tablas principales para registrar automáticamente cambios en AUDITORIA_ACCIONES.

4. **Backup:** Se debe realizar backup regular del tablespace tbs_citas_medicas.

5. **Monitoreo:** Monitorear el crecimiento del tablespace y el espacio disponible.

---

**Documento generado:** Sistema de Gestión de Citas Médicas - Oracle XE 18c  
**Propósito:** Material educativo para Electivas I, II, III, IV  
**Última actualización:** 2024