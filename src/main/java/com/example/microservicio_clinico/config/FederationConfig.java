package com.example.microservicio_clinico.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class FederationConfig {
    // DGS maneja automáticamente la configuración de Federation
    // Solo necesitamos las directivas @key en el schema y los @DgsEntityFetcher
}