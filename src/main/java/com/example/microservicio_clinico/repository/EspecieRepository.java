package com.example.microservicio_clinico.repository;

import com.example.microservicio_clinico.entity.Especie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EspecieRepository extends JpaRepository<Especie, Integer> {
    
    // Buscar especie por descripción exacta
    Optional<Especie> findByDescripcion(String descripcion);
    
    // Buscar especies por descripción que contenga el texto
    @Query("SELECT e FROM Especie e WHERE LOWER(e.descripcion) LIKE LOWER(CONCAT('%', :descripcion, '%'))")
    List<Especie> findByDescripcionContainingIgnoreCase(@Param("descripcion") String descripcion);
    
    // Verificar si existe una especie con la descripción (excluyendo un ID específico para updates)
    boolean existsByDescripcionAndIdNot(String descripcion, Integer id);
}