package com.example.microservicio_clinico.dto;

import lombok.Data;
import jakarta.validation.constraints.Size;

@Data
public class DiagnosticoUpdateDTO {
    
    @Size(max = 200, message = "La enfermedad no puede exceder 200 caracteres")
    private String enfermedad;
    
    private String descripcion;
    
    private String sintomas;
    
    private String medicamentosRecetados;
}