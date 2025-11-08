package com.example.microservicio_clinico.service;

import com.example.microservicio_clinico.entity.Cita;
import com.example.microservicio_clinico.entity.Cliente;
import com.example.microservicio_clinico.entity.Doctor;
import com.example.microservicio_clinico.entity.Mascota;
import com.example.microservicio_clinico.repository.CitaRepository;
import com.example.microservicio_clinico.repository.ClienteRepository;
import com.example.microservicio_clinico.repository.DoctorRepository;
import com.example.microservicio_clinico.repository.MascotaRepository;
import com.example.microservicio_clinico.dto.CitaInputDTO;
import com.example.microservicio_clinico.dto.CitaUpdateDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class CitaService {
    
    private final CitaRepository citaRepository;
    private final ClienteRepository clienteRepository;
    private final DoctorRepository doctorRepository;
    private final MascotaRepository mascotaRepository;
    
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    
    public Cita create(CitaInputDTO input) {
        log.info("Creando cita con input: {}", input);
        
        // Buscar las entidades relacionadas
        Cliente cliente = clienteRepository.findById(input.getClienteId())
            .orElseThrow(() -> new RuntimeException("Cliente no encontrado con ID: " + input.getClienteId()));
        log.info("Cliente encontrado: {}", cliente.getNombre());
        
        Doctor doctor = doctorRepository.findById(input.getDoctorId())
            .orElseThrow(() -> new RuntimeException("Doctor no encontrado con ID: " + input.getDoctorId()));
        log.info("Doctor encontrado: {} {}", doctor.getNombre(), doctor.getApellido());
        
        Mascota mascota = mascotaRepository.findById(input.getMascotaId())
            .orElseThrow(() -> new RuntimeException("Mascota no encontrada con ID: " + input.getMascotaId()));
        log.info("Mascota encontrada: {}", mascota.getNombre());
        
        // Validar que la mascota pertenece al cliente
        if (!mascota.getCliente().getId().equals(input.getClienteId())) {
            throw new RuntimeException("La mascota no pertenece al cliente especificado");
        }
        
        Cita cita = new Cita();
        cita.setMotivo(input.getMotivo());
        cita.setFechaProgramada(input.getFechaProgramada());
        cita.setFechaReservacion(LocalDateTime.now());
        cita.setEstado(input.getEstado() != null ? input.getEstado() : "PROGRAMADA");
        cita.setObservaciones(input.getObservaciones());
        cita.setCliente(cliente);
        cita.setDoctor(doctor);
        cita.setMascota(mascota);
        
        Cita savedCita = citaRepository.save(cita);
        log.info("Cita creada exitosamente con ID: {}", savedCita.getId());
        
        return savedCita;
    }
    
    public Cita update(Long id, CitaUpdateDTO input) {
        log.info("Actualizando cita con ID: {}, input: {}", id, input);
        
        Optional<Cita> existingCita = citaRepository.findById(id);
        
        if (existingCita.isEmpty()) {
            throw new RuntimeException("Cita no encontrada con ID: " + id);
        }
        
        Cita cita = existingCita.get();
        
        // Actualizar las entidades relacionadas si se proporcionan
        if (input.getClienteId() != null) {
            Cliente cliente = clienteRepository.findById(input.getClienteId())
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con ID: " + input.getClienteId()));
            cita.setCliente(cliente);
        }
        
        if (input.getDoctorId() != null) {
            Doctor doctor = doctorRepository.findById(input.getDoctorId())
                .orElseThrow(() -> new RuntimeException("Doctor no encontrado con ID: " + input.getDoctorId()));
            cita.setDoctor(doctor);
        }
        
        if (input.getMascotaId() != null) {
            Mascota mascota = mascotaRepository.findById(input.getMascotaId())
                .orElseThrow(() -> new RuntimeException("Mascota no encontrada con ID: " + input.getMascotaId()));
            cita.setMascota(mascota);
        }
        
        if (input.getMotivo() != null) {
            cita.setMotivo(input.getMotivo());
        }
        
        if (input.getFechaProgramada() != null) {
            cita.setFechaProgramada(input.getFechaProgramada());
        }
        
        if (input.getEstado() != null) {
            cita.setEstado(input.getEstado());
        }
        
        if (input.getObservaciones() != null) {
            cita.setObservaciones(input.getObservaciones());
        }
        
        Cita updatedCita = citaRepository.save(cita);
        log.info("Cita actualizada exitosamente: {}", updatedCita.getId());
        
        return updatedCita;
    }
    
    public Boolean delete(Long id) {
        log.info("Eliminando cita con ID: {}", id);
        
        if (!citaRepository.existsById(id)) {
            throw new RuntimeException("Cita no encontrada con ID: " + id);
        }
        
        citaRepository.deleteById(id);
        log.info("Cita eliminada exitosamente: {}", id);
        
        return true;
    }
    
    public List<Cita> findAll() {
        log.info("Obteniendo todas las citas");
        return citaRepository.findAll();
    }
    
    public Optional<Cita> findById(Long id) {
        log.info("Buscando cita con ID: {}", id);
        return citaRepository.findById(id);
    }
    
    public List<Cita> findByClienteId(Long clienteId) {
        log.info("Buscando citas por cliente ID: {}", clienteId);
        return citaRepository.findByClienteId(clienteId);
    }
    
    public List<Cita> findByMascotaId(Long mascotaId) {
        log.info("Buscando citas por mascota ID: {}", mascotaId);
        return citaRepository.findByMascotaId(mascotaId);
    }
    
    public List<Cita> findByHorarioId(Long horarioId) {
        log.info("Buscando citas por horario ID: {}", horarioId);
        return citaRepository.findByHorarioId(horarioId);
    }
}