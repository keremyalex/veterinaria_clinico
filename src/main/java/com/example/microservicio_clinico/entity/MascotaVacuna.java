package com.example.microservicio_clinico.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "mascota_vacunas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MascotaVacuna {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "fecha_aplicacion", nullable = false)
    private LocalDateTime fechaAplicacion;
    
    @Column(name = "fecha_proxima_dosis")
    private LocalDateTime fechaProximaDosis;
    
    @Column(length = 100)
    private String veterinario; // Nombre del veterinario que aplicó la vacuna
    
    @Column(length = 500)
    private String observaciones;
    
    @Column(length = 50)
    private String lote; // Lote de la vacuna
    
    // Relación ManyToOne con Mascota
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "mascota_id", nullable = false)
    private Mascota mascota;
    
    // Relación ManyToOne con Vacuna
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "vacuna_id", nullable = false)
    private Vacuna vacuna;
}