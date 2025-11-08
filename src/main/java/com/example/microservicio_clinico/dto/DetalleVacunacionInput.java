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
public class DetalleVacunacionInput {
    
    @NotNull(message = "La fecha de vacunación es obligatoria")
    private LocalDate fechavacunacion;
    
    private LocalDate proximavacunacion;
    
    @NotNull(message = "El ID del carnet de vacunación es obligatorio")
    @Positive(message = "El ID del carnet de vacunación debe ser un número positivo")
    private Integer carnetVacunacionId;
    
    @NotNull(message = "El ID de la vacuna es obligatorio")
    @Positive(message = "El ID de la vacuna debe ser un número positivo")
    private Integer vacunaId;
}
