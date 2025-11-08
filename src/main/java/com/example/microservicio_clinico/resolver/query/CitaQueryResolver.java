package com.example.microservicio_clinico.resolver.query;

import com.example.microservicio_clinico.entity.Cita;
import com.example.microservicio_clinico.service.CitaService;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
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
    public Cita cita(@InputArgument Long id) {
        return citaService.findById(id)
            .orElseThrow(() -> new RuntimeException("Cita no encontrada con ID: " + id));
    }
    
    @DgsQuery
    public List<Cita> citasPorCliente(@InputArgument Long clienteId) {
        return citaService.findByClienteId(clienteId);
    }
    
    @DgsQuery
    public List<Cita> citasPorMascota(@InputArgument Long mascotaId) {
        return citaService.findByMascotaId(mascotaId);
    }
    
    @DgsQuery
    public List<Cita> citasPorDoctor(@InputArgument Long doctorId) {
        return citaService.findByDoctorId(doctorId);
    }
    
    @DgsQuery
    public List<Cita> citasPorEstado(@InputArgument String estado) {
        return citaService.findByEstado(estado);
    }
    
    @DgsQuery
    public List<Cita> citasPorFecha(@InputArgument String fecha) {
        LocalDate localDate = LocalDate.parse(fecha);
        return citaService.findByFecha(localDate);
    }
    
    @DgsQuery
    public List<Cita> citasPorDoctorYFecha(@InputArgument Long doctorId, @InputArgument String fecha) {
        LocalDate localDate = LocalDate.parse(fecha);
        return citaService.findByDoctorIdAndFecha(doctorId, localDate);
    }
    
    @DgsQuery
    public List<Cita> citasPorDoctorYEstado(@InputArgument Long doctorId, @InputArgument String estado) {
        return citaService.findByDoctorIdAndEstado(doctorId, estado);
    }
    
    @DgsQuery
    public List<Cita> citasProximasCliente(@InputArgument Long clienteId) {
        return citaService.findCitasProximasCliente(clienteId);
    }
}