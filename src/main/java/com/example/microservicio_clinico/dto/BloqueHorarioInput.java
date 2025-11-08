package com.example.microservicio_clinico.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BloqueHorarioInput {
    
    @NotNull(message = "El día de la semana es obligatorio")
    @Min(value = 1, message = "El día de la semana debe estar entre 1 (Lunes) y 7 (Domingo)")
    @Max(value = 7, message = "El día de la semana debe estar entre 1 (Lunes) y 7 (Domingo)")
    private Integer diasemana;
    
    @NotNull(message = "La hora inicial es obligatoria")
    private LocalTime horainicio;
    
    @NotNull(message = "La hora final es obligatoria")
    private LocalTime horafinal;
    
    @NotNull(message = "El estado activo es obligatorio")
    @Min(value = 0, message = "El estado activo debe ser 0 (inactivo) o 1 (activo)")
    @Max(value = 1, message = "El estado activo debe ser 0 (inactivo) o 1 (activo)")
    private Integer activo;
}
