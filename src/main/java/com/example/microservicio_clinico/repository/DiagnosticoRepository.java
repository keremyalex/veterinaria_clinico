package com.example.microservicio_clinico.repository;

import com.example.microservicio_clinico.entity.Diagnostico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface DiagnosticoRepository extends JpaRepository<Diagnostico, Long> {
    
    List<Diagnostico> findByMascotaId(Long mascotaId);
    
    List<Diagnostico> findByFechaDiagnosticoBetween(LocalDateTime inicio, LocalDateTime fin);
    
    List<Diagnostico> findByMascotaIdAndFechaDiagnosticoBetween(Long mascotaId, LocalDateTime inicio, LocalDateTime fin);
    
    List<Diagnostico> findByMascotaIdOrderByFechaDiagnosticoDesc(Long mascotaId);
}