package com.example.microservicio_clinico.repository;

import com.example.microservicio_clinico.entity.CarnetVacunacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface CarnetVacunacionRepository extends JpaRepository<CarnetVacunacion, Integer> {
    
    // Buscar carnet de vacunación por mascota ID
    Optional<CarnetVacunacion> findByMascotaId(Integer mascotaId);
    
    // Buscar carnets por rango de fechas de emisión
    List<CarnetVacunacion> findByFechaemisionBetween(LocalDateTime fechaInicio, LocalDateTime fechaFin);
    
    // Buscar carnets de un cliente específico (a través de la mascota)
    @Query("SELECT c FROM CarnetVacunacion c WHERE c.mascota.cliente.id = :clienteId")
    List<CarnetVacunacion> findByClienteId(@Param("clienteId") Integer clienteId);
    
    // Verificar si una mascota ya tiene carnet de vacunación
    boolean existsByMascotaId(Integer mascotaId);
    
    // Buscar carnets por especie de mascota
    @Query("SELECT c FROM CarnetVacunacion c WHERE c.mascota.especie.id = :especieId")
    List<CarnetVacunacion> findByEspecieId(@Param("especieId") Integer especieId);
}