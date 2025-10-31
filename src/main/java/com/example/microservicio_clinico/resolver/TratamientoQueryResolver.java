package com.example.microservicio_clinico.resolver;

import com.example.microservicio_clinico.entity.Tratamiento;
import com.example.microservicio_clinico.service.TratamientoService;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import lombok.RequiredArgsConstructor;

import java.util.List;

@DgsComponent
@RequiredArgsConstructor
public class TratamientoQueryResolver {
    
    private final TratamientoService tratamientoService;
    
    @DgsQuery
    public List<Tratamiento> tratamientos() {
        return tratamientoService.findAll();
    }
    
    @DgsQuery
    public Tratamiento tratamiento(@InputArgument String id) {
        Long longId = Long.parseLong(id);
        return tratamientoService.findById(longId).orElse(null);
    }
    
    @DgsQuery
    public List<Tratamiento> tratamientosByDiagnostico(@InputArgument String diagnosticoId) {
        Long longDiagnosticoId = Long.parseLong(diagnosticoId);
        return tratamientoService.findByDiagnosticoId(longDiagnosticoId);
    }
    
    @DgsQuery
    public List<Tratamiento> tratamientosByEstado(@InputArgument String estado) {
        return tratamientoService.findByEstado(estado);
    }
}