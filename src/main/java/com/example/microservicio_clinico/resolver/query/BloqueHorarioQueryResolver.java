package com.example.microservicio_clinico.resolver.query;

import com.example.microservicio_clinico.entity.BloqueHorario;
import com.example.microservicio_clinico.service.BloqueHorarioService;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import lombok.RequiredArgsConstructor;

import java.time.LocalTime;
import java.util.List;

@DgsComponent
@RequiredArgsConstructor
public class BloqueHorarioQueryResolver {
    
    private final BloqueHorarioService bloqueHorarioService;
    
    @DgsQuery
    public List<BloqueHorario> bloquesHorarios() {
        return bloqueHorarioService.findAll();
    }
    
    @DgsQuery
    public BloqueHorario bloqueHorario(@InputArgument Long id) {
        return bloqueHorarioService.findById(id)
            .orElseThrow(() -> new RuntimeException("Bloque horario no encontrado con ID: " + id));
    }
    
    @DgsQuery
    public List<BloqueHorario> bloquesHorariosPorDoctor(@InputArgument Long doctorId) {
        return bloqueHorarioService.findByDoctorId(doctorId);
    }
    
    @DgsQuery
    public List<BloqueHorario> bloquesHorariosPorDia(@InputArgument String dia) {
        return bloqueHorarioService.findByDia(dia);
    }
    
    @DgsQuery
    public List<BloqueHorario> bloquesHorariosPorDoctorYDia(@InputArgument Long doctorId, @InputArgument String dia) {
        return bloqueHorarioService.findByDoctorIdAndDia(doctorId, dia);
    }
    
    @DgsQuery
    public List<BloqueHorario> bloquesDisponiblesParaHora(@InputArgument Long doctorId, @InputArgument String dia, @InputArgument String hora) {
        LocalTime localTime = LocalTime.parse(hora);
        return bloqueHorarioService.findDisponibleParaHora(doctorId, dia, localTime);
    }
}