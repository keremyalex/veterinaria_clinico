package com.example.microservicio_clinico.repository;

import com.example.microservicio_clinico.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Integer> {
    
    // Buscar doctor por CI
    Optional<Doctor> findByCi(String ci);
    
    // Buscar doctor por email
    Optional<Doctor> findByEmail(String email);
    
    // Buscar doctores por nombre o apellido
    @Query("SELECT d FROM Doctor d WHERE LOWER(d.nombre) LIKE LOWER(CONCAT('%', :nombre, '%')) OR LOWER(d.apellido) LIKE LOWER(CONCAT('%', :apellido, '%'))")
    List<Doctor> findByNombreOrApellidoContainingIgnoreCase(@Param("nombre") String nombre, @Param("apellido") String apellido);
    
    // Verificar si existe un doctor con el CI (excluyendo un ID específico para updates)
    boolean existsByCiAndIdNot(String ci, Integer id);
    
    // Verificar si existe un doctor con el email (excluyendo un ID específico para updates)
    boolean existsByEmailAndIdNot(String email, Integer id);
}