package com.example.microservicio_clinico.resolver;

import com.example.microservicio_clinico.entity.Especie;
import com.example.microservicio_clinico.service.EspecieService;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import lombok.RequiredArgsConstructor;

import java.util.List;

@DgsComponent
@RequiredArgsConstructor
public class EspecieQueryResolver {
    
    private final EspecieService especieService;
    
    @DgsQuery
    public List<Especie> especies() {
        return especieService.findAll();
    }
    
    @DgsQuery
    public Especie especie(@InputArgument String id) {
        Long longId = Long.parseLong(id);
        return especieService.findById(longId)
            .orElseThrow(() -> new RuntimeException("Especie no encontrada con ID: " + id));
    }
    
    @DgsQuery
    public Especie especieByDescripcion(@InputArgument String descripcion) {
        return especieService.findByDescripcion(descripcion)
            .orElseThrow(() -> new RuntimeException("Especie no encontrada con descripción: " + descripcion));
    }
}