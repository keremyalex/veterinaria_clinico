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
- **👤 Cliente**: Propietarios de las mascotas
- **🐕 Mascota**: Registro de mascotas con datos médicos

### Módulo de Citas
- **⏰ Horario**: Disponibilidad de horarios veterinarios
- **📅 Cita**: Agendamiento de consultas

### Módulo Clínico
- **🩺 Diagnóstico**: Resultados de consultas médicas
- **💊 Tratamiento**: Planes de tratamiento médico

### Módulo de Vacunación
- **💉 Vacuna**: Catálogo de vacunas disponibles
- **📋 MascotaVacuna**: Historial de vacunación con fechas y recordatorios

## 🛠️ Requisitos Previos

- **Docker** >= 20.10
- **Docker Compose** >= 2.0 (comando `docker compose` sin guión)
- **Java 17** (solo para desarrollo local)
- **Maven 3.8+** (solo para desarrollo local)

## 🚀 Instalación y Ejecución

### Opción 1: Con Docker (Recomendado)

1. **Clonar el repositorio**:
```bash
git clone <repository-url>
cd microservicio_clinico
```

2. **Configurar variables de entorno (Opcional)**:
```bash
# Copiar archivo de ejemplo
cp .env.example .env

# Editar .env con tus credenciales personalizadas
# NUNCA commits el archivo .env a git
```

3. **Ejecutar con Docker Compose**:
```bash
docker compose up -d
```

3. **Verificar que los servicios estén corriendo**:
```bash
docker compose ps
```

4. **Acceder a las interfaces**:
- **GraphQL Playground**: http://localhost:8081/graphiql
- **API Health Check**: http://localhost:8081/actuator/health
- **Base de datos**: Conectar con tu gestor preferido a `localhost:5433`
  - Usuario: `vet_admin`
  - Contraseña: `VetClinic2025!SecurePass`
  - Base de datos: `veterinaria_db`

### Opción 2: Desarrollo Local

1. **Iniciar PostgreSQL**:
```bash
docker compose up -d postgres
```

2. **Ejecutar la aplicación**:
```bash
mvn spring-boot:run
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

## 📖 Documentación de Queries y Mutations

### 🐾 Especies

#### Queries
```graphql
# Obtener todas las especies
query {
  especies {
    id
    descripcion
  }
}

# Obtener especie por ID
query {
  especie(id: "1") {
    id
    descripcion
  }
}

# Buscar especie por descripción
query {
  especieByDescripcion(descripcion: "Perro") {
    id
    descripcion
  }
}
```

#### Mutations
```graphql
# Crear nueva especie
mutation {
  createEspecie(input: {
    descripcion: "Perro"
  }) {
    id
    descripcion
  }
}

# Actualizar especie
mutation {
  updateEspecie(input: {
    id: "1"
    descripcion: "Canino"
  }) {
    id
    descripcion
  }
}

# Eliminar especie
mutation {
  deleteEspecie(id: "1")
}
```

### 👤 Clientes

#### Queries
```graphql
# Obtener todos los clientes
query {
  clientes {
    id
    nombre
    apellidos
    email
    telefono
    fechaNacimiento
  }
}

# Buscar cliente por email
query {
  clienteByEmail(email: "juan@example.com") {
    id
    nombre
    apellidos
    telefono
  }
}

# Buscar clientes por término
query {
  searchClientes(searchTerm: "Juan") {
    id
    nombre
    apellidos
    email
  }
}
```

#### Mutations
```graphql
# Crear nuevo cliente
mutation {
  createCliente(input: {
    nombre: "Juan"
    apellidos: "Pérez García"
    email: "juan.perez@example.com"
    telefono: "+57 300 123 4567"
    fechaNacimiento: "1985-03-15"
  }) {
    id
    nombre
    apellidos
    email
  }
}
```

### 🐕 Mascotas

#### Queries
```graphql
# Obtener todas las mascotas con relaciones
query {
  mascotas {
    id
    nombre
    sexo
    raza
    fechaNacimiento
    peso
    cliente {
      nombre
      apellidos
    }
    especie {
      descripcion
    }
  }
}

# Obtener mascotas por cliente
query {
  mascotasByCliente(clienteId: "1") {
    id
    nombre
    raza
    especie {
      descripcion
    }
  }
}

