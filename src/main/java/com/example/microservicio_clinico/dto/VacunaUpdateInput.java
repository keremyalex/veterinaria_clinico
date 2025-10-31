package com.example.microservicio_clinico.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VacunaUpdateInput {
    private String id;
    private String nombre;
    private String descripcion;
    private Integer duracionMeses;
    private String laboratorio;
    private Integer edadMinimaDias;
}