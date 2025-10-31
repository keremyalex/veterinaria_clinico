package com.example.microservicio_clinico.repository;

import com.example.microservicio_clinico.entity.Especie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EspecieRepository extends JpaRepository<Especie, Long> {
    
    Optional<Especie> findByDescripcion(String descripcion);
    
    boolean existsByDescripcion(String descripcion);
}