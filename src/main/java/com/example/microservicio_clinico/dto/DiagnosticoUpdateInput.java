package com.example.microservicio_clinico.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiagnosticoUpdateInput {
    private String id;
    private String descripcion;
    private String observaciones;
    private String mascotaId;
}