package com.example.microservicio_clinico.service;

import com.example.microservicio_clinico.dto.*;
import com.example.microservicio_clinico.entity.CarnetVacunacion;
import com.example.microservicio_clinico.entity.DetalleVacunacion;
import com.example.microservicio_clinico.entity.Vacuna;
import com.example.microservicio_clinico.repository.CarnetVacunacionRepository;
import com.example.microservicio_clinico.repository.DetalleVacunacionRepository;
import com.example.microservicio_clinico.repository.VacunaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class DetalleVacunacionService {
    
    private final DetalleVacunacionRepository detalleVacunacionRepository;
    private final CarnetVacunacionRepository carnetVacunacionRepository;
    private final VacunaRepository vacunaRepository;
    private final CarnetVacunacionService carnetVacunacionService;
    private final VacunaService vacunaService;
    
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    
    // Crear nuevo detalle de vacunación
    public DetalleVacunacionOutput crearDetalleVacunacion(DetalleVacunacionInput input) {
        log.info("Creando nuevo detalle de vacunación para carnet ID: {} y vacuna ID: {}", 
                input.getCarnetVacunacionId(), input.getVacunaId());
        
        // Validar que el carnet de vacunación exista
        CarnetVacunacion carnet = carnetVacunacionRepository.findById(input.getCarnetVacunacionId())
            .orElseThrow(() -> new RuntimeException("Carnet de vacunación no encontrado con ID: " + input.getCarnetVacunacionId()));
        
        // Validar que la vacuna exista
        Vacuna vacuna = vacunaRepository.findById(input.getVacunaId())
            .orElseThrow(() -> new RuntimeException("Vacuna no encontrada con ID: " + input.getVacunaId()));
        
        // Convertir fechas de string a LocalDate
        LocalDate fechaVacunacion = convertirStringALocalDate(input.getFechavacunacion());
        LocalDate proximaVacunacion = input.getProximavacunacion() != null ? 
            convertirStringALocalDate(input.getProximavacunacion()) : null;
        
        // Validar que la fecha de vacunación no sea futura
        if (fechaVacunacion.isAfter(LocalDate.now())) {
            throw new RuntimeException("La fecha de vacunación no puede ser en el futuro");
        }
        
        // Validar que la próxima vacunación sea posterior a la fecha de vacunación
        if (proximaVacunacion != null && proximaVacunacion.isBefore(fechaVacunacion)) {
            throw new RuntimeException("La fecha de próxima vacunación debe ser posterior a la fecha de vacunación");
        }
        
        DetalleVacunacion detalle = new DetalleVacunacion();
        detalle.setFechavacunacion(fechaVacunacion);
        detalle.setProximavacunacion(proximaVacunacion);
        detalle.setCarnetVacunacion(carnet);
        detalle.setVacuna(vacuna);
        
        DetalleVacunacion savedDetalle = detalleVacunacionRepository.save(detalle);
        log.info("Detalle de vacunación creado exitosamente con ID: {}", savedDetalle.getId());
        
        return convertirAOutput(savedDetalle);
    }
    
    // Actualizar detalle de vacunación
    public DetalleVacunacionOutput actualizarDetalleVacunacion(DetalleVacunacionUpdateInput input) {
        log.info("Actualizando detalle de vacunación con ID: {}", input.getId());
        
        DetalleVacunacion detalle = detalleVacunacionRepository.findById(input.getId())
            .orElseThrow(() -> new RuntimeException("Detalle de vacunación no encontrado con ID: " + input.getId()));
        
        // Actualizar carnet de vacunación si se proporciona
        if (input.getCarnetVacunacionId() != null && 
            !input.getCarnetVacunacionId().equals(detalle.getCarnetVacunacion().getId())) {
            CarnetVacunacion carnet = carnetVacunacionRepository.findById(input.getCarnetVacunacionId())
                .orElseThrow(() -> new RuntimeException("Carnet de vacunación no encontrado con ID: " + input.getCarnetVacunacionId()));
            detalle.setCarnetVacunacion(carnet);
        }
        
        // Actualizar vacuna si se proporciona
        if (input.getVacunaId() != null && !input.getVacunaId().equals(detalle.getVacuna().getId())) {
            Vacuna vacuna = vacunaRepository.findById(input.getVacunaId())
                .orElseThrow(() -> new RuntimeException("Vacuna no encontrada con ID: " + input.getVacunaId()));
            detalle.setVacuna(vacuna);
        }
        
        // Actualizar fechas si se proporcionan
        if (input.getFechavacunacion() != null) {
            LocalDate nuevaFechaVacunacion = convertirStringALocalDate(input.getFechavacunacion());
            if (nuevaFechaVacunacion.isAfter(LocalDate.now())) {
                throw new RuntimeException("La fecha de vacunación no puede ser en el futuro");
            }
            detalle.setFechavacunacion(nuevaFechaVacunacion);
        }
        
        if (input.getProximavacunacion() != null) {
            LocalDate nuevaProximaVacunacion = convertirStringALocalDate(input.getProximavacunacion());
            if (nuevaProximaVacunacion.isBefore(detalle.getFechavacunacion())) {
                throw new RuntimeException("La fecha de próxima vacunación debe ser posterior a la fecha de vacunación");
            }
            detalle.setProximavacunacion(nuevaProximaVacunacion);
        }
        
        DetalleVacunacion updatedDetalle = detalleVacunacionRepository.save(detalle);
        log.info("Detalle de vacunación actualizado exitosamente con ID: {}", updatedDetalle.getId());
        
        return convertirAOutput(updatedDetalle);
    }
    
    // Obtener detalle de vacunación por ID
    @Transactional(readOnly = true)
    public DetalleVacunacionOutput obtenerDetalleVacunacionPorId(Integer id) {
        log.info("Obteniendo detalle de vacunación por ID: {}", id);
        
        DetalleVacunacion detalle = detalleVacunacionRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Detalle de vacunación no encontrado con ID: " + id));
        
        return convertirAOutput(detalle);
    }
    
    // Obtener todos los detalles de vacunación
    @Transactional(readOnly = true)
    public List<DetalleVacunacionOutput> obtenerTodosLosDetalles() {
        log.info("Obteniendo todos los detalles de vacunación");
        
        return detalleVacunacionRepository.findAll()
            .stream()
            .map(this::convertirAOutput)
            .collect(Collectors.toList());
    }
    
    // Obtener detalles por carnet de vacunación
    @Transactional(readOnly = true)
    public List<DetalleVacunacionOutput> obtenerDetallesPorCarnet(Integer carnetVacunacionId) {
        log.info("Obteniendo detalles de vacunación del carnet ID: {}", carnetVacunacionId);
        
        return detalleVacunacionRepository.findByCarnetVacunacionId(carnetVacunacionId)
            .stream()
            .map(this::convertirAOutput)
            .collect(Collectors.toList());
    }
    
    // Obtener historial de vacunación por mascota
    @Transactional(readOnly = true)
    public List<DetalleVacunacionOutput> obtenerHistorialPorMascota(Integer mascotaId) {
        log.info("Obteniendo historial de vacunación de la mascota ID: {}", mascotaId);
        
        return detalleVacunacionRepository.findHistorialVacunacionByMascotaId(mascotaId)
            .stream()
            .map(this::convertirAOutput)
            .collect(Collectors.toList());
    }
    
    // Obtener detalles por mascota y vacuna específica
    @Transactional(readOnly = true)
    public List<DetalleVacunacionOutput> obtenerDetallesPorMascotaYVacuna(Integer mascotaId, Integer vacunaId) {
        log.info("Obteniendo detalles de vacunación de la mascota ID: {} para la vacuna ID: {}", mascotaId, vacunaId);
        
        return detalleVacunacionRepository.findByMascotaIdAndVacunaId(mascotaId, vacunaId)
            .stream()
            .map(this::convertirAOutput)
            .collect(Collectors.toList());
    }
    
    // Obtener vacunas vencidas
    @Transactional(readOnly = true)
    public List<VacunasVencidasOutput> obtenerVacunasVencidas() {
        log.info("Obteniendo vacunas vencidas");
        
        List<DetalleVacunacion> vencidas = detalleVacunacionRepository.findVacunacionesVencidas(LocalDate.now());
        
        return vencidas.stream()
            .collect(Collectors.groupingBy(d -> d.getCarnetVacunacion().getMascota()))
            .entrySet().stream()
            .map(entry -> {
                VacunasVencidasOutput output = new VacunasVencidasOutput();
                output.setMascotaId(entry.getKey().getId());
                output.setMascotaNombre(entry.getKey().getNombre());
                output.setClienteNombre(entry.getKey().getCliente().getNombre() + " " + 
                                       entry.getKey().getCliente().getApellido());
                output.setClienteTelefono(entry.getKey().getCliente().getTelefono());
                
                List<VacunasVencidasOutput.VacunaVencida> vacunasVencidas = entry.getValue().stream()
                    .map(detalle -> {
                        VacunasVencidasOutput.VacunaVencida vacunaVencida = new VacunasVencidasOutput.VacunaVencida();
                        vacunaVencida.setVacunaNombre(detalle.getVacuna().getDescripcion());
                        vacunaVencida.setFechaVencimiento(detalle.getProximavacunacion());
                        vacunaVencida.setDiasVencidos(Math.toIntExact(
                            ChronoUnit.DAYS.between(detalle.getProximavacunacion(), LocalDate.now())));
                        return vacunaVencida;
                    })
                    .collect(Collectors.toList());
                
                output.setVacunasVencidas(vacunasVencidas);
                return output;
            })
            .collect(Collectors.toList());
    }
    
    // Obtener detalles por rango de fechas
    @Transactional(readOnly = true)
    public List<DetalleVacunacionOutput> obtenerDetallesPorRangoFechas(LocalDate fechaInicio, LocalDate fechaFin) {
        log.info("Obteniendo detalles de vacunación entre {} y {}", fechaInicio, fechaFin);
        
        return detalleVacunacionRepository.findByFechavacunacionBetween(fechaInicio, fechaFin)
            .stream()
            .map(this::convertirAOutput)
            .collect(Collectors.toList());
    }
    
    // Obtener próximas vacunaciones
    @Transactional(readOnly = true)
    public List<DetalleVacunacionOutput> obtenerProximasVacunaciones(LocalDate fechaInicio, LocalDate fechaFin) {
        log.info("Obteniendo próximas vacunaciones entre {} y {}", fechaInicio, fechaFin);
        
        return detalleVacunacionRepository.findByProximavacunacionBetween(fechaInicio, fechaFin)
            .stream()
            .map(this::convertirAOutput)
            .collect(Collectors.toList());
    }
    
    // Eliminar detalle de vacunación
    public boolean eliminarDetalleVacunacion(Integer id) {
        log.info("Eliminando detalle de vacunación con ID: {}", id);
        
        if (!detalleVacunacionRepository.existsById(id)) {
            throw new RuntimeException("Detalle de vacunación no encontrado con ID: " + id);
        }
        
        detalleVacunacionRepository.deleteById(id);
        log.info("Detalle de vacunación eliminado exitosamente con ID: {}", id);
        
        return true;
    }
    
    // ========== MÉTODOS PARA QUERY RESOLVERS ==========
    
    // Obtener todos los detalles de vacunación (entidades) con relaciones cargadas
    @Transactional(readOnly = true)
    public List<DetalleVacunacion> obtenerTodos() {
        log.info("Obteniendo todas las entidades DetalleVacunacion con relaciones");
        return detalleVacunacionRepository.findAllWithRelations();
    }
    
    // Obtener detalle de vacunación por ID (entidad) con relaciones cargadas
    @Transactional(readOnly = true)
    public java.util.Optional<DetalleVacunacion> obtenerPorId(Long id) {
        log.info("Obteniendo entidad DetalleVacunacion por ID: {} con relaciones", id);
        return detalleVacunacionRepository.findByIdWithRelations(id.intValue());
    }
    
    // Obtener detalles de vacunación por carnet ID (entidades) con relaciones cargadas
    @Transactional(readOnly = true)
    public List<DetalleVacunacion> obtenerPorCarnetVacunacionId(Long carnetVacunacionId) {
        log.info("Obteniendo entidades DetalleVacunacion por carnet ID: {} con relaciones", carnetVacunacionId);
        return detalleVacunacionRepository.findByCarnetVacunacionIdWithRelations(carnetVacunacionId.intValue());
    }
    
    // Obtener detalles de vacunación por mascota ID (entidades) con relaciones cargadas
    @Transactional(readOnly = true)
    public List<DetalleVacunacion> obtenerPorMascotaId(Long mascotaId) {
        log.info("Obteniendo entidades DetalleVacunacion por mascota ID: {} con relaciones", mascotaId);
        return detalleVacunacionRepository.findByMascotaIdWithRelations(mascotaId.intValue());
    }
    
    // Obtener detalles de vacunación por vacuna ID (entidades) con relaciones cargadas
    @Transactional(readOnly = true)
    public List<DetalleVacunacion> obtenerPorVacunaId(Long vacunaId) {
        log.info("Obteniendo entidades DetalleVacunacion por vacuna ID: {} con relaciones", vacunaId);
        return detalleVacunacionRepository.findByVacunaIdWithRelations(vacunaId.intValue());
    }
    
    // Método privado para convertir Entity a DTO
    private DetalleVacunacionOutput convertirAOutput(DetalleVacunacion detalle) {
        DetalleVacunacionOutput output = new DetalleVacunacionOutput();
        output.setId(detalle.getId());
        output.setFechavacunacion(convertirLocalDateAString(detalle.getFechavacunacion()));
        output.setProximavacunacion(convertirLocalDateAString(detalle.getProximavacunacion()));
        
        // Convertir carnet y vacuna
        output.setCarnetVacunacion(carnetVacunacionService.obtenerCarnetVacunacionPorId(detalle.getCarnetVacunacion().getId()));
        output.setVacuna(vacunaService.obtenerVacunaPorId(detalle.getVacuna().getId()));
        
        return output;
    }
    
    // Método helper para convertir String a LocalDate
    private LocalDate convertirStringALocalDate(String fechaStr) {
        try {
            return LocalDate.parse(fechaStr, DATE_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new RuntimeException("Formato de fecha inválido. Use yyyy-MM-dd. Valor recibido: " + fechaStr, e);
        }
    }
    
    // Método helper para convertir LocalDate a String
    private String convertirLocalDateAString(LocalDate fecha) {
        return fecha != null ? fecha.format(DATE_FORMATTER) : null;
    }
}
