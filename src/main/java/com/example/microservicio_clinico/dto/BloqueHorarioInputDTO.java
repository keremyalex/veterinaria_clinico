package com.example.microservicio_clinico.dto;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalTime;

@Data
public class BloqueHorarioInputDTO {
    
    @NotBlank(message = "El día es obligatorio")
    private String dia;
    
    @NotNull(message = "La hora de inicio es obligatoria")
    private LocalTime horaInicio;
    
    @NotNull(message = "La hora de fin es obligatoria")
    private LocalTime horaFin;
    
    @NotNull(message = "El ID del doctor es obligatorio")
    private Long doctorId;
}