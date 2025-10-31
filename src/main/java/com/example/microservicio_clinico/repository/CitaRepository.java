package com.example.microservicio_clinico.repository;

import com.example.microservicio_clinico.entity.Cita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CitaRepository extends JpaRepository<Cita, Long> {
    
    List<Cita> findByClienteId(Long clienteId);
    
    List<Cita> findByMascotaId(Long mascotaId);
    
    List<Cita> findByHorarioId(Long horarioId);
    
    List<Cita> findByFechaProgramadaBetween(LocalDateTime inicio, LocalDateTime fin);
    
    List<Cita> findByClienteIdAndFechaProgramadaBetween(Long clienteId, LocalDateTime inicio, LocalDateTime fin);
}