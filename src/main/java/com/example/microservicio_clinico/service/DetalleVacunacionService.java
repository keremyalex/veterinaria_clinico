package com.example.microservicio_clinico.service;

import com.example.microservicio_clinico.entity.DetalleVacunacion;
import com.example.microservicio_clinico.entity.CarnetVacunacion;
import com.example.microservicio_clinico.entity.Vacuna;
import com.example.microservicio_clinico.repository.DetalleVacunacionRepository;
import com.example.microservicio_clinico.repository.CarnetVacunacionRepository;
import com.example.microservicio_clinico.repository.VacunaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class DetalleVacunacionService {
    
    private final DetalleVacunacionRepository detalleVacunacionRepository;
    private final CarnetVacunacionRepository carnetVacunacionRepository;
    private final VacunaRepository vacunaRepository;
    
    public List<DetalleVacunacion> findAll() {
        return detalleVacunacionRepository.findAll();
    }
    
    public Optional<DetalleVacunacion> findById(Long id) {
        return detalleVacunacionRepository.findById(id);
    }
    
    public List<DetalleVacunacion> findByCarnetVacunacionId(Long carnetId) {
        return detalleVacunacionRepository.findByCarnetVacunacionId(carnetId);
    }
    
    public List<DetalleVacunacion> findByVacunaId(Long vacunaId) {
        return detalleVacunacionRepository.findByVacunaId(vacunaId);
    }
    
    public List<DetalleVacunacion> findByMascotaId(Long mascotaId) {
        return detalleVacunacionRepository.findByMascotaIdOrderByFechaDesc(mascotaId);
    }
    
    public List<DetalleVacunacion> findByClienteId(Long clienteId) {
        return detalleVacunacionRepository.findByClienteIdOrderByFechaDesc(clienteId);
    }
    
    public List<DetalleVacunacion> findByCarnetAndVacuna(Long carnetId, Long vacunaId) {
        return detalleVacunacionRepository.findByCarnetAndVacunaOrderByFechaDesc(carnetId, vacunaId);
    }
    
    public List<DetalleVacunacion> findByFechaProximaDosis(LocalDate fecha) {
        return detalleVacunacionRepository.findByFechaProximaDosis(fecha);
    }
    
    public List<DetalleVacunacion> findByFechaProximaDosisBetween(LocalDate fechaInicio, LocalDate fechaFin) {
        return detalleVacunacionRepository.findByFechaProximaDosisBetween(fechaInicio, fechaFin);
    }
    
    public List<DetalleVacunacion> findByFechaAplicacionBetween(LocalDate fechaInicio, LocalDate fechaFin) {
        return detalleVacunacionRepository.findByFechaAplicacionBetween(fechaInicio, fechaFin);
    }
    
    public List<DetalleVacunacion> findByLote(String lote) {
        return detalleVacunacionRepository.findByLote(lote);
    }
    
    public List<DetalleVacunacion> buscarPorVeterinario(String veterinario) {
        return detalleVacunacionRepository.findByVeterinarioAplicadorContaining(veterinario);
    }
    
    public List<DetalleVacunacion> findVacunasProximasAVencer(int diasAnticipacion) {
        LocalDate fechaInicio = LocalDate.now();
        LocalDate fechaFin = LocalDate.now().plusDays(diasAnticipacion);
        return findByFechaProximaDosisBetween(fechaInicio, fechaFin);
    }
    
    public DetalleVacunacion save(DetalleVacunacion detalleVacunacion) {
        return detalleVacunacionRepository.save(detalleVacunacion);
    }
    
    public DetalleVacunacion create(DetalleVacunacion detalleVacunacion) {
        // Validar que el carnet de vacunación existe
        if (detalleVacunacion.getCarnetVacunacion() == null || 
            detalleVacunacion.getCarnetVacunacion().getId() == null) {
            throw new RuntimeException("Debe especificar un carnet de vacunación válido");
        }
        
        CarnetVacunacion carnet = carnetVacunacionRepository.findById(detalleVacunacion.getCarnetVacunacion().getId())
            .orElseThrow(() -> new RuntimeException("Carnet de vacunación no encontrado con ID: " + 
                detalleVacunacion.getCarnetVacunacion().getId()));
        
        // Validar que la vacuna existe
        if (detalleVacunacion.getVacuna() == null || detalleVacunacion.getVacuna().getId() == null) {
            throw new RuntimeException("Debe especificar una vacuna válida");
        }
        
        Vacuna vacuna = vacunaRepository.findById(detalleVacunacion.getVacuna().getId())
            .orElseThrow(() -> new RuntimeException("Vacuna no encontrada con ID: " + 
                detalleVacunacion.getVacuna().getId()));
        
        // Validar fecha de aplicación
        if (detalleVacunacion.getFechaAplicacion() == null) {
            throw new RuntimeException("La fecha de aplicación es obligatoria");
        }
        
        if (detalleVacunacion.getFechaAplicacion().isAfter(LocalDate.now())) {
            throw new RuntimeException("La fecha de aplicación no puede ser futura");
        }
        
        // Validar fecha próxima dosis
        if (detalleVacunacion.getFechaProximaDosis() != null && 
            detalleVacunacion.getFechaProximaDosis().isBefore(detalleVacunacion.getFechaAplicacion())) {
            throw new RuntimeException("La fecha de próxima dosis no puede ser anterior a la fecha de aplicación");
        }
        
        // Validar número de dosis
        if (detalleVacunacion.getNumeroDosis() != null && detalleVacunacion.getNumeroDosis() <= 0) {
            throw new RuntimeException("El número de dosis debe ser mayor a 0");
        }
        
        // Calcular fecha próxima dosis automáticamente si no se especifica y la vacuna tiene duración
        if (detalleVacunacion.getFechaProximaDosis() == null && vacuna.getDuracionInmunidad() != null) {
            detalleVacunacion.setFechaProximaDosis(
                detalleVacunacion.getFechaAplicacion().plusDays(vacuna.getDuracionInmunidad())
            );
        }
        
        detalleVacunacion.setCarnetVacunacion(carnet);
        detalleVacunacion.setVacuna(vacuna);
        
        return detalleVacunacionRepository.save(detalleVacunacion);
    }
    
    public DetalleVacunacion update(Long id, DetalleVacunacion detalleActualizado) {
        DetalleVacunacion detalleExistente = detalleVacunacionRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Detalle de vacunación no encontrado con ID: " + id));
        
        // No permitir cambiar el carnet de vacunación
        if (detalleActualizado.getCarnetVacunacion() != null && 
            detalleActualizado.getCarnetVacunacion().getId() != null &&
            !detalleActualizado.getCarnetVacunacion().getId().equals(detalleExistente.getCarnetVacunacion().getId())) {
            throw new RuntimeException("No se puede cambiar el carnet de vacunación de un detalle existente");
        }
        
        // Validar vacuna si se cambió
        if (detalleActualizado.getVacuna() != null && 
            detalleActualizado.getVacuna().getId() != null &&
            !detalleActualizado.getVacuna().getId().equals(detalleExistente.getVacuna().getId())) {
            
            Vacuna nuevaVacuna = vacunaRepository.findById(detalleActualizado.getVacuna().getId())
                .orElseThrow(() -> new RuntimeException("Vacuna no encontrada con ID: " + 
                    detalleActualizado.getVacuna().getId()));
            
            detalleExistente.setVacuna(nuevaVacuna);
        }
        
        // Validar y actualizar fechas
        if (detalleActualizado.getFechaAplicacion() != null) {
            if (detalleActualizado.getFechaAplicacion().isAfter(LocalDate.now())) {
                throw new RuntimeException("La fecha de aplicación no puede ser futura");
            }
            detalleExistente.setFechaAplicacion(detalleActualizado.getFechaAplicacion());
        }
        
        if (detalleActualizado.getFechaProximaDosis() != null) {
            if (detalleActualizado.getFechaProximaDosis().isBefore(detalleExistente.getFechaAplicacion())) {
                throw new RuntimeException("La fecha de próxima dosis no puede ser anterior a la fecha de aplicación");
            }
            detalleExistente.setFechaProximaDosis(detalleActualizado.getFechaProximaDosis());
        }
        
        // Actualizar otros campos
        if (detalleActualizado.getNumeroDosis() != null) {
            if (detalleActualizado.getNumeroDosis() <= 0) {
                throw new RuntimeException("El número de dosis debe ser mayor a 0");
            }
            detalleExistente.setNumeroDosis(detalleActualizado.getNumeroDosis());
        }
        
        if (detalleActualizado.getLote() != null) {
            detalleExistente.setLote(detalleActualizado.getLote());
        }
        
        if (detalleActualizado.getVeterinarioAplicador() != null) {
            detalleExistente.setVeterinarioAplicador(detalleActualizado.getVeterinarioAplicador());
        }
        
        if (detalleActualizado.getObservaciones() != null) {
            detalleExistente.setObservaciones(detalleActualizado.getObservaciones());
        }
        
        return detalleVacunacionRepository.save(detalleExistente);
    }
    
    public void deleteById(Long id) {
        if (!detalleVacunacionRepository.existsById(id)) {
            throw new RuntimeException("Detalle de vacunación no encontrado con ID: " + id);
        }
        detalleVacunacionRepository.deleteById(id);
    }
    
    public boolean existsById(Long id) {
        return detalleVacunacionRepository.existsById(id);
    }
}