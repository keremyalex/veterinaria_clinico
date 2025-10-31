package com.example.microservicio_clinico.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "tratamientos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tratamiento {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 500)
    private String descripcion;
    
    @Column(name = "fecha_inicio", nullable = false)
    private LocalDateTime fechaInicio;
    
    @Column(name = "fecha_fin")
    private LocalDateTime fechaFin;
    
    @Column(length = 1000)
    private String instrucciones;
    
    @Column(length = 50)
    private String estado; // "ACTIVO", "COMPLETADO", "SUSPENDIDO"
    
    // Relación ManyToOne con Diagnostico (Un diagnóstico puede tener muchos tratamientos)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "diagnostico_id", nullable = false)
    private Diagnostico diagnostico;
}