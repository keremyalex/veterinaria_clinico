package com.example.microservicio_clinico.repository;

import com.example.microservicio_clinico.entity.Diagnostico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface DiagnosticoRepository extends JpaRepository<Diagnostico, Integer> {
    
    // Buscar diagnósticos por cita ID
    List<Diagnostico> findByCitaId(Integer citaId);
    
    // Buscar diagnósticos por descripción (búsqueda parcial)
    @Query("SELECT d FROM Diagnostico d WHERE LOWER(d.descripcion) LIKE LOWER(CONCAT('%', :descripcion, '%'))")
    List<Diagnostico> findByDescripcionContainingIgnoreCase(@Param("descripcion") String descripcion);
    
    // Buscar diagnósticos por observaciones (búsqueda parcial)
    @Query("SELECT d FROM Diagnostico d WHERE LOWER(d.observaciones) LIKE LOWER(CONCAT('%', :observaciones, '%'))")
    List<Diagnostico> findByObservacionesContainingIgnoreCase(@Param("observaciones") String observaciones);
    
    // Buscar diagnósticos por rango de fechas de registro
    List<Diagnostico> findByFecharegistroBetween(LocalDateTime fechaInicio, LocalDateTime fechaFin);
    
    // Buscar diagnósticos de un doctor específico (a través de la cita)
    @Query("SELECT d FROM Diagnostico d WHERE d.cita.doctor.id = :doctorId")
    List<Diagnostico> findByDoctorId(@Param("doctorId") Integer doctorId);
    
    // Buscar diagnósticos de una mascota específica (a través de la cita)
    @Query("SELECT d FROM Diagnostico d WHERE d.cita.mascota.id = :mascotaId")
    List<Diagnostico> findByMascotaId(@Param("mascotaId") Integer mascotaId);
    
    // Buscar diagnósticos de un doctor en un rango de fechas
    @Query("SELECT d FROM Diagnostico d WHERE d.cita.doctor.id = :doctorId AND d.fecharegistro BETWEEN :fechaInicio AND :fechaFin")
    List<Diagnostico> findByDoctorIdAndFecharegistroBetween(@Param("doctorId") Integer doctorId, 
                                                            @Param("fechaInicio") LocalDateTime fechaInicio, 
                                                            @Param("fechaFin") LocalDateTime fechaFin);
}