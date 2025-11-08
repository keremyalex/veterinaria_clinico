package com.example.microservicio_clinico.resolver.query;

import com.example.microservicio_clinico.entity.DetalleVacunacion;
import com.example.microservicio_clinico.service.DetalleVacunacionService;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@DgsComponent
@RequiredArgsConstructor
public class DetalleVacunacionQueryResolver {
    
    private final DetalleVacunacionService detalleVacunacionService;
    
    @DgsQuery
    public List<DetalleVacunacion> detallesVacunacion() {
        return detalleVacunacionService.findAll();
    }
    
    @DgsQuery
    public DetalleVacunacion detalleVacunacion(@InputArgument Long id) {
        return detalleVacunacionService.findById(id)
            .orElseThrow(() -> new RuntimeException("Detalle de vacunación no encontrado con ID: " + id));
    }
    
    @DgsQuery
    public List<DetalleVacunacion> detallesVacunacionPorCarnet(@InputArgument Long carnetId) {
        return detalleVacunacionService.findByCarnetVacunacionId(carnetId);
    }
    
    @DgsQuery
    public List<DetalleVacunacion> detallesVacunacionPorVacuna(@InputArgument Long vacunaId) {
        return detalleVacunacionService.findByVacunaId(vacunaId);
    }
    
    @DgsQuery
    public List<DetalleVacunacion> detallesVacunacionPorMascota(@InputArgument Long mascotaId) {
        return detalleVacunacionService.findByMascotaId(mascotaId);
    }
    
    @DgsQuery
    public List<DetalleVacunacion> detallesVacunacionPorCliente(@InputArgument Long clienteId) {
        return detalleVacunacionService.findByClienteId(clienteId);
    }
    
    @DgsQuery
    public List<DetalleVacunacion> detallesVacunacionPorCarnetYVacuna(@InputArgument Long carnetId, @InputArgument Long vacunaId) {
        return detalleVacunacionService.findByCarnetAndVacuna(carnetId, vacunaId);
    }
    
    @DgsQuery
    public List<DetalleVacunacion> detallesVacunacionPorFechaProximaDosis(@InputArgument String fecha) {
        LocalDate localDate = LocalDate.parse(fecha);
        return detalleVacunacionService.findByFechaProximaDosis(localDate);
    }
    
    @DgsQuery
    public List<DetalleVacunacion> detallesVacunacionEntreFechasProximaDosis(@InputArgument String fechaInicio, @InputArgument String fechaFin) {
        LocalDate fechaIni = LocalDate.parse(fechaInicio);
        LocalDate fechaFinal = LocalDate.parse(fechaFin);
        return detalleVacunacionService.findByFechaProximaDosisBetween(fechaIni, fechaFinal);
    }
    
    @DgsQuery
    public List<DetalleVacunacion> detallesVacunacionEntreFechasAplicacion(@InputArgument String fechaInicio, @InputArgument String fechaFin) {
        LocalDate fechaIni = LocalDate.parse(fechaInicio);
        LocalDate fechaFinal = LocalDate.parse(fechaFin);
        return detalleVacunacionService.findByFechaAplicacionBetween(fechaIni, fechaFinal);
    }
    
    @DgsQuery
    public List<DetalleVacunacion> detallesVacunacionPorLote(@InputArgument String lote) {
        return detalleVacunacionService.findByLote(lote);
    }
    
    @DgsQuery
    public List<DetalleVacunacion> buscarDetallesPorVeterinario(@InputArgument String veterinario) {
        return detalleVacunacionService.buscarPorVeterinario(veterinario);
    }
    
    @DgsQuery
    public List<DetalleVacunacion> vacunasProximasAVencer(@InputArgument Integer diasAnticipacion) {
        return detalleVacunacionService.findVacunasProximasAVencer(diasAnticipacion);
    }
}