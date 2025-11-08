package com.example.microservicio_clinico.dto;

import lombok.Data;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

@Data
public class TratamientoUpdateDTO {
    
    @Size(max = 200, message = "El medicamento no puede exceder 200 caracteres")
    private String medicamento;
    
    @Size(max = 100, message = "La dosis no puede exceder 100 caracteres")
    private String dosis;
    
    private LocalDate fechaInicio;
    
    private LocalDate fechaFin;
    
    @Size(max = 500, message = "Las observaciones no pueden exceder 500 caracteres")
    private String observaciones;
}