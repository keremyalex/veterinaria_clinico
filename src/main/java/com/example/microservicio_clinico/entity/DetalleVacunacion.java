package com.example.microservicio_clinico.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "detalle_vacunacion")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetalleVacunacion {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "fechavacunacion", nullable = false)
    private LocalDate fechavacunacion;
    
    @Column(name = "proximavacunacion")
    private LocalDate proximavacunacion;
    
    // Relación muchos a uno con CarnetVacunacion
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "carnet_vacunacion_id", nullable = false)
    private CarnetVacunacion carnetVacunacion;
    
    // Relación muchos a uno con Vacuna
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vacuna_id", nullable = false)
    private Vacuna vacuna;
}