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
public class DetalleVacunacionInput {
    
    @NotNull(message = "La fecha de vacunación es obligatoria")
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "La fecha debe tener formato yyyy-MM-dd")
    private String fechavacunacion;
    
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "La fecha debe tener formato yyyy-MM-dd")
    private String proximavacunacion;
    
    @NotNull(message = "El ID del carnet de vacunación es obligatorio")
    @Positive(message = "El ID del carnet de vacunación debe ser un número positivo")
    private Integer carnetVacunacionId;
    
    @NotNull(message = "El ID de la vacuna es obligatorio")
    @Positive(message = "El ID de la vacuna debe ser un número positivo")
    private Integer vacunaId;
}
