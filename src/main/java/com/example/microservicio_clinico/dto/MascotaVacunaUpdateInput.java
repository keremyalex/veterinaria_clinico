package com.example.microservicio_clinico.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MascotaVacunaUpdateInput {
    private String id;
    private String fechaAplicacion; // Formato "yyyy-MM-dd HH:mm"
    private String fechaProximaDosis; // Formato "yyyy-MM-dd HH:mm"
    private String veterinario;
    private String observaciones;
    private String lote;
    private String mascotaId;
    private String vacunaId;
}