package com.example.microservicio_clinico.resolver;

import com.example.microservicio_clinico.entity.Cita;
import com.example.microservicio_clinico.service.CitaService;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import lombok.RequiredArgsConstructor;

import java.util.List;

@DgsComponent
@RequiredArgsConstructor
public class CitaQueryResolver {
    
    private final CitaService citaService;
    
    @DgsQuery
    public List<Cita> citas() {
        return citaService.findAll();
    }
    
    @DgsQuery
    public Cita cita(@InputArgument String id) {
        Long longId = Long.parseLong(id);
        return citaService.findById(longId).orElse(null);
    }
    
    @DgsQuery
    public List<Cita> citasByCliente(@InputArgument String clienteId) {
        Long longClienteId = Long.parseLong(clienteId);
        return citaService.findByClienteId(longClienteId);
    }
    
    @DgsQuery
    public List<Cita> citasByMascota(@InputArgument String mascotaId) {
        Long longMascotaId = Long.parseLong(mascotaId);
        return citaService.findByMascotaId(longMascotaId);
    }
    
    @DgsQuery
    public List<Cita> citasByHorario(@InputArgument String horarioId) {
        Long longHorarioId = Long.parseLong(horarioId);
        return citaService.findByHorarioId(longHorarioId);
    }
}