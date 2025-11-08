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
    
    // Buscar bloques horarios por día de la semana
    List<BloqueHorario> findByDiasemana(Integer diasemana);
    
    // Buscar bloques horarios activos por día de la semana
    List<BloqueHorario> findByDiasemanaAndActivo(Integer diasemana, Integer activo);
    
    // Buscar todos los bloques horarios activos
    List<BloqueHorario> findByActivo(Integer activo);
    
    // Verificar si hay conflicto de horarios en un día específico
    @Query("SELECT COUNT(b) FROM BloqueHorario b WHERE b.diasemana = :diasemana AND b.activo = 1 AND " +
           "((b.horainicio <= :horaInicial AND b.horafinal > :horaInicial) OR " +
           "(b.horainicio < :horaFinal AND b.horafinal >= :horaFinal) OR " +
           "(b.horainicio >= :horaInicial AND b.horafinal <= :horaFinal))")
    Long countConflictingSchedules(@Param("diasemana") Integer diasemana,
                                   @Param("horaInicial") LocalTime horaInicial, 
                                   @Param("horaFinal") LocalTime horaFinal);
}