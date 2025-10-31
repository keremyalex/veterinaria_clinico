package com.example.microservicio_clinico.service;

import com.example.microservicio_clinico.entity.Especie;
import com.example.microservicio_clinico.repository.EspecieRepository;
import com.example.microservicio_clinico.dto.EspecieInput;
import com.example.microservicio_clinico.dto.EspecieUpdateInput;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class EspecieService {
    
    private final EspecieRepository especieRepository;
    
    public List<Especie> findAll() {
        return especieRepository.findAll();
    }
    
    public Optional<Especie> findById(Long id) {
        return especieRepository.findById(id);
    }
    
    public Optional<Especie> findByDescripcion(String descripcion) {
        return especieRepository.findByDescripcion(descripcion);
    }
    
    public Especie create(EspecieInput input) {
        if (especieRepository.existsByDescripcion(input.getDescripcion())) {
            throw new RuntimeException("Ya existe una especie con la descripción: " + input.getDescripcion());
        }
        
        Especie especie = new Especie(input.getDescripcion());
        return especieRepository.save(especie);
    }
    
    public Especie update(EspecieUpdateInput input) {
        Long longId = Long.parseLong(input.getId());
        Especie especie = especieRepository.findById(longId)
            .orElseThrow(() -> new RuntimeException("Especie no encontrada con ID: " + input.getId()));
        
        // Verificar si la nueva descripción ya existe en otra especie
        Optional<Especie> existingEspecie = especieRepository.findByDescripcion(input.getDescripcion());
        if (existingEspecie.isPresent() && !existingEspecie.get().getId().equals(longId)) {
            throw new RuntimeException("Ya existe otra especie con la descripción: " + input.getDescripcion());
        }
        
        especie.setDescripcion(input.getDescripcion());
        return especieRepository.save(especie);
    }
    
    public boolean delete(Long id) {
        if (!especieRepository.existsById(id)) {
            throw new RuntimeException("Especie no encontrada con ID: " + id);
        }
        
        especieRepository.deleteById(id);
        return true;
    }
}