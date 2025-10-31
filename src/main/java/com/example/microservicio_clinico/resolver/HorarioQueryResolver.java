package com.example.microservicio_clinico.resolver;

import com.example.microservicio_clinico.entity.Horario;
import com.example.microservicio_clinico.service.HorarioService;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import lombok.RequiredArgsConstructor;

import java.util.List;

@DgsComponent
@RequiredArgsConstructor
public class HorarioQueryResolver {
    
    private final HorarioService horarioService;
    
    @DgsQuery
    public List<Horario> horarios() {
        return horarioService.findAll();
    }
    
    @DgsQuery
    public Horario horario(@InputArgument String id) {
        Long longId = Long.parseLong(id);
        return horarioService.findById(longId).orElse(null);
    }
    
    @DgsQuery
    public List<Horario> horariosByDia(@InputArgument String dia) {
        return horarioService.findByDia(dia);
    }
}