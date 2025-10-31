package com.example.microservicio_clinico.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "vacunas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Vacuna {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 100)
    private String nombre;
    
    @Column(length = 500)
    private String descripcion;
    
    @Column(name = "duracion_meses")
    private Integer duracionMeses; // Duración de inmunidad en meses
    
    @Column(length = 50)
    private String laboratorio;
    
    @Column(name = "edad_minima_dias")
    private Integer edadMinimaDias; // Edad mínima para aplicar la vacuna
}