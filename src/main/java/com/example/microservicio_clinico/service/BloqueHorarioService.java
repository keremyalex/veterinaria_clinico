package com.example.microservicio_clinico.service;

import com.example.microservicio_clinico.entity.BloqueHorario;
import com.example.microservicio_clinico.entity.Doctor;
import com.example.microservicio_clinico.repository.BloqueHorarioRepository;
import com.example.microservicio_clinico.repository.DoctorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class BloqueHorarioService {
    
    private final BloqueHorarioRepository bloqueHorarioRepository;
    private final DoctorRepository doctorRepository;
    
    public List<BloqueHorario> findAll() {
        return bloqueHorarioRepository.findAll();
    }
    
    public Optional<BloqueHorario> findById(Long id) {
        return bloqueHorarioRepository.findById(id);
    }
    
    public List<BloqueHorario> findByDoctorId(Long doctorId) {
        return bloqueHorarioRepository.findByDoctorIdOrderByDiaAscHoraInicioAsc(doctorId);
    }
    
    public List<BloqueHorario> findByDia(String dia) {
        return bloqueHorarioRepository.findByDia(dia);
    }
    
    public List<BloqueHorario> findByDoctorIdAndDia(Long doctorId, String dia) {
        return bloqueHorarioRepository.findByDoctorIdAndDia(doctorId, dia);
    }
    
    public List<BloqueHorario> findDisponibleParaHora(Long doctorId, String dia, LocalTime hora) {
        return bloqueHorarioRepository.findByDoctorIdAndDiaAndHoraEnRango(doctorId, dia, hora);
    }
    
    public boolean existeConflictoHorario(Long doctorId, LocalTime horaInicio, LocalTime horaFin) {
        List<BloqueHorario> conflictos = bloqueHorarioRepository.findByDoctorIdAndHorarioSolapado(doctorId, horaInicio, horaFin);
        return !conflictos.isEmpty();
    }
    
    public BloqueHorario save(BloqueHorario bloqueHorario) {
        return bloqueHorarioRepository.save(bloqueHorario);
    }
    
    public BloqueHorario create(BloqueHorario bloqueHorario) {
        // Validar que el doctor existe
        if (bloqueHorario.getDoctor() == null || bloqueHorario.getDoctor().getId() == null) {
            throw new RuntimeException("Debe especificar un doctor válido para el bloque horario");
        }
        
        Doctor doctor = doctorRepository.findById(bloqueHorario.getDoctor().getId())
            .orElseThrow(() -> new RuntimeException("Doctor no encontrado con ID: " + bloqueHorario.getDoctor().getId()));
        
        // Validar que hora inicio sea menor que hora fin
        if (bloqueHorario.getHoraInicio().isAfter(bloqueHorario.getHoraFin()) || 
            bloqueHorario.getHoraInicio().equals(bloqueHorario.getHoraFin())) {
            throw new RuntimeException("La hora de inicio debe ser menor que la hora de fin");
        }
        
        // Validar que no exista conflicto de horarios
        if (existeConflictoHorario(doctor.getId(), bloqueHorario.getHoraInicio(), bloqueHorario.getHoraFin())) {
            throw new RuntimeException("Ya existe un bloque horario que se solapa con el horario especificado");
        }
        
        bloqueHorario.setDoctor(doctor);
        return bloqueHorarioRepository.save(bloqueHorario);
    }
    
    public BloqueHorario update(Long id, BloqueHorario bloqueHorarioActualizado) {
        BloqueHorario bloqueExistente = bloqueHorarioRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Bloque horario no encontrado con ID: " + id));
        
        // Validar doctor si se cambió
        if (bloqueHorarioActualizado.getDoctor() != null && 
            bloqueHorarioActualizado.getDoctor().getId() != null &&
            !bloqueHorarioActualizado.getDoctor().getId().equals(bloqueExistente.getDoctor().getId())) {
            
            Doctor nuevoDoctor = doctorRepository.findById(bloqueHorarioActualizado.getDoctor().getId())
                .orElseThrow(() -> new RuntimeException("Doctor no encontrado con ID: " + bloqueHorarioActualizado.getDoctor().getId()));
            
            bloqueExistente.setDoctor(nuevoDoctor);
        }
        
        // Validar horas
        LocalTime nuevaHoraInicio = bloqueHorarioActualizado.getHoraInicio() != null ? 
            bloqueHorarioActualizado.getHoraInicio() : bloqueExistente.getHoraInicio();
        LocalTime nuevaHoraFin = bloqueHorarioActualizado.getHoraFin() != null ? 
            bloqueHorarioActualizado.getHoraFin() : bloqueExistente.getHoraFin();
            
        if (nuevaHoraInicio.isAfter(nuevaHoraFin) || nuevaHoraInicio.equals(nuevaHoraFin)) {
            throw new RuntimeException("La hora de inicio debe ser menor que la hora de fin");
        }
        
        // Actualizar campos
        bloqueExistente.setDia(bloqueHorarioActualizado.getDia());
        bloqueExistente.setHoraInicio(nuevaHoraInicio);
        bloqueExistente.setHoraFin(nuevaHoraFin);
        
        return bloqueHorarioRepository.save(bloqueExistente);
    }
    
    public void deleteById(Long id) {
        if (!bloqueHorarioRepository.existsById(id)) {
            throw new RuntimeException("Bloque horario no encontrado con ID: " + id);
        }
        bloqueHorarioRepository.deleteById(id);
    }
    
    public boolean existsById(Long id) {
        return bloqueHorarioRepository.existsById(id);
    }
}