package com.example.microservicio_clinico.resolver.query;

import com.example.microservicio_clinico.entity.Vacuna;
import com.example.microservicio_clinico.service.VacunaService;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;

import java.util.List;

@DgsComponent
public class VacunaQueryResolver {
    
    private final VacunaService vacunaService;
    
    public VacunaQueryResolver(VacunaService vacunaService) {
        this.vacunaService = vacunaService;
    }
    
    @DgsQuery
    public List<Vacuna> vacunas() {
        return vacunaService.findAll();
    }
    
    @DgsQuery
    public Vacuna vacuna(@InputArgument String id) {
        return vacunaService.findById(Long.parseLong(id))
            .orElseThrow(() -> new RuntimeException("Vacuna no encontrada"));
    }
    
    @DgsQuery
    public List<Vacuna> vacunasByNombre(@InputArgument String nombre) {
        return vacunaService.findByNombreContaining(nombre);
    }
    
    @DgsQuery
    public List<Vacuna> vacunasByLaboratorio(@InputArgument String laboratorio) {
        return vacunaService.findByLaboratorio(laboratorio);
    }
}