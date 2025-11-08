package com.example.microservicio_clinico.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BloqueHorarioInput {
    
    @NotNull(message = "El día de la semana es obligatorio")
    @Min(value = 1, message = "El día de la semana debe estar entre 1 (Lunes) y 7 (Domingo)")
    @Max(value = 7, message = "El día de la semana debe estar entre 1 (Lunes) y 7 (Domingo)")
    private Integer diasemana;
    
    @NotBlank(message = "La hora inicial es obligatoria")
    @Pattern(regexp = "^([01]?[0-9]|2[0-3]):[0-5][0-9]$", message = "Formato de hora inicial inválido (HH:MM)")
    private String horainicio;
    
    @NotBlank(message = "La hora final es obligatoria")
    @Pattern(regexp = "^([01]?[0-9]|2[0-3]):[0-5][0-9]$", message = "Formato de hora final inválido (HH:MM)")
    private String horafinal;
    
    @NotNull(message = "El estado activo es obligatorio")
    @Min(value = 0, message = "El estado activo debe ser 0 (inactivo) o 1 (activo)")
    @Max(value = 1, message = "El estado activo debe ser 0 (inactivo) o 1 (activo)")
    private Integer activo;
}
