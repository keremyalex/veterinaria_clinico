package com.example.microservicio_clinico.repository;

import com.example.microservicio_clinico.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    
    Optional<Cliente> findByEmail(String email);
    
    boolean existsByEmail(String email);
    
    List<Cliente> findByNombreContainingIgnoreCase(String nombre);
    
    List<Cliente> findByApellidosContainingIgnoreCase(String apellidos);
    
    List<Cliente> findByNombreContainingIgnoreCaseOrApellidosContainingIgnoreCase(String nombre, String apellidos);
}