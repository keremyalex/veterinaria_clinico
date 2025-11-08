package com.example.microservicio_clinico.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MascotaInput {
    
    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "El nombre no puede exceder 100 caracteres")
    private String nombre;
    
    @NotBlank(message = "El sexo es obligatorio")
    @Size(max = 1, message = "El sexo debe ser un solo carácter")
    private String sexo;
    
    @NotBlank(message = "La raza es obligatoria")
    @Size(max = 100, message = "La raza no puede exceder 100 caracteres")
    private String raza;
    
    @Size(max = 255, message = "La URL de la foto no puede exceder 255 caracteres")
    private String fotourl;
    
    @NotBlank(message = "La fecha de nacimiento es obligatoria")
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "Formato de fecha de nacimiento inválido. Use YYYY-MM-DD")
    private String fechanacimiento;
    
    @NotNull(message = "El ID del cliente es obligatorio")
    @Positive(message = "El ID del cliente debe ser un número positivo")
    private Integer clienteId;
    
    @NotNull(message = "El ID de la especie es obligatorio")
    @Positive(message = "El ID de la especie debe ser un número positivo")
    private Integer especieId;
}
