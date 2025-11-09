# 🏥 Sistema Veterinario - Microservicio Clínico

Microservicio para la gestión clínica veterinaria desarrollado con **Spring Boot 3.5.7** y **GraphQL Federation v2**. Este sistema maneja especies, clientes, mascotas, citas, diagnósticos, tratamientos y vacunación.

## 🚀 Características

- **GraphQL Federation 2**: Subgraph listo para integración con gateway
- **Netflix DGS Framework**: Framework GraphQL empresarial
- **Spring Boot 3.5.7**: Framework moderno con Java 17
- **PostgreSQL 15**: Base de datos relacional optimizada
- **Docker**: Containerización completa con docker-compose
- **Spring Data JPA**: ORM con relaciones optimizadas para GraphQL
- **Lombok**: Reducción de código boilerplate
- **Health Checks**: Monitoreo de salud con Spring Boot Actuator

## 📋 Entidades del Sistema

### Módulo Básico
- **🐾 Especie**: Clasificación de animales (Perro, Gato, etc.)
- **�‍⚕️ Doctor**: Profesionales veterinarios
- **�👤 Cliente**: Propietarios de las mascotas
- **🐕 Mascota**: Registro de mascotas con datos médicos

### Módulo de Citas
- **⏰ BloqueHorario**: Disponibilidad de horarios veterinarios
- **📅 Cita**: Agendamiento de consultas

### Módulo Clínico
- **🩺 Diagnóstico**: Resultados de consultas médicas
- **💊 Tratamiento**: Planes de tratamiento médico

### Módulo de Vacunación
- **💉 Vacuna**: Catálogo de vacunas disponibles
- **📋 CarnetVacunacion**: Carnet de vacunación de mascotas
- **📝 DetalleVacunacion**: Historial detallado de vacunación con fechas y recordatorios

## 🛠️ Requisitos Previos

- **Docker** >= 20.10
- **Docker Compose** >= 2.0
- **Java 17** (solo para desarrollo local)
- **Maven 3.8+** (solo para desarrollo local)

## 🚀 Instalación y Ejecución

### Con Docker (Recomendado)

1. **Clonar el repositorio**:
```bash
git clone <repository-url>
cd microservicio_clinico
```

2. **Ejecutar con Docker Compose**:
```bash
docker compose up -d
```

3. **Verificar servicios**:
```bash
docker compose ps
```

4. **Acceder a las interfaces**:
- **GraphQL Playground**: http://localhost:8081/graphiql
- **API Health Check**: http://localhost:8081/actuator/health
- **Base de datos**: `localhost:5433`

### Desarrollo Local

1. **Iniciar PostgreSQL**:
```bash
docker compose up -d postgres
```

2. **Ejecutar la aplicación**:
```bash
mvn spring-boot:run
```

## � Ejemplos de Uso - Datos Completos de Prueba

### 🎯 Orden de Ejecución

Ejecuta estas mutations **en orden** para crear un sistema completo:

### **1. Especies (Crear primero)**
```graphql
mutation { crearEspecie(input: { descripcion: "Perro" }) { id descripcion } }
```
```graphql
mutation { crearEspecie(input: { descripcion: "Gato" }) { id descripcion } }
```
```graphql
mutation { crearEspecie(input: { descripcion: "Conejo" }) { id descripcion } }
```

### **2. Doctores**
```graphql
mutation {
  crearDoctor(input: {
    nombre: "Dr. María"
    apellido: "González"
    ci: "12345678"
    telefono: "555-0101"
    email: "maria.gonzalez@veterinaria.com"
    fotourl: "https://example.com/doctors/maria.jpg"
  }) {
    id nombre apellido email
  }
}
```

```graphql
mutation {
  crearDoctor(input: {
    nombre: "Dr. Carlos"
    apellido: "Rodríguez"
    ci: "87654321"
    telefono: "555-0102"
    email: "carlos.rodriguez@veterinaria.com"
    fotourl: "https://example.com/doctors/carlos.jpg"
  }) {
    id nombre apellido email
  }
}
```

### **3. Clientes**
```graphql
mutation {
  crearCliente(input: {
    nombre: "Ana"
    apellido: "López"
    ci: "11111111"
    telefono: "555-1001"
    fotourl: "https://example.com/clients/ana.jpg"
  }) {
    id nombre apellido telefono
  }
}
```

```graphql
mutation {
  crearCliente(input: {
    nombre: "Pedro"
    apellido: "Martínez"
    ci: "22222222"
    telefono: "555-1002"
    fotourl: "https://example.com/clients/pedro.jpg"
  }) {
    id nombre apellido telefono
  }
}
```

