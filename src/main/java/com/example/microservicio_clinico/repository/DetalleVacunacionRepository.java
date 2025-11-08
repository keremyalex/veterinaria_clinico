package com.example.microservicio_clinico.repository;

import com.example.microservicio_clinico.entity.DetalleVacunacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface DetalleVacunacionRepository extends JpaRepository<DetalleVacunacion, Long> {
    
    List<DetalleVacunacion> findByCarnetVacunacionId(Long carnetId);
    List<DetalleVacunacion> findByVacunaId(Long vacunaId);
    List<DetalleVacunacion> findByFechaAplicacionBetween(LocalDate fechaInicio, LocalDate fechaFin);
    List<DetalleVacunacion> findByProximaDosisLessThanEqual(LocalDate fecha);
    List<DetalleVacunacion> findByCarnetVacunacionMascotaId(Long mascotaId);
}