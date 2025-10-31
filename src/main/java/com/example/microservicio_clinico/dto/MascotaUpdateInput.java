package com.example.microservicio_clinico.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MascotaUpdateInput {
    private String id;
    private String nombre;
    private String sexo; // 'M' o 'F'
    private String raza;
    private String fechaNacimiento; // Formato: "YYYY-MM-DD"
    private Double peso;
    private String clienteId;
    private String especieId;
}