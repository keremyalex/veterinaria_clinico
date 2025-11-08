package com.example.microservicio_clinico.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BloqueHorarioOutput {
    
    private Integer id;
    private Integer diasemana;
    private String diasemanaNombre; // Lunes, Martes, etc.
    private LocalTime horainicio;
    private LocalTime horafinal;
    private Integer activo;
}
