package com.example.microservicio_clinico.repository;

import com.example.microservicio_clinico.entity.BloqueHorario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.util.List;

@Repository
public interface BloqueHorarioRepository extends JpaRepository<BloqueHorario, Integer> {
    
    // Buscar bloques horarios por doctor ID
    List<BloqueHorario> findByDoctorId(Integer doctorId);
    
    // Buscar bloques horarios activos por doctor ID
    List<BloqueHorario> findByDoctorIdAndActivo(Integer doctorId, Integer activo);
    
    // Buscar bloques horarios por día de la semana
    List<BloqueHorario> findByDiasemana(Integer diasemana);
    
    // Buscar bloques horarios activos por día de la semana
    List<BloqueHorario> findByDiasemanaAndActivo(Integer diasemana, Integer activo);
    
    // Buscar bloques horarios de un doctor por día de la semana
    List<BloqueHorario> findByDoctorIdAndDiasemana(Integer doctorId, Integer diasemana);
    
    // Buscar bloques horarios activos de un doctor por día de la semana
    List<BloqueHorario> findByDoctorIdAndDiasemanaAndActivo(Integer doctorId, Integer diasemana, Integer activo);
    
    // Verificar si hay conflicto de horarios para un doctor en un día específico
    @Query("SELECT COUNT(b) FROM BloqueHorario b WHERE b.doctor.id = :doctorId AND b.diasemana = :diasemana AND b.activo = 1 AND " +
           "((b.horainicio <= :horaInicial AND b.horafinal > :horaInicial) OR " +
           "(b.horainicio < :horaFinal AND b.horafinal >= :horaFinal) OR " +
           "(b.horainicio >= :horaInicial AND b.horafinal <= :horaFinal))")
    Long countConflictingSchedules(@Param("doctorId") Integer doctorId, 
                                   @Param("diasemana") Integer diasemana,
                                   @Param("horaInicial") LocalTime horaInicial, 
                                   @Param("horaFinal") LocalTime horaFinal);
}