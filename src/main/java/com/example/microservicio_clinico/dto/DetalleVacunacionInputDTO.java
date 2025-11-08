package com.example.microservicio_clinico.dto;

import lombok.Data;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Min;
import java.time.LocalDate;

@Data
public class DetalleVacunacionInputDTO {
    
    @NotNull(message = "La fecha de aplicación es obligatoria")
    private LocalDate fechaAplicacion;
    
    private LocalDate fechaProximaDosis;
    
    @Min(value = 1, message = "El número de dosis debe ser mayor a 0")
    private Integer numeroDosis;
    
    @Size(max = 100, message = "El lote no puede exceder 100 caracteres")
    private String lote;
    
    @Size(max = 200, message = "El veterinario aplicador no puede exceder 200 caracteres")
    private String veterinarioAplicador;
    
    @Size(max = 500, message = "Las observaciones no pueden exceder 500 caracteres")
    private String observaciones;
    
    @NotNull(message = "El ID del carnet de vacunación es obligatorio")
    private Long carnetVacunacionId;
    
    @NotNull(message = "El ID de la vacuna es obligatorio")
    private Long vacunaId;
}