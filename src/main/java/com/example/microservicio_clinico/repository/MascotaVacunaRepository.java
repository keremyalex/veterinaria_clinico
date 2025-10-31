package com.example.microservicio_clinico.repository;

import com.example.microservicio_clinico.entity.MascotaVacuna;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MascotaVacunaRepository extends JpaRepository<MascotaVacuna, Long> {
    
    List<MascotaVacuna> findByMascotaId(Long mascotaId);
    
    List<MascotaVacuna> findByVacunaId(Long vacunaId);
    
    List<MascotaVacuna> findByMascotaIdAndVacunaId(Long mascotaId, Long vacunaId);
    
    List<MascotaVacuna> findByFechaAplicacionBetween(LocalDateTime inicio, LocalDateTime fin);
    
    @Query("SELECT mv FROM MascotaVacuna mv WHERE mv.fechaProximaDosis <= :fecha")
    List<MascotaVacuna> findVacunasPorVencer(@Param("fecha") LocalDateTime fecha);
    
    List<MascotaVacuna> findByMascotaIdOrderByFechaAplicacionDesc(Long mascotaId);
}