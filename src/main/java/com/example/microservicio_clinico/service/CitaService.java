package com.example.microservicio_clinico.service;

import com.example.microservicio_clinico.dto.*;
import com.example.microservicio_clinico.entity.Cita;
import com.example.microservicio_clinico.entity.Doctor;
import com.example.microservicio_clinico.entity.Mascota;
import com.example.microservicio_clinico.entity.BloqueHorario;
import com.example.microservicio_clinico.repository.CitaRepository;
import com.example.microservicio_clinico.repository.DoctorRepository;
import com.example.microservicio_clinico.repository.MascotaRepository;
import com.example.microservicio_clinico.repository.BloqueHorarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class CitaService {
    
    private final CitaRepository citaRepository;
    private final DoctorRepository doctorRepository;
    private final MascotaRepository mascotaRepository;
    private final BloqueHorarioRepository bloqueHorarioRepository;
    private final DoctorService doctorService;
    private final MascotaService mascotaService;
    
    private static final DateTimeFormatter[] DATETIME_FORMATTERS = {
        DateTimeFormatter.ISO_LOCAL_DATE_TIME,
        DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'"),
        DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"),
        DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX"),
        DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    };
    
    // Crear nueva cita
    public CitaOutput crearCita(CitaInput input) {
        log.info("Creando nueva cita para mascota ID: {} con doctor ID: {}", 
                input.getMascotaId(), input.getDoctorId());
        
        // Convertir strings a LocalDateTime
        LocalDateTime fechaCreacion = convertirStringALocalDateTime(input.getFechacreacion());
        LocalDateTime fechaReserva = convertirStringALocalDateTime(input.getFechareserva());
        
        // Validar que el doctor exista
        Doctor doctor = doctorRepository.findById(input.getDoctorId())
            .orElseThrow(() -> new RuntimeException("Doctor no encontrado con ID: " + input.getDoctorId()));
        
        // Validar que la mascota exista
        Mascota mascota = mascotaRepository.findById(input.getMascotaId())
            .orElseThrow(() -> new RuntimeException("Mascota no encontrada con ID: " + input.getMascotaId()));
        
        // Validar disponibilidad del doctor en esa fecha/hora
        Long citasExistentes = citaRepository.countByDoctorAndFechareservaAndEstadoNot(
            input.getDoctorId(), 
            fechaReserva
        );
        
        if (citasExistentes > 0) {
            throw new RuntimeException("El doctor ya tiene una cita programada en esa fecha y hora");
        }
        
        // Validar que la fecha de reserva sea en el futuro
        if (fechaReserva.isBefore(LocalDateTime.now())) {
            throw new RuntimeException("La fecha de reserva debe ser en el futuro");
        }
        
        // Validar y obtener bloque horario si se proporciona
        BloqueHorario bloqueHorario = null;
        if (input.getBloqueHorarioId() != null) {
            bloqueHorario = bloqueHorarioRepository.findById(input.getBloqueHorarioId())
                .orElseThrow(() -> new RuntimeException("Bloque horario no encontrado con ID: " + input.getBloqueHorarioId()));
        }
        
        Cita cita = new Cita();
        cita.setFechacreacion(fechaCreacion);
        cita.setMotivo(input.getMotivo());
        cita.setFechareserva(fechaReserva);
        cita.setEstado(input.getEstado());
        cita.setDoctor(doctor);
        cita.setMascota(mascota);
        cita.setBloqueHorario(bloqueHorario);
        
        Cita savedCita = citaRepository.save(cita);
        log.info("Cita creada exitosamente con ID: {}", savedCita.getId());
        
        return convertirAOutput(savedCita);
    }
    
    // Actualizar cita
    public CitaOutput actualizarCita(CitaUpdateInput input) {
        log.info("Actualizando cita con ID: {}", input.getId());
        
        Cita cita = citaRepository.findById(input.getId())
            .orElseThrow(() -> new RuntimeException("Cita no encontrada con ID: " + input.getId()));
        
        // Actualizar doctor si se proporciona
        if (input.getDoctorId() != null && !input.getDoctorId().equals(cita.getDoctor().getId())) {
            Doctor doctor = doctorRepository.findById(input.getDoctorId())
                .orElseThrow(() -> new RuntimeException("Doctor no encontrado con ID: " + input.getDoctorId()));
            cita.setDoctor(doctor);
        }
        
        // Actualizar mascota si se proporciona
        if (input.getMascotaId() != null && !input.getMascotaId().equals(cita.getMascota().getId())) {
            Mascota mascota = mascotaRepository.findById(input.getMascotaId())
                .orElseThrow(() -> new RuntimeException("Mascota no encontrada con ID: " + input.getMascotaId()));
            cita.setMascota(mascota);
        }
        
        // Actualizar bloque horario si se proporciona
        if (input.getBloqueHorarioId() != null) {
            BloqueHorario bloqueHorario = bloqueHorarioRepository.findById(input.getBloqueHorarioId())
                .orElseThrow(() -> new RuntimeException("Bloque horario no encontrado con ID: " + input.getBloqueHorarioId()));
            cita.setBloqueHorario(bloqueHorario);
        }
        
        // Actualizar otros campos si se proporcionan
        if (input.getFechacreacion() != null) {
            LocalDateTime fechaCreacion = convertirStringALocalDateTime(input.getFechacreacion());
            cita.setFechacreacion(fechaCreacion);
        }
        if (input.getFechareserva() != null) {
            LocalDateTime fechaReserva = convertirStringALocalDateTime(input.getFechareserva());
            // Validar disponibilidad si se cambia la fecha
            if (!fechaReserva.equals(cita.getFechareserva())) {
                Long citasExistentes = citaRepository.countByDoctorAndFechareservaAndEstadoNot(
                    cita.getDoctor().getId(), 
                    fechaReserva
                );
                if (citasExistentes > 0) {
                    throw new RuntimeException("El doctor ya tiene una cita programada en esa fecha y hora");
                }
            }
            cita.setFechareserva(fechaReserva);
        }
        if (input.getMotivo() != null) cita.setMotivo(input.getMotivo());
        if (input.getEstado() != null) cita.setEstado(input.getEstado());
        
        Cita updatedCita = citaRepository.save(cita);
        log.info("Cita actualizada exitosamente con ID: {}", updatedCita.getId());
        
        return convertirAOutput(updatedCita);
    }
    
    // Obtener cita por ID
    @Transactional(readOnly = true)
    public CitaOutput obtenerCitaPorId(Integer id) {
        log.info("Obteniendo cita por ID: {}", id);
        
        Cita cita = citaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Cita no encontrada con ID: " + id));
        
        return convertirAOutput(cita);
    }
    
    // Obtener todas las citas
    @Transactional(readOnly = true)
    public List<CitaOutput> obtenerTodasLasCitas() {
        log.info("Obteniendo todas las citas");
        
        return citaRepository.findAll()
            .stream()
            .map(this::convertirAOutput)
            .collect(Collectors.toList());
    }
    
    // Obtener citas por doctor
    @Transactional(readOnly = true)
    public List<CitaOutput> obtenerCitasPorDoctor(Integer doctorId) {
        log.info("Obteniendo citas del doctor ID: {}", doctorId);
        
        return citaRepository.findByDoctorId(doctorId)
            .stream()
            .map(this::convertirAOutput)
            .collect(Collectors.toList());
    }
    
    // Obtener citas por mascota
    @Transactional(readOnly = true)
    public List<CitaOutput> obtenerCitasPorMascota(Integer mascotaId) {
        log.info("Obteniendo citas de la mascota ID: {}", mascotaId);
        
        return citaRepository.findByMascotaId(mascotaId)
            .stream()
            .map(this::convertirAOutput)
            .collect(Collectors.toList());
    }
    
    // Obtener citas por estado
    @Transactional(readOnly = true)
    public List<CitaOutput> obtenerCitasPorEstado(Integer estado) {
        log.info("Obteniendo citas con estado: {}", estado);
        
        return citaRepository.findByEstado(estado)
            .stream()
            .map(this::convertirAOutput)
            .collect(Collectors.toList());
    }
    
    // Obtener citas por rango de fechas
    @Transactional(readOnly = true)
    public List<CitaOutput> obtenerCitasPorRangoFechas(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        log.info("Obteniendo citas entre {} y {}", fechaInicio, fechaFin);
        
        return citaRepository.findByFechareservaBetween(fechaInicio, fechaFin)
            .stream()
            .map(this::convertirAOutput)
            .collect(Collectors.toList());
    }
    
    // Obtener citas de un doctor en un rango de fechas
    @Transactional(readOnly = true)
    public List<CitaOutput> obtenerCitasPorDoctorYFechas(Integer doctorId, LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        log.info("Obteniendo citas del doctor ID: {} entre {} y {}", doctorId, fechaInicio, fechaFin);
        
        return citaRepository.findByDoctorIdAndFechareservaBetween(doctorId, fechaInicio, fechaFin)
            .stream()
            .map(this::convertirAOutput)
            .collect(Collectors.toList());
    }
    
    // Buscar citas por motivo
    @Transactional(readOnly = true)
    public List<CitaOutput> buscarCitasPorMotivo(String motivo) {
        log.info("Buscando citas por motivo: {}", motivo);
        
        return citaRepository.findByMotivoContainingIgnoreCase(motivo)
            .stream()
            .map(this::convertirAOutput)
            .collect(Collectors.toList());
    }
    
    // Eliminar cita
    public boolean eliminarCita(Integer id) {
        log.info("Eliminando cita con ID: {}", id);
        
        if (!citaRepository.existsById(id)) {
            throw new RuntimeException("Cita no encontrada con ID: " + id);
        }
        
        citaRepository.deleteById(id);
        log.info("Cita eliminada exitosamente con ID: {}", id);
        
        return true;
    }
    
    // Método privado para convertir Entity a DTO
    private CitaOutput convertirAOutput(Cita cita) {
        CitaOutput output = new CitaOutput();
        output.setId(cita.getId());
        output.setFechacreacion(cita.getFechacreacion());
        output.setMotivo(cita.getMotivo());
        output.setFechareserva(cita.getFechareserva());
        output.setEstado(cita.getEstado());
        output.setEstadoNombre(obtenerNombreEstado(cita.getEstado()));
        
        // Convertir doctor y mascota
        output.setDoctor(doctorService.obtenerDoctorPorId(cita.getDoctor().getId()));
        output.setMascota(mascotaService.obtenerMascotaPorId(cita.getMascota().getId()));
        
        // Convertir bloque horario si existe
        if (cita.getBloqueHorario() != null) {
            BloqueHorarioOutput bloqueOutput = new BloqueHorarioOutput();
            bloqueOutput.setId(cita.getBloqueHorario().getId());
            bloqueOutput.setDiasemana(cita.getBloqueHorario().getDiasemana());
            bloqueOutput.setDiasemanaNombre(obtenerNombreDia(cita.getBloqueHorario().getDiasemana()));
            bloqueOutput.setHorainicio(cita.getBloqueHorario().getHorainicio().format(java.time.format.DateTimeFormatter.ofPattern("HH:mm")));
            bloqueOutput.setHorafinal(cita.getBloqueHorario().getHorafinal().format(java.time.format.DateTimeFormatter.ofPattern("HH:mm")));
            bloqueOutput.setActivo(cita.getBloqueHorario().getActivo());
            output.setBloqueHorario(bloqueOutput);
        }
        
        return output;
    }
    
    // Método helper para obtener nombre del estado
    private String obtenerNombreEstado(Integer estado) {
        return switch (estado) {
            case 1 -> "Programada";
            case 2 -> "En Progreso";
            case 3 -> "Completada";
            case 4 -> "Cancelada";
            case 5 -> "No Asistió";
            default -> "Desconocido";
        };
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
    
    // Método helper para convertir String a LocalDateTime
    private LocalDateTime convertirStringALocalDateTime(String fechaString) {
        if (fechaString == null || fechaString.trim().isEmpty()) {
            throw new RuntimeException("La fecha no puede estar vacía");
        }
        
        // Normalizar la fecha (agregar Z si no tiene zona horaria)
        String fechaNormalizada = fechaString.trim();
        if (!fechaNormalizada.endsWith("Z") && !fechaNormalizada.contains("+") && fechaNormalizada.indexOf("-", 10) == -1) {
            fechaNormalizada += "Z";
        }
        
        for (DateTimeFormatter formatter : DATETIME_FORMATTERS) {
            try {
                // Si termina en Z, parsear como Instant y convertir a LocalDateTime
                if (fechaNormalizada.endsWith("Z")) {
                    return java.time.Instant.parse(fechaNormalizada).atZone(java.time.ZoneId.systemDefault()).toLocalDateTime();
                } else {
                    return LocalDateTime.parse(fechaNormalizada, formatter);
                }
            } catch (DateTimeParseException e) {
                // Intentar con el siguiente formatter
            }
        }
        
        log.error("Error al parsear la fecha: {}", fechaString);
        throw new RuntimeException("Formato de fecha inválido: " + fechaString + 
                                 ". Use formato ISO 8601: YYYY-MM-DDTHH:mm:ss[.sss][Z]");
    }
}
