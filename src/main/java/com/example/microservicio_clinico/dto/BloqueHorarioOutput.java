package com.example.microservicio_clinico.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BloqueHorarioOutput {
    
    private Integer id;
    private Integer diasemana;
    private String diasemanaNombre; // Lunes, Martes, etc.
    private String horainicio;
    private String horafinal;
    private Integer activo;
}
