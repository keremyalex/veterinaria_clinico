package com.example.microservicio_clinico.repository;

import com.example.microservicio_clinico.entity.Tratamiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TratamientoRepository extends JpaRepository<Tratamiento, Integer> {
    
    // Buscar tratamientos por diagnóstico ID
    List<Tratamiento> findByDiagnosticoId(Integer diagnosticoId);
    
    // Buscar tratamientos por nombre (búsqueda parcial)
    @Query("SELECT t FROM Tratamiento t WHERE LOWER(t.nombre) LIKE LOWER(CONCAT('%', :nombre, '%'))")
    List<Tratamiento> findByNombreContainingIgnoreCase(@Param("nombre") String nombre);
    
    // Buscar tratamientos por descripción (búsqueda parcial)
    @Query("SELECT t FROM Tratamiento t WHERE LOWER(t.descripcion) LIKE LOWER(CONCAT('%', :descripcion, '%'))")
    List<Tratamiento> findByDescripcionContainingIgnoreCase(@Param("descripcion") String descripcion);
    
    // Buscar tratamientos por observaciones (búsqueda parcial)
    @Query("SELECT t FROM Tratamiento t WHERE LOWER(t.observaciones) LIKE LOWER(CONCAT('%', :observaciones, '%'))")
    List<Tratamiento> findByObservacionesContainingIgnoreCase(@Param("observaciones") String observaciones);
    
    // Buscar tratamientos de un doctor específico (a través del diagnóstico y la cita)
    @Query("SELECT t FROM Tratamiento t WHERE t.diagnostico.cita.doctor.id = :doctorId")
    List<Tratamiento> findByDoctorId(@Param("doctorId") Integer doctorId);
    
    // Buscar tratamientos de una mascota específica (a través del diagnóstico y la cita)
    @Query("SELECT t FROM Tratamiento t WHERE t.diagnostico.cita.mascota.id = :mascotaId")
    List<Tratamiento> findByMascotaId(@Param("mascotaId") Integer mascotaId);
    
    // Buscar tratamientos de una cita específica (a través del diagnóstico)
    @Query("SELECT t FROM Tratamiento t WHERE t.diagnostico.cita.id = :citaId")
    List<Tratamiento> findByCitaId(@Param("citaId") Integer citaId);
}