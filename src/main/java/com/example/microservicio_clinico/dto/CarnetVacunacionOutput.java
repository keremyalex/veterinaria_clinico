package com.example.microservicio_clinico.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarnetVacunacionOutput {
    
    private Integer id;
    private LocalDateTime fechaemision;
    private MascotaOutput mascota;
    private List<DetalleVacunacionOutput> detallesVacunacion;
}
