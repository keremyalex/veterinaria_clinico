-- Script de inicialización de la base de datos PostgreSQL
-- Este archivo se ejecuta automáticamente cuando se inicia el contenedor de PostgreSQL

-- Crear extensiones necesarias
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- Configurar timezone por defecto
SET timezone = 'America/La_Paz';

-- Crear índices adicionales para optimizar consultas frecuentes
-- (Las tablas se crean automáticamente por Hibernate)

-- Comentarios de documentación para las futuras tablas
COMMENT ON SCHEMA public IS 'Esquema principal del sistema veterinario';

-- Log de inicialización
DO $$
BEGIN
    RAISE NOTICE 'Base de datos veterinaria inicializada correctamente';
    RAISE NOTICE 'Timezone configurado: %', current_setting('timezone');
END $$;