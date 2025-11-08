package com.example.microservicio_clinico.service;

import com.example.microservicio_clinico.dto.*;
import com.example.microservicio_clinico.entity.Doctor;
import com.example.microservicio_clinico.repository.DoctorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class DoctorService {
    
    private final DoctorRepository doctorRepository;
    
    // Crear nuevo doctor
    public DoctorOutput crearDoctor(DoctorInput input) {
        try {
            log.info("Creando nuevo doctor con CI: {}", input.getCi());
            
            // Validar que el CI no exista
            if (doctorRepository.findByCi(input.getCi()).isPresent()) {
                throw new RuntimeException("Ya existe un doctor con el CI: " + input.getCi());
            }
            
            // Validar que el email no exista
            if (doctorRepository.findByEmail(input.getEmail()).isPresent()) {
                throw new RuntimeException("Ya existe un doctor con el email: " + input.getEmail());
            }
            
            Doctor doctor = new Doctor();
            doctor.setNombre(input.getNombre());
            doctor.setApellido(input.getApellido());
            doctor.setCi(input.getCi());
            doctor.setTelefono(input.getTelefono());
            doctor.setEmail(input.getEmail());
            doctor.setFotourl(input.getFotourl());
            
            Doctor savedDoctor = doctorRepository.save(doctor);
            log.info("Doctor creado exitosamente con ID: {}", savedDoctor.getId());
            
            return convertirAOutput(savedDoctor);
        } catch (Exception e) {
            log.error("Error al crear doctor: {}", e.getMessage(), e);
            throw new RuntimeException("Error al crear doctor: " + e.getMessage(), e);
        }
    }
    
    // Actualizar doctor
    public DoctorOutput actualizarDoctor(DoctorUpdateInput input) {
        log.info("Actualizando doctor con ID: {}", input.getId());
        
        Doctor doctor = doctorRepository.findById(input.getId())
            .orElseThrow(() -> new RuntimeException("Doctor no encontrado con ID: " + input.getId()));
        
        // Validar CI único si se está cambiando
        if (input.getCi() != null && !input.getCi().equals(doctor.getCi())) {
            if (doctorRepository.existsByCiAndIdNot(input.getCi(), input.getId())) {
                throw new RuntimeException("Ya existe otro doctor con el CI: " + input.getCi());
            }
            doctor.setCi(input.getCi());
        }
        
        // Validar email único si se está cambiando
        if (input.getEmail() != null && !input.getEmail().equals(doctor.getEmail())) {
            if (doctorRepository.existsByEmailAndIdNot(input.getEmail(), input.getId())) {
                throw new RuntimeException("Ya existe otro doctor con el email: " + input.getEmail());
            }
            doctor.setEmail(input.getEmail());
        }
        
        // Actualizar otros campos si se proporcionan
        if (input.getNombre() != null) doctor.setNombre(input.getNombre());
        if (input.getApellido() != null) doctor.setApellido(input.getApellido());
        if (input.getTelefono() != null) doctor.setTelefono(input.getTelefono());
        if (input.getFotourl() != null) doctor.setFotourl(input.getFotourl());
        
        Doctor updatedDoctor = doctorRepository.save(doctor);
        log.info("Doctor actualizado exitosamente con ID: {}", updatedDoctor.getId());
        
        return convertirAOutput(updatedDoctor);
    }
    
    // Obtener doctor por ID
    @Transactional(readOnly = true)
    public DoctorOutput obtenerDoctorPorId(Integer id) {
        log.info("Obteniendo doctor por ID: {}", id);
        
        Doctor doctor = doctorRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Doctor no encontrado con ID: " + id));
        
        return convertirAOutput(doctor);
    }
    
    // Obtener todos los doctores
    @Transactional(readOnly = true)
    public List<DoctorOutput> obtenerTodosLosDoctores() {
        log.info("Obteniendo todos los doctores");
        
        return doctorRepository.findAll()
            .stream()
            .map(this::convertirAOutput)
            .collect(Collectors.toList());
    }
    
    // Buscar doctores por nombre o apellido
    @Transactional(readOnly = true)
    public List<DoctorOutput> buscarDoctoresPorNombre(String termino) {
        log.info("Buscando doctores por término: {}", termino);
        
        return doctorRepository.findByNombreOrApellidoContainingIgnoreCase(termino, termino)
            .stream()
            .map(this::convertirAOutput)
            .collect(Collectors.toList());
    }
    
    // Obtener doctor por CI
    @Transactional(readOnly = true)
    public Optional<DoctorOutput> obtenerDoctorPorCi(String ci) {
        log.info("Obteniendo doctor por CI: {}", ci);
        
        return doctorRepository.findByCi(ci)
            .map(this::convertirAOutput);
    }
    
    // Obtener doctor por email
    @Transactional(readOnly = true)
    public Optional<DoctorOutput> obtenerDoctorPorEmail(String email) {
        log.info("Obteniendo doctor por email: {}", email);
        
        return doctorRepository.findByEmail(email)
            .map(this::convertirAOutput);
    }
    
    // Eliminar doctor
    public boolean eliminarDoctor(Integer id) {
        log.info("Eliminando doctor con ID: {}", id);
        
        if (!doctorRepository.existsById(id)) {
            throw new RuntimeException("Doctor no encontrado con ID: " + id);
        }
        
        doctorRepository.deleteById(id);
        log.info("Doctor eliminado exitosamente con ID: {}", id);
        
        return true;
    }
    
    // Método privado para convertir Entity a DTO
    private DoctorOutput convertirAOutput(Doctor doctor) {
        DoctorOutput output = new DoctorOutput();
        output.setId(doctor.getId());
        output.setNombre(doctor.getNombre());
        output.setApellido(doctor.getApellido());
        output.setCi(doctor.getCi());
        output.setTelefono(doctor.getTelefono());
        output.setEmail(doctor.getEmail());
        output.setFotourl(doctor.getFotourl());
        return output;
    }
}
