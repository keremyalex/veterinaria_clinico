package com.example.microservicio_clinico.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetalleVacunacionOutput {
    
    private Integer id;
    private String fechavacunacion;
    private String proximavacunacion;
    private CarnetVacunacionOutput carnetVacunacion;
    private VacunaOutput vacuna;
}
