package com.example.microservicio_clinico.repository;

import com.example.microservicio_clinico.entity.BloqueHorario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface BloqueHorarioRepository extends JpaRepository<BloqueHorario, Long> {
    
    List<BloqueHorario> findByDoctorId(Long doctorId);
    List<BloqueHorario> findByDia(String dia);
    List<BloqueHorario> findByDoctorIdAndDia(Long doctorId, String dia);
    List<BloqueHorario> findByHoraInicioGreaterThanEqualAndHoraFinLessThanEqual(LocalTime horaInicio, LocalTime horaFin);
}