package com.example.microservicio_clinico.resolver.query;

import com.example.microservicio_clinico.entity.Tratamiento;
import com.example.microservicio_clinico.service.TratamientoService;
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
public class TratamientoQueryResolver {

    private final TratamientoService tratamientoService;

    @DgsQuery
    public List<Tratamiento> tratamientos() {
        log.info("Consultando todos los tratamientos");
        return tratamientoService.obtenerTodos();
    }

    @DgsQuery
    public Tratamiento tratamiento(@InputArgument String id) {
        log.info("Consultando tratamiento con ID: {}", id);
        Optional<Tratamiento> tratamiento = tratamientoService.obtenerPorId(Long.parseLong(id));
        return tratamiento.orElse(null);
    }

    @DgsQuery
    public List<Tratamiento> tratamientosPorDiagnostico(@InputArgument String diagnosticoId) {
        log.info("Consultando tratamientos del diagnóstico ID: {}", diagnosticoId);
        return tratamientoService.obtenerPorDiagnosticoId(Long.parseLong(diagnosticoId));
    }
}