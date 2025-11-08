package com.example.microservicio_clinico.repository;

import com.example.microservicio_clinico.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    
    Optional<Doctor> findByEmail(String email);
    Optional<Doctor> findByCi(String ci);
    List<Doctor> findByNombreContainingIgnoreCase(String nombre);
    List<Doctor> findByApellidoContainingIgnoreCase(String apellido);
    boolean existsByEmail(String email);
    boolean existsByCi(String ci);
}