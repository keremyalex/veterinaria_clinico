package com.example.microservicio_clinico.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DoctorOutput {
    
    private Integer id;
    private String nombre;
    private String apellido;
    private String ci;
    private String telefono;
    private String email;
    private String fotoUrl;
}
