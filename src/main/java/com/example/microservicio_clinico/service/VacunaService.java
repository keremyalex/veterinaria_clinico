package com.example.microservicio_clinico.service;

import com.example.microservicio_clinico.dto.*;
import com.example.microservicio_clinico.entity.Vacuna;
import com.example.microservicio_clinico.repository.VacunaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class VacunaService {
    
    private final VacunaRepository vacunaRepository;
    
    // Crear nueva vacuna
    public VacunaOutput crearVacuna(VacunaInput input) {
        log.info("Creando nueva vacuna: {}", input.getDescripcion());
        
        // Validar que la descripción no exista
        if (vacunaRepository.findByDescripcion(input.getDescripcion()).isPresent()) {
            throw new RuntimeException("Ya existe una vacuna con la descripción: " + input.getDescripcion());
        }
        
        Vacuna vacuna = new Vacuna();
        vacuna.setDescripcion(input.getDescripcion());
        
        Vacuna savedVacuna = vacunaRepository.save(vacuna);
        log.info("Vacuna creada exitosamente con ID: {}", savedVacuna.getId());
        
        return convertirAOutput(savedVacuna);
    }
    
    // Actualizar vacuna
    public VacunaOutput actualizarVacuna(VacunaUpdateInput input) {
        log.info("Actualizando vacuna con ID: {}", input.getId());
        
        Vacuna vacuna = vacunaRepository.findById(input.getId())
            .orElseThrow(() -> new RuntimeException("Vacuna no encontrada con ID: " + input.getId()));
        
        // Validar descripción única si se está cambiando
        if (input.getDescripcion() != null && !input.getDescripcion().equals(vacuna.getDescripcion())) {
            if (vacunaRepository.existsByDescripcionAndIdNot(input.getDescripcion(), input.getId())) {
                throw new RuntimeException("Ya existe otra vacuna con la descripción: " + input.getDescripcion());
            }
            vacuna.setDescripcion(input.getDescripcion());
        }
        
        Vacuna updatedVacuna = vacunaRepository.save(vacuna);
        log.info("Vacuna actualizada exitosamente con ID: {}", updatedVacuna.getId());
        
        return convertirAOutput(updatedVacuna);
    }
    
    // Obtener vacuna por ID
    @Transactional(readOnly = true)
    public VacunaOutput obtenerVacunaPorId(Integer id) {
        log.info("Obteniendo vacuna por ID: {}", id);
        
        Vacuna vacuna = vacunaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Vacuna no encontrada con ID: " + id));
        
        return convertirAOutput(vacuna);
    }
    
    // Obtener todas las vacunas
    @Transactional(readOnly = true)
    public List<VacunaOutput> obtenerTodasLasVacunas() {
        log.info("Obteniendo todas las vacunas");
        
        return vacunaRepository.findAllOrderByDescripcion()
            .stream()
            .map(this::convertirAOutput)
            .collect(Collectors.toList());
    }
    
    // Buscar vacunas por descripción
    @Transactional(readOnly = true)
    public List<VacunaOutput> buscarVacunasPorDescripcion(String termino) {
        log.info("Buscando vacunas por término: {}", termino);
        
        return vacunaRepository.findByDescripcionContainingIgnoreCase(termino)
            .stream()
            .map(this::convertirAOutput)
            .collect(Collectors.toList());
    }
    
    // Obtener vacuna por descripción exacta
    @Transactional(readOnly = true)
    public Optional<VacunaOutput> obtenerVacunaPorDescripcion(String descripcion) {
        log.info("Obteniendo vacuna por descripción: {}", descripcion);
        
        return vacunaRepository.findByDescripcion(descripcion)
            .map(this::convertirAOutput);
    }
    
    // Eliminar vacuna
    public boolean eliminarVacuna(Integer id) {
        log.info("Eliminando vacuna con ID: {}", id);
        
        if (!vacunaRepository.existsById(id)) {
            throw new RuntimeException("Vacuna no encontrada con ID: " + id);
        }
        
        vacunaRepository.deleteById(id);
        log.info("Vacuna eliminada exitosamente con ID: {}", id);
        
        return true;
    }
    
    // Método privado para convertir Entity a DTO
    private VacunaOutput convertirAOutput(Vacuna vacuna) {
        VacunaOutput output = new VacunaOutput();
        output.setId(vacuna.getId());
        output.setDescripcion(vacuna.getDescripcion());
        return output;
    }
}
