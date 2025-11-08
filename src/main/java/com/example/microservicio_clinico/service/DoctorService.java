package com.example.microservicio_clinico.service;

import com.example.microservicio_clinico.entity.Doctor;
import com.example.microservicio_clinico.repository.DoctorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class DoctorService {
    
    private final DoctorRepository doctorRepository;
    
    public List<Doctor> findAll() {
        return doctorRepository.findAll();
    }
    
    public Optional<Doctor> findById(Long id) {
        return doctorRepository.findById(id);
    }
    
    public Optional<Doctor> findByEmail(String email) {
        return doctorRepository.findByEmail(email);
    }
    
    public Optional<Doctor> findByCi(String ci) {
        return doctorRepository.findByCi(ci);
    }
    
    public List<Doctor> buscarPorNombre(String nombre) {
        return doctorRepository.findByNombreCompletoContainingIgnoreCase(nombre);
    }
    
    public Doctor save(Doctor doctor) {
        return doctorRepository.save(doctor);
    }
    
    public Doctor create(Doctor doctor) {
        // Validaciones antes de crear
        if (doctorRepository.existsByEmail(doctor.getEmail())) {
            throw new RuntimeException("Ya existe un doctor con el email: " + doctor.getEmail());
        }
        
        if (doctor.getCi() != null && doctorRepository.existsByCi(doctor.getCi())) {
            throw new RuntimeException("Ya existe un doctor con el CI: " + doctor.getCi());
        }
        
        return doctorRepository.save(doctor);
    }
    
    public Doctor update(Long id, Doctor doctorActualizado) {
        Doctor doctorExistente = doctorRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Doctor no encontrado con ID: " + id));
        
        // Validar email único (si cambió)
        if (!doctorExistente.getEmail().equals(doctorActualizado.getEmail()) && 
            doctorRepository.existsByEmail(doctorActualizado.getEmail())) {
            throw new RuntimeException("Ya existe un doctor con el email: " + doctorActualizado.getEmail());
        }
        
        // Validar CI único (si cambió)
        if (doctorActualizado.getCi() != null && 
            !doctorActualizado.getCi().equals(doctorExistente.getCi()) && 
            doctorRepository.existsByCi(doctorActualizado.getCi())) {
            throw new RuntimeException("Ya existe un doctor con el CI: " + doctorActualizado.getCi());
        }
        
        // Actualizar campos
        doctorExistente.setNombre(doctorActualizado.getNombre());
        doctorExistente.setApellido(doctorActualizado.getApellido());
        doctorExistente.setCi(doctorActualizado.getCi());
        doctorExistente.setTelefono(doctorActualizado.getTelefono());
        doctorExistente.setEmail(doctorActualizado.getEmail());
        doctorExistente.setFotocuf(doctorActualizado.getFotocuf());
        
        return doctorRepository.save(doctorExistente);
    }
    
    public void deleteById(Long id) {
        if (!doctorRepository.existsById(id)) {
            throw new RuntimeException("Doctor no encontrado con ID: " + id);
        }
        doctorRepository.deleteById(id);
    }
    
    public boolean existsById(Long id) {
        return doctorRepository.existsById(id);
    }
    
    public boolean existsByEmail(String email) {
        return doctorRepository.existsByEmail(email);
    }
    
    public boolean existsByCi(String ci) {
        return doctorRepository.existsByCi(ci);
    }
}