# Buscar mascotas por sexo
query {
  mascotasBySexo(sexo: "Macho") {
    id
    nombre
    raza
    cliente {
      nombre
      telefono
    }
  }
}
```

#### Mutations
```graphql
# Crear nueva mascota
mutation {
  createMascota(input: {
    nombre: "Max"
    sexo: "Macho"
    raza: "Golden Retriever"
    fechaNacimiento: "2020-05-10"
    peso: 25.5
    clienteId: "1"
    especieId: "1"
  }) {
    id
    nombre
    raza
    cliente {
      nombre
    }
    especie {
      descripcion
    }
  }
}
```

### ⏰ Horarios

#### Queries
```graphql
# Obtener horarios por día
query {
  horariosByDia(dia: "Lunes") {
    id
    dia
    horaInicio
    horaFin
  }
}
```

#### Mutations
```graphql
# Crear nuevo horario
mutation {
  createHorario(input: {
    dia: "Lunes"
    horaInicio: "08:00"
    horaFin: "12:00"
  }) {
    id
    dia
    horaInicio
    horaFin
  }
}
```

### 📅 Citas

#### Queries
```graphql
# Obtener citas con todas las relaciones
query {
  citas {
    id
    fechaReservacion
    motivo
    fechaProgramada
    cliente {
      nombre
      apellidos
      telefono
    }
    mascota {
      nombre
      raza
    }
    horario {
      dia
      horaInicio
    }
  }
}

# Obtener citas por mascota
query {
  citasByMascota(mascotaId: "1") {
    id
    motivo
    fechaProgramada
    cliente {
      nombre
      telefono
    }
  }
}
```

#### Mutations
```graphql
# Crear nueva cita
mutation {
  createCita(input: {
    motivo: "Consulta de rutina"
    fechaProgramada: "2025-11-05 10:00"
    clienteId: "1"
    horarioId: "1"
    mascotaId: "1"
  }) {
    id
    motivo
    fechaProgramada
    cliente {
      nombre
    }
    mascota {
      nombre
    }
  }
}
```

### 🩺 Diagnósticos

#### Queries
```graphql
# Obtener diagnósticos por mascota
query {
  diagnosticosByMascota(mascotaId: "1") {
    id
    descripcion
    fechaDiagnostico
    observaciones
    mascota {
      nombre
      cliente {
        nombre
        telefono
      }
    }
  }
}
```

#### Mutations
```graphql
# Crear nuevo diagnóstico
mutation {
  createDiagnostico(input: {
    descripcion: "Infección respiratoria leve"
    observaciones: "Tratamiento con antibióticos recomendado"
    mascotaId: "1"
  }) {
    id
    descripcion
    fechaDiagnostico
    observaciones
  }
}
```

### 💊 Tratamientos

#### Queries
```graphql
# Obtener tratamientos por diagnóstico
query {
  tratamientosByDiagnostico(diagnosticoId: "1") {
    id
    descripcion
    fechaInicio
    fechaFin
    instrucciones
    estado
    diagnostico {
      descripcion
      mascota {
        nombre
      }
    }
  }
}

# Obtener tratamientos por estado
query {
  tratamientosByEstado(estado: "ACTIVO") {
    id
    descripcion
    fechaInicio
    estado
  }
}
```

#### Mutations
```graphql
# Crear nuevo tratamiento
mutation {
  createTratamiento(input: {
    descripcion: "Antibiótico oral"
    fechaInicio: "2025-11-01"
    fechaFin: "2025-11-08"
    instrucciones: "Administrar 1 tableta cada 12 horas con comida"
    estado: "ACTIVO"
    diagnosticoId: "1"
  }) {
    id
    descripcion
    instrucciones
    estado
  }
}
```

### 💉 Vacunas

#### Queries
```graphql
# Obtener todas las vacunas
query {
  vacunas {
    id
    nombre
    descripcion
    duracionMeses
    laboratorio
    edadMinimaDias
  }
}

# Buscar vacunas por laboratorio
query {
  vacunasByLaboratorio(laboratorio: "Pfizer Animal Health") {
    id
    nombre
    descripcion
    duracionMeses
  }
}

# Buscar vacunas por nombre
query {
  vacunasByNombre(nombre: "Rabia") {
    id
    nombre
    laboratorio
    edadMinimaDias
  }
}
```

#### Mutations
```graphql
# Crear nueva vacuna
mutation {
  createVacuna(input: {
    nombre: "Rabia"
    descripcion: "Vacuna contra la rabia para perros y gatos"
    duracionMeses: 12
    laboratorio: "Pfizer Animal Health"
    edadMinimaDias: 90
  }) {
    id
    nombre
    descripcion
    duracionMeses
    laboratorio
    edadMinimaDias
  }
}
```

### 📋 Historial de Vacunación

#### Queries
```graphql
# Obtener historial de vacunación por mascota
query {
  vacunasByMascota(mascotaId: "1") {
    id
    fechaAplicacion
    fechaProximaDosis
    veterinario
    observaciones
    lote
    mascota {
      nombre
      cliente {
        nombre
        telefono
      }
    }
    vacuna {
      nombre
      descripcion
      laboratorio
    }
  }
}

