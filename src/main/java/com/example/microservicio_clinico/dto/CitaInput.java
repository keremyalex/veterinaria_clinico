package com.example.microservicio_clinico.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CitaInput {
    private String motivo;
    private String fechaProgramada; // Formato "yyyy-MM-dd HH:mm"
    private String clienteId;
    private String horarioId;
    private String mascotaId;
}