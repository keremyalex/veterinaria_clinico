package com.example.microservicio_clinico.dto;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Min;

@Data
public class VacunaInputDTO {
    
    @NotBlank(message = "El nombre de la vacuna es obligatorio")
    @Size(max = 100, message = "El nombre no puede exceder 100 caracteres")
    private String nombre;
    
    private String descripcion;
    
    @Size(max = 200, message = "La enfermedad que previene no puede exceder 200 caracteres")
    private String enfermedadPreviene;
    
    @Min(value = 1, message = "La duración de inmunidad debe ser mayor a 0")
    private Integer duracionInmunidad;
    
    @Min(value = 0, message = "La edad mínima no puede ser negativa")
    private Integer edadMinima;
}