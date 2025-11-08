package com.example.microservicio_clinico.dto;

import lombok.Data;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

@Data
public class CarnetVacunacionUpdateDTO {
    
    private LocalDate fechaCreacion;
    
    @Size(max = 500, message = "Las observaciones no pueden exceder 500 caracteres")
    private String observaciones;
}