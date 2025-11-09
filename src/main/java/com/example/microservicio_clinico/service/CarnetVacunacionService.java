package com.example.microservicio_clinico.service;

import com.example.microservicio_clinico.dto.*;
import com.example.microservicio_clinico.entity.CarnetVacunacion;
import com.example.microservicio_clinico.entity.Mascota;
import com.example.microservicio_clinico.repository.CarnetVacunacionRepository;
import com.example.microservicio_clinico.repository.MascotaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class CarnetVacunacionService {
    
    private final CarnetVacunacionRepository carnetVacunacionRepository;
    private final MascotaRepository mascotaRepository;
    private final MascotaService mascotaService;
    
    private static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
    
    // Crear nuevo carnet de vacunación
    public CarnetVacunacionOutput crearCarnetVacunacion(CarnetVacunacionInput input) {
        log.info("Creando nuevo carnet de vacunación para mascota ID: {}", input.getMascotaId());
        
        // Validar que la mascota exista
        Mascota mascota = mascotaRepository.findById(input.getMascotaId())
            .orElseThrow(() -> new RuntimeException("Mascota no encontrada con ID: " + input.getMascotaId()));
        
        // Validar que la mascota no tenga ya un carnet de vacunación
        if (carnetVacunacionRepository.existsByMascotaId(input.getMascotaId())) {
            throw new RuntimeException("La mascota ya tiene un carnet de vacunación");
        }
        
        CarnetVacunacion carnet = new CarnetVacunacion();
        carnet.setFechaemision(convertirStringALocalDateTime(input.getFechaemision()));
        carnet.setMascota(mascota);
        
        CarnetVacunacion savedCarnet = carnetVacunacionRepository.save(carnet);
        log.info("Carnet de vacunación creado exitosamente con ID: {}", savedCarnet.getId());
        
        return convertirAOutput(savedCarnet);
    }
    
    // Actualizar carnet de vacunación
    public CarnetVacunacionOutput actualizarCarnetVacunacion(CarnetVacunacionUpdateInput input) {
        log.info("Actualizando carnet de vacunación con ID: {}", input.getId());
        
        CarnetVacunacion carnet = carnetVacunacionRepository.findById(input.getId())
            .orElseThrow(() -> new RuntimeException("Carnet de vacunación no encontrado con ID: " + input.getId()));
        
        // Actualizar mascota si se proporciona
        if (input.getMascotaId() != null && !input.getMascotaId().equals(carnet.getMascota().getId())) {
            Mascota mascota = mascotaRepository.findById(input.getMascotaId())
                .orElseThrow(() -> new RuntimeException("Mascota no encontrada con ID: " + input.getMascotaId()));
            carnet.setMascota(mascota);
        }
        
        // Actualizar fecha de emisión si se proporciona
        if (input.getFechaemision() != null) carnet.setFechaemision(convertirStringALocalDateTime(input.getFechaemision()));
        
        CarnetVacunacion updatedCarnet = carnetVacunacionRepository.save(carnet);
        log.info("Carnet de vacunación actualizado exitosamente con ID: {}", updatedCarnet.getId());
        
        return convertirAOutput(updatedCarnet);
    }
    
    // Obtener carnet de vacunación por ID
    @Transactional(readOnly = true)
    public CarnetVacunacionOutput obtenerCarnetVacunacionPorId(Integer id) {
        log.info("Obteniendo carnet de vacunación por ID: {}", id);
        
        CarnetVacunacion carnet = carnetVacunacionRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Carnet de vacunación no encontrado con ID: " + id));
        
        return convertirAOutput(carnet);
    }
    
    // Obtener todos los carnets de vacunación
    @Transactional(readOnly = true)
    public List<CarnetVacunacionOutput> obtenerTodosLosCarnets() {
        log.info("Obteniendo todos los carnets de vacunación");
        
        return carnetVacunacionRepository.findAll()
            .stream()
            .map(this::convertirAOutput)
            .collect(Collectors.toList());
    }
    
    // Obtener carnet de vacunación por mascota
    @Transactional(readOnly = true)
    public Optional<CarnetVacunacionOutput> obtenerCarnetPorMascota(Integer mascotaId) {
        log.info("Obteniendo carnet de vacunación de la mascota ID: {}", mascotaId);
        
        return carnetVacunacionRepository.findByMascotaId(mascotaId)
            .map(this::convertirAOutput);
    }
    
    // Obtener carnets por cliente
    @Transactional(readOnly = true)
    public List<CarnetVacunacionOutput> obtenerCarnetsPorCliente(Integer clienteId) {
        log.info("Obteniendo carnets de vacunación del cliente ID: {}", clienteId);
        
        return carnetVacunacionRepository.findByClienteId(clienteId)
            .stream()
            .map(this::convertirAOutput)
            .collect(Collectors.toList());
    }
    
    // Obtener carnets por especie
    @Transactional(readOnly = true)
    public List<CarnetVacunacionOutput> obtenerCarnetsPorEspecie(Integer especieId) {
        log.info("Obteniendo carnets de vacunación de la especie ID: {}", especieId);
        
        return carnetVacunacionRepository.findByEspecieId(especieId)
            .stream()
            .map(this::convertirAOutput)
            .collect(Collectors.toList());
    }
    
    // Obtener carnets por rango de fechas de emisión
    @Transactional(readOnly = true)
    public List<CarnetVacunacionOutput> obtenerCarnetsPorRangoFechas(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        log.info("Obteniendo carnets de vacunación entre {} y {}", fechaInicio, fechaFin);
        
        return carnetVacunacionRepository.findByFechaemisionBetween(fechaInicio, fechaFin)
            .stream()
            .map(this::convertirAOutput)
            .collect(Collectors.toList());
    }
    
    // Verificar si una mascota tiene carnet
    @Transactional(readOnly = true)
    public boolean mascotaTieneCarnet(Integer mascotaId) {
        log.info("Verificando si la mascota ID: {} tiene carnet de vacunación", mascotaId);
        
        return carnetVacunacionRepository.existsByMascotaId(mascotaId);
    }
    
    // Eliminar carnet de vacunación
    public boolean eliminarCarnetVacunacion(Integer id) {
        log.info("Eliminando carnet de vacunación con ID: {}", id);
        
        if (!carnetVacunacionRepository.existsById(id)) {
            throw new RuntimeException("Carnet de vacunación no encontrado con ID: " + id);
        }
        
        carnetVacunacionRepository.deleteById(id);
        log.info("Carnet de vacunación eliminado exitosamente con ID: {}", id);
        
        return true;
    }
    
    // Método privado para convertir Entity a DTO
    private CarnetVacunacionOutput convertirAOutput(CarnetVacunacion carnet) {
        CarnetVacunacionOutput output = new CarnetVacunacionOutput();
        output.setId(carnet.getId());
        output.setFechaemision(convertirLocalDateTimeAString(carnet.getFechaemision()));
        
        // Convertir mascota
        output.setMascota(mascotaService.obtenerMascotaPorId(carnet.getMascota().getId()));
        
        // Los detalles de vacunación se cargarán por separado cuando sea necesario
        
        return output;
    }
    
    // Método helper para convertir String a LocalDateTime
    private LocalDateTime convertirStringALocalDateTime(String fechaStr) {
        if (fechaStr == null || fechaStr.trim().isEmpty()) {
            throw new RuntimeException("La fecha no puede estar vacía");
        }
        
        String fechaNormalizada = fechaStr.trim();
        
        try {
            // Si es solo una fecha (YYYY-MM-DD), agregar tiempo por defecto
            if (fechaNormalizada.matches("\\d{4}-\\d{2}-\\d{2}")) {
                fechaNormalizada += "T00:00:00";
                return LocalDateTime.parse(fechaNormalizada, DATETIME_FORMATTER);
            }
            
            // Si ya tiene tiempo, intentar parsear directamente
            if (fechaNormalizada.contains("T")) {
                return LocalDateTime.parse(fechaNormalizada, DATETIME_FORMATTER);
            }
            
        } catch (DateTimeParseException e) {
            log.error("Error al parsear la fecha: {}", fechaStr);
            throw new RuntimeException("Formato de fecha inválido: " + fechaStr + 
                                     ". Use formato YYYY-MM-DD o YYYY-MM-DDTHH:mm:ss", e);
        }
        
        log.error("Error al parsear la fecha después de todos los intentos: {}", fechaStr);
        throw new RuntimeException("Formato de fecha inválido: " + fechaStr + 
                                 ". Use formato YYYY-MM-DD o YYYY-MM-DDTHH:mm:ss");
    }
    
    // Método helper para convertir LocalDateTime a String
    private String convertirLocalDateTimeAString(LocalDateTime fechaDateTime) {
        return fechaDateTime != null ? fechaDateTime.format(DATETIME_FORMATTER) : null;
    }
}
