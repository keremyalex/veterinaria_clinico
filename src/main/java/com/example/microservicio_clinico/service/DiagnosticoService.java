package com.example.microservicio_clinico.service;

import com.example.microservicio_clinico.dto.*;
import com.example.microservicio_clinico.entity.Cita;
import com.example.microservicio_clinico.entity.Diagnostico;
import com.example.microservicio_clinico.repository.CitaRepository;
import com.example.microservicio_clinico.repository.DiagnosticoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class DiagnosticoService {
    
    private final DiagnosticoRepository diagnosticoRepository;
    private final CitaRepository citaRepository;
    private final CitaService citaService;
    
    // Crear nuevo diagnóstico
    public DiagnosticoOutput crearDiagnostico(DiagnosticoInput input) {
        log.info("Creando nuevo diagnóstico para cita ID: {}", input.getCitaId());
        
        // Validar que la cita exista
        Cita cita = citaRepository.findById(input.getCitaId())
            .orElseThrow(() -> new RuntimeException("Cita no encontrada con ID: " + input.getCitaId()));
        
        // Validar que la cita esté en estado completada o en progreso
        if (cita.getEstado() != 2 && cita.getEstado() != 3) {
            throw new RuntimeException("Solo se pueden crear diagnósticos para citas en progreso o completadas");
        }
        
        Diagnostico diagnostico = new Diagnostico();
        diagnostico.setDescripcion(input.getDescripcion());
        diagnostico.setObservaciones(input.getObservaciones());
        diagnostico.setFecharegistro(input.getFecharegistro());
        diagnostico.setCita(cita);
        
        Diagnostico savedDiagnostico = diagnosticoRepository.save(diagnostico);
        log.info("Diagnóstico creado exitosamente con ID: {}", savedDiagnostico.getId());
        
        return convertirAOutput(savedDiagnostico);
    }
    
    // Actualizar diagnóstico
    public DiagnosticoOutput actualizarDiagnostico(DiagnosticoUpdateInput input) {
        log.info("Actualizando diagnóstico con ID: {}", input.getId());
        
        Diagnostico diagnostico = diagnosticoRepository.findById(input.getId())
            .orElseThrow(() -> new RuntimeException("Diagnóstico no encontrado con ID: " + input.getId()));
        
        // Actualizar cita si se proporciona
        if (input.getCitaId() != null && !input.getCitaId().equals(diagnostico.getCita().getId())) {
            Cita cita = citaRepository.findById(input.getCitaId())
                .orElseThrow(() -> new RuntimeException("Cita no encontrada con ID: " + input.getCitaId()));
            diagnostico.setCita(cita);
        }
        
        // Actualizar otros campos si se proporcionan
        if (input.getDescripcion() != null) diagnostico.setDescripcion(input.getDescripcion());
        if (input.getObservaciones() != null) diagnostico.setObservaciones(input.getObservaciones());
        if (input.getFecharegistro() != null) diagnostico.setFecharegistro(input.getFecharegistro());
        
        Diagnostico updatedDiagnostico = diagnosticoRepository.save(diagnostico);
        log.info("Diagnóstico actualizado exitosamente con ID: {}", updatedDiagnostico.getId());
        
        return convertirAOutput(updatedDiagnostico);
    }
    
    // Obtener diagnóstico por ID
    @Transactional(readOnly = true)
    public DiagnosticoOutput obtenerDiagnosticoPorId(Integer id) {
        log.info("Obteniendo diagnóstico por ID: {}", id);
        
        Diagnostico diagnostico = diagnosticoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Diagnóstico no encontrado con ID: " + id));
        
        return convertirAOutput(diagnostico);
    }
    
    // Obtener todos los diagnósticos
    @Transactional(readOnly = true)
    public List<DiagnosticoOutput> obtenerTodosLosDiagnosticos() {
        log.info("Obteniendo todos los diagnósticos");
        
        return diagnosticoRepository.findAll()
            .stream()
            .map(this::convertirAOutput)
            .collect(Collectors.toList());
    }
    
    // Obtener diagnósticos por cita
    @Transactional(readOnly = true)
    public List<DiagnosticoOutput> obtenerDiagnosticosPorCita(Integer citaId) {
        log.info("Obteniendo diagnósticos de la cita ID: {}", citaId);
        
        return diagnosticoRepository.findByCitaId(citaId)
            .stream()
            .map(this::convertirAOutput)
            .collect(Collectors.toList());
    }
    
    // Obtener diagnósticos por doctor
    @Transactional(readOnly = true)
    public List<DiagnosticoOutput> obtenerDiagnosticosPorDoctor(Integer doctorId) {
        log.info("Obteniendo diagnósticos del doctor ID: {}", doctorId);
        
        return diagnosticoRepository.findByDoctorId(doctorId)
            .stream()
            .map(this::convertirAOutput)
            .collect(Collectors.toList());
    }
    
    // Obtener diagnósticos por mascota
    @Transactional(readOnly = true)
    public List<DiagnosticoOutput> obtenerDiagnosticosPorMascota(Integer mascotaId) {
        log.info("Obteniendo diagnósticos de la mascota ID: {}", mascotaId);
        
        return diagnosticoRepository.findByMascotaId(mascotaId)
            .stream()
            .map(this::convertirAOutput)
            .collect(Collectors.toList());
    }
    
    // Buscar diagnósticos por descripción
    @Transactional(readOnly = true)
    public List<DiagnosticoOutput> buscarDiagnosticosPorDescripcion(String termino) {
        log.info("Buscando diagnósticos por descripción: {}", termino);
        
        return diagnosticoRepository.findByDescripcionContainingIgnoreCase(termino)
            .stream()
            .map(this::convertirAOutput)
            .collect(Collectors.toList());
    }
    
    // Obtener diagnósticos por rango de fechas
    @Transactional(readOnly = true)
    public List<DiagnosticoOutput> obtenerDiagnosticosPorRangoFechas(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        log.info("Obteniendo diagnósticos entre {} y {}", fechaInicio, fechaFin);
        
        return diagnosticoRepository.findByFecharegistroBetween(fechaInicio, fechaFin)
            .stream()
            .map(this::convertirAOutput)
            .collect(Collectors.toList());
    }
    
    // Obtener diagnósticos de un doctor en un rango de fechas
    @Transactional(readOnly = true)
    public List<DiagnosticoOutput> obtenerDiagnosticosPorDoctorYFechas(Integer doctorId, LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        log.info("Obteniendo diagnósticos del doctor ID: {} entre {} y {}", doctorId, fechaInicio, fechaFin);
        
        return diagnosticoRepository.findByDoctorIdAndFecharegistroBetween(doctorId, fechaInicio, fechaFin)
            .stream()
            .map(this::convertirAOutput)
            .collect(Collectors.toList());
    }
    
    // Eliminar diagnóstico
    public boolean eliminarDiagnostico(Integer id) {
        log.info("Eliminando diagnóstico con ID: {}", id);
        
        if (!diagnosticoRepository.existsById(id)) {
            throw new RuntimeException("Diagnóstico no encontrado con ID: " + id);
        }
        
        diagnosticoRepository.deleteById(id);
        log.info("Diagnóstico eliminado exitosamente con ID: {}", id);
        
        return true;
    }
    
    // Método privado para convertir Entity a DTO
    private DiagnosticoOutput convertirAOutput(Diagnostico diagnostico) {
        DiagnosticoOutput output = new DiagnosticoOutput();
        output.setId(diagnostico.getId());
        output.setDescripcion(diagnostico.getDescripcion());
        output.setObservaciones(diagnostico.getObservaciones());
        output.setFecharegistro(diagnostico.getFecharegistro());
        
        // Convertir cita
        output.setCita(citaService.obtenerCitaPorId(diagnostico.getCita().getId()));
        
        // Los tratamientos se cargarán por separado cuando sea necesario
        
        return output;
    }
}
