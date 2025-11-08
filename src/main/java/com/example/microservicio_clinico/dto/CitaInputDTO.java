package com.example.microservicio_clinico.dto;

import lombok.Data;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
public class CitaInputDTO {
    
    @NotNull(message = "La fecha y hora de la cita es obligatoria")
    private LocalDateTime fechaHora;
    
    @Size(max = 500, message = "El motivo no puede exceder 500 caracteres")
    private String motivo;
    
    @Size(max = 50, message = "El estado no puede exceder 50 caracteres")
    private String estado;
    
    @NotNull(message = "El ID del cliente es obligatorio")
    private Long clienteId;
    
    @NotNull(message = "El ID de la mascota es obligatorio")
    private Long mascotaId;
    
    @NotNull(message = "El ID del doctor es obligatorio")
    private Long doctorId;
}