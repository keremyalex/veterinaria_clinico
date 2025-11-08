package com.example.microservicio_clinico.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CitaOutput {
    
    private Integer id;
    private String fechacreacion;
    private String motivo;
    private String fechareserva;
    private Integer estado;
    private String estadoNombre; // Programada, Completada, Cancelada, etc.
    private DoctorOutput doctor;
    private MascotaOutput mascota;
    private BloqueHorarioOutput bloqueHorario;
    private List<DiagnosticoOutput> diagnosticos;
}
