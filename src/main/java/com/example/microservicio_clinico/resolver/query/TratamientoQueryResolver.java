package com.example.microservicio_clinico.resolver.query;

import com.example.microservicio_clinico.entity.Tratamiento;
import com.example.microservicio_clinico.service.TratamientoService;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
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
    public Tratamiento tratamiento(@InputArgument Long id) {
        return tratamientoService.findById(id)
            .orElseThrow(() -> new RuntimeException("Tratamiento no encontrado con ID: " + id));
    }
    
    @DgsQuery
    public List<Tratamiento> tratamientosPorDiagnostico(@InputArgument Long diagnosticoId) {
        return tratamientoService.findByDiagnosticoId(diagnosticoId);
    }
    
    @DgsQuery
    public List<Tratamiento> tratamientosPorMascota(@InputArgument Long mascotaId) {
        return tratamientoService.findByMascotaId(mascotaId);
    }
    
    @DgsQuery
    public List<Tratamiento> tratamientosPorCliente(@InputArgument Long clienteId) {
        return tratamientoService.findByClienteId(clienteId);
    }
    
    @DgsQuery
    public List<Tratamiento> buscarTratamientosPorMedicamento(@InputArgument String medicamento) {
        return tratamientoService.buscarPorMedicamento(medicamento);
    }
    
    @DgsQuery
    public List<Tratamiento> tratamientosActivos() {
        return tratamientoService.findTratamientosActivos();
    }
    
    @DgsQuery
    public List<Tratamiento> tratamientosFinalizados() {
        return tratamientoService.findTratamientosFinalizados();
    }
    
    @DgsQuery
    public List<Tratamiento> tratamientosActivosEnFecha(@InputArgument String fecha) {
        LocalDate localDate = LocalDate.parse(fecha);
        return tratamientoService.findTratamientosActivosEnFecha(localDate);
    }
    
    @DgsQuery
    public List<Tratamiento> tratamientosActivosPorMascota(@InputArgument Long mascotaId) {
        return tratamientoService.findTratamientosActivosByMascotaId(mascotaId);
    }
}