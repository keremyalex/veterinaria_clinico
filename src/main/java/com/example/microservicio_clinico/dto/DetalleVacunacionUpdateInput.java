package com.example.microservicio_clinico.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetalleVacunacionUpdateInput {
    
    @NotNull(message = "El ID es obligatorio")
    @Positive(message = "El ID debe ser un número positivo")
    private Integer id;
    
    private LocalDate fechavacunacion;
    private LocalDate proximavacunacion;
    private Integer carnetVacunacionId;
    private Integer vacunaId;
}