# Obtener vacunas próximas a vencer
query {
  vacunasPorVencer(fecha: "2026-12-31 23:59") {
    id
    fechaProximaDosis
    mascota {
      nombre
      cliente {
        nombre
        apellidos
        telefono
        email
      }
    }
    vacuna {
      nombre
    }
  }
}

# Obtener aplicaciones por tipo de vacuna
query {
  aplicacionesByVacuna(vacunaId: "1") {
    id
    fechaAplicacion
    veterinario
    mascota {
      nombre
      cliente {
        nombre
      }
    }
  }
}
```

#### Mutations
```graphql
# Aplicar vacuna a mascota
mutation {
  aplicarVacuna(input: {
    mascotaId: "1"
    vacunaId: "1"
    fechaAplicacion: "2025-11-01 10:00"
    veterinario: "Dr. García"
    observaciones: "Primera dosis, mascota en buen estado"
    lote: "RAB-2025-001"
  }) {
    id
    fechaAplicacion
    fechaProximaDosis
    veterinario
    observaciones
    lote
    mascota {
      nombre
    }
    vacuna {
      nombre
    }
  }
}

# Actualizar registro de vacunación
mutation {
  updateMascotaVacuna(input: {
    id: "1"
    fechaProximaDosis: "2026-11-01 10:00"
    observaciones: "Próxima dosis programada"
  }) {
    id
    fechaProximaDosis
    observaciones
  }
}
```

## 🔧 Configuración

### 🔐 Seguridad de Credenciales

**⚠️ IMPORTANTE**: Las credenciales incluidas son para desarrollo local. Para producción:

1. **Cambia todas las credenciales por defecto**
2. **Usa contraseñas fuertes** (mínimo 12 caracteres, con mayúsculas, minúsculas, números y símbolos)
3. **Usa variables de entorno** en lugar de valores hardcodeados

### Variables de Entorno

| Variable | Descripción | Valor por Defecto |
|----------|-------------|-------------------|
| `SPRING_DATASOURCE_URL` | URL de conexión a PostgreSQL | `jdbc:postgresql://localhost:5433/veterinaria_db` |
| `SPRING_DATASOURCE_USERNAME` | Usuario de base de datos | `vet_admin` |
| `SPRING_DATASOURCE_PASSWORD` | Contraseña de base de datos | `VetClinic2025!SecurePass` |
| `SERVER_PORT` | Puerto del servidor | `8081` |
| `SPRING_JPA_SHOW_SQL` | Mostrar consultas SQL | `true` |
| `DGS_GRAPHQL_GRAPHIQL_ENABLED` | Habilitar GraphQL Playground | `true` |

### Profiles de Spring

- **default**: Configuración local de desarrollo
- **docker**: Configuración optimizada para contenedores

## 📊 Monitoreo y Logs

### Health Checks
```bash
# Verificar salud de la aplicación
curl http://localhost:8081/actuator/health

# Verificar métricas
curl http://localhost:8081/actuator/metrics
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

# Eliminar volúmenes (⚠️ Borra datos)
docker compose down -v

# Ver estado de servicios
docker compose ps

# Ejecutar comandos dentro del contenedor
docker compose exec microservicio-clinico bash
```

## 🔍 Troubleshooting

### Problemas Comunes

1. **Puerto 8081 ocupado**:
```bash
# Cambiar puerto en docker-compose.yml
ports:
  - "8082:8081"  # Usar puerto 8082 en lugar de 8081
```

2. **Error de conexión a base de datos**:
```bash
# Verificar que PostgreSQL esté corriendo
docker compose ps postgres

# Reiniciar PostgreSQL
docker compose restart postgres
```

3. **Limpiar cache de Docker**:
```bash
docker system prune -a
docker compose build --no-cache
```

## 🤝 Contribución

1. Fork del repositorio
2. Crear rama feature (`git checkout -b feature/nueva-funcionalidad`)
3. Commit cambios (`git commit -m 'Agregar nueva funcionalidad'`)
4. Push a la rama (`git push origin feature/nueva-funcionalidad`)
5. Crear Pull Request

## 📄 Licencia

Este proyecto está bajo la Licencia MIT. Ver `LICENSE` para más detalles.

## 👥 Equipo de Desarrollo

- **Backend**: Spring Boot + GraphQL Federation
- **Base de Datos**: PostgreSQL con optimizaciones
- **DevOps**: Docker + Docker Compose
- **Documentación**: Markdown + GraphQL Schema

---

### 🚀 ¡Listo para Producción!

Este microservicio está preparado para:
- ✅ Integración con GraphQL Federation Gateway
- ✅ Despliegue en contenedores Docker
- ✅ Escalabilidad horizontal
- ✅ Monitoreo y health checks
- ✅ Persistencia de datos
- ✅ Documentación completa

**¿Necesitas ayuda?** Consulta los logs, revisa la documentación de GraphQL en `/graphiql`, o verifica los health checks en `/actuator/health`.