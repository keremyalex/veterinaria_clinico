package com.example.microservicio_clinico.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DoctorUpdateInput {
    
    @NotNull(message = "El ID es obligatorio")
    @Positive(message = "El ID debe ser un número positivo")
    private Integer id;
    
    private String nombre;
    private String apellido;
    private String ci;
    private String telefono;
    private String email;
    private String fotoUrl;
}
