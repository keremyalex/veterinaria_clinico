package com.example.microservicio_clinico.service;

import com.example.microservicio_clinico.entity.Cita;
import com.example.microservicio_clinico.entity.Cliente;
import com.example.microservicio_clinico.entity.Horario;
import com.example.microservicio_clinico.entity.Mascota;
import com.example.microservicio_clinico.repository.CitaRepository;
import com.example.microservicio_clinico.repository.ClienteRepository;
import com.example.microservicio_clinico.repository.HorarioRepository;
import com.example.microservicio_clinico.repository.MascotaRepository;
import com.example.microservicio_clinico.dto.CitaInput;
import com.example.microservicio_clinico.dto.CitaUpdateInput;
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
    private final HorarioRepository horarioRepository;
    private final MascotaRepository mascotaRepository;
    
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    
    public Cita create(CitaInput input) {
        log.info("Creando cita con input: {}", input);
        
        // Buscar las entidades relacionadas
        Long clienteId = Long.parseLong(input.getClienteId());
        Cliente cliente = clienteRepository.findById(clienteId)
            .orElseThrow(() -> new RuntimeException("Cliente no encontrado con ID: " + clienteId));
        log.info("Cliente encontrado: {}", cliente.getNombre());
        
        Long horarioId = Long.parseLong(input.getHorarioId());
        Horario horario = horarioRepository.findById(horarioId)
            .orElseThrow(() -> new RuntimeException("Horario no encontrado con ID: " + horarioId));
        log.info("Horario encontrado: {} de {} a {}", horario.getDia(), horario.getHoraInicio(), horario.getHoraFin());
        
        Long mascotaId = Long.parseLong(input.getMascotaId());
        Mascota mascota = mascotaRepository.findById(mascotaId)
            .orElseThrow(() -> new RuntimeException("Mascota no encontrada con ID: " + mascotaId));
        log.info("Mascota encontrada: {}", mascota.getNombre());
        
        // Validar que la mascota pertenece al cliente
        if (!mascota.getCliente().getId().equals(clienteId)) {
            throw new RuntimeException("La mascota no pertenece al cliente especificado");
        }
        
        Cita cita = new Cita();
        cita.setMotivo(input.getMotivo());
        cita.setFechaProgramada(LocalDateTime.parse(input.getFechaProgramada(), dateTimeFormatter));
        cita.setFechaReservacion(LocalDateTime.now());
        cita.setCliente(cliente);
        cita.setHorario(horario);
        cita.setMascota(mascota);
        
        Cita savedCita = citaRepository.save(cita);
        log.info("Cita creada exitosamente con ID: {}", savedCita.getId());
        
        return savedCita;
    }
    
    public Cita update(CitaUpdateInput input) {
        log.info("Actualizando cita con input: {}", input);
        
        Long id = Long.parseLong(input.getId());
        Optional<Cita> existingCita = citaRepository.findById(id);
        
        if (existingCita.isEmpty()) {
            throw new RuntimeException("Cita no encontrada con ID: " + id);
        }
        
        Cita cita = existingCita.get();
        
        // Actualizar las entidades relacionadas si se proporcionan
        if (input.getClienteId() != null) {
            Long clienteId = Long.parseLong(input.getClienteId());
            Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con ID: " + clienteId));
            cita.setCliente(cliente);
        }
        
        if (input.getHorarioId() != null) {
            Long horarioId = Long.parseLong(input.getHorarioId());
            Horario horario = horarioRepository.findById(horarioId)
                .orElseThrow(() -> new RuntimeException("Horario no encontrado con ID: " + horarioId));
            cita.setHorario(horario);
        }
        
        if (input.getMascotaId() != null) {
            Long mascotaId = Long.parseLong(input.getMascotaId());
            Mascota mascota = mascotaRepository.findById(mascotaId)
                .orElseThrow(() -> new RuntimeException("Mascota no encontrada con ID: " + mascotaId));
            cita.setMascota(mascota);
        }
        
        if (input.getMotivo() != null) {
            cita.setMotivo(input.getMotivo());
        }
        
        if (input.getFechaProgramada() != null) {
            cita.setFechaProgramada(LocalDateTime.parse(input.getFechaProgramada(), dateTimeFormatter));
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