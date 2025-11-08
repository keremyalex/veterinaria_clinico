package com.example.microservicio_clinico.service;

import com.example.microservicio_clinico.dto.*;
import com.example.microservicio_clinico.entity.BloqueHorario;
import com.example.microservicio_clinico.entity.Doctor;
import com.example.microservicio_clinico.repository.BloqueHorarioRepository;
import com.example.microservicio_clinico.repository.DoctorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class BloqueHorarioService {
    
    private final BloqueHorarioRepository bloqueHorarioRepository;
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");
    
    // Crear nuevo bloque horario
    public BloqueHorarioOutput crearBloqueHorario(BloqueHorarioInput input) {
        log.info("Creando nuevo bloque horario para el día {}", input.getDiasemana());
        
        // Convertir strings a LocalTime
        LocalTime horaInicio = convertirStringALocalTime(input.getHorainicio());
        LocalTime horaFinal = convertirStringALocalTime(input.getHorafinal());
        
        // Validar que la hora final sea posterior a la inicial
        if (!horaFinal.isAfter(horaInicio)) {
            throw new RuntimeException("La hora final debe ser posterior a la hora inicial");
        }
        
        BloqueHorario bloqueHorario = new BloqueHorario();
        bloqueHorario.setDiasemana(input.getDiasemana());
        bloqueHorario.setHorainicio(horaInicio);
        bloqueHorario.setHorafinal(horaFinal);
        bloqueHorario.setActivo(input.getActivo());
        
        BloqueHorario savedBloque = bloqueHorarioRepository.save(bloqueHorario);
        log.info("Bloque horario creado exitosamente con ID: {}", savedBloque.getId());
        
        return convertirAOutput(savedBloque);
    }
    
    // Actualizar bloque horario
    public BloqueHorarioOutput actualizarBloqueHorario(BloqueHorarioUpdateInput input) {
        log.info("Actualizando bloque horario con ID: {}", input.getId());
        
        BloqueHorario bloqueHorario = bloqueHorarioRepository.findById(input.getId())
            .orElseThrow(() -> new RuntimeException("Bloque horario no encontrado con ID: " + input.getId()));
        
        // Actualizar campos si se proporcionan
        if (input.getDiasemana() != null) bloqueHorario.setDiasemana(input.getDiasemana());
        if (input.getHorainicio() != null) bloqueHorario.setHorainicio(convertirStringALocalTime(input.getHorainicio()));
        if (input.getHorafinal() != null) bloqueHorario.setHorafinal(convertirStringALocalTime(input.getHorafinal()));
        if (input.getActivo() != null) bloqueHorario.setActivo(input.getActivo());
        
        // Validar horarios después de las actualizaciones
        if (!bloqueHorario.getHorafinal().isAfter(bloqueHorario.getHorainicio())) {
            throw new RuntimeException("La hora final debe ser posterior a la hora inicial");
        }
        
        BloqueHorario updatedBloque = bloqueHorarioRepository.save(bloqueHorario);
        log.info("Bloque horario actualizado exitosamente con ID: {}", updatedBloque.getId());
        
        return convertirAOutput(updatedBloque);
    }
    
    // Obtener bloque horario por ID
    @Transactional(readOnly = true)
    public BloqueHorarioOutput obtenerBloqueHorarioPorId(Integer id) {
        log.info("Obteniendo bloque horario por ID: {}", id);
        
        BloqueHorario bloqueHorario = bloqueHorarioRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Bloque horario no encontrado con ID: " + id));
        
        return convertirAOutput(bloqueHorario);
    }
    
    // Obtener todos los bloques horarios
    @Transactional(readOnly = true)
    public List<BloqueHorarioOutput> obtenerTodosLosBloques() {
        log.info("Obteniendo todos los bloques horarios");
        
        return bloqueHorarioRepository.findAll()
            .stream()
            .map(this::convertirAOutput)
            .collect(Collectors.toList());
    }
    
    // Obtener bloques horarios por día
    @Transactional(readOnly = true)
    public List<BloqueHorarioOutput> obtenerBloquesPorDia(Integer diasemana) {
        log.info("Obteniendo bloques horarios del día: {}", diasemana);
        
        return bloqueHorarioRepository.findByDiasemanaAndActivo(diasemana, 1)
            .stream()
            .map(this::convertirAOutput)
            .collect(Collectors.toList());
    }
    
    // Eliminar bloque horario
    public boolean eliminarBloqueHorario(Integer id) {
        log.info("Eliminando bloque horario con ID: {}", id);
        
        if (!bloqueHorarioRepository.existsById(id)) {
            throw new RuntimeException("Bloque horario no encontrado con ID: " + id);
        }
        
        bloqueHorarioRepository.deleteById(id);
        log.info("Bloque horario eliminado exitosamente con ID: {}", id);
        
        return true;
    }
    
    // Método privado para convertir Entity a DTO
    private BloqueHorarioOutput convertirAOutput(BloqueHorario bloqueHorario) {
        BloqueHorarioOutput output = new BloqueHorarioOutput();
        output.setId(bloqueHorario.getId());
        output.setDiasemana(bloqueHorario.getDiasemana());
        output.setDiasemanaNombre(obtenerNombreDia(bloqueHorario.getDiasemana()));
        output.setHorainicio(bloqueHorario.getHorainicio().format(TIME_FORMATTER));
        output.setHorafinal(bloqueHorario.getHorafinal().format(TIME_FORMATTER));
        output.setActivo(bloqueHorario.getActivo());
        
        return output;
    }
    
    // Método helper para obtener nombre del día
    private String obtenerNombreDia(Integer dia) {
        return switch (dia) {
            case 1 -> "Lunes";
            case 2 -> "Martes";
            case 3 -> "Miércoles";
            case 4 -> "Jueves";
            case 5 -> "Viernes";
            case 6 -> "Sábado";
            case 7 -> "Domingo";
            default -> "Desconocido";
        };
    }
    
    // Método helper para convertir String a LocalTime
    private LocalTime convertirStringALocalTime(String horaString) {
        try {
            return LocalTime.parse(horaString, TIME_FORMATTER);
        } catch (DateTimeParseException e) {
            log.error("Error al parsear la hora: {}", horaString);
            throw new RuntimeException("Formato de hora inválido: " + horaString + ". Use formato HH:MM");
        }
    }
}
