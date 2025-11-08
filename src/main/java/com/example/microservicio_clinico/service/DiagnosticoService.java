package com.example.microservicio_clinico.service;

import com.example.microservicio_clinico.entity.Diagnostico;
import com.example.microservicio_clinico.entity.Mascota;
import com.example.microservicio_clinico.repository.DiagnosticoRepository;
import com.example.microservicio_clinico.repository.MascotaRepository;
import com.example.microservicio_clinico.dto.DiagnosticoInputDTO;
import com.example.microservicio_clinico.dto.DiagnosticoUpdateDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class DiagnosticoService {
    
    private final DiagnosticoRepository diagnosticoRepository;
    private final MascotaRepository mascotaRepository;
    
    public Diagnostico create(DiagnosticoInputDTO input) {
        log.info("Creando diagnóstico con input: {}", input);
        
        // Buscar la mascota
        Long mascotaId = Long.parseLong(input.getMascotaId());
        Mascota mascota = mascotaRepository.findById(mascotaId)
            .orElseThrow(() -> new RuntimeException("Mascota no encontrada con ID: " + mascotaId));
        log.info("Mascota encontrada: {}", mascota.getNombre());
        
        Diagnostico diagnostico = new Diagnostico();
        diagnostico.setDescripcion(input.getDescripcion());
        diagnostico.setObservaciones(input.getObservaciones());
        diagnostico.setFechaDiagnostico(LocalDateTime.now());
        diagnostico.setMascota(mascota);
        
        Diagnostico savedDiagnostico = diagnosticoRepository.save(diagnostico);
        log.info("Diagnóstico creado exitosamente con ID: {}", savedDiagnostico.getId());
        
        return savedDiagnostico;
    }
    
    public Diagnostico update(Long id, DiagnosticoUpdateDTO input) {
        log.info("Actualizando diagnóstico con input: {}", input);
        
        Long id = Long.parseLong(input.getId());
        Optional<Diagnostico> existingDiagnostico = diagnosticoRepository.findById(id);
        
        if (existingDiagnostico.isEmpty()) {
            throw new RuntimeException("Diagnóstico no encontrado con ID: " + id);
        }
        
        Diagnostico diagnostico = existingDiagnostico.get();
        
        if (input.getDescripcion() != null) {
            diagnostico.setDescripcion(input.getDescripcion());
        }
        
        if (input.getObservaciones() != null) {
            diagnostico.setObservaciones(input.getObservaciones());
        }
        
        if (input.getMascotaId() != null) {
            Long mascotaId = Long.parseLong(input.getMascotaId());
            Mascota mascota = mascotaRepository.findById(mascotaId)
                .orElseThrow(() -> new RuntimeException("Mascota no encontrada con ID: " + mascotaId));
            diagnostico.setMascota(mascota);
        }
        
        Diagnostico updatedDiagnostico = diagnosticoRepository.save(diagnostico);
        log.info("Diagnóstico actualizado exitosamente: {}", updatedDiagnostico.getId());
        
        return updatedDiagnostico;
    }
    
    public Boolean delete(Long id) {
        log.info("Eliminando diagnóstico con ID: {}", id);
        
        if (!diagnosticoRepository.existsById(id)) {
            throw new RuntimeException("Diagnóstico no encontrado con ID: " + id);
        }
        
        diagnosticoRepository.deleteById(id);
        log.info("Diagnóstico eliminado exitosamente: {}", id);
        
        return true;
    }
    
    public List<Diagnostico> findAll() {
        log.info("Obteniendo todos los diagnósticos");
        return diagnosticoRepository.findAll();
    }
    
    public Optional<Diagnostico> findById(Long id) {
        log.info("Buscando diagnóstico con ID: {}", id);
        return diagnosticoRepository.findById(id);
    }
    
    public List<Diagnostico> findByMascotaId(Long mascotaId) {
        log.info("Buscando diagnósticos por mascota ID: {}", mascotaId);
        return diagnosticoRepository.findByMascotaIdOrderByFechaDiagnosticoDesc(mascotaId);
    }
}