### **4. Mascotas**
```graphql
mutation {
  crearMascota(input: {
    nombre: "Buddy"
    sexo: "M"
    raza: "Golden Retriever"
    fotourl: "https://example.com/pets/buddy.jpg"
    fechanacimiento: "2022-03-15"
    clienteId: 1
    especieId: 1
  }) {
    id nombre raza fechanacimiento
    cliente { nombre }
    especie { descripcion }
  }
}
```

```graphql
mutation {
  crearMascota(input: {
    nombre: "Luna"
    sexo: "F"
    raza: "Persa"
    fotourl: "https://example.com/pets/luna.jpg"
    fechanacimiento: "2021-07-20"
    clienteId: 2
    especieId: 2
  }) {
    id nombre raza fechanacimiento
    cliente { nombre }
    especie { descripcion }
  }
}
```

### **5. BloqueHorarios**
```graphql
mutation {
  crearBloqueHorario(input: {
    diasemana: 1
    horainicio: "08:00"
    horafinal: "12:00"
    activo: 1
  }) {
    id diasemana horainicio horafinal
  }
}
```

```graphql
mutation {
  crearBloqueHorario(input: {
    diasemana: 1
    horainicio: "14:00"
    horafinal: "18:00"
    activo: 1
  }) {
    id diasemana horainicio horafinal
  }
}
```

### **6. Vacunas**
```graphql
mutation { crearVacuna(input: { descripcion: "Antirrábica" }) { id descripcion } }
```
```graphql
mutation { crearVacuna(input: { descripcion: "Parvovirus" }) { id descripcion } }
```
```graphql
mutation { crearVacuna(input: { descripcion: "Triple Felina" }) { id descripcion } }
```

### **7. CarnetVacunacion**
```graphql
mutation {
  crearCarnetVacunacion(input: {
    fechaemision: "2024-01-15"
    mascotaId: 1
  }) {
    id fechaemision
    mascota { nombre }
  }
}
```

```graphql
mutation {
  crearCarnetVacunacion(input: {
    fechaemision: "2024-02-10"
    mascotaId: 2
  }) {
    id fechaemision
    mascota { nombre }
  }
}
```

### **8. DetalleVacunacion**
```graphql
mutation {
  crearDetalleVacunacion(input: {
    fechavacunacion: "2024-01-15"
    proximavacunacion: "2025-01-15"
    carnetVacunacionId: 1
    vacunaId: 1
  }) {
    id fechavacunacion proximavacunacion
    carnetVacunacion { fechaemision }
    vacuna { descripcion }
  }
}
```

```graphql
mutation {
  crearDetalleVacunacion(input: {
    fechavacunacion: "2024-02-10"
    proximavacunacion: "2025-02-10"
    carnetVacunacionId: 2
    vacunaId: 3
  }) {
    id fechavacunacion proximavacunacion
    carnetVacunacion { fechaemision }
    vacuna { descripcion }
  }
}
```

### **9. Citas**
```graphql
mutation {
  crearCita(input: {
    mascotaId: 1
    doctorId: 1
    bloqueHorarioId: 1
    fechacreacion: "2024-11-08"
    fechareserva: "2024-11-15"
    motivo: "Consulta general y chequeo"
    estado: 1
  }) {
    id fechacreacion fechareserva motivo estado
    mascota { nombre }
    doctor { nombre }
  }
}
```

```graphql
mutation {
  crearCita(input: {
    mascotaId: 2
    doctorId: 2
    bloqueHorarioId: 2
    fechacreacion: "2024-11-09"
    fechareserva: "2024-11-16"
    motivo: "Control post-vacunación"
    estado: 2
  }) {
    id fechacreacion fechareserva motivo estado
    mascota { nombre }
    doctor { nombre }
  }
}
```

### **10. Diagnósticos**
```graphql
mutation {
  crearDiagnostico(input: {
    descripcion: "Infección leve en el oído"
    observaciones: "El paciente presenta síntomas de otitis externa. Se recomienda tratamiento con antibióticos tópicos."
    fecharegistro: "2024-11-15"
    citaId: 1
  }) {
    id descripcion observaciones fecharegistro
    cita {
      motivo
      mascota { nombre }
    }
  }
}
```

