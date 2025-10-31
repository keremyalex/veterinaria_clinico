package com.example.microservicio_clinico.resolver;

import com.example.microservicio_clinico.entity.Diagnostico;
import com.example.microservicio_clinico.service.DiagnosticoService;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import lombok.RequiredArgsConstructor;

import java.util.List;

@DgsComponent
@RequiredArgsConstructor
public class DiagnosticoQueryResolver {
    
    private final DiagnosticoService diagnosticoService;
    
    @DgsQuery
    public List<Diagnostico> diagnosticos() {
        return diagnosticoService.findAll();
    }
    
    @DgsQuery
    public Diagnostico diagnostico(@InputArgument String id) {
        Long longId = Long.parseLong(id);
        return diagnosticoService.findById(longId).orElse(null);
    }
    
    @DgsQuery
    public List<Diagnostico> diagnosticosByMascota(@InputArgument String mascotaId) {
        Long longMascotaId = Long.parseLong(mascotaId);
        return diagnosticoService.findByMascotaId(longMascotaId);
    }
}