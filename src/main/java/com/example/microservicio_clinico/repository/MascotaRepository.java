package com.example.microservicio_clinico.repository;

import com.example.microservicio_clinico.entity.Mascota;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MascotaRepository extends JpaRepository<Mascota, Integer> {
    
    // Buscar mascotas por cliente ID
    List<Mascota> findByClienteId(Integer clienteId);
    
    // Buscar mascotas por especie ID
    List<Mascota> findByEspecieId(Integer especieId);
    
    // Buscar mascotas por nombre
    @Query("SELECT m FROM Mascota m WHERE LOWER(m.nombre) LIKE LOWER(CONCAT('%', :nombre, '%'))")
    List<Mascota> findByNombreContainingIgnoreCase(@Param("nombre") String nombre);
    
    // Buscar mascotas por raza
    @Query("SELECT m FROM Mascota m WHERE LOWER(m.raza) LIKE LOWER(CONCAT('%', :raza, '%'))")
    List<Mascota> findByRazaContainingIgnoreCase(@Param("raza") String raza);
    
    // Buscar mascotas por sexo
    List<Mascota> findBySexo(String sexo);
    
    // Buscar mascotas por rango de fechas de nacimiento
    List<Mascota> findByFechanacimientoBetween(LocalDate fechaInicio, LocalDate fechaFin);
    
    // Buscar mascotas de un cliente por nombre
    @Query("SELECT m FROM Mascota m WHERE m.cliente.id = :clienteId AND LOWER(m.nombre) LIKE LOWER(CONCAT('%', :nombre, '%'))")
    List<Mascota> findByClienteIdAndNombreContainingIgnoreCase(@Param("clienteId") Integer clienteId, @Param("nombre") String nombre);
    
    // ========== MÉTODOS CON EAGER LOADING PARA GRAPHQL ==========
    
    // Buscar todas las mascotas con relaciones cargadas
    @Query("SELECT m FROM Mascota m LEFT JOIN FETCH m.cliente LEFT JOIN FETCH m.especie")
    List<Mascota> findAllWithRelations();
    
    // Buscar mascota por ID con relaciones cargadas
    @Query("SELECT m FROM Mascota m LEFT JOIN FETCH m.cliente LEFT JOIN FETCH m.especie WHERE m.id = :id")
    java.util.Optional<Mascota> findByIdWithRelations(@Param("id") Integer id);
    
    // Buscar mascotas por cliente ID con relaciones cargadas
    @Query("SELECT m FROM Mascota m LEFT JOIN FETCH m.cliente LEFT JOIN FETCH m.especie WHERE m.cliente.id = :clienteId")
    List<Mascota> findByClienteIdWithRelations(@Param("clienteId") Integer clienteId);
    
    // Buscar mascotas por especie ID con relaciones cargadas
    @Query("SELECT m FROM Mascota m LEFT JOIN FETCH m.cliente LEFT JOIN FETCH m.especie WHERE m.especie.id = :especieId")
    List<Mascota> findByEspecieIdWithRelations(@Param("especieId") Integer especieId);
}