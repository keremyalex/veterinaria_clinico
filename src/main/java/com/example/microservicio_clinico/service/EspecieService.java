package com.example.microservicio_clinico.service;

import com.example.microservicio_clinico.dto.*;
import com.example.microservicio_clinico.entity.Especie;
import com.example.microservicio_clinico.repository.EspecieRepository;
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
public class EspecieService {
    
    private final EspecieRepository especieRepository;
    
    // Crear nueva especie
    public EspecieOutput crearEspecie(EspecieInput input) {
        log.info("Creando nueva especie: {}", input.getDescripcion());
        
        // Validar que la descripción no exista
        if (especieRepository.findByDescripcion(input.getDescripcion()).isPresent()) {
            throw new RuntimeException("Ya existe una especie con la descripción: " + input.getDescripcion());
        }
        
        Especie especie = new Especie();
        especie.setDescripcion(input.getDescripcion());
        
        Especie savedEspecie = especieRepository.save(especie);
        log.info("Especie creada exitosamente con ID: {}", savedEspecie.getId());
        
        return convertirAOutput(savedEspecie);
    }
    
    // Actualizar especie
    public EspecieOutput actualizarEspecie(EspecieUpdateInput input) {
        log.info("Actualizando especie con ID: {}", input.getId());
        
        Especie especie = especieRepository.findById(input.getId())
            .orElseThrow(() -> new RuntimeException("Especie no encontrada con ID: " + input.getId()));
        
        // Validar descripción única si se está cambiando
        if (input.getDescripcion() != null && !input.getDescripcion().equals(especie.getDescripcion())) {
            if (especieRepository.existsByDescripcionAndIdNot(input.getDescripcion(), input.getId())) {
                throw new RuntimeException("Ya existe otra especie con la descripción: " + input.getDescripcion());
            }
            especie.setDescripcion(input.getDescripcion());
        }
        
        Especie updatedEspecie = especieRepository.save(especie);
        log.info("Especie actualizada exitosamente con ID: {}", updatedEspecie.getId());
        
        return convertirAOutput(updatedEspecie);
    }
    
    // Obtener especie por ID
    @Transactional(readOnly = true)
    public EspecieOutput obtenerEspeciePorId(Integer id) {
        log.info("Obteniendo especie por ID: {}", id);
        
        Especie especie = especieRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Especie no encontrada con ID: " + id));
        
        return convertirAOutput(especie);
    }
    
    // Obtener todas las especies
    @Transactional(readOnly = true)
    public List<EspecieOutput> obtenerTodasLasEspecies() {
        log.info("Obteniendo todas las especies");
        
        return especieRepository.findAll()
            .stream()
            .map(this::convertirAOutput)
            .collect(Collectors.toList());
    }
    
    // Buscar especies por descripción
    @Transactional(readOnly = true)
    public List<EspecieOutput> buscarEspeciesPorDescripcion(String termino) {
        log.info("Buscando especies por término: {}", termino);
        
        return especieRepository.findByDescripcionContainingIgnoreCase(termino)
            .stream()
            .map(this::convertirAOutput)
            .collect(Collectors.toList());
    }
    
    // Obtener especie por descripción exacta
    @Transactional(readOnly = true)
    public Optional<EspecieOutput> obtenerEspeciePorDescripcion(String descripcion) {
        log.info("Obteniendo especie por descripción: {}", descripcion);
        
        return especieRepository.findByDescripcion(descripcion)
            .map(this::convertirAOutput);
    }
    
    // Eliminar especie
    public boolean eliminarEspecie(Integer id) {
        log.info("Eliminando especie con ID: {}", id);
        
        if (!especieRepository.existsById(id)) {
            throw new RuntimeException("Especie no encontrada con ID: " + id);
        }
        
        especieRepository.deleteById(id);
        log.info("Especie eliminada exitosamente con ID: {}", id);
        
        return true;
    }
    
    // Método privado para convertir Entity a DTO
    private EspecieOutput convertirAOutput(Especie especie) {
        EspecieOutput output = new EspecieOutput();
        output.setId(especie.getId());
        output.setDescripcion(especie.getDescripcion());
        return output;
    }
}
