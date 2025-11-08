package com.example.microservicio_clinico.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteOutput {
    
    private Integer id;
    private String nombre;
    private String apellido;
    private String ci;
    private String telefono;
    private String fotourl;
    private List<MascotaOutput> mascotas;
}
