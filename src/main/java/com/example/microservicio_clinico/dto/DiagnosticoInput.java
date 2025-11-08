package com.example.microservicio_clinico.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiagnosticoInput {
    
    @NotBlank(message = "La descripción es obligatoria")
    @Size(max = 1000, message = "La descripción no puede exceder 1000 caracteres")
    private String descripcion;
    
    @NotBlank(message = "Las observaciones son obligatorias")
    @Size(max = 2000, message = "Las observaciones no pueden exceder 2000 caracteres")
    private String observaciones;
    
    @NotBlank(message = "La fecha de registro es obligatoria")
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}(T\\d{2}:\\d{2}:\\d{2})?", 
             message = "La fecha debe tener formato YYYY-MM-DD o YYYY-MM-DDTHH:mm:ss")
    private String fecharegistro;
    
    @NotNull(message = "El ID de la cita es obligatorio")
    @Positive(message = "El ID de la cita debe ser un número positivo")
    private Integer citaId;
}
