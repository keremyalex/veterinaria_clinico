package com.example.microservicio_clinico.repository;

import com.example.microservicio_clinico.entity.CarnetVacunacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface CarnetVacunacionRepository extends JpaRepository<CarnetVacunacion, Long> {
    
    List<CarnetVacunacion> findByMascotaId(Long mascotaId);
    List<CarnetVacunacion> findByFechaCreacionBetween(LocalDate fechaInicio, LocalDate fechaFin);
    List<CarnetVacunacion> findByMascotaClienteId(Long clienteId);
}