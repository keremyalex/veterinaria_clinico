package com.example.microservicio_clinico.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MascotaUpdateInput {
    
    @NotNull(message = "El ID es obligatorio")
    @Positive(message = "El ID debe ser un número positivo")
    private Integer id;
    
    private String nombre;
    @Size(max = 1, message = "El sexo debe ser un solo carácter")
    private String sexo;
    private String raza;
    private String fotourl;
    
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "Formato de fecha de nacimiento inválido. Use YYYY-MM-DD")
    private String fechanacimiento;
    
    private Integer clienteId;
    private Integer especieId;
}
