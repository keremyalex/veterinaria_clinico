package com.example.microservicio_clinico.repository;

import com.example.microservicio_clinico.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
    
    // Buscar cliente por CI
    Optional<Cliente> findByCi(String ci);
    
    // Buscar clientes por nombre o apellido
    @Query("SELECT c FROM Cliente c WHERE LOWER(c.nombre) LIKE LOWER(CONCAT('%', :nombre, '%')) OR LOWER(c.apellido) LIKE LOWER(CONCAT('%', :apellido, '%'))")
    List<Cliente> findByNombreOrApellidoContainingIgnoreCase(@Param("nombre") String nombre, @Param("apellido") String apellido);
    
    // Buscar cliente por teléfono
    Optional<Cliente> findByTelefono(String telefono);
    
    // Verificar si existe un cliente con el CI (excluyendo un ID específico para updates)
    boolean existsByCiAndIdNot(String ci, Integer id);
    
    // Verificar si existe un cliente con el teléfono (excluyendo un ID específico para updates)
    boolean existsByTelefonoAndIdNot(String telefono, Integer id);
}