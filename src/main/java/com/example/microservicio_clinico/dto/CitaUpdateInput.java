package com.example.microservicio_clinico.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CitaUpdateInput {
    
    @NotNull(message = "El ID es obligatorio")
    @Positive(message = "El ID debe ser un número positivo")
    private Integer id;
    
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}(\\.\\d{3})?Z?$", 
             message = "Formato de fecha de creación inválido. Use ISO 8601: YYYY-MM-DDTHH:mm:ss[.sss][Z]")
    private String fechacreacion;
    
    private String motivo;
    
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}(\\.\\d{3})?Z?$", 
             message = "Formato de fecha de reserva inválido. Use ISO 8601: YYYY-MM-DDTHH:mm:ss[.sss][Z]")
    private String fechareserva;
    
    private Integer estado;
    private Integer doctorId;
    private Integer mascotaId;
    private Integer bloqueHorarioId;
}
