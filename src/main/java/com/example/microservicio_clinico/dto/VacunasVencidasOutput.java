package com.example.microservicio_clinico.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VacunasVencidasOutput {
    
    private Integer mascotaId;
    private String mascotaNombre;
    private String clienteNombre;
    private String clienteTelefono;
    private List<VacunaVencida> vacunasVencidas;
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class VacunaVencida {
        private String vacunaNombre;
        private LocalDate fechaVencimiento;
        private Integer diasVencidos;
    }
}
