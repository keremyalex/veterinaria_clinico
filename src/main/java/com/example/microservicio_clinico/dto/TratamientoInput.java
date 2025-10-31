package com.example.microservicio_clinico.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TratamientoInput {
    private String descripcion;
    private String fechaInicio; // Formato "yyyy-MM-dd HH:mm"
    private String fechaFin;    // Formato "yyyy-MM-dd HH:mm" (opcional)
    private String instrucciones;
    private String estado;      // "ACTIVO", "COMPLETADO", "SUSPENDIDO"
    private String diagnosticoId;
}