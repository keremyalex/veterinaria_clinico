package com.example.microservicio_clinico.service;

import com.example.microservicio_clinico.dto.*;
import com.example.microservicio_clinico.entity.Diagnostico;
import com.example.microservicio_clinico.entity.Tratamiento;
import com.example.microservicio_clinico.repository.DiagnosticoRepository;
import com.example.microservicio_clinico.repository.TratamientoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class TratamientoService {
    
    private final TratamientoRepository tratamientoRepository;
    private final DiagnosticoRepository diagnosticoRepository;
    private final DiagnosticoService diagnosticoService;
    
    // Crear nuevo tratamiento
    public TratamientoOutput crearTratamiento(TratamientoInput input) {
        log.info("Creando nuevo tratamiento para diagnóstico ID: {}", input.getDiagnosticoId());
        
        // Validar que el diagnóstico exista
        Diagnostico diagnostico = diagnosticoRepository.findById(input.getDiagnosticoId())
            .orElseThrow(() -> new RuntimeException("Diagnóstico no encontrado con ID: " + input.getDiagnosticoId()));
        
        Tratamiento tratamiento = new Tratamiento();
        tratamiento.setNombre(input.getNombre());
        tratamiento.setDescripcion(input.getDescripcion());
        tratamiento.setObservaciones(input.getObservaciones());
        tratamiento.setDiagnostico(diagnostico);
        
        Tratamiento savedTratamiento = tratamientoRepository.save(tratamiento);
        log.info("Tratamiento creado exitosamente con ID: {}", savedTratamiento.getId());
        
        return convertirAOutput(savedTratamiento);
    }
    
    // Actualizar tratamiento
    public TratamientoOutput actualizarTratamiento(TratamientoUpdateInput input) {
        log.info("Actualizando tratamiento con ID: {}", input.getId());
        
        Tratamiento tratamiento = tratamientoRepository.findById(input.getId())
            .orElseThrow(() -> new RuntimeException("Tratamiento no encontrado con ID: " + input.getId()));
        
        // Actualizar diagnóstico si se proporciona
        if (input.getDiagnosticoId() != null && !input.getDiagnosticoId().equals(tratamiento.getDiagnostico().getId())) {
            Diagnostico diagnostico = diagnosticoRepository.findById(input.getDiagnosticoId())
                .orElseThrow(() -> new RuntimeException("Diagnóstico no encontrado con ID: " + input.getDiagnosticoId()));
            tratamiento.setDiagnostico(diagnostico);
        }
        
        // Actualizar otros campos si se proporcionan
        if (input.getNombre() != null) tratamiento.setNombre(input.getNombre());
        if (input.getDescripcion() != null) tratamiento.setDescripcion(input.getDescripcion());
        if (input.getObservaciones() != null) tratamiento.setObservaciones(input.getObservaciones());
        
        Tratamiento updatedTratamiento = tratamientoRepository.save(tratamiento);
        log.info("Tratamiento actualizado exitosamente con ID: {}", updatedTratamiento.getId());
        
        return convertirAOutput(updatedTratamiento);
    }
    
    // Obtener tratamiento por ID
    @Transactional(readOnly = true)
    public TratamientoOutput obtenerTratamientoPorId(Integer id) {
        log.info("Obteniendo tratamiento por ID: {}", id);
        
        Tratamiento tratamiento = tratamientoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Tratamiento no encontrado con ID: " + id));
        
        return convertirAOutput(tratamiento);
    }
    
    // Obtener todos los tratamientos
    @Transactional(readOnly = true)
    public List<TratamientoOutput> obtenerTodosLosTratamientos() {
        log.info("Obteniendo todos los tratamientos");
        
        return tratamientoRepository.findAll()
            .stream()
            .map(this::convertirAOutput)
            .collect(Collectors.toList());
    }
    
    // Obtener tratamientos por diagnóstico
    @Transactional(readOnly = true)
    public List<TratamientoOutput> obtenerTratamientosPorDiagnostico(Integer diagnosticoId) {
        log.info("Obteniendo tratamientos del diagnóstico ID: {}", diagnosticoId);
        
        return tratamientoRepository.findByDiagnosticoId(diagnosticoId)
            .stream()
            .map(this::convertirAOutput)
            .collect(Collectors.toList());
    }
    
    // Obtener tratamientos por doctor
    @Transactional(readOnly = true)
    public List<TratamientoOutput> obtenerTratamientosPorDoctor(Integer doctorId) {
        log.info("Obteniendo tratamientos del doctor ID: {}", doctorId);
        
        return tratamientoRepository.findByDoctorId(doctorId)
            .stream()
            .map(this::convertirAOutput)
            .collect(Collectors.toList());
    }
    
    // Obtener tratamientos por mascota
    @Transactional(readOnly = true)
    public List<TratamientoOutput> obtenerTratamientosPorMascota(Integer mascotaId) {
        log.info("Obteniendo tratamientos de la mascota ID: {}", mascotaId);
        
        return tratamientoRepository.findByMascotaId(mascotaId)
            .stream()
            .map(this::convertirAOutput)
            .collect(Collectors.toList());
    }
    
    // Obtener tratamientos por cita
    @Transactional(readOnly = true)
    public List<TratamientoOutput> obtenerTratamientosPorCita(Integer citaId) {
        log.info("Obteniendo tratamientos de la cita ID: {}", citaId);
        
        return tratamientoRepository.findByCitaId(citaId)
            .stream()
            .map(this::convertirAOutput)
            .collect(Collectors.toList());
    }
    
    // Buscar tratamientos por nombre
    @Transactional(readOnly = true)
    public List<TratamientoOutput> buscarTratamientosPorNombre(String nombre) {
        log.info("Buscando tratamientos por nombre: {}", nombre);
        
        return tratamientoRepository.findByNombreContainingIgnoreCase(nombre)
            .stream()
            .map(this::convertirAOutput)
            .collect(Collectors.toList());
    }
    
    // Buscar tratamientos por descripción
    @Transactional(readOnly = true)
    public List<TratamientoOutput> buscarTratamientosPorDescripcion(String descripcion) {
        log.info("Buscando tratamientos por descripción: {}", descripcion);
        
        return tratamientoRepository.findByDescripcionContainingIgnoreCase(descripcion)
            .stream()
            .map(this::convertirAOutput)
            .collect(Collectors.toList());
    }
    
    // Buscar tratamientos por observaciones
    @Transactional(readOnly = true)
    public List<TratamientoOutput> buscarTratamientosPorObservaciones(String observaciones) {
        log.info("Buscando tratamientos por observaciones: {}", observaciones);
        
        return tratamientoRepository.findByObservacionesContainingIgnoreCase(observaciones)
            .stream()
            .map(this::convertirAOutput)
            .collect(Collectors.toList());
    }
    
    // Eliminar tratamiento
    public boolean eliminarTratamiento(Integer id) {
        log.info("Eliminando tratamiento con ID: {}", id);
        
        if (!tratamientoRepository.existsById(id)) {
            throw new RuntimeException("Tratamiento no encontrado con ID: " + id);
        }
        
        tratamientoRepository.deleteById(id);
        log.info("Tratamiento eliminado exitosamente con ID: {}", id);
        
        return true;
    }
    
    // Método privado para convertir Entity a DTO
    private TratamientoOutput convertirAOutput(Tratamiento tratamiento) {
        TratamientoOutput output = new TratamientoOutput();
        output.setId(tratamiento.getId());
        output.setNombre(tratamiento.getNombre());
        output.setDescripcion(tratamiento.getDescripcion());
        output.setObservaciones(tratamiento.getObservaciones());
        
        // Convertir diagnóstico
        output.setDiagnostico(diagnosticoService.obtenerDiagnosticoPorId(tratamiento.getDiagnostico().getId()));
        
        return output;
    }
}
