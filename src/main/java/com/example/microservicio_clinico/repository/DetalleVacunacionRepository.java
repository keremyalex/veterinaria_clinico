package com.example.microservicio_clinico.repository;

import com.example.microservicio_clinico.entity.DetalleVacunacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DetalleVacunacionRepository extends JpaRepository<DetalleVacunacion, Integer> {
    
    // Buscar detalles de vacunación por carnet ID
    List<DetalleVacunacion> findByCarnetVacunacionId(Integer carnetVacunacionId);
    
    // Buscar detalles de vacunación por vacuna ID
    List<DetalleVacunacion> findByVacunaId(Integer vacunaId);
    
    // Buscar detalles por rango de fechas de vacunación
    List<DetalleVacunacion> findByFechavacunacionBetween(LocalDate fechaInicio, LocalDate fechaFin);
    
    // Buscar detalles por rango de fechas de próxima vacunación
    List<DetalleVacunacion> findByProximavacunacionBetween(LocalDate fechaInicio, LocalDate fechaFin);
    
    // Buscar detalles de vacunación de una mascota específica
    @Query("SELECT d FROM DetalleVacunacion d WHERE d.carnetVacunacion.mascota.id = :mascotaId")
    List<DetalleVacunacion> findByMascotaId(@Param("mascotaId") Integer mascotaId);
    
    // Buscar detalles de vacunación por mascota y vacuna
    @Query("SELECT d FROM DetalleVacunacion d WHERE d.carnetVacunacion.mascota.id = :mascotaId AND d.vacuna.id = :vacunaId")
    List<DetalleVacunacion> findByMascotaIdAndVacunaId(@Param("mascotaId") Integer mascotaId, @Param("vacunaId") Integer vacunaId);
    
    // Buscar próximas vacunaciones vencidas o por vencer
    @Query("SELECT d FROM DetalleVacunacion d WHERE d.proximavacunacion IS NOT NULL AND d.proximavacunacion <= :fecha")
    List<DetalleVacunacion> findVacunacionesVencidas(@Param("fecha") LocalDate fecha);
    
    // Buscar historial de vacunación de una mascota ordenado por fecha
    @Query("SELECT d FROM DetalleVacunacion d WHERE d.carnetVacunacion.mascota.id = :mascotaId ORDER BY d.fechavacunacion DESC")
    List<DetalleVacunacion> findHistorialVacunacionByMascotaId(@Param("mascotaId") Integer mascotaId);
    
    // Buscar detalles de vacunación de un cliente específico
    @Query("SELECT d FROM DetalleVacunacion d WHERE d.carnetVacunacion.mascota.cliente.id = :clienteId")
    List<DetalleVacunacion> findByClienteId(@Param("clienteId") Integer clienteId);
}