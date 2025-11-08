package com.example.microservicio_clinico.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FiltroFechaInput {
    
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
}
