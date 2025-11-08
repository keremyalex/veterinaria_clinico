package com.example.microservicio_clinico.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiagnosticoUpdateInput {
    
    @NotNull(message = "El ID es obligatorio")
    @Positive(message = "El ID debe ser un número positivo")
    private Integer id;
    
    private String descripcion;
    private String observaciones;
    private LocalDateTime fecharegistro;
    private Integer citaId;
}
