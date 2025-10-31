package com.example.microservicio_clinico.service;

import com.example.microservicio_clinico.entity.Horario;
import com.example.microservicio_clinico.repository.HorarioRepository;
import com.example.microservicio_clinico.dto.HorarioInput;
import com.example.microservicio_clinico.dto.HorarioUpdateInput;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class HorarioService {
    
    private final HorarioRepository horarioRepository;
    private final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
    
    public Horario create(HorarioInput input) {
        log.info("Creando horario con input: {}", input);
        
        Horario horario = new Horario();
        horario.setDia(input.getDia());
        horario.setHoraInicio(LocalTime.parse(input.getHoraInicio(), timeFormatter));
        horario.setHoraFin(LocalTime.parse(input.getHoraFin(), timeFormatter));
        
        Horario savedHorario = horarioRepository.save(horario);
        log.info("Horario creado exitosamente con ID: {}", savedHorario.getId());
        
        return savedHorario;
    }
    
    public Horario update(HorarioUpdateInput input) {
        log.info("Actualizando horario con input: {}", input);
        
        Long id = Long.parseLong(input.getId());
        Optional<Horario> existingHorario = horarioRepository.findById(id);
        
        if (existingHorario.isEmpty()) {
            throw new RuntimeException("Horario no encontrado con ID: " + id);
        }
        
        Horario horario = existingHorario.get();
        horario.setDia(input.getDia());
        horario.setHoraInicio(LocalTime.parse(input.getHoraInicio(), timeFormatter));
        horario.setHoraFin(LocalTime.parse(input.getHoraFin(), timeFormatter));
        
        Horario updatedHorario = horarioRepository.save(horario);
        log.info("Horario actualizado exitosamente: {}", updatedHorario.getId());
        
        return updatedHorario;
    }
    
    public Boolean delete(Long id) {
        log.info("Eliminando horario con ID: {}", id);
        
        if (!horarioRepository.existsById(id)) {
            throw new RuntimeException("Horario no encontrado con ID: " + id);
        }
        
        horarioRepository.deleteById(id);
        log.info("Horario eliminado exitosamente: {}", id);
        
        return true;
    }
    
    public List<Horario> findAll() {
        log.info("Obteniendo todos los horarios");
        return horarioRepository.findAll();
    }
    
    public Optional<Horario> findById(Long id) {
        log.info("Buscando horario con ID: {}", id);
        return horarioRepository.findById(id);
    }
    
    public List<Horario> findByDia(String dia) {
        log.info("Buscando horarios por día: {}", dia);
        return horarioRepository.findByDia(dia);
    }
}