package com.example.microservicio_clinico.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiagnosticoOutput {
    
    private Integer id;
    private String descripcion;
    private String observaciones;
    private String fecharegistro;
    private CitaOutput cita;
    private List<TratamientoOutput> tratamientos;
}
