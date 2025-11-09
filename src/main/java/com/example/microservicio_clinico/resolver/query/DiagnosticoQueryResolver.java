package com.example.microservicio_clinico.resolver.query;

import com.example.microservicio_clinico.entity.Diagnostico;
import com.example.microservicio_clinico.service.DiagnosticoService;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

@DgsComponent
@RequiredArgsConstructor
@Slf4j
public class DiagnosticoQueryResolver {

    private final DiagnosticoService diagnosticoService;

    @DgsQuery
    public List<Diagnostico> diagnosticos() {
        log.info("Consultando todos los diagnósticos");
        return diagnosticoService.obtenerTodos();
    }

    @DgsQuery
    public Diagnostico diagnostico(@InputArgument String id) {
        log.info("Consultando diagnóstico con ID: {}", id);
        Optional<Diagnostico> diagnostico = diagnosticoService.obtenerPorId(Long.parseLong(id));
        return diagnostico.orElse(null);
    }

    @DgsQuery
    public List<Diagnostico> diagnosticosPorCita(@InputArgument String citaId) {
        log.info("Consultando diagnósticos de la cita ID: {}", citaId);
        return diagnosticoService.obtenerPorCitaId(Long.parseLong(citaId));
    }

    @DgsQuery
    public List<Diagnostico> diagnosticosPorMascota(@InputArgument String mascotaId) {
        log.info("Consultando diagnósticos de la mascota ID: {}", mascotaId);
        return diagnosticoService.obtenerPorMascotaId(Long.parseLong(mascotaId));
    }
}