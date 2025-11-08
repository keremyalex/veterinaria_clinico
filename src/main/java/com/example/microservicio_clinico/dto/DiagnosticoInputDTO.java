package com.example.microservicio_clinico.dto;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Data
public class DiagnosticoInputDTO {
    
    @NotBlank(message = "La enfermedad es obligatoria")
    @Size(max = 200, message = "La enfermedad no puede exceder 200 caracteres")
    private String enfermedad;
    
    private String descripcion;
    
    private String sintomas;
    
    private String medicamentosRecetados;
    
    @NotNull(message = "El ID de la cita es obligatorio")
    private Long citaId;
}