package com.example.microservicio_clinico.dto;

import lombok.Data;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

@Data
public class CarnetVacunacionInputDTO {
    
    private LocalDate fechaCreacion;
    
    @Size(max = 500, message = "Las observaciones no pueden exceder 500 caracteres")
    private String observaciones;
    
    @NotNull(message = "El ID de la mascota es obligatorio")
    private Long mascotaId;
}