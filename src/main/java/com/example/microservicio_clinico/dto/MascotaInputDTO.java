package com.example.microservicio_clinico.dto;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Data
public class MascotaInputDTO {
    
    @NotBlank(message = "El nombre de la mascota es obligatorio")
    @Size(max = 100, message = "El nombre no puede exceder 100 caracteres")
    private String nombre;
    
    @Size(max = 50, message = "La raza no puede exceder 50 caracteres")
    private String raza;
    
    @Size(max = 50, message = "El color no puede exceder 50 caracteres")
    private String color;
    
    @Size(max = 500, message = "La foto CUF no puede exceder 500 caracteres")
    private String fotocuf;
    
    @Size(max = 500, message = "Las observaciones no pueden exceder 500 caracteres")
    private String observaciones;
    
    @NotNull(message = "El ID del cliente es obligatorio")
    private Long clienteId;
}