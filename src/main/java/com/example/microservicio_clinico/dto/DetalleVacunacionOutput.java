package com.example.microservicio_clinico.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetalleVacunacionOutput {
    
    private Integer id;
    private LocalDate fechavacunacion;
    private LocalDate proximavacunacion;
    private CarnetVacunacionOutput carnetVacunacion;
    private VacunaOutput vacuna;
}
