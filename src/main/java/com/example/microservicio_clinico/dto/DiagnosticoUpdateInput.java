package com.example.microservicio_clinico.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiagnosticoUpdateInput {
    
    @NotNull(message = "El ID es obligatorio")
    @Positive(message = "El ID debe ser un número positivo")
    private Integer id;
    
    private String descripcion;
    private String observaciones;
    
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}(T\\d{2}:\\d{2}:\\d{2})?", 
             message = "La fecha debe tener formato YYYY-MM-DD o YYYY-MM-DDTHH:mm:ss")
    private String fecharegistro;
    private Integer citaId;
}
