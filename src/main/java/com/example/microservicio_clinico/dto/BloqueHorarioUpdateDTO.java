package com.example.microservicio_clinico.dto;

import lombok.Data;
import java.time.LocalTime;

@Data
public class BloqueHorarioUpdateDTO {
    
    private String dia;
    
    private LocalTime horaInicio;
    
    private LocalTime horaFin;
    
    private Long doctorId;
}