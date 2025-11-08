package com.example.microservicio_clinico.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CitaOutput {
    
    private Integer id;
    private LocalDateTime fechasesion;
    private String motivo;
    private LocalDateTime fechareserva;
    private Integer estado;
    private String estadoNombre; // Programada, Completada, Cancelada, etc.
    private DoctorOutput doctor;
    private MascotaOutput mascota;
    private List<DiagnosticoOutput> diagnosticos;
}
