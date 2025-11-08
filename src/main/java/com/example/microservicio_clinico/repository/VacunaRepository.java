package com.example.microservicio_clinico.repository;

import com.example.microservicio_clinico.entity.Vacuna;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VacunaRepository extends JpaRepository<Vacuna, Integer> {
    
    // Buscar vacuna por descripción exacta
    Optional<Vacuna> findByDescripcion(String descripcion);
    
    // Buscar vacunas por descripción (búsqueda parcial)
    @Query("SELECT v FROM Vacuna v WHERE LOWER(v.descripcion) LIKE LOWER(CONCAT('%', :descripcion, '%'))")
    List<Vacuna> findByDescripcionContainingIgnoreCase(@Param("descripcion") String descripcion);
    
    // Verificar si existe una vacuna con la descripción (excluyendo un ID específico para updates)
    boolean existsByDescripcionAndIdNot(String descripcion, Integer id);
    
    // Obtener todas las vacunas ordenadas por descripción
    @Query("SELECT v FROM Vacuna v ORDER BY v.descripcion")
    List<Vacuna> findAllOrderByDescripcion();
}