package com.example.microservicio_clinico.resolver.query;

import com.example.microservicio_clinico.entity.Vacuna;
import com.example.microservicio_clinico.service.VacunaService;
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
public class VacunaQueryResolver {

    private final VacunaService vacunaService;

    @DgsQuery
    public List<Vacuna> vacunas() {
        log.info("Consultando todas las vacunas");
        return vacunaService.obtenerTodas();
    }

    @DgsQuery
    public Vacuna vacuna(@InputArgument String id) {
        log.info("Consultando vacuna con ID: {}", id);
        Optional<Vacuna> vacuna = vacunaService.obtenerPorId(Long.parseLong(id));
        return vacuna.orElse(null);
    }

    @DgsQuery
    public Vacuna vacunaPorDescripcion(@InputArgument String descripcion) {
        log.info("Consultando vacuna con descripción: {}", descripcion);
        Optional<Vacuna> vacuna = vacunaService.obtenerPorDescripcion(descripcion);
        return vacuna.orElse(null);
    }
}