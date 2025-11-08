package com.example.microservicio_clinico.service;

import com.example.microservicio_clinico.entity.CarnetVacunacion;
import com.example.microservicio_clinico.entity.Mascota;
import com.example.microservicio_clinico.repository.CarnetVacunacionRepository;
import com.example.microservicio_clinico.repository.MascotaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CarnetVacunacionService {
    
    private final CarnetVacunacionRepository carnetVacunacionRepository;
    private final MascotaRepository mascotaRepository;
    
    public List<CarnetVacunacion> findAll() {
        return carnetVacunacionRepository.findAll();
    }
    
    public Optional<CarnetVacunacion> findById(Long id) {
        return carnetVacunacionRepository.findById(id);
    }
    
    public Optional<CarnetVacunacion> findByMascotaId(Long mascotaId) {
        return carnetVacunacionRepository.findByMascotaId(mascotaId);
    }
    
    public List<CarnetVacunacion> findByClienteId(Long clienteId) {
        return carnetVacunacionRepository.findByClienteId(clienteId);
    }
    
    public List<CarnetVacunacion> findByFechaCreacion(LocalDate fechaCreacion) {
        return carnetVacunacionRepository.findByFechaCreacion(fechaCreacion);
    }
    
    public List<CarnetVacunacion> findByFechaCreacionBetween(LocalDate fechaInicio, LocalDate fechaFin) {
        return carnetVacunacionRepository.findByFechaCreacionBetween(fechaInicio, fechaFin);
    }
    
    public List<CarnetVacunacion> buscarPorNombreMascota(String nombre) {
        return carnetVacunacionRepository.findByMascotaNombreContaining(nombre);
    }
    
    public boolean existsByMascotaId(Long mascotaId) {
        return carnetVacunacionRepository.existsByMascotaId(mascotaId);
    }
    
    public long contarPorClienteId(Long clienteId) {
        return carnetVacunacionRepository.countByClienteId(clienteId);
    }
    
    public CarnetVacunacion save(CarnetVacunacion carnetVacunacion) {
        return carnetVacunacionRepository.save(carnetVacunacion);
    }
    
    public CarnetVacunacion create(CarnetVacunacion carnetVacunacion) {
        // Validar que la mascota existe
        if (carnetVacunacion.getMascota() == null || carnetVacunacion.getMascota().getId() == null) {
            throw new RuntimeException("Debe especificar una mascota válida para el carnet de vacunación");
        }
        
        Mascota mascota = mascotaRepository.findById(carnetVacunacion.getMascota().getId())
            .orElseThrow(() -> new RuntimeException("Mascota no encontrada con ID: " + carnetVacunacion.getMascota().getId()));
        
        // Validar que no existe ya un carnet para esta mascota
        if (existsByMascotaId(mascota.getId())) {
            throw new RuntimeException("Ya existe un carnet de vacunación para la mascota con ID: " + mascota.getId());
        }
        
        // Establecer fecha de creación si no se especifica
        if (carnetVacunacion.getFechaCreacion() == null) {
            carnetVacunacion.setFechaCreacion(LocalDate.now());
        }
        
        carnetVacunacion.setMascota(mascota);
        return carnetVacunacionRepository.save(carnetVacunacion);
    }
    
    public CarnetVacunacion update(Long id, CarnetVacunacion carnetActualizado) {
        CarnetVacunacion carnetExistente = carnetVacunacionRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Carnet de vacunación no encontrado con ID: " + id));
        
        // No permitir cambiar la mascota de un carnet existente
        if (carnetActualizado.getMascota() != null && 
            carnetActualizado.getMascota().getId() != null &&
            !carnetActualizado.getMascota().getId().equals(carnetExistente.getMascota().getId())) {
            throw new RuntimeException("No se puede cambiar la mascota de un carnet de vacunación existente");
        }
        
        // Actualizar campos permitidos
        if (carnetActualizado.getFechaCreacion() != null) {
            carnetExistente.setFechaCreacion(carnetActualizado.getFechaCreacion());
        }
        
        if (carnetActualizado.getObservaciones() != null) {
            carnetExistente.setObservaciones(carnetActualizado.getObservaciones());
        }
        
        return carnetVacunacionRepository.save(carnetExistente);
    }
    
    public void deleteById(Long id) {
        if (!carnetVacunacionRepository.existsById(id)) {
            throw new RuntimeException("Carnet de vacunación no encontrado con ID: " + id);
        }
        carnetVacunacionRepository.deleteById(id);
    }
    
    public boolean existsById(Long id) {
        return carnetVacunacionRepository.existsById(id);
    }
    
    public CarnetVacunacion getOrCreateCarnetForMascota(Long mascotaId) {
        return findByMascotaId(mascotaId)
            .orElseGet(() -> {
                Mascota mascota = mascotaRepository.findById(mascotaId)
                    .orElseThrow(() -> new RuntimeException("Mascota no encontrada con ID: " + mascotaId));
                
                CarnetVacunacion nuevoCarnet = new CarnetVacunacion();
                nuevoCarnet.setMascota(mascota);
                nuevoCarnet.setFechaCreacion(LocalDate.now());
                
                return carnetVacunacionRepository.save(nuevoCarnet);
            });
    }
}