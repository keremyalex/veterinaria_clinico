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
public class CarnetVacunacionInput {
    
    @NotNull(message = "La fecha de emisión es obligatoria")
    private LocalDateTime fechaemision;
    
    @NotNull(message = "El ID de la mascota es obligatorio")
    @Positive(message = "El ID de la mascota debe ser un número positivo")
    private Integer mascotaId;
}
