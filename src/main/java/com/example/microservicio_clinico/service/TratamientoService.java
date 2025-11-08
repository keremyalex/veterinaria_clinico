package com.example.microservicio_clinico.service;

import com.example.microservicio_clinico.entity.Tratamiento;
import com.example.microservicio_clinico.entity.Diagnostico;
import com.example.microservicio_clinico.repository.TratamientoRepository;
import com.example.microservicio_clinico.repository.DiagnosticoRepository;
import com.example.microservicio_clinico.dto.TratamientoInputDTO;
import com.example.microservicio_clinico.dto.TratamientoUpdateDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class TratamientoService {
    
    private final TratamientoRepository tratamientoRepository;
    private final DiagnosticoRepository diagnosticoRepository;
    
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    
    public Tratamiento create(TratamientoInputDTO input) {
        log.info("Creando tratamiento con input: {}", input);
        
        // Buscar el diagnóstico
        Long diagnosticoId = Long.parseLong(input.getDiagnosticoId());
        Diagnostico diagnostico = diagnosticoRepository.findById(diagnosticoId)
            .orElseThrow(() -> new RuntimeException("Diagnóstico no encontrado con ID: " + diagnosticoId));
        log.info("Diagnóstico encontrado para mascota: {}", diagnostico.getMascota().getNombre());
        
        Tratamiento tratamiento = new Tratamiento();
        tratamiento.setDescripcion(input.getDescripcion());
        tratamiento.setFechaInicio(LocalDateTime.parse(input.getFechaInicio(), dateTimeFormatter));
        
        if (input.getFechaFin() != null && !input.getFechaFin().isEmpty()) {
            tratamiento.setFechaFin(LocalDateTime.parse(input.getFechaFin(), dateTimeFormatter));
        }
        
        tratamiento.setInstrucciones(input.getInstrucciones());
        tratamiento.setEstado(input.getEstado() != null ? input.getEstado() : "ACTIVO");
        tratamiento.setDiagnostico(diagnostico);
        
        Tratamiento savedTratamiento = tratamientoRepository.save(tratamiento);
        log.info("Tratamiento creado exitosamente con ID: {}", savedTratamiento.getId());
        
        return savedTratamiento;
    }
    
    public Tratamiento update(Long id, TratamientoUpdateDTO input) {
        log.info("Actualizando tratamiento con input: {}", input);
        
        Long id = Long.parseLong(input.getId());
        Optional<Tratamiento> existingTratamiento = tratamientoRepository.findById(id);
        
        if (existingTratamiento.isEmpty()) {
            throw new RuntimeException("Tratamiento no encontrado con ID: " + id);
        }
        
        Tratamiento tratamiento = existingTratamiento.get();
        
        if (input.getDescripcion() != null) {
            tratamiento.setDescripcion(input.getDescripcion());
        }
        
        if (input.getFechaInicio() != null) {
            tratamiento.setFechaInicio(LocalDateTime.parse(input.getFechaInicio(), dateTimeFormatter));
        }
        
        if (input.getFechaFin() != null && !input.getFechaFin().isEmpty()) {
            tratamiento.setFechaFin(LocalDateTime.parse(input.getFechaFin(), dateTimeFormatter));
        }
        
        if (input.getInstrucciones() != null) {
            tratamiento.setInstrucciones(input.getInstrucciones());
        }
        
        if (input.getEstado() != null) {
            tratamiento.setEstado(input.getEstado());
        }
        
        if (input.getDiagnosticoId() != null) {
            Long diagnosticoId = Long.parseLong(input.getDiagnosticoId());
            Diagnostico diagnostico = diagnosticoRepository.findById(diagnosticoId)
                .orElseThrow(() -> new RuntimeException("Diagnóstico no encontrado con ID: " + diagnosticoId));
            tratamiento.setDiagnostico(diagnostico);
        }
        
        Tratamiento updatedTratamiento = tratamientoRepository.save(tratamiento);
        log.info("Tratamiento actualizado exitosamente: {}", updatedTratamiento.getId());
        
        return updatedTratamiento;
    }
    
    public Boolean delete(Long id) {
        log.info("Eliminando tratamiento con ID: {}", id);
        
        if (!tratamientoRepository.existsById(id)) {
            throw new RuntimeException("Tratamiento no encontrado con ID: " + id);
        }
        
        tratamientoRepository.deleteById(id);
        log.info("Tratamiento eliminado exitosamente: {}", id);
        
        return true;
    }
    
    public List<Tratamiento> findAll() {
        log.info("Obteniendo todos los tratamientos");
        return tratamientoRepository.findAll();
    }
    
    public Optional<Tratamiento> findById(Long id) {
        log.info("Buscando tratamiento con ID: {}", id);
        return tratamientoRepository.findById(id);
    }
    
    public List<Tratamiento> findByDiagnosticoId(Long diagnosticoId) {
        log.info("Buscando tratamientos por diagnóstico ID: {}", diagnosticoId);
        return tratamientoRepository.findByDiagnosticoIdOrderByFechaInicioDesc(diagnosticoId);
    }
    
    public List<Tratamiento> findByEstado(String estado) {
        log.info("Buscando tratamientos por estado: {}", estado);
        return tratamientoRepository.findByEstado(estado);
    }
}