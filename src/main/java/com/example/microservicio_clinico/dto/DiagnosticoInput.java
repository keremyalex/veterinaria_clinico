package com.example.microservicio_clinico.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiagnosticoInput {
    private String descripcion;
    private String observaciones;
    private String mascotaId;
}