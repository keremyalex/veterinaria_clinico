package com.example.microservicio_clinico.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EspecieInput {
    
    @NotBlank(message = "La descripción es obligatoria")
    @Size(max = 100, message = "La descripción no puede exceder 100 caracteres")
    private String descripcion;
}
