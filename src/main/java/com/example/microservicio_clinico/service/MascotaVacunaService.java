package com.example.microservicio_clinico.service;

import com.example.microservicio_clinico.entity.MascotaVacuna;
import com.example.microservicio_clinico.entity.Mascota;
import com.example.microservicio_clinico.entity.Vacuna;
import com.example.microservicio_clinico.repository.MascotaVacunaRepository;
import com.example.microservicio_clinico.repository.MascotaRepository;
import com.example.microservicio_clinico.repository.VacunaRepository;
import com.example.microservicio_clinico.dto.MascotaVacunaInput;
import com.example.microservicio_clinico.dto.MascotaVacunaUpdateInput;
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
public class MascotaVacunaService {
    
    private final MascotaVacunaRepository mascotaVacunaRepository;
    private final MascotaRepository mascotaRepository;
    private final VacunaRepository vacunaRepository;
    
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    
    public MascotaVacuna create(MascotaVacunaInput input) {
        log.info("Aplicando vacuna con input: {}", input);
        
        // Buscar la mascota
        Long mascotaId = Long.parseLong(input.getMascotaId());
        Mascota mascota = mascotaRepository.findById(mascotaId)
            .orElseThrow(() -> new RuntimeException("Mascota no encontrada con ID: " + mascotaId));
        log.info("Mascota encontrada: {}", mascota.getNombre());
        
        // Buscar la vacuna
        Long vacunaId = Long.parseLong(input.getVacunaId());
        Vacuna vacuna = vacunaRepository.findById(vacunaId)
            .orElseThrow(() -> new RuntimeException("Vacuna no encontrada con ID: " + vacunaId));
        log.info("Vacuna encontrada: {}", vacuna.getNombre());
        
        MascotaVacuna mascotaVacuna = new MascotaVacuna();
        mascotaVacuna.setFechaAplicacion(LocalDateTime.parse(input.getFechaAplicacion(), dateTimeFormatter));
        
        if (input.getFechaProximaDosis() != null && !input.getFechaProximaDosis().isEmpty()) {
            mascotaVacuna.setFechaProximaDosis(LocalDateTime.parse(input.getFechaProximaDosis(), dateTimeFormatter));
        } else if (vacuna.getDuracionMeses() != null) {
            // Calcular próxima dosis automáticamente basada en la duración de la vacuna
            LocalDateTime proximaDosis = mascotaVacuna.getFechaAplicacion().plusMonths(vacuna.getDuracionMeses());
            mascotaVacuna.setFechaProximaDosis(proximaDosis);
        }
        
        mascotaVacuna.setVeterinario(input.getVeterinario());
        mascotaVacuna.setObservaciones(input.getObservaciones());
        mascotaVacuna.setLote(input.getLote());
        mascotaVacuna.setMascota(mascota);
        mascotaVacuna.setVacuna(vacuna);
        
        MascotaVacuna savedMascotaVacuna = mascotaVacunaRepository.save(mascotaVacuna);
        log.info("Vacuna aplicada exitosamente con ID: {}", savedMascotaVacuna.getId());
        
        return savedMascotaVacuna;
    }
    
    public MascotaVacuna update(MascotaVacunaUpdateInput input) {
        log.info("Actualizando aplicación de vacuna con input: {}", input);
        
        Long id = Long.parseLong(input.getId());
        Optional<MascotaVacuna> existingMascotaVacuna = mascotaVacunaRepository.findById(id);
        
        if (existingMascotaVacuna.isEmpty()) {
            throw new RuntimeException("Aplicación de vacuna no encontrada con ID: " + id);
        }
        
        MascotaVacuna mascotaVacuna = existingMascotaVacuna.get();
        
        if (input.getFechaAplicacion() != null) {
            mascotaVacuna.setFechaAplicacion(LocalDateTime.parse(input.getFechaAplicacion(), dateTimeFormatter));
        }
        
        if (input.getFechaProximaDosis() != null && !input.getFechaProximaDosis().isEmpty()) {
            mascotaVacuna.setFechaProximaDosis(LocalDateTime.parse(input.getFechaProximaDosis(), dateTimeFormatter));
        }
        
        if (input.getVeterinario() != null) {
            mascotaVacuna.setVeterinario(input.getVeterinario());
        }
        
        if (input.getObservaciones() != null) {
            mascotaVacuna.setObservaciones(input.getObservaciones());
        }
        
        if (input.getLote() != null) {
            mascotaVacuna.setLote(input.getLote());
        }
        
        if (input.getMascotaId() != null) {
            Long mascotaId = Long.parseLong(input.getMascotaId());
            Mascota mascota = mascotaRepository.findById(mascotaId)
                .orElseThrow(() -> new RuntimeException("Mascota no encontrada con ID: " + mascotaId));
            mascotaVacuna.setMascota(mascota);
        }
        
        if (input.getVacunaId() != null) {
            Long vacunaId = Long.parseLong(input.getVacunaId());
            Vacuna vacuna = vacunaRepository.findById(vacunaId)
                .orElseThrow(() -> new RuntimeException("Vacuna no encontrada con ID: " + vacunaId));
            mascotaVacuna.setVacuna(vacuna);
        }
        
        MascotaVacuna updatedMascotaVacuna = mascotaVacunaRepository.save(mascotaVacuna);
        log.info("Aplicación de vacuna actualizada exitosamente: {}", updatedMascotaVacuna.getId());
        
        return updatedMascotaVacuna;
    }
    
    public Boolean delete(Long id) {
        log.info("Eliminando aplicación de vacuna con ID: {}", id);
        
        if (!mascotaVacunaRepository.existsById(id)) {
            throw new RuntimeException("Aplicación de vacuna no encontrada con ID: " + id);
        }
        
        mascotaVacunaRepository.deleteById(id);
        log.info("Aplicación de vacuna eliminada exitosamente: {}", id);
        
        return true;
    }
    
    public List<MascotaVacuna> findAll() {
        log.info("Obteniendo todas las aplicaciones de vacunas");
        return mascotaVacunaRepository.findAll();
    }
    
    public Optional<MascotaVacuna> findById(Long id) {
        log.info("Buscando aplicación de vacuna con ID: {}", id);
        return mascotaVacunaRepository.findById(id);
    }
    
    public List<MascotaVacuna> findByMascotaId(Long mascotaId) {
        log.info("Buscando vacunas por mascota ID: {}", mascotaId);
        return mascotaVacunaRepository.findByMascotaIdOrderByFechaAplicacionDesc(mascotaId);
    }
    
    public List<MascotaVacuna> findByVacunaId(Long vacunaId) {
        log.info("Buscando aplicaciones por vacuna ID: {}", vacunaId);
        return mascotaVacunaRepository.findByVacunaId(vacunaId);
    }
    
    public List<MascotaVacuna> findVacunasPorVencer(LocalDateTime fecha) {
        log.info("Buscando vacunas que vencen antes de: {}", fecha);
        return mascotaVacunaRepository.findVacunasPorVencer(fecha);
    }
}