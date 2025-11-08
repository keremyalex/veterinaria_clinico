package com.example.microservicio_clinico.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MascotaOutput {
    
    private Integer id;
    private String nombre;
    private Character sexo;
    private String raza;
    private String fotoUrl;
    private LocalDate fechanacimiento;
    private ClienteOutput cliente;
    private EspecieOutput especie;
    private CarnetVacunacionOutput carnetVacunacion;
}
