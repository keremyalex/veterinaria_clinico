package com.example.microservicio_clinico.repository;

import com.example.microservicio_clinico.entity.Mascota;
import com.example.microservicio_clinico.entity.Cliente;
import com.example.microservicio_clinico.entity.Especie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MascotaRepository extends JpaRepository<Mascota, Long> {
    
    List<Mascota> findByClienteId(Long clienteId);
    
    List<Mascota> findByEspecieId(Long especieId);
    
    List<Mascota> findByNombreContainingIgnoreCase(String nombre);
    
    List<Mascota> findByRazaContainingIgnoreCase(String raza);
    
    List<Mascota> findBySexo(String sexo);
    
    @Query("SELECT m FROM Mascota m WHERE m.cliente.id = :clienteId AND m.especie.id = :especieId")
    List<Mascota> findByClienteIdAndEspecieId(@Param("clienteId") Long clienteId, @Param("especieId") Long especieId);
    
    @Query("SELECT m FROM Mascota m WHERE " +
           "LOWER(m.nombre) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(m.raza) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    List<Mascota> searchByNombreOrRaza(@Param("searchTerm") String searchTerm);
}