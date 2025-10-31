package com.example.microservicio_clinico.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HorarioUpdateInput {
    private String id;
    private String dia;
    private String horaInicio; // Formato "HH:mm"
    private String horaFin;    // Formato "HH:mm"
}