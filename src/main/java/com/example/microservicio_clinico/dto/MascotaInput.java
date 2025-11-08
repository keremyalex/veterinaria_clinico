package com.example.microservicio_clinico.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MascotaInput {
    
    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "El nombre no puede exceder 100 caracteres")
    private String nombre;
    
    @NotNull(message = "El sexo es obligatorio")
    private Character sexo;
    
    @NotBlank(message = "La raza es obligatoria")
    @Size(max = 100, message = "La raza no puede exceder 100 caracteres")
    private String raza;
    
    @Size(max = 255, message = "La URL de la foto no puede exceder 255 caracteres")
    private String fotoUrl;
    
    @NotNull(message = "La fecha de nacimiento es obligatoria")
    private LocalDate fechanacimiento;
    
    @NotNull(message = "El ID del cliente es obligatorio")
    @Positive(message = "El ID del cliente debe ser un número positivo")
    private Integer clienteId;
    
    @NotNull(message = "El ID de la especie es obligatorio")
    @Positive(message = "El ID de la especie debe ser un número positivo")
    private Integer especieId;
}
