package com.example.microservicio_clinico.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MascotaUpdateInput {
    
    @NotNull(message = "El ID es obligatorio")
    @Positive(message = "El ID debe ser un número positivo")
    private Integer id;
    
    private String nombre;
    private Character sexo;
    private String raza;
    private String fotoUrl;
    private LocalDate fechanacimiento;
    private Integer clienteId;
    private Integer especieId;
}
