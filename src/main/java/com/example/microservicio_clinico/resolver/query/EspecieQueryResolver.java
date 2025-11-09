package com.example.microservicio_clinico.resolver.query;

import com.example.microservicio_clinico.entity.Especie;
import com.example.microservicio_clinico.service.EspecieService;
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
public class EspecieQueryResolver {

    private final EspecieService especieService;

    @DgsQuery
    public List<Especie> especies() {
        log.info("Consultando todas las especies");
        return especieService.obtenerTodas();
    }

    @DgsQuery
    public Especie especie(@InputArgument String id) {
        log.info("Consultando especie con ID: {}", id);
        Optional<Especie> especie = especieService.obtenerPorId(Long.parseLong(id));
        return especie.orElse(null);
    }

    @DgsQuery
    public Especie especiePorDescripcion(@InputArgument String descripcion) {
        log.info("Consultando especie con descripción: {}", descripcion);
        Optional<Especie> especie = especieService.obtenerPorDescripcion(descripcion);
        return especie.orElse(null);
    }
}