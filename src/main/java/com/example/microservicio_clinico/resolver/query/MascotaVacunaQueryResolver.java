package com.example.microservicio_clinico.resolver.query;

import com.example.microservicio_clinico.entity.MascotaVacuna;
import com.example.microservicio_clinico.service.MascotaVacunaService;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@DgsComponent
public class MascotaVacunaQueryResolver {
    
    private final MascotaVacunaService mascotaVacunaService;
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    
    public MascotaVacunaQueryResolver(MascotaVacunaService mascotaVacunaService) {
        this.mascotaVacunaService = mascotaVacunaService;
    }
    
    @DgsQuery
    public List<MascotaVacuna> mascotaVacunas() {
        return mascotaVacunaService.findAll();
    }
    
    @DgsQuery
    public MascotaVacuna mascotaVacuna(@InputArgument String id) {
        return mascotaVacunaService.findById(Long.parseLong(id))
            .orElseThrow(() -> new RuntimeException("Aplicación de vacuna no encontrada"));
    }
    
    @DgsQuery
    public List<MascotaVacuna> vacunasByMascota(@InputArgument String mascotaId) {
        return mascotaVacunaService.findByMascotaId(Long.parseLong(mascotaId));
    }
    
    @DgsQuery
    public List<MascotaVacuna> aplicacionesByVacuna(@InputArgument String vacunaId) {
        return mascotaVacunaService.findByVacunaId(Long.parseLong(vacunaId));
    }
    
    @DgsQuery
    public List<MascotaVacuna> vacunasPorVencer(@InputArgument String fecha) {
        LocalDateTime fechaLimite = LocalDateTime.parse(fecha, dateTimeFormatter);
        return mascotaVacunaService.findVacunasPorVencer(fechaLimite);
    }
}