```graphql
mutation {
  crearDiagnostico(input: {
    descripcion: "Control post-vacunación normal"
    observaciones: "La mascota se encuentra en buen estado después de la vacunación. No se observan reacciones adversas."
    fecharegistro: "2024-11-16"
    citaId: 2
  }) {
    id descripcion observaciones fecharegistro
    cita {
      motivo
      mascota { nombre }
    }
  }
}
```

### **11. Tratamientos**
```graphql
mutation {
  crearTratamiento(input: {
    nombre: "Tratamiento antibiótico tópico"
    descripcion: "Aplicación de gotas antibióticas en el oído afectado"
    observaciones: "Aplicar 3 gotas cada 8 horas durante 7 días. Evitar que el agua entre en el oído durante el tratamiento."
    diagnosticoId: 1
  }) {
    id nombre descripcion observaciones
    diagnostico {
      descripcion
      cita {
        mascota { nombre }
      }
    }
  }
}
```

```graphql
mutation {
  crearTratamiento(input: {
    nombre: "Seguimiento post-vacunación"
    descripcion: "Observación y monitoreo después de la vacunación"
    observaciones: "Revisar al paciente en 2 semanas para confirmar que no hay reacciones tardías a la vacuna."
    diagnosticoId: 2
  }) {
    id nombre descripcion observaciones
    diagnostico {
      descripcion
      cita {
        mascota { nombre }
      }
    }
  }
}
```

## 📊 Arquitectura del Sistema

```
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│   Gateway       │    │  Microservicio  │    │   PostgreSQL    │
│  (Federation)   │◄──►│    Clínico      │◄──►│   Database      │
└─────────────────┘    └─────────────────┘    └─────────────────┘
                              │
                              ▼
                       ┌─────────────────┐
                       │   GraphQL       │
                       │   Playground    │
                       └─────────────────┘
```

## 🔍 API GraphQL

### 🎯 Endpoints Principales

- **GraphQL Endpoint**: `http://localhost:8081/graphql`
- **GraphQL Playground**: `http://localhost:8081/graphiql`
- **Health Check**: `http://localhost:8081/actuator/health`

## 📋 Notas Importantes

### 🗓️ Formato de Fechas
- **Fechas simples**: `"2024-11-08"` (se convierte automáticamente a `YYYY-MM-DDTHH:mm:ss`)
- **Fechas con hora**: `"2024-11-08T14:30:00"`
- **Compatibilidad**: Frontend puede enviar fechas simples, backend las procesa correctamente

### 🔢 Estados de Cita
- `1`: Programada
- `2`: Confirmada
- `3`: En proceso
- `4`: Completada
- `5`: Cancelada

### � Estructura de Base de Datos
- **Autogeneración**: Las tablas se crean automáticamente con JPA
- **Relaciones**: Configuradas con claves foráneas optimizadas
- **Índices**: Optimizados para consultas GraphQL

## 🔧 Configuración

### Variables de Entorno

| Variable | Descripción | Valor por Defecto |
|----------|-------------|-------------------|
| `SPRING_DATASOURCE_URL` | URL de conexión a PostgreSQL | `jdbc:postgresql://localhost:5433/veterinaria_db` |
| `SPRING_DATASOURCE_USERNAME` | Usuario de base de datos | `vet_admin` |
| `SPRING_DATASOURCE_PASSWORD` | Contraseña de base de datos | `VetClinic2025!SecurePass` |
| `SERVER_PORT` | Puerto del servidor | `8081` |

## 📊 Monitoreo y Logs

### Health Checks
```bash
# Verificar salud de la aplicación
curl http://localhost:8081/actuator/health
```

### Ver logs en tiempo real
```bash
# Logs de la aplicación
docker compose logs -f microservicio-clinico

# Logs de PostgreSQL
docker compose logs -f postgres
```

## 🐳 Comandos Docker Útiles

```bash
# Construir y ejecutar
docker compose up --build -d

# Detener servicios
docker compose down

# Reiniciar solo la aplicación
docker compose restart microservicio-clinico

# Ver estado de servicios
docker compose ps
```

## 🚀 ¡Listo para Producción!

Este microservicio está preparado para:
- ✅ Integración con GraphQL Federation Gateway
- ✅ Despliegue en contenedores Docker
- ✅ Escalabilidad horizontal
- ✅ Monitoreo y health checks
- ✅ Persistencia de datos
- ✅ **Datos de prueba completos**

---

## 🔍 Ejemplos de Queries GraphQL

### 1. Query de Mascotas con Relaciones
```graphql
query {
  mascotas {
    id
    nombre
    raza
    sexo
    fechanacimiento
    fotourl
    cliente {
      id
      nombre
      apellido
      ci
      telefono
      fotourl
    }
    especie {
      id
      descripcion
    }
  }
}
```

