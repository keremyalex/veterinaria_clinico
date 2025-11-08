package com.example.microservicio_clinico.dto;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

@Data
public class TratamientoInputDTO {
    
    @NotBlank(message = "El medicamento es obligatorio")
    @Size(max = 200, message = "El medicamento no puede exceder 200 caracteres")
    private String medicamento;
    
    @NotBlank(message = "La dosis es obligatoria")
    @Size(max = 100, message = "La dosis no puede exceder 100 caracteres")
    private String dosis;
    
    @NotNull(message = "La fecha de inicio es obligatoria")
    private LocalDate fechaInicio;
    
    private LocalDate fechaFin;
    
    @Size(max = 500, message = "Las observaciones no pueden exceder 500 caracteres")
    private String observaciones;
    
    @NotNull(message = "El ID del diagnóstico es obligatorio")
    private Long diagnosticoId;
}