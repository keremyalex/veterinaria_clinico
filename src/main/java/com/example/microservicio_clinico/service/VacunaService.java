package com.example.microservicio_clinico.service;

import com.example.microservicio_clinico.entity.Vacuna;
import com.example.microservicio_clinico.repository.VacunaRepository;
import com.example.microservicio_clinico.dto.VacunaInput;
import com.example.microservicio_clinico.dto.VacunaUpdateInput;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class VacunaService {
    
    private final VacunaRepository vacunaRepository;
    
    public Vacuna create(VacunaInput input) {
        log.info("Creando vacuna con input: {}", input);
        
        // Verificar que no exista una vacuna con el mismo nombre
        Optional<Vacuna> existingVacuna = vacunaRepository.findByNombre(input.getNombre());
        if (existingVacuna.isPresent()) {
            throw new RuntimeException("Ya existe una vacuna con el nombre: " + input.getNombre());
        }
        
        Vacuna vacuna = new Vacuna();
        vacuna.setNombre(input.getNombre());
        vacuna.setDescripcion(input.getDescripcion());
        vacuna.setDuracionMeses(input.getDuracionMeses());
        vacuna.setLaboratorio(input.getLaboratorio());
        vacuna.setEdadMinimaDias(input.getEdadMinimaDias());
        
        Vacuna savedVacuna = vacunaRepository.save(vacuna);
        log.info("Vacuna creada exitosamente con ID: {}", savedVacuna.getId());
        
        return savedVacuna;
    }
    
    public Vacuna update(VacunaUpdateInput input) {
        log.info("Actualizando vacuna con input: {}", input);
        
        Long id = Long.parseLong(input.getId());
        Optional<Vacuna> existingVacuna = vacunaRepository.findById(id);
        
        if (existingVacuna.isEmpty()) {
            throw new RuntimeException("Vacuna no encontrada con ID: " + id);
        }
        
        Vacuna vacuna = existingVacuna.get();
        
        if (input.getNombre() != null) {
            // Verificar que no exista otra vacuna con el mismo nombre
            Optional<Vacuna> duplicateVacuna = vacunaRepository.findByNombre(input.getNombre());
            if (duplicateVacuna.isPresent() && !duplicateVacuna.get().getId().equals(id)) {
                throw new RuntimeException("Ya existe otra vacuna con el nombre: " + input.getNombre());
            }
            vacuna.setNombre(input.getNombre());
        }
        
        if (input.getDescripcion() != null) {
            vacuna.setDescripcion(input.getDescripcion());
        }
        
        if (input.getDuracionMeses() != null) {
            vacuna.setDuracionMeses(input.getDuracionMeses());
        }
        
        if (input.getLaboratorio() != null) {
            vacuna.setLaboratorio(input.getLaboratorio());
        }
        
        if (input.getEdadMinimaDias() != null) {
            vacuna.setEdadMinimaDias(input.getEdadMinimaDias());
        }
        
        Vacuna updatedVacuna = vacunaRepository.save(vacuna);
        log.info("Vacuna actualizada exitosamente: {}", updatedVacuna.getId());
        
        return updatedVacuna;
    }
    
    public Boolean delete(Long id) {
        log.info("Eliminando vacuna con ID: {}", id);
        
        if (!vacunaRepository.existsById(id)) {
            throw new RuntimeException("Vacuna no encontrada con ID: " + id);
        }
        
        vacunaRepository.deleteById(id);
        log.info("Vacuna eliminada exitosamente: {}", id);
        
        return true;
    }
    
    public List<Vacuna> findAll() {
        log.info("Obteniendo todas las vacunas");
        return vacunaRepository.findAll();
    }
    
    public Optional<Vacuna> findById(Long id) {
        log.info("Buscando vacuna con ID: {}", id);
        return vacunaRepository.findById(id);
    }
    
    public Optional<Vacuna> findByNombre(String nombre) {
        log.info("Buscando vacuna por nombre: {}", nombre);
        return vacunaRepository.findByNombre(nombre);
    }
    
    public List<Vacuna> findByLaboratorio(String laboratorio) {
        log.info("Buscando vacunas por laboratorio: {}", laboratorio);
        return vacunaRepository.findByLaboratorio(laboratorio);
    }
    
    public List<Vacuna> findByNombreContaining(String nombre) {
        log.info("Buscando vacunas que contengan: {}", nombre);
        return vacunaRepository.findByNombreContainingIgnoreCase(nombre);
    }
    
    public List<Vacuna> searchByNombre(String nombre) {
        log.info("Buscando vacunas que contengan: {}", nombre);
        return vacunaRepository.findByNombreContainingIgnoreCase(nombre);
    }
}