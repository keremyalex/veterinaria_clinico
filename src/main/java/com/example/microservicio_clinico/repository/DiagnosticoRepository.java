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
    
    // ========== MÉTODOS CON EAGER LOADING PARA GRAPHQL ==========
    
    // Buscar todos los diagnósticos con relaciones cargadas
    @Query("SELECT d FROM Diagnostico d " +
           "LEFT JOIN FETCH d.cita c " +
           "LEFT JOIN FETCH c.doctor " +
           "LEFT JOIN FETCH c.mascota m " +
           "LEFT JOIN FETCH m.cliente " +
           "LEFT JOIN FETCH m.especie " +
           "LEFT JOIN FETCH c.bloqueHorario " +
           "LEFT JOIN FETCH d.tratamientos")
    List<Diagnostico> findAllWithRelations();
    
    // Buscar diagnóstico por ID con relaciones cargadas
    @Query("SELECT d FROM Diagnostico d " +
           "LEFT JOIN FETCH d.cita c " +
           "LEFT JOIN FETCH c.doctor " +
           "LEFT JOIN FETCH c.mascota m " +
           "LEFT JOIN FETCH m.cliente " +
           "LEFT JOIN FETCH m.especie " +
           "LEFT JOIN FETCH c.bloqueHorario " +
           "LEFT JOIN FETCH d.tratamientos " +
           "WHERE d.id = :id")
    java.util.Optional<Diagnostico> findByIdWithRelations(@Param("id") Integer id);
    
    // Buscar diagnósticos por cita ID con relaciones cargadas
    @Query("SELECT d FROM Diagnostico d " +
           "LEFT JOIN FETCH d.cita c " +
           "LEFT JOIN FETCH c.doctor " +
           "LEFT JOIN FETCH c.mascota m " +
           "LEFT JOIN FETCH m.cliente " +
           "LEFT JOIN FETCH m.especie " +
           "LEFT JOIN FETCH c.bloqueHorario " +
           "LEFT JOIN FETCH d.tratamientos " +
           "WHERE d.cita.id = :citaId")
    List<Diagnostico> findByCitaIdWithRelations(@Param("citaId") Integer citaId);
    
    // Buscar diagnósticos por mascota ID con relaciones cargadas
    @Query("SELECT d FROM Diagnostico d " +
           "LEFT JOIN FETCH d.cita c " +
           "LEFT JOIN FETCH c.doctor " +
           "LEFT JOIN FETCH c.mascota m " +
           "LEFT JOIN FETCH m.cliente " +
           "LEFT JOIN FETCH m.especie " +
           "LEFT JOIN FETCH c.bloqueHorario " +
           "LEFT JOIN FETCH d.tratamientos " +
           "WHERE c.mascota.id = :mascotaId")
    List<Diagnostico> findByMascotaIdWithRelations(@Param("mascotaId") Integer mascotaId);
}