### 2. Query de Citas con Relaciones
```graphql
query {
  citas {
    id
    fechacreacion
    motivo
    fechareserva
    estado
    doctor {
      id
      nombre
      apellido
      ci
      telefono
      email
      fotourl
    }
    mascota {
      id
      nombre
      raza
      fechanacimiento
      cliente {
        nombre
        apellido
        ci
      }
      especie {
        descripcion
      }
    }
    bloqueHorario {
      id
      diasemana
      horainicio
      horafinal
      activo
    }
  }
}
```

### 3. Query de Diagnósticos con Relaciones
```graphql
query {
  diagnosticos {
    id
    descripcion
    fecharegistro
    observaciones
    cita {
      id
      fechacreacion
      motivo
      fechareserva
      doctor {
        nombre
        apellido
        ci
      }
      mascota {
        nombre
        cliente {
          nombre
          apellido
        }
      }
    }
    tratamientos {
      id
      nombre
      descripcion
      observaciones
    }
  }
}
```

### 4. Query de Tratamientos con Relaciones
```graphql
query {
  tratamientos {
    id
    nombre
    descripcion
    observaciones
    diagnostico {
      id
      descripcion
      fecharegistro
      cita {
        id
        fechareserva
        mascota {
          nombre
          cliente {
            nombre
            apellido
          }
        }
        doctor {
          nombre
          apellido
        }
      }
    }
  }
}
```

### 5. Query de Carnets de Vacunación con Relaciones
```graphql
query {
  carnetsVacunacion {
    id
    fechaemision
    mascota {
      id
      nombre
      raza
      cliente {
        nombre
        apellido
        ci
      }
      especie {
        descripcion
      }
    }
    detallesVacunacion {
      id
      fechavacunacion
      proximavacunacion
      vacuna {
        id
        descripcion
      }
    }
  }
}
```

### 6. Query de Detalles de Vacunación con Relaciones
```graphql
query {
  detallesVacunacion {
    id
    fechavacunacion
    proximavacunacion
    vacuna {
      id
      descripcion
    }
    carnetVacunacion {
      id
      fechaemision
      mascota {
        nombre
        cliente {
          nombre
          apellido
        }
      }
    }
  }
}
```

### 7. Query de Doctores
```graphql
query {
  doctores {
    id
    nombre
    apellido
    ci
    telefono
    email
    fotourl
  }
}
```

### 8. Query de Clientes
```graphql
query {
  clientes {
    id
    nombre
    apellido
    ci
    telefono
    fotourl
  }
}
```

### 9. Query de Especies
```graphql
query {
  especies {
    id
    descripcion
  }
}
```

### 10. Query de Vacunas
```graphql
query {
  vacunas {
    id
    descripcion
  }
}
```

### 11. Query de Bloques Horarios
```graphql
query {
  bloquesHorarios {
    id
    diasemana
    horainicio
    horafinal
    activo
  }
}
```

### 12. Queries Específicas por ID
```graphql
# Mascota específica
query {
  mascota(id: 1) {
    id
    nombre
    fechanacimiento
    raza
    cliente {
      nombre
      apellido
    }
    especie {
      descripcion
    }
  }
}

# Cita específica
query {
  cita(id: 1) {
    id
    fechareserva
    motivo
    estado
    doctor {
      nombre
      apellido
    }
    mascota {
      nombre
      cliente {
        nombre
      }
    }
  }
}

# Cita por fecha

query {
  citasPorFecha(fecha: "2024-11-15") {
    id
    fechacreacion
    motivo
    fechareserva
    estado
    doctor {
      id
      nombre
      apellido
      ci
      telefono
      email
    }
    mascota {
      id
      nombre
      raza
      fechanacimiento
      cliente {
        nombre
        apellido
        ci
      }
      especie {
        descripcion
      }
    }
    bloqueHorario {
      id
      diasemana
      horainicio
      horafinal
      activo
    }
  }
}
```

**🎯 Orden recomendado para pruebas:**
1. Especies → 2. Doctores → 3. Clientes → 4. Mascotas → 5. BloqueHorarios → 6. Vacunas → 7. CarnetVacunacion → 8. DetalleVacunacion → 9. Citas → 10. Diagnósticos → 11. Tratamientos

**¿Necesitas ayuda?** Consulta los logs, revisa la documentación de GraphQL en `/graphiql`, o verifica los health checks en `/actuator/health`.