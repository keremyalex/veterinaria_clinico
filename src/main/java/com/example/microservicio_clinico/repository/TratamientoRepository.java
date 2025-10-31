package com.example.microservicio_clinico.repository;

import com.example.microservicio_clinico.entity.Tratamiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TratamientoRepository extends JpaRepository<Tratamiento, Long> {
    
    List<Tratamiento> findByDiagnosticoId(Long diagnosticoId);
    
    List<Tratamiento> findByEstado(String estado);
    
    List<Tratamiento> findByFechaInicioBetween(LocalDateTime inicio, LocalDateTime fin);
    
    List<Tratamiento> findByDiagnosticoIdAndEstado(Long diagnosticoId, String estado);
    
    List<Tratamiento> findByDiagnosticoIdOrderByFechaInicioDesc(Long diagnosticoId);
}