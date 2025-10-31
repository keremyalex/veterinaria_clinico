package com.example.microservicio_clinico.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteInput {
    private String nombre;
    private String apellidos;
    private String email;
    private String telefono;
    private String fechaNacimiento; // Formato: "YYYY-MM-DD"
}