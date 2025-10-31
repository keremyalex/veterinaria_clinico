package com.example.microservicio_clinico.repository;

import com.example.microservicio_clinico.entity.Vacuna;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VacunaRepository extends JpaRepository<Vacuna, Long> {
    
    Optional<Vacuna> findByNombre(String nombre);
    
    List<Vacuna> findByLaboratorio(String laboratorio);
    
    List<Vacuna> findByNombreContainingIgnoreCase(String nombre);
}