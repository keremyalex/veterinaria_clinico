package com.example.microservicio_clinico.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CitaInput {
    
    @NotNull(message = "La fecha de creación es obligatoria")
    private LocalDateTime fechacreacion;
    
    @NotBlank(message = "El motivo es obligatorio")
    @Size(max = 500, message = "El motivo no puede exceder 500 caracteres")
    private String motivo;
    
    @NotNull(message = "La fecha de reserva es obligatoria")
    private LocalDateTime fechareserva;
    
    @NotNull(message = "El estado es obligatorio")
    private Integer estado;
    
    @NotNull(message = "El ID del doctor es obligatorio")
    @Positive(message = "El ID del doctor debe ser un número positivo")
    private Integer doctorId;
    
    @NotNull(message = "El ID de la mascota es obligatorio")
    @Positive(message = "El ID de la mascota debe ser un número positivo")
    private Integer mascotaId;
    
    @Positive(message = "El ID del bloque horario debe ser un número positivo")
    private Integer bloqueHorarioId;
}
