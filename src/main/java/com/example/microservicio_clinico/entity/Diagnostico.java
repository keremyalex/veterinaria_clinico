package com.example.microservicio_clinico.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "diagnosticos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Diagnostico {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 500)
    private String descripcion;
    
    @Column(name = "fecha_diagnostico", nullable = false)
    private LocalDateTime fechaDiagnostico;
    
    @Column(length = 1000)
    private String observaciones;
    
    // Relación ManyToOne con Mascota (Una mascota puede tener muchos diagnósticos)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "mascota_id", nullable = false)
    private Mascota mascota;
}