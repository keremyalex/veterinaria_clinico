package com.example.microservicio_clinico.repository;

import com.example.microservicio_clinico.entity.Cita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CitaRepository extends JpaRepository<Cita, Integer> {
    
    // Buscar citas por doctor ID
    List<Cita> findByDoctorId(Integer doctorId);
    
    // Buscar citas por mascota ID
    List<Cita> findByMascotaId(Integer mascotaId);
    
    // Buscar citas por estado
    List<Cita> findByEstado(Integer estado);
    
    // Buscar citas de un doctor por estado
    List<Cita> findByDoctorIdAndEstado(Integer doctorId, Integer estado);
    
    // Buscar citas de una mascota por estado
    List<Cita> findByMascotaIdAndEstado(Integer mascotaId, Integer estado);
    
    // Buscar citas por rango de fechas de reserva
    List<Cita> findByFechareservaBetween(LocalDateTime fechaInicio, LocalDateTime fechaFin);
    
    // Buscar citas de un doctor en un rango de fechas de reserva
    List<Cita> findByDoctorIdAndFechareservaBetween(Integer doctorId, LocalDateTime fechaInicio, LocalDateTime fechaFin);
    
    // Buscar citas por rango de fechas de creación
    List<Cita> findByFechacreacionBetween(LocalDateTime fechaInicio, LocalDateTime fechaFin);
    
    // Verificar disponibilidad de doctor en una fecha específica (fecha de reserva)
    @Query("SELECT COUNT(c) FROM Cita c WHERE c.doctor.id = :doctorId AND c.fechareserva = :fechareserva AND c.estado != 3")
    Long countByDoctorAndFechareservaAndEstadoNot(@Param("doctorId") Integer doctorId, 
                                                  @Param("fechareserva") LocalDateTime fechareserva);
    
    // Buscar citas por motivo (búsqueda parcial)
    @Query("SELECT c FROM Cita c WHERE LOWER(c.motivo) LIKE LOWER(CONCAT('%', :motivo, '%'))")
    List<Cita> findByMotivoContainingIgnoreCase(@Param("motivo") String motivo);
    
    // ========== MÉTODOS CON EAGER LOADING PARA GRAPHQL ==========
    
    // Buscar todas las citas con relaciones cargadas
    @Query("SELECT c FROM Cita c " +
           "LEFT JOIN FETCH c.doctor " +
           "LEFT JOIN FETCH c.mascota m " +
           "LEFT JOIN FETCH m.cliente " +
           "LEFT JOIN FETCH m.especie " +
           "LEFT JOIN FETCH c.bloqueHorario")
    List<Cita> findAllWithRelations();
    
    // Buscar cita por ID con relaciones cargadas
    @Query("SELECT c FROM Cita c " +
           "LEFT JOIN FETCH c.doctor " +
           "LEFT JOIN FETCH c.mascota m " +
           "LEFT JOIN FETCH m.cliente " +
           "LEFT JOIN FETCH m.especie " +
           "LEFT JOIN FETCH c.bloqueHorario " +
           "WHERE c.id = :id")
    java.util.Optional<Cita> findByIdWithRelations(@Param("id") Integer id);
    
    // Buscar citas por doctor ID con relaciones cargadas
    @Query("SELECT c FROM Cita c " +
           "LEFT JOIN FETCH c.doctor " +
           "LEFT JOIN FETCH c.mascota m " +
           "LEFT JOIN FETCH m.cliente " +
           "LEFT JOIN FETCH m.especie " +
           "LEFT JOIN FETCH c.bloqueHorario " +
           "WHERE c.doctor.id = :doctorId")
    List<Cita> findByDoctorIdWithRelations(@Param("doctorId") Integer doctorId);
    
    // Buscar citas por mascota ID con relaciones cargadas
    @Query("SELECT c FROM Cita c " +
           "LEFT JOIN FETCH c.doctor " +
           "LEFT JOIN FETCH c.mascota m " +
           "LEFT JOIN FETCH m.cliente " +
           "LEFT JOIN FETCH m.especie " +
           "LEFT JOIN FETCH c.bloqueHorario " +
           "WHERE c.mascota.id = :mascotaId")
    List<Cita> findByMascotaIdWithRelations(@Param("mascotaId") Integer mascotaId);
    
    // Buscar citas por estado con relaciones cargadas
    @Query("SELECT c FROM Cita c " +
           "LEFT JOIN FETCH c.doctor " +
           "LEFT JOIN FETCH c.mascota m " +
           "LEFT JOIN FETCH m.cliente " +
           "LEFT JOIN FETCH m.especie " +
           "LEFT JOIN FETCH c.bloqueHorario " +
           "WHERE c.estado = :estado")
    List<Cita> findByEstadoWithRelations(@Param("estado") Integer estado);
    
    // Buscar citas por rango de fechas de reserva con relaciones cargadas
    @Query("SELECT c FROM Cita c " +
           "LEFT JOIN FETCH c.doctor " +
           "LEFT JOIN FETCH c.mascota m " +
           "LEFT JOIN FETCH m.cliente " +
           "LEFT JOIN FETCH m.especie " +
           "LEFT JOIN FETCH c.bloqueHorario " +
           "WHERE c.fechareserva BETWEEN :fechaInicio AND :fechaFin")
    List<Cita> findByFechareservaBetweenWithRelations(@Param("fechaInicio") LocalDateTime fechaInicio, 
                                                     @Param("fechaFin") LocalDateTime fechaFin);
}