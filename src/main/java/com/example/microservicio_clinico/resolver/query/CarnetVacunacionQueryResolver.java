package com.example.microservicio_clinico.resolver.query;

import com.example.microservicio_clinico.entity.CarnetVacunacion;
import com.example.microservicio_clinico.service.CarnetVacunacionService;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@DgsComponent
@RequiredArgsConstructor
public class CarnetVacunacionQueryResolver {
    
    private final CarnetVacunacionService carnetVacunacionService;
    
    @DgsQuery
    public List<CarnetVacunacion> carnetsVacunacion() {
        return carnetVacunacionService.findAll();
    }
    
    @DgsQuery
    public CarnetVacunacion carnetVacunacion(@InputArgument Long id) {
        return carnetVacunacionService.findById(id)
            .orElseThrow(() -> new RuntimeException("Carnet de vacunación no encontrado con ID: " + id));
    }
    
    @DgsQuery
    public CarnetVacunacion carnetVacunacionPorMascota(@InputArgument Long mascotaId) {
        return carnetVacunacionService.findByMascotaId(mascotaId)
            .orElseThrow(() -> new RuntimeException("No se encontró carnet de vacunación para la mascota con ID: " + mascotaId));
    }
    
    @DgsQuery
    public List<CarnetVacunacion> carnetsVacunacionPorCliente(@InputArgument Long clienteId) {
        return carnetVacunacionService.findByClienteId(clienteId);
    }
    
    @DgsQuery
    public List<CarnetVacunacion> carnetsVacunacionPorFecha(@InputArgument String fecha) {
        LocalDate localDate = LocalDate.parse(fecha);
        return carnetVacunacionService.findByFechaCreacion(localDate);
    }
    
    @DgsQuery
    public List<CarnetVacunacion> carnetsVacunacionEntreFechas(@InputArgument String fechaInicio, @InputArgument String fechaFin) {
        LocalDate fechaIni = LocalDate.parse(fechaInicio);
        LocalDate fechaFinal = LocalDate.parse(fechaFin);
        return carnetVacunacionService.findByFechaCreacionBetween(fechaIni, fechaFinal);
    }
    
    @DgsQuery
    public List<CarnetVacunacion> buscarCarnetsPorNombreMascota(@InputArgument String nombre) {
        return carnetVacunacionService.buscarPorNombreMascota(nombre);
    }
    
    @DgsQuery
    public Long contarCarnetsPorCliente(@InputArgument Long clienteId) {
        return carnetVacunacionService.contarPorClienteId(clienteId);
    }
}