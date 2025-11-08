package com.example.microservicio_clinico.dto;

import lombok.Data;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Min;
import java.time.LocalDate;

@Data
public class DetalleVacunacionUpdateDTO {
    
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
    
    private Long vacunaId;
}