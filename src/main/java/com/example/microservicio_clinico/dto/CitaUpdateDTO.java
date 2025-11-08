package com.example.microservicio_clinico.dto;

import lombok.Data;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
public class CitaUpdateDTO {
    
    private LocalDateTime fechaHora;
    
    @Size(max = 500, message = "El motivo no puede exceder 500 caracteres")
    private String motivo;
    
    @Size(max = 50, message = "El estado no puede exceder 50 caracteres")
    private String estado;
    
    private Long clienteId;
    
    private Long mascotaId;
    
    private Long doctorId;
}