package com.example.microservicio_clinico.resolver.query;

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
    public Diagnostico diagnostico(@InputArgument Long id) {
        return diagnosticoService.findById(id)
            .orElseThrow(() -> new RuntimeException("Diagnóstico no encontrado con ID: " + id));
    }
    
    @DgsQuery
    public Diagnostico diagnosticoPorCita(@InputArgument Long citaId) {
        return diagnosticoService.findByCitaId(citaId)
            .orElseThrow(() -> new RuntimeException("No se encontró diagnóstico para la cita con ID: " + citaId));
    }
    
    @DgsQuery
    public List<Diagnostico> diagnosticosPorMascota(@InputArgument Long mascotaId) {
        return diagnosticoService.findByMascotaId(mascotaId);
    }
    
    @DgsQuery
    public List<Diagnostico> diagnosticosPorCliente(@InputArgument Long clienteId) {
        return diagnosticoService.findByClienteId(clienteId);
    }
    
    @DgsQuery
    public List<Diagnostico> diagnosticosPorDoctor(@InputArgument Long doctorId) {
        return diagnosticoService.findByDoctorId(doctorId);
    }
    
    @DgsQuery
    public List<Diagnostico> buscarDiagnosticosPorEnfermedad(@InputArgument String enfermedad) {
        return diagnosticoService.buscarPorEnfermedad(enfermedad);
    }
    
    @DgsQuery
    public List<Diagnostico> buscarDiagnosticos(@InputArgument String termino) {
        return diagnosticoService.buscarPorEnfermedadODescripcion(termino);
